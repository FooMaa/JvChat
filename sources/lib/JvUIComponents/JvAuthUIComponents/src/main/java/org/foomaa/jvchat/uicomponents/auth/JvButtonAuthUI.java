package org.foomaa.jvchat.uicomponents.auth;

import javax.swing.*;
import java.awt.event.KeyEvent;


public class JvButtonAuthUI extends JButton {
    JvButtonAuthUI(String text) {
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