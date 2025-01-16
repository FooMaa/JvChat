package org.foomaa.jvchat.ctrl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.foomaa.jvchat.globaldefines.JvMainChatsGlobalDefines;
import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.messages.JvDefinesMessages;
import org.foomaa.jvchat.models.JvChatsModel;
import org.foomaa.jvchat.models.JvGetterModels;
import org.foomaa.jvchat.models.JvMessagesModel;
import org.foomaa.jvchat.settings.JvGetterSettings;
import org.foomaa.jvchat.structobjects.JvChatStructObject;
import org.foomaa.jvchat.structobjects.JvGetterStructObjects;
import org.foomaa.jvchat.structobjects.JvMessageStructObject;
import org.foomaa.jvchat.tools.JvGetterTools;


public class JvMessagesDialogCtrl {
    private final JvMessagesModel messagesModel;
    private final JvChatsModel chatsModel;

    JvMessagesDialogCtrl() {
        messagesModel = JvGetterModels.getInstance().getBeanMessagesModel();
        chatsModel = JvGetterModels.getInstance().getBeanChatsModel();
    }

    public void setCurrentActiveChatUuid(UUID newUuidChat) {
        chatsModel.setCurrentActiveChatUuid(newUuidChat);
    }

    public UUID getCurrentActiveChatUuid() {
        return chatsModel.getCurrentActiveChatUuid();
    }

    public JvChatStructObject findChatByUuid(UUID targetUuid) {
        List<JvChatStructObject> chatsList = chatsModel.getAllChatsObjects();

        for (JvChatStructObject chat : chatsList) {
            UUID chatUuid = chat.getUuid();
            if (chatUuid.equals(targetUuid)) {
                return chat;
            }
        }
        return null;
    }

    public JvMessageStructObject createAndSendMessage(String text) {
        if (getCurrentActiveChatUuid() == null) {
            JvLog.write(JvLog.TypeLog.Error, "Не выбран диалог, отправка не выполнена");
            return null;
        }

        UUID uuidSender = JvGetterSettings.getInstance().getBeanUsersInfoSettings().getUuid();
        JvChatStructObject chat = findChatByUuid(getCurrentActiveChatUuid());
        UUID uuidReceiver = chat.getUserChat().getUuid();
        UUID uuidMessage = UUID.randomUUID();
        LocalDateTime timestamp = LocalDateTime.now();
        JvMainChatsGlobalDefines.TypeStatusMessage status = JvMainChatsGlobalDefines.TypeStatusMessage.Sent;

        JvMessageStructObject messageStructObject = messagesModel.createNewMessage(
                uuidSender, uuidReceiver, uuidMessage, status, text, timestamp);

        sendNewMessage(messageStructObject);
        setLastMessageInChatCtrl(messageStructObject);

        return messageStructObject;
    }

    public void createMessagesObjects(List<Map<JvDefinesMessages.TypeData, Object>> msgInfo) {
        messagesModel.clearModel();
        int normalizeCountTimestamp = 3;

        for (Map<JvDefinesMessages.TypeData, Object> msg : msgInfo) {
            UUID uuidUserSender = (UUID) msg.get(JvDefinesMessages.TypeData.UuidUserSender);
            UUID uuidUserReceiver = (UUID) msg.get(JvDefinesMessages.TypeData.UuidUserReceiver);
            UUID uuidMessage = (UUID) msg.get(JvDefinesMessages.TypeData.UuidMessage);
            String text = (String) msg.get(JvDefinesMessages.TypeData.TextMessage);
            JvMainChatsGlobalDefines.TypeStatusMessage statusMessage = JvMainChatsGlobalDefines.TypeStatusMessage
                    .getTypeStatusMessage((Integer) msg.get(JvDefinesMessages.TypeData.StatusMessage));
            LocalDateTime timestampMessage = JvGetterTools.getInstance()
                    .getBeanFormatTools().stringToLocalDateTime(
                            (String) msg.get(JvDefinesMessages.TypeData.Timestamp), normalizeCountTimestamp);

            if (timestampMessage == null) {
                JvLog.write(JvLog.TypeLog.Warn, "Не получилось нормализовать дату и время к нужному формату");
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

    private void setLastMessageInChatCtrl(JvMessageStructObject message) {
        JvGetterControls.getInstance().getBeanChatsCtrl().changeLastMessage(message);
    }

    private void sendNewMessage(JvMessageStructObject message) {
        String timestampNewMessage = JvGetterTools.getInstance().getBeanFormatTools()
                .localDateTimeToString(message.getTimestamp());

        JvGetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(
                JvDefinesMessages.TypeMessage.TextMessageSendUserToServer,
                message.getUuidUserSender(),
                message.getUuidUserReceiver(),
                message.getUuid(),
                message.getText(),
                timestampNewMessage);
    }

    public void setDirtyStatusToMessage(Map<UUID, JvMainChatsGlobalDefines.TypeStatusMessage> mapStatusesMessages) {
        for (UUID uuid : mapStatusesMessages.keySet()) {
            JvMessageStructObject message = findMessage(uuid);
            if (message != null) {
                message.setStatusMessage(mapStatusesMessages.get(uuid));
            }
        }
        JvGetterControls.getInstance().getBeanMessagesDefinesCtrl()
                .setTextMessagesChangingStatusFromServerFlag(JvMessagesDefinesCtrl.TypeFlags.TRUE);
    }

    public JvMessageStructObject findMessage(UUID uuid) {
        List<JvMessageStructObject> listMessages = messagesModel.getAllMessages();

        for (JvMessageStructObject message : listMessages) {
            if (message != null && message.getUuid().equals(uuid)) {
                return message;
            }
        }
        return null;
    }

    public boolean isCurrentUserSender(JvMessageStructObject messageStructObject) {
        UUID currentUuid = JvGetterSettings.getInstance().getBeanUsersInfoSettings().getUuid();
        return Objects.equals(currentUuid, messageStructObject.getUuidUserSender());
    }

    public void redirectMessageToOnlineUser(UUID uuidUserSender,
                                            UUID uuidUserReceiver,
                                            UUID uuidMessage,
                                            JvMainChatsGlobalDefines.TypeStatusMessage statusMessage,
                                            String text,
                                            LocalDateTime timestamp) {
        JvMessageStructObject messageStructObject = createMessageByData(
                uuidUserSender, uuidUserReceiver, uuidMessage, statusMessage, text, timestamp);
        JvOnlineServersCtrl onlineServersCtrl =  JvGetterControls.getInstance().getBeanOnlineServersCtrl();

        boolean isUserOnline = onlineServersCtrl.isUuidUserInListCheckerOnline(messageStructObject.getUuidUserReceiver());
        if (!isUserOnline) {
            return;
        }

        Runnable runnableUserCtrl = onlineServersCtrl.getRunnableByUuidUser(messageStructObject.getUuidUserReceiver());
        if (runnableUserCtrl == null) {
            JvLog.write(JvLog.TypeLog.Error, "Здесь runnableUserCtrl оказался null");
            return;
        }

        String timestampMessage = JvGetterTools.getInstance().getBeanFormatTools()
                .localDateTimeToString(messageStructObject.getTimestamp());

        JvGetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(
                JvDefinesMessages.TypeMessage.TextMessageRedirectServerToUser,
                messageStructObject.getUuidUserSender(),
                messageStructObject.getUuidUserReceiver(),
                messageStructObject.getUuid(),
                messageStructObject.getText(),
                timestampMessage,
                runnableUserCtrl);
    }

    private JvMessageStructObject createMessageByData(UUID uuidUserSender,
                                                      UUID uuidUserReceiver,
                                                      UUID uuidMessage,
                                                      JvMainChatsGlobalDefines.TypeStatusMessage statusMessage,
                                                      String text,
                                                      LocalDateTime timestamp) {
        JvMessageStructObject messageObj = JvGetterStructObjects.getInstance().getBeanMessageStructObject();

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
            JvLog.write(JvLog.TypeLog.Error, "Здесь timestamp оказался null");
            return "";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return timestamp.format(formatter);
    }

    public void addRedirectMessageToModel(UUID uuidUserSender,
                                          UUID uuidUserReceiver,
                                          UUID uuidMessage,
                                          JvMainChatsGlobalDefines.TypeStatusMessage statusMessage,
                                          String text,
                                          LocalDateTime timestamp) {
        JvMessageStructObject messageStructObject = createMessageByData(
                uuidUserSender, uuidUserReceiver, uuidMessage, statusMessage, text, timestamp);
        messagesModel.addMessageStructObject(messageStructObject);
    }

    public List<JvMessageStructObject> getAllSortedMessages() {
        return messagesModel.getSortedMessagesObjects();
    }

    public UUID findUuidChatByUuidUser(UUID uuidSender) {
        List<JvChatStructObject> chatsList = chatsModel.getAllChatsObjects();

        for (JvChatStructObject chat : chatsList) {
            UUID userUuid = chat.getUserChat().getUuid();
            if (userUuid.equals(uuidSender)) {
                return chat.getUuid();
            }
        }
        return null;
    }
}
