package org.foomaa.jvchat.uicomponents.mainchat;

import java.util.Objects;

import org.springframework.beans.factory.ObjectProvider;

import lombok.Builder;

import org.foomaa.jvchat.structobjects.MessageStructObject;

public class RectMessageMainChatUIFactory {
    // DI ↓
    private final ObjectProvider<RectMessageMainChatUI> rectMessageObjectProvider;

    @Builder
    RectMessageMainChatUIFactory(ObjectProvider<RectMessageMainChatUI> rectMessageObjectProvider) {
        this.rectMessageObjectProvider =
                Objects.requireNonNull(rectMessageObjectProvider, "rectMessageObjectProvider is mandatory");
    }

    public RectMessageMainChatUI create(MessageStructObject messageStructObject) {
        RectMessageMainChatUI rectChat = rectMessageObjectProvider.getObject();
        rectChat.setMessageObject(messageStructObject);
        return rectChat;
    }
}
