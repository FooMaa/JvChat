package org.foomaa.jvchat.structobjects;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatStructObject extends BaseStructObject {
    private MessageStructObject lastMessage;
    private UserStructObject userChat;

    @Builder
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
}
