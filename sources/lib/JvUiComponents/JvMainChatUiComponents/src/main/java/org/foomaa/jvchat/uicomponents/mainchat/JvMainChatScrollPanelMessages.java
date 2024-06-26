package org.foomaa.jvchat.uicomponents.mainchat;

import javax.swing.*;
import java.util.ArrayList;

public class JvMainChatScrollPanelMessages extends JScrollBar {
    private static JvMainChatScrollPanelMessages instance;
    private ArrayList<Integer> idMessages;

    JvMainChatScrollPanelMessages() {

    }

    public static JvMainChatScrollPanelMessages getInstance() {
        if (instance == null) {
            instance = new JvMainChatScrollPanelMessages();
        }
        return instance;
    }
}
