package org.foomaa.jvchat.structobjects;


public class JvChatStructObject extends JvBaseStructObject {
    private JvMessageStructObject lastMessage;
    private JvUserStructObject userChat;

    JvChatStructObject() {
        lastMessage = null;
        userChat = null;
        commitProperties();
    }

    public void setLastMessage(JvMessageStructObject newLastMessage) {
        if (lastMessage != newLastMessage) {
            lastMessage = newLastMessage;
            commitProperties();
        }
    }

    public void setUserChat(JvUserStructObject newUserChat) {
        if (userChat != newUserChat) {
            userChat = newUserChat;
            commitProperties();
        }
    }

    public JvMessageStructObject getLastMessage() {
        return lastMessage;
    }

    public JvUserStructObject getUserChat() {
        return userChat;
    }
}
