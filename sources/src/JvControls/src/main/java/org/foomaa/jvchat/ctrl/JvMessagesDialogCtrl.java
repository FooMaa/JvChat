package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.globaldefines.JvMainChatsGlobalDefines;
import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.messages.JvDefinesMessages;
import org.foomaa.jvchat.settings.JvGetterSettings;
import org.foomaa.jvchat.tools.JvGetterTools;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class JvMessagesDialogCtrl {
    private static JvMessagesDialogCtrl instance;
    private final List<Message> currentListMessages;
    private String currentActiveLoginUI; // loginReceiver

    private static class Message {
        public String loginSender;
        public String loginReceiver;
        public JvMainChatsGlobalDefines.TypeStatusMessage status;
        public UUID uuid;
        public String text;
        public LocalDateTime timestamp;
    }

    private JvMessagesDialogCtrl() {
        currentListMessages = new ArrayList<>();
        currentActiveLoginUI = "";
    }

    static JvMessagesDialogCtrl getInstance() {
        if (instance == null) {
            instance = new JvMessagesDialogCtrl();
        }
        return instance;
    }

    public void setCurrentActiveLoginUI(String newCurrentActiveLoginUI) {
        if (!Objects.equals(currentActiveLoginUI, newCurrentActiveLoginUI)) {
            currentActiveLoginUI = newCurrentActiveLoginUI;
        }
    }

    public void createAndSendMessage(String text) {
        if (Objects.equals(currentActiveLoginUI, "")) {
            JvLog.write(JvLog.TypeLog.Error, "Не выбран диалог, отправка не выполнена");
            return;
        }

        Message message = new Message();

        message.loginSender = JvGetterSettings.getInstance().getBeanUsersInfoSettings().getLogin();
        message.loginReceiver = currentActiveLoginUI;
        message.uuid = UUID.randomUUID();
        message.text = text;
        message.timestamp = LocalDateTime.now();

        currentListMessages.add(message);
        sendNewMessage(message);
    }

    private void sendNewMessage(Message message) {
        String timestampNewMessage = JvGetterTools.getInstance().getBeanFormattedTools()
                .formattedTimestampToDB(message.timestamp);

        JvGetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(
                JvDefinesMessages.TypeMessage.TextMessageSendUserToServer,
                message.loginSender,
                message.loginReceiver,
                message.uuid,
                message.text,
                timestampNewMessage);
    }
}
