package org.foomaa.jvchat.uicomponents.mainchat;

import javax.swing.*;

import lombok.Builder;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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
