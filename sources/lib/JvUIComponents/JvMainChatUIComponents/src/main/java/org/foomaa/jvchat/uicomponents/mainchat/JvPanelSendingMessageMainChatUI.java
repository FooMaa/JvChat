package org.foomaa.jvchat.uicomponents.mainchat;

import javax.swing.*;
import java.awt.*;


public class JvPanelSendingMessageMainChatUI extends JPanel {
    private static JvPanelSendingMessageMainChatUI instance;
    private final JvSendingTextAreaScrollMainChatUI sendingTextAreaScroll;
    private final JButton sendButton;

    JvPanelSendingMessageMainChatUI() {
        sendingTextAreaScroll = JvGetterMainChatUIComponents.getInstance().getBeanSendingTextAreaScrollMainChatUI();
        sendButton = JvGetterMainChatUIComponents.getInstance().getBeanSendButtonMainChatUI("Отправить");

        settingPanel();
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
}
