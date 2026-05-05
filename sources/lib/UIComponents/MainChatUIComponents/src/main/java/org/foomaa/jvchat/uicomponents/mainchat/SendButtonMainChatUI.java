package org.foomaa.jvchat.uicomponents.mainchat;

import javax.swing.*;

import lombok.Builder;

public class SendButtonMainChatUI extends JButton {
    @Builder
    SendButtonMainChatUI() {
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
