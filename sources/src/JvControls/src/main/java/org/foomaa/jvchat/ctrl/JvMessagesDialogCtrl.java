package org.foomaa.jvchat.ctrl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.foomaa.jvchat.globaldefines.JvDbGlobalDefines;
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

        String loginSender = JvGetterSettings.getInstance().getBeanUsersInfoSettings().getLogin();
        JvChatStructObject chat = findChatByUuid(getCurrentActiveChatUuid());
        String loginReceiver = chat.getUserChat().getLogin();
        UUID uuid = UUID.randomUUID();
        LocalDateTime timestamp = LocalDateTime.now();
        JvMainChatsGlobalDefines.TypeStatusMessage status = JvMainChatsGlobalDefines.TypeStatusMessage.Sent;

        JvMessageStructObject messageStructObject = messagesModel.createNewMessage(
                loginSender, loginReceiver, text, status, uuid, timestamp);

        sendNewMessage(messageStructObject);
        setLastMessageInChatCtrl(messageStructObject);

        return messageStructObject;
    }

    public void createMessagesObjects(List<Map<JvDbGlobalDefines.LineKeys, String>> msgInfo) {
        messagesModel.clearModel();
        int normalizeCountTimestamp = 3;

        for (Map<JvDbGlobalDefines.LineKeys, String> msg : msgInfo) {
            String lastMessageLoginSender = msg.get(JvDbGlobalDefines.LineKeys.Sender);
            String lastMessageLoginReceiver = msg.get(JvDbGlobalDefines.LineKeys.Receiver);
            String textMessage = msg.get(JvDbGlobalDefines.LineKeys.TextMessage);
            UUID uuidMessage = UUID.fromString(msg.get(JvDbGlobalDefines.LineKeys.UuidMessage));
            JvMainChatsGlobalDefines.TypeStatusMessage statusMessage = JvGetterTools.getInstance().getBeanFormatTools()
                    .statusMessageStringToInt(msg.get(JvDbGlobalDefines.LineKeys.StatusMessage));
            LocalDateTime timestampMessage = JvGetterTools.getInstance()
                    .getBeanFormatTools().stringToLocalDateTime(msg.get(JvDbGlobalDefines.LineKeys.DateTimeMessage), normalizeCountTimestamp);

            if (timestampMessage == null) {
                JvLog.write(JvLog.TypeLog.Warn, "Не получилось нормализовать дату и время к нужному формату");
            }

            messagesModel.createNewMessage(
                    lastMessageLoginSender,
                    lastMessageLoginReceiver,
                    textMessage,
                    statusMessage,
                    uuidMessage,
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
                message.getLoginSender(),
                message.getLoginReceiver(),
                message.getUuid().toString(),
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
        String currentLogin = JvGetterSettings.getInstance().getBeanUsersInfoSettings().getLogin();
        return Objects.equals(currentLogin, messageStructObject.getLoginSender());
    }

    public void redirectMessageToOnlineUser(String loginSender,
                                            String loginReceiver,
                                            String text,
                                            JvMainChatsGlobalDefines.TypeStatusMessage statusMessage,
                                            UUID uuid,
                                            LocalDateTime timestamp) {
        JvMessageStructObject messageStructObject = createMessageByData(loginSender, loginReceiver, text, statusMessage, uuid, timestamp);
        JvOnlineServersCtrl onlineServersCtrl =  JvGetterControls.getInstance().getBeanOnlineServersCtrl();

        boolean isUserOnline = onlineServersCtrl.isLoginInListCheckerOnline(messageStructObject.getLoginReceiver());
        if (!isUserOnline) {
            return;
        }

        Runnable runnableUserCtrl = onlineServersCtrl.getRunnableByLogin(messageStructObject.getLoginReceiver());
        if (runnableUserCtrl == null) {
            JvLog.write(JvLog.TypeLog.Error, "Здесь runnableUserCtrl оказался null");
            return;
        }

        String timestampMessage = JvGetterTools.getInstance().getBeanFormatTools()
                .localDateTimeToString(messageStructObject.getTimestamp());

        JvGetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(
                JvDefinesMessages.TypeMessage.TextMessageRedirectServerToUser,
                messageStructObject.getLoginSender(),
                messageStructObject.getLoginReceiver(),
                messageStructObject.getUuid().toString(),
                messageStructObject.getText(),
                timestampMessage,
                runnableUserCtrl);
    }

    private JvMessageStructObject createMessageByData(String loginSender,
                                                     String loginReceiver,
                                                     String text,
                                                     JvMainChatsGlobalDefines.TypeStatusMessage statusMessage,
                                                     UUID uuid,
                                                     LocalDateTime timestamp) {
        JvMessageStructObject messageObj = JvGetterStructObjects.getInstance().getBeanMessageStructObject();

        messageObj.setLoginSender(loginSender);
        messageObj.setLoginReceiver(loginReceiver);
        messageObj.setText(text);
        messageObj.setStatusMessage(statusMessage);
        messageObj.setUuid(uuid);
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

    public void addRedirectMessageToModel(String loginSender,
                                   String loginReceiver,
                                   String text,
                                   JvMainChatsGlobalDefines.TypeStatusMessage statusMessage,
                                   UUID uuid,
                                   LocalDateTime timestamp) {
        JvMessageStructObject messageStructObject = createMessageByData(
                loginSender, loginReceiver, text, statusMessage, uuid, timestamp);
        messagesModel.addMessageStructObject(messageStructObject);
    }

    public List<JvMessageStructObject> getAllSortedMessages() {
        return messagesModel.getSortedMessagesObjects();
    }
}
