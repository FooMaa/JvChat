package org.foomaa.jvchat.uicomponents.auth;

import javax.swing.*;

public class JvAuthButton extends JButton {
    public JvAuthButton(String text) {
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