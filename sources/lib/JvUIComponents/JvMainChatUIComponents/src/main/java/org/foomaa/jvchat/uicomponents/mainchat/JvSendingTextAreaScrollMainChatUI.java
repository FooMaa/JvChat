package org.foomaa.jvchat.uicomponents.mainchat;

import javax.swing.*;


public class JvSendingTextAreaScrollMainChatUI extends JScrollPane {
    private static JvSendingTextAreaScrollMainChatUI instance;

    JvSendingTextAreaScrollMainChatUI() {
        JTextArea textArea = new JTextArea();
        setViewportView(textArea);
    }

    public static JvSendingTextAreaScrollMainChatUI getInstance() {
        if (instance == null) {
            instance = new JvSendingTextAreaScrollMainChatUI();
        }
        return instance;
    }
}
