package org.foomaa.jvchat.uicomponents.mainchat;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class JvMainChatScrollPanelMessages extends JScrollBar {
    private static JvMainChatScrollPanelMessages instance;
    private ArrayList<Integer> idMessages;

    JvMainChatScrollPanelMessages() {
        makePanel();
    }

    public static JvMainChatScrollPanelMessages getInstance() {
        if (instance == null) {
            instance = new JvMainChatScrollPanelMessages();
        }
        return instance;
    }

    private void makePanel() {
        Box box = Box.createVerticalBox();

        box.add(new JLabel("AAA"));
        box.add(new JLabel("AAA"));
        box.add(new JLabel("AAA"));

        JScrollPane scrollPane = new JScrollPane(box);

        setLayout(new BorderLayout());
        add(scrollPane);
    }
}
