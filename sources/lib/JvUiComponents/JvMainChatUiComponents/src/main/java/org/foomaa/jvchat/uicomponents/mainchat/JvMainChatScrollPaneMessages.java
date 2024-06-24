package org.foomaa.jvchat.uicomponents.mainchat;

import javax.swing.*;
import java.util.ArrayList;

public class JvMainChatScrollPaneMessages extends JScrollBar {
    private static JvMainChatScrollPaneMessages instance;
    private ArrayList<Integer> idMessages;

    JvMainChatScrollPaneMessages() {

    }

    public static JvMainChatScrollPaneMessages getInstance() {
        if (instance == null) {
            instance = new JvMainChatScrollPaneMessages();
        }
        return instance;
    }
}
