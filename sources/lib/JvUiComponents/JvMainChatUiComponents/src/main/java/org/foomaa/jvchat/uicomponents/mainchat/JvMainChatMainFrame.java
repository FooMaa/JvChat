package org.foomaa.jvchat.uicomponents.mainchat;

import org.foomaa.jvchat.settings.JvDisplaySettings;
import org.foomaa.jvchat.settings.JvGetterSettings;

import javax.swing.*;
import java.awt.*;


public class JvMainChatMainFrame extends JFrame {
    private static JvMainChatMainFrame instance;
    private final JvMainChatScrollPanelChats scrollPanelChats;
    private final JvMainChatScrollPanelMessages scrollPanelMessages;

    private JvMainChatMainFrame() {
        super("MainChatWindow");

        scrollPanelChats = JvGetterMainChatUiComponents.getInstance().getBeanMainChatScrollPanelChats();
        scrollPanelMessages = JvGetterMainChatUiComponents.getInstance().getBeanMainChatScrollPanelMessages();

        addGeneralSettingsToWidget();
        makeFrameSetting();
        addListenerToElements();
    }

    public static JvMainChatMainFrame getInstance() {
        if (instance == null) {
            instance = new JvMainChatMainFrame();
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
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = gridxNum;
        panel.add(scrollPanelChats, gbc);
        gridxNum++;

        gbc.weightx = 1.25;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = gridxNum;
        panel.add(scrollPanelMessages, gbc);

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
}
