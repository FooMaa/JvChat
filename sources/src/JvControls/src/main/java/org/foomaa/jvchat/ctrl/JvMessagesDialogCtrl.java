package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.globaldefines.JvMainChatsGlobalDefines;
import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.messages.JvDefinesMessages;
import org.foomaa.jvchat.models.JvGetterModels;
import org.foomaa.jvchat.models.JvMessagesModel;
import org.foomaa.jvchat.settings.JvGetterSettings;
import org.foomaa.jvchat.structobjects.JvBaseStructObject;
import org.foomaa.jvchat.structobjects.JvMessageStructObject;
import org.foomaa.jvchat.tools.JvGetterTools;

import java.time.LocalDateTime;
import java.util.*;


public class JvMessagesDialogCtrl {
    private static JvMessagesDialogCtrl instance;
    private final JvMessagesModel messagesModel;

    private JvMessagesDialogCtrl() {
        messagesModel = JvGetterModels.getInstance().getBeanMessagesModel();
    }

    static JvMessagesDialogCtrl getInstance() {
        if (instance == null) {
            instance = new JvMessagesDialogCtrl();
        }
        return instance;
    }

    public void setCurrentActiveLoginUI(String newCurrentActiveLoginUI) {
        messagesModel.setCurrentActiveLoginUI(newCurrentActiveLoginUI);
    }

    public String getCurrentActiveLoginUI() {
        return messagesModel.getCurrentActiveLoginUI();
    }

    public void createAndSendMessage(String text) {
        if (Objects.equals(messagesModel.getCurrentActiveLoginUI(), "")) {
            JvLog.write(JvLog.TypeLog.Error, "Не выбран диалог, отправка не выполнена");
            return;
        }

        String loginSender = JvGetterSettings.getInstance().getBeanUsersInfoSettings().getLogin();
        String loginReceiver = messagesModel.getCurrentActiveLoginUI();
        UUID uuid = UUID.randomUUID();
        LocalDateTime timestamp = LocalDateTime.now();
        JvMainChatsGlobalDefines.TypeStatusMessage status = JvMainChatsGlobalDefines.TypeStatusMessage.Sent;

        JvMessageStructObject messageStructObject = messagesModel.createNewMessage(
                loginSender, loginReceiver, text, status, uuid, timestamp);

        sendNewMessage(messageStructObject);
        setLastMessageInChatCtrl(messageStructObject);
    }

    private void setLastMessageInChatCtrl(JvMessageStructObject message) {
        String timeFormatted = JvGetterTools.getInstance().getBeanFormattedTools()
                .formattedTimestampWithMilliSeconds(message.getTimestamp());

        JvGetterControls.getInstance().getBeanChatsCtrl().changeLastMessage(
                message.getText(),
                message.getLoginSender(),
                message.getLoginReceiver(),
                timeFormatted,
                message.getUuid().toString(),
                message.getStatusMessage());
    }

    private void sendNewMessage(JvMessageStructObject message) {
        String timestampNewMessage = JvGetterTools.getInstance().getBeanFormattedTools()
                .formattedTimestampToDB(message.getTimestamp());

        JvGetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(
                JvDefinesMessages.TypeMessage.TextMessageSendUserToServer,
                message.getLoginSender(),
                message.getLoginReceiver(),
                message.getUuid().toString(),
                message.getText(),
                timestampNewMessage);
    }

    public void setDirtyStatusToMessage(String loginSender, String loginReceiver, Map<UUID, JvMainChatsGlobalDefines.TypeStatusMessage> mapStatusesMessages) {
        for (UUID uuid : mapStatusesMessages.keySet()) {
            for (JvBaseStructObject messageBase : messagesModel.getRootObject().getChildren()) {
                JvMessageStructObject message = (JvMessageStructObject) messageBase;
                if (message.getUuid().equals(uuid) &&
                        Objects.equals(message.getLoginSender(), loginSender) &&
                        Objects.equals(message.getLoginReceiver(), loginReceiver)) {
                    message.setStatusMessage(mapStatusesMessages.get(uuid));
                    System.out.println("Set Status");
                    return;
                }
            }
        }
    }
}
