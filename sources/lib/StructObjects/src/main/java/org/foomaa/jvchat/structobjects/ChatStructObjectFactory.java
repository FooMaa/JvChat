package org.foomaa.jvchat.structobjects;

import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.ObjectProvider;

import lombok.Builder;

public class ChatStructObjectFactory {
    private final ObjectProvider<ChatStructObject> chatStructObjectObjectProvider;

    @Builder
    ChatStructObjectFactory(ObjectProvider<ChatStructObject> chatStructObjectObjectProvider) {
        this.chatStructObjectObjectProvider = Objects.requireNonNull(chatStructObjectObjectProvider,
                "chatStructObjectObjectProvider is mandatory");
    }

    public ChatStructObject create() {
        return chatStructObjectObjectProvider.getObject();
    }

    public ChatStructObject create(UserStructObject userChat, MessageStructObject lastMessage, UUID uuidChat) {
        ChatStructObject chatStructObject = chatStructObjectObjectProvider.getObject();

        chatStructObject.setUserChat(userChat);
        chatStructObject.setLastMessage(lastMessage);
        chatStructObject.setUuid(uuidChat);

        return chatStructObject;
    }
}
