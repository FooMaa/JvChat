package org.foomaa.jvchat.uicomponents.mainchat;

import lombok.Builder;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import org.foomaa.jvchat.structobjects.MessageStructObject;

import java.util.Objects;

public class RectMessageMainChatUIFactory {
    private final ObjectProvider<RectMessageMainChatUI> rectMessageObjectProvider;

    @Builder
    RectMessageMainChatUIFactory(ObjectProvider<RectMessageMainChatUI> rectMessageObjectProvider) {
        this.rectMessageObjectProvider = Objects.requireNonNull(rectMessageObjectProvider,
                "rectMessageObjectProvider is mandatory");
    }

    public RectMessageMainChatUI create(MessageStructObject messageStructObject) {
        RectMessageMainChatUI rectChat = rectMessageObjectProvider.getObject();
        rectChat.setMessageObject(messageStructObject);
        return rectChat;
    }
}
