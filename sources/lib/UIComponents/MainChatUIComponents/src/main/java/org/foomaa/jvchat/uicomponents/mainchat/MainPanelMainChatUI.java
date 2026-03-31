package org.foomaa.jvchat.uicomponents.mainchat;

import java.awt.*;
import java.util.Objects;

import javax.swing.*;

import lombok.Builder;

public class MainPanelMainChatUI extends JPanel {
    private final FindTextFieldMainChatUI findTextField;
    private final ScrollPanelChatsMainChatUI scrollPanelChats;
    private final ScrollPanelMessagesMainChatUI scrollPanelMessages;
    private final PanelSendingMessageMainChatUI panelSendingMessage;

    @Builder
    MainPanelMainChatUI(ScrollPanelChatsMainChatUI scrollPanelChats, ScrollPanelMessagesMainChatUI scrollPanelMessages,
            PanelSendingMessageMainChatUIFactory panelSendingMessageFactory,
            FindTextFieldMainChatUIFactory findTextFieldMainChatUIFactory) {
        Objects.requireNonNull(panelSendingMessageFactory, "panelSendingMessageFactory is mandatory");
        Objects.requireNonNull(findTextFieldMainChatUIFactory, "findTextFieldMainChatUIFactory is mandatory");

        this.scrollPanelChats = Objects.requireNonNull(scrollPanelChats, "scrollPanelChats is mandatory");
        this.scrollPanelMessages = Objects.requireNonNull(scrollPanelMessages, "scrollPanelMessages is mandatory");

        findTextField = findTextFieldMainChatUIFactory.create("Find by login");
        panelSendingMessage = panelSendingMessageFactory.create();

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
