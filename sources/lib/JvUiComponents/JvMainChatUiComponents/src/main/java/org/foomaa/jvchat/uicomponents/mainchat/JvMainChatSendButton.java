package org.foomaa.jvchat.uicomponents.mainchat;

import javax.swing.*;


public class JvMainChatSendButton extends JButton {
    JvMainChatSendButton(String text) {
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
