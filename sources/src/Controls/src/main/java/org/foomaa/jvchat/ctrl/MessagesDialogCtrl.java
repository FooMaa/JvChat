package org.foomaa.jvchat.ctrl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.foomaa.jvchat.globaldefines.MainChatsGlobalDefines;
import org.foomaa.jvchat.logger.Log;
import org.foomaa.jvchat.messages.DefinesMessages;
import org.foomaa.jvchat.models.ChatsModel;
import org.foomaa.jvchat.models.GetterModels;
import org.foomaa.jvchat.models.MessagesModel;
import org.foomaa.jvchat.settings.GetterSettings;
import org.foomaa.jvchat.structobjects.ChatStructObject;
import org.foomaa.jvchat.structobjects.GetterStructObjects;
import org.foomaa.jvchat.structobjects.MessageStructObject;
import org.foomaa.jvchat.tools.JvGetterTools;


public class MessagesDialogCtrl {
    private final MessagesModel messagesModel;
    private final ChatsModel chatsModel;

    MessagesDialogCtrl() {
        messagesModel = GetterModels.getInstance().getBeanMessagesModel();
        chatsModel = GetterModels.getInstance().getBeanChatsModel();
    }

    public void setCurrentActiveChatUuid(UUID newUuidChat) {
        chatsModel.setCurrentActiveChatUuid(newUuidChat);
    }

    public UUID getCurrentActiveChatUuid() {
        return chatsModel.getCurrentActiveChatUuid();
    }

    public ChatStructObject findChatByUuid(UUID targetUuid) {
        List<ChatStructObject> chatsList = chatsModel.getAllChatsObjects();

        for (ChatStructObject chat : chatsList) {
            UUID chatUuid = chat.getUuid();
            if (chatUuid.equals(targetUuid)) {
                return chat;
            }
        }
        return null;
    }

    public MessageStructObject createAndSendMessage(String text) {
        if (getCurrentActiveChatUuid() == null) {
            Log.write(Log.TypeLog.Error, "Не выбран диалог, отправка не выполнена");
            return null;
        }

        UUID uuidSender = GetterSettings.getInstance().getBeanUsersInfoSettings().getUuid();
        ChatStructObject chat = findChatByUuid(getCurrentActiveChatUuid());
        UUID uuidReceiver = chat.getUserChat().getUuid();
        UUID uuidMessage = UUID.randomUUID();
        LocalDateTime timestamp = LocalDateTime.now();
        MainChatsGlobalDefines.TypeStatusMessage status = MainChatsGlobalDefines.TypeStatusMessage.Sent;

        MessageStructObject messageStructObject = messagesModel.createNewMessage(
                uuidSender, uuidReceiver, uuidMessage, status, text, timestamp);

        sendNewMessage(messageStructObject);
        setLastMessageInChatCtrl(messageStructObject);

        return messageStructObject;
    }

    public void createMessagesObjects(List<Map<DefinesMessages.TypeData, Object>> msgInfo) {
        messagesModel.clearModel();
        int normalizeCountTimestamp = 3;

        for (Map<DefinesMessages.TypeData, Object> msg : msgInfo) {
            UUID uuidUserSender = (UUID) msg.get(DefinesMessages.TypeData.UuidUserSender);
            UUID uuidUserReceiver = (UUID) msg.get(DefinesMessages.TypeData.UuidUserReceiver);
            UUID uuidMessage = (UUID) msg.get(DefinesMessages.TypeData.UuidMessage);
            String text = (String) msg.get(DefinesMessages.TypeData.TextMessage);
            MainChatsGlobalDefines.TypeStatusMessage statusMessage = MainChatsGlobalDefines.TypeStatusMessage
                    .getTypeStatusMessage((Integer) msg.get(DefinesMessages.TypeData.StatusMessage));
            LocalDateTime timestampMessage = JvGetterTools.getInstance()
                    .getBeanFormatTools().stringToLocalDateTime(
                            (String) msg.get(DefinesMessages.TypeData.Timestamp), normalizeCountTimestamp);

            if (timestampMessage == null) {
                Log.write(Log.TypeLog.Warn, "It was not possible to normalize the date and time to the required format.");
            }

            messagesModel.createNewMessage(
                    uuidUserSender,
                    uuidUserReceiver,
                    uuidMessage,
                    statusMessage,
                    text,
                    timestampMessage);
        }
    }

    private void setLastMessageInChatCtrl(MessageStructObject message) {
        GetterControls.getInstance().getBeanChatsCtrl().changeLastMessage(message);
    }

    private void sendNewMessage(MessageStructObject message) {
        String timestampNewMessage = JvGetterTools.getInstance().getBeanFormatTools()
                .localDateTimeToString(message.getTimestamp());

        GetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(
                DefinesMessages.TypeMessage.TextMessageSendUserToServer,
                message.getUuidUserSender(),
                message.getUuidUserReceiver(),
                message.getUuid(),
                message.getText(),
                timestampNewMessage);
    }

    public void setDirtyStatusToMessage(Map<UUID, MainChatsGlobalDefines.TypeStatusMessage> mapStatusesMessages) {
        for (UUID uuid : mapStatusesMessages.keySet()) {
            MessageStructObject message = findMessage(uuid);
            if (message != null) {
                message.setStatusMessage(mapStatusesMessages.get(uuid));
            }
        }
        GetterControls.getInstance().getBeanMessagesDefinesCtrl()
                .setTextMessagesChangingStatusFromServerFlag(MessagesDefinesCtrl.TypeFlags.TRUE);
    }

    public MessageStructObject findMessage(UUID uuid) {
        List<MessageStructObject> listMessages = messagesModel.getAllMessages();

        for (MessageStructObject message : listMessages) {
            if (message != null && message.getUuid().equals(uuid)) {
                return message;
            }
        }
        return null;
    }

    public boolean isCurrentUserSender(MessageStructObject messageStructObject) {
        UUID currentUuid = GetterSettings.getInstance().getBeanUsersInfoSettings().getUuid();
        return Objects.equals(currentUuid, messageStructObject.getUuidUserSender());
    }

    public void redirectMessageToOnlineUser(UUID uuidUserSender,
                                            UUID uuidUserReceiver,
                                            UUID uuidMessage,
                                            MainChatsGlobalDefines.TypeStatusMessage statusMessage,
                                            String text,
                                            LocalDateTime timestamp) {
        MessageStructObject messageStructObject = createMessageByData(
                uuidUserSender, uuidUserReceiver, uuidMessage, statusMessage, text, timestamp);
        OnlineServersCtrl onlineServersCtrl =  GetterControls.getInstance().getBeanOnlineServersCtrl();

        boolean isUserOnline = onlineServersCtrl.isUuidUserInListCheckerOnline(messageStructObject.getUuidUserReceiver());
        if (!isUserOnline) {
            return;
        }

        Runnable runnableUserCtrl = onlineServersCtrl.getRunnableByUuidUser(messageStructObject.getUuidUserReceiver());
        if (runnableUserCtrl == null) {
            Log.write(Log.TypeLog.Error, "Here runnableUserCtrl turned out to be null.");
            return;
        }

        String timestampMessage = JvGetterTools.getInstance().getBeanFormatTools()
                .localDateTimeToString(messageStructObject.getTimestamp());

        GetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(
                DefinesMessages.TypeMessage.TextMessageRedirectServerToUser,
                messageStructObject.getUuidUserSender(),
                messageStructObject.getUuidUserReceiver(),
                messageStructObject.getUuid(),
                messageStructObject.getText(),
                timestampMessage,
                runnableUserCtrl);
    }

    private MessageStructObject createMessageByData(UUID uuidUserSender,
                                                    UUID uuidUserReceiver,
                                                    UUID uuidMessage,
                                                    MainChatsGlobalDefines.TypeStatusMessage statusMessage,
                                                    String text,
                                                    LocalDateTime timestamp) {
        MessageStructObject messageObj = GetterStructObjects.getInstance().getBeanMessageStructObject();

        messageObj.setUuidUserSender(uuidUserSender);
        messageObj.setUuidUserReceiver(uuidUserReceiver);
        messageObj.setText(text);
        messageObj.setStatusMessage(statusMessage);
        messageObj.setUuid(uuidMessage);
        messageObj.setTimestamp(timestamp);

        return messageObj;
    }

    public String getTimeFormattedMessage(LocalDateTime timestamp) {
        if (timestamp == null) {
            Log.write(Log.TypeLog.Error, "Here the timestamp turned out to be null.");
            return "";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return timestamp.format(formatter);
    }

    public void addRedirectMessageToModel(UUID uuidUserSender,
                                          UUID uuidUserReceiver,
                                          UUID uuidMessage,
                                          MainChatsGlobalDefines.TypeStatusMessage statusMessage,
                                          String text,
                                          LocalDateTime timestamp) {
        MessageStructObject messageStructObject = createMessageByData(
                uuidUserSender, uuidUserReceiver, uuidMessage, statusMessage, text, timestamp);
        messagesModel.addMessageStructObject(messageStructObject);
    }

    public List<MessageStructObject> getAllSortedMessages() {
        return messagesModel.getSortedMessagesObjects();
    }

    public UUID findUuidChatByUuidUser(UUID uuidSender) {
        List<ChatStructObject> chatsList = chatsModel.getAllChatsObjects();

        for (ChatStructObject chat : chatsList) {
            UUID userUuid = chat.getUserChat().getUuid();
            if (userUuid.equals(uuidSender)) {
                return chat.getUuid();
            }
        }
        return null;
    }
}
