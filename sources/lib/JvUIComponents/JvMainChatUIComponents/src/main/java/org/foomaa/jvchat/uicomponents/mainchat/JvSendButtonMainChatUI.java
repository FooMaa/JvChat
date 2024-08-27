package org.foomaa.jvchat.uicomponents.mainchat;

import javax.swing.*;
import java.util.ArrayList;


public class JvSendButtonMainChatUI extends JButton {
    private static JvSendButtonMainChatUI instance;

    JvSendButtonMainChatUI(String text) {
        setText(text);
        setFocusable(false);
        addListenerToElements();
    }

    public static JvSendButtonMainChatUI getInstance(String text) {
        if (instance == null) {
            instance = new JvSendButtonMainChatUI(text);
        }
        return instance;
    }

    private void addListenerToElements() {
        addActionListener(event -> {
            setFocusable(true);
            requestFocusInWindow();
        });
    }
}
