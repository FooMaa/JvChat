package org.foomaa.jvchat.uicomponents.mainchat;

import org.foomaa.jvchat.settings.JvDisplaySettings;
import org.foomaa.jvchat.settings.JvGetterSettings;

import javax.swing.*;
import java.awt.*;


public class JvMainFrameMainChatUI extends JFrame {
    private static JvMainFrameMainChatUI instance;
    private final JvScrollPanelChatsMainChatUI scrollPanelChats;
    private final JvScrollPanelMessagesMainChatUI scrollPanelMessages;
    private final JvSendingTextAreaScrollMainChatUI sendingTextAreaScroll;
    private final JButton sendButton;

    private JvMainFrameMainChatUI() {
        super("MainChatWindow");

        scrollPanelChats = JvGetterMainChatUIComponents.getInstance().getBeanScrollPanelChatsMainChatUI();
        scrollPanelMessages = JvGetterMainChatUIComponents.getInstance().getBeanScrollPanelMessagesMainChatUI();

        sendingTextAreaScroll = JvGetterMainChatUIComponents.getInstance().getBeanSendingTextAreaScrollMainChatUI();
        sendButton = JvGetterMainChatUIComponents.getInstance().getBeanSendButtonMainChatUI("Отправить");

        addGeneralSettingsToWidget();
        makeFrameSetting();
        addListenerToElements();
    }

    public static JvMainFrameMainChatUI getInstance() {
        if (instance == null) {
            instance = new JvMainFrameMainChatUI();
        }
        return instance;
    }

    private void addGeneralSettingsToWidget() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Главное окно");

        setSize(JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.585,
                        JvDisplaySettings.TypeOfDisplayBorder.WIDTH),
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.5625,
                        JvDisplaySettings.TypeOfDisplayBorder.HEIGHT));

        Dimension minSiseDimension = new Dimension(JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.4,
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
        gbc.weighty = 1.0;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = gridxNum;
        panel.add(scrollPanelChats, gbc);
        gridxNum++;

        gbc.weightx = 1.25;
        gbc.weighty = 2.5;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = gridxNum;
        panel.add(scrollPanelMessages, gbc);

        gbc.weightx = 1.25;
        gbc.weighty = 0.25;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = gridxNum;
        panel.add(createPanelSending(), gbc);

        getContentPane().add(panel);
    }

    public void openWindow() {
        setVisible(true);
    }

    private void addListenerToElements() {
    }

    private void closeWindow() {
        //dispose();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(false);
    }

    private JPanel createPanelSending() {
        JPanel rowPanelSending = new JPanel();
        rowPanelSending.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        int gridxNum = 0;

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = gridxNum;
        rowPanelSending.add(sendingTextAreaScroll, gbc);
        gridxNum++;

        gbc.weightx = 0.25;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = gridxNum;
        rowPanelSending.add(sendButton, gbc);

        return rowPanelSending;
    }
}
