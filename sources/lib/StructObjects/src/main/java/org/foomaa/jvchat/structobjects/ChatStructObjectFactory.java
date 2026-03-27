package org.foomaa.jvchat.structobjects;

import java.util.UUID;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

@Component
public class ChatStructObjectFactory {
    private final ObjectProvider<ChatStructObject> chatStructObjectObjectProvider;

    ChatStructObjectFactory(ObjectProvider<ChatStructObject> chatStructObjectObjectProvider) {
        this.chatStructObjectObjectProvider = chatStructObjectObjectProvider;
    }

    public ChatStructObject create() {
        return chatStructObjectObjectProvider.getObject();
    }

    public ChatStructObject create(UserStructObject userChat, MessageStructObject lastMessage,
            UUID uuidChat) {
        ChatStructObject chatStructObject = chatStructObjectObjectProvider.getObject();

        chatStructObject.setUserChat(userChat);
        chatStructObject.setLastMessage(lastMessage);
        chatStructObject.setUuid(uuidChat);

        return chatStructObject;
    }
}
