package org.foomaa.jvchat.uicomponents.mainchat;

import lombok.Builder;
import org.springframework.beans.factory.ObjectProvider;

import org.foomaa.jvchat.structobjects.ChatStructObject;

import java.util.Objects;

public class RectChatMainChatUIFactory {
    private final ObjectProvider<RectChatMainChatUI> rectChatObjectProvider;

    @Builder
    RectChatMainChatUIFactory(ObjectProvider<RectChatMainChatUI> rectChatObjectProvider) {
        this.rectChatObjectProvider = Objects.requireNonNull(rectChatObjectProvider,
                "rectChatObjectProvider is mandatory");
    }

    public RectChatMainChatUI create(ChatStructObject chatStructObject) {
        RectChatMainChatUI rectChat = rectChatObjectProvider.getObject();
        rectChat.setChatObject(chatStructObject);
        return rectChat;
    }
}
