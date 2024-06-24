package org.foomaa.jvchat.uicomponents.mainchat;

import javax.swing.*;

import java.util.ArrayList;

public class JvMainChatScrollPaneChats extends JScrollPane {
    private static JvMainChatScrollPaneChats instance;
    private ArrayList<Integer> idChats;

    private JvMainChatScrollPaneChats() {
        setVerticalScrollBar(new JScrollBar());
        setSize(100,100);
        setVisible(true);
        setEnabled(true);
        setFocusable(true);
    }

    public static JvMainChatScrollPaneChats getInstance() {
        if (instance == null) {
            instance = new JvMainChatScrollPaneChats();
        }
        return instance;
    }
}
