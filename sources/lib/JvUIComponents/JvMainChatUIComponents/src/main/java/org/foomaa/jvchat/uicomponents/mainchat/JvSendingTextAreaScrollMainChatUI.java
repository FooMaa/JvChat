package org.foomaa.jvchat.uicomponents.mainchat;

import javax.swing.*;


public class JvSendingTextAreaScrollMainChatUI extends JScrollPane {
    private final JTextArea textArea;

    JvSendingTextAreaScrollMainChatUI() {
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
