package org.foomaa.jvchat.uicomponents.mainchat;

import javax.swing.*;


public class JvSendingTextAreaScrollMainChatUI extends JScrollPane{
    JvSendingTextAreaScrollMainChatUI() {
        JTextArea textArea = new JTextArea();
        setViewportView(textArea);
    }
}
