package org.foomaa.jvchat.uicomponents.mainchat;

import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;


@Component
@Scope("prototype")
@Profile("users")
public class SendButtonMainChatUI extends JButton {
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
