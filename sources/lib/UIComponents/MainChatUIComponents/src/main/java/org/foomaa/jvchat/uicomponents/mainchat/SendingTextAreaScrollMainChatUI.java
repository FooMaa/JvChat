package org.foomaa.jvchat.uicomponents.mainchat;

import javax.swing.*;

import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Profile("users")
public class SendingTextAreaScrollMainChatUI extends JScrollPane {
    private final JTextArea textArea;

    SendingTextAreaScrollMainChatUI() {
        textArea = new JTextArea();
        settingPane();
    }

    private void settingPane() {
        setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        setViewportView(textArea);
    }

    public String getText() {
        return textArea.getText();
    }

    public void clearText() {
        textArea.setText("");
    }
}
