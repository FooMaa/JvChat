package org.foomaa.jvchat.uicomponents.mainchat;

import org.foomaa.jvchat.ctrl.JvChatsCtrl;
import org.foomaa.jvchat.ctrl.JvGetterControls;
import org.foomaa.jvchat.globaldefines.JvMainChatsGlobalDefines;
import org.foomaa.jvchat.settings.JvGetterSettings;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;


public class JvPanelSendingMessageMainChatUI extends JPanel {
    private static JvPanelSendingMessageMainChatUI instance;
    private final JvSendingTextAreaScrollMainChatUI sendingTextAreaScroll;
    private final JButton sendButton;

    JvPanelSendingMessageMainChatUI() {
        sendingTextAreaScroll = JvGetterMainChatUIComponents.getInstance().getBeanSendingTextAreaScrollMainChatUI();
        sendButton = JvGetterMainChatUIComponents.getInstance().getBeanSendButtonMainChatUI("Отправить");

        settingPanel();
        addListenerToElements();
    }

    public static JvPanelSendingMessageMainChatUI getInstance() {
        if (instance == null) {
            instance = new JvPanelSendingMessageMainChatUI();
        }
        return instance;
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
        sendButton.addActionListener(event -> {
            processSendingMessage();
        });
    }

    private void processSendingMessage() {
        sendMessageToServer();
        updateComponentsAfterSending();
    }

    private void sendMessageToServer() {
        String text = sendingTextAreaScroll.getText();
        if (!Objects.equals(text, "")) {
            JvGetterControls.getInstance().getBeanMessagesDialogCtrl().createAndSendMessage(text);
        }
    }

    private void updateComponentsAfterSending() {
        String selectedLogin = JvGetterControls.getInstance().getBeanMessagesDialogCtrl().getCurrentActiveLoginUI();
        String senderLogin = JvGetterSettings.getInstance().getBeanUsersInfoSettings().getLogin();
        JvChatsCtrl chatsCtrl = JvGetterControls.getInstance().getBeanChatsCtrl();
        String message =  chatsCtrl.getMapLastMessagesByLogin().get(selectedLogin).getText();
        JvMainChatsGlobalDefines.TypeStatusMessage statusMessage =
                chatsCtrl.getMapLastMessagesByLogin().get(selectedLogin).getStatusMessage();
        String time = chatsCtrl.getTimeFormattedLastMessage(
                chatsCtrl.getMapLastMessagesByLogin().get(selectedLogin).getTimestamp());

        Box boxComponents = JvGetterMainChatUIComponents.getInstance().getBeanScrollPanelChatsMainChatUI().getBoxComponents();

        for (Component component : boxComponents.getComponents()) {
            JvRectChatMainChatUI rectChatMainChatUI = (JvRectChatMainChatUI) component;
            String login = rectChatMainChatUI.getNickName();
            if (Objects.equals(selectedLogin, login)) {
                rectChatMainChatUI.updateLastMessage(senderLogin, message, time, statusMessage);
            }
        }

        sendingTextAreaScroll.clearText();
    }
}
