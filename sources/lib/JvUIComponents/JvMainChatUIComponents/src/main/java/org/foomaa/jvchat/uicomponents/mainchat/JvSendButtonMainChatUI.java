package org.foomaa.jvchat.uicomponents.mainchat;

import javax.swing.*;


public class JvSendButtonMainChatUI extends JButton {
    JvSendButtonMainChatUI(String text) {
        setText(text);
        setFocusable(false);
        addListenerToElements();
    }

    private void addListenerToElements() {
        addActionListener(event -> {
            setFocusable(true);
            requestFocusInWindow();
        });
    }
}
