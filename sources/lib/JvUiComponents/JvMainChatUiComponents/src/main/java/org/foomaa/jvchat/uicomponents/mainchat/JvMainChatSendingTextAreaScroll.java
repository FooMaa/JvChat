package org.foomaa.jvchat.uicomponents.mainchat;

import javax.swing.*;


public class JvMainChatSendingTextAreaScroll extends JScrollPane{
    JvMainChatSendingTextAreaScroll() {
        JTextArea textArea = new JTextArea();
        setViewportView(textArea);
    }
}
