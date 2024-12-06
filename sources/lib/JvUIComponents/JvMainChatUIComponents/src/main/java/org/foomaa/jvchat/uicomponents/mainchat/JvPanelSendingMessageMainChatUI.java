package org.foomaa.jvchat.uicomponents.mainchat;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.UUID;

import org.foomaa.jvchat.ctrl.JvGetterControls;
import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.structobjects.JvMessageStructObject;


public class JvPanelSendingMessageMainChatUI extends JPanel {
    private final JvSendingTextAreaScrollMainChatUI sendingTextAreaScroll;
    private final JButton sendButton;

    JvPanelSendingMessageMainChatUI() {
        sendingTextAreaScroll = JvGetterMainChatUIComponents.getInstance().getBeanSendingTextAreaScrollMainChatUI();
        sendButton = JvGetterMainChatUIComponents.getInstance().getBeanSendButtonMainChatUI("Отправить");

        settingPanel();
        addListenerToElements();
    }

    private void settingPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        int gridxNum = 0;

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = gridxNum;
        add(sendingTextAreaScroll, gbc);
        gridxNum++;

        gbc.weightx = 0.25;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = gridxNum;
        add(sendButton, gbc);
    }

    private void addListenerToElements() {
        sendButton.addActionListener(event -> processSendingMessage());
    }

    private void processSendingMessage() {
        sendMessageToServer();
        updateComponentsAfterSending();
    }

    private void sendMessageToServer() {
        String text = sendingTextAreaScroll.getText();
        if (text == null) {
            JvLog.write(JvLog.TypeLog.Error, "sendingTextAreaScroll.getText() вернул null");
            return;
        }

        if (!Objects.equals(text, "")) {
            JvMessageStructObject messageObj = JvGetterControls.getInstance().getBeanMessagesDialogCtrl().createAndSendMessage(text);
            if (messageObj == null) {
                JvLog.write(JvLog.TypeLog.Error, "Не создано сообщение для отправки, не отправлено...");
                return;
            }
            JvGetterMainChatUIComponents.getInstance().getBeanScrollPanelMessagesMainChatUI().addMessage(messageObj);
        }
    }

    private void updateComponentsAfterSending() {
        UUID selectedUuid = JvGetterControls.getInstance().getBeanMessagesDialogCtrl().getCurrentActiveChatUuid();
        String selectedLogin = JvGetterControls.getInstance()
                .getBeanMessagesDialogCtrl().findChatByUuid(selectedUuid).getUserChat().getLogin();
        JvMessageStructObject message = JvGetterControls.getInstance().getBeanChatsCtrl().getMessageObjectByLoginChat(selectedLogin);

        Box boxComponents = JvGetterMainChatUIComponents.getInstance().getBeanScrollPanelChatsMainChatUI().getBoxComponents();

        for (Component component : boxComponents.getComponents()) {
            JvRectChatMainChatUI rectChatMainChatUI = (JvRectChatMainChatUI) component;
            String login = rectChatMainChatUI.getNickName();
            if (Objects.equals(selectedLogin, login)) {
                boxComponents.remove(rectChatMainChatUI);
                rectChatMainChatUI.updateLastMessage(message);
                boxComponents.add(rectChatMainChatUI, 0);
            }
        }

        sendingTextAreaScroll.clearText();
    }
}
