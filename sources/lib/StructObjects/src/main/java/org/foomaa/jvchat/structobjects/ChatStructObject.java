package org.foomaa.jvchat.structobjects;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
public class ChatStructObject extends BaseStructObject {
    private MessageStructObject lastMessage;
    private UserStructObject userChat;

    ChatStructObject() {
        lastMessage = null;
        userChat = null;
        commitProperties();
    }

    public void setLastMessage(MessageStructObject newLastMessage) {
        if (lastMessage != newLastMessage) {
            lastMessage = newLastMessage;
            commitProperties();
        }
    }

    public void setUserChat(UserStructObject newUserChat) {
        if (userChat != newUserChat) {
            userChat = newUserChat;
            commitProperties();
        }
    }

    public MessageStructObject getLastMessage() {
        return lastMessage;
    }

    public UserStructObject getUserChat() {
        return userChat;
    }
}
