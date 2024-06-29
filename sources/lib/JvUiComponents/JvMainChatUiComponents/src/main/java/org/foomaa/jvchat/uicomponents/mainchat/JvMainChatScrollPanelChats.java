package org.foomaa.jvchat.uicomponents.mainchat;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;

public class JvMainChatScrollPanelChats extends JPanel {
    private static JvMainChatScrollPanelChats instance;
    private ArrayList<Integer> idChats;

    private JvMainChatScrollPanelChats() {
        makePanel();
    }

    public static JvMainChatScrollPanelChats getInstance() {
        if (instance == null) {
            instance = new JvMainChatScrollPanelChats();
        }
        return instance;
    }

    private void makePanel() {
        Box box = Box.createVerticalBox();

        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat());
        box.add(new JLabel("AAA"));

        JScrollPane scrollPane = new JScrollPane(box);

        setLayout(new BorderLayout());
        add(scrollPane);
    }
}
