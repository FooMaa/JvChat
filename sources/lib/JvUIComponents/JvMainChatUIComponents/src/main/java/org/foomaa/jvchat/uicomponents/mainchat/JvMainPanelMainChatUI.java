package org.foomaa.jvchat.uicomponents.mainchat;

import javax.swing.*;
import java.awt.*;


public class JvMainPanelMainChatUI extends JPanel {
    private final JvFindTextFieldMainChatUI findTextField;
    private final JvScrollPanelChatsMainChatUI scrollPanelChats;
    private final JvScrollPanelMessagesMainChatUI scrollPanelMessages;
    private final JvPanelSendingMessageMainChatUI panelSendingMessage;

    JvMainPanelMainChatUI() {
        findTextField = JvGetterMainChatUIComponents.getInstance().getBeanFindTextFieldMainChatUI("Поиск по логину");
        scrollPanelChats = JvGetterMainChatUIComponents.getInstance().getBeanScrollPanelChatsMainChatUI();
        scrollPanelMessages = JvGetterMainChatUIComponents.getInstance().getBeanScrollPanelMessagesMainChatUI();
        panelSendingMessage = JvGetterMainChatUIComponents.getInstance().getBeanPanelSendingMessageMainChatUI();

        makePanelSetting();
        makePanelTransparent();
    }

    private void makePanelSetting() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        int gridxNum = 0;
        int gridyNum = 0;

        gbc.weightx = 0.3;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = gridxNum;
        gbc.gridy = gridyNum;
        add(findTextField, gbc);
        gridyNum++;

        gbc.weighty = 1.0;
        gbc.gridheight = 5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = gridyNum;
        add(scrollPanelChats, gbc);
        gridxNum++;
        gridyNum = 0;

        gbc.weightx = 0.7;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = gridxNum;
        gbc.gridy = gridyNum;
        gbc.gridheight = 2;
        add(scrollPanelMessages, gbc);
        gridyNum += 2;

        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.gridx = gridxNum;
        gbc.gridy = gridyNum;
        add(panelSendingMessage, gbc);
    }

    private void makePanelTransparent() {
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
    }
}
