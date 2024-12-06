package org.foomaa.jvchat.uicomponents.mainchat;

import javax.swing.*;
import java.awt.*;

import org.foomaa.jvchat.settings.JvDisplaySettings;
import org.foomaa.jvchat.settings.JvGetterSettings;


public class JvMainFrameMainChatUI extends JFrame {
    private final JvScrollPanelChatsMainChatUI scrollPanelChats;
    private final JvScrollPanelMessagesMainChatUI scrollPanelMessages;
    private final JvPanelSendingMessageMainChatUI panelSendingMessage;
    private final JvFindTextFieldMainChatUI findTextField;

    JvMainFrameMainChatUI() {
        super("MainChatWindow");

        scrollPanelChats = JvGetterMainChatUIComponents.getInstance().getBeanScrollPanelChatsMainChatUI();
        scrollPanelMessages = JvGetterMainChatUIComponents.getInstance().getBeanScrollPanelMessagesMainChatUI();
        panelSendingMessage = JvGetterMainChatUIComponents.getInstance().getBeanPanelSendingMessageMainChatUI();
        findTextField = JvGetterMainChatUIComponents.getInstance().getBeanFindTextFieldMainChatUI("Поиск по логину");

        addGeneralSettingsToWidget();
        makeFrameSetting();
        addListenerToElements();
    }

    private void addGeneralSettingsToWidget() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Главное окно");

        setSize(JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.585,
                        JvDisplaySettings.TypeOfDisplayBorder.WIDTH),
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.5625,
                        JvDisplaySettings.TypeOfDisplayBorder.HEIGHT));

        Dimension minSiseDimension = new Dimension(JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.43,
                JvDisplaySettings.TypeOfDisplayBorder.WIDTH), JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.28,
                JvDisplaySettings.TypeOfDisplayBorder.HEIGHT));
        setMinimumSize(minSiseDimension);

        setResizable(true);
        setLocationRelativeTo(null);
        toFront();
        setVisible(true);
        requestFocus();
    }

    private void makeFrameSetting() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        int gridxNum = 0;

        gbc.weightx = 1.0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridx = gridxNum;
        panel.add(findTextField, gbc);

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridheight = 5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = gridxNum;
        panel.add(scrollPanelChats, gbc);
        gridxNum++;

        double ratioMessagePart = 0.8;
        int sizeYMsg = (int) Math.floor(getHeight() * ratioMessagePart);
        int sizeYPanelSending = getHeight() - sizeYMsg;

        gbc.weightx = 1.25;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = gridxNum;
        gbc.ipady = sizeYMsg;
        panel.add(scrollPanelMessages, gbc);

        gbc.weightx = 1.25;
        gbc.weighty = 0;
        gbc.ipady = sizeYPanelSending;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = gridxNum;
        panel.add(panelSendingMessage, gbc);

        getContentPane().add(panel);
    }

    public void openWindow() {
        setVisible(true);
    }

    private void addListenerToElements() {
    }
}
