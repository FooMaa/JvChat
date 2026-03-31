package org.foomaa.jvchat.uicomponents.mainchat;

import javax.swing.*;

import lombok.Builder;

public class SendingTextAreaScrollMainChatUI extends JScrollPane {
    private final JTextArea textArea;

    @Builder
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
