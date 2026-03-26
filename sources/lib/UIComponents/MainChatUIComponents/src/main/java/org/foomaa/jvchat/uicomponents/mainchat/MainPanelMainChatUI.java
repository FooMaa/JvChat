package org.foomaa.jvchat.uicomponents.mainchat;

import java.awt.*;

import javax.swing.*;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("users")
public class MainPanelMainChatUI extends JPanel {
    private final FindTextFieldMainChatUI findTextField;
    private final ScrollPanelChatsMainChatUI scrollPanelChats;
    private final ScrollPanelMessagesMainChatUI scrollPanelMessages;
    private final PanelSendingMessageMainChatUI panelSendingMessage;

    MainPanelMainChatUI(ScrollPanelChatsMainChatUI scrollPanelChats, ScrollPanelMessagesMainChatUI scrollPanelMessages,
            PanelSendingMessageMainChatUIFactory panelSendingMessageFactory,
            FindTextFieldMainChatUIFactory findTextFieldMainChatUIFactory) {
        this.scrollPanelChats = scrollPanelChats;
        this.scrollPanelMessages = scrollPanelMessages;

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
