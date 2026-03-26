package org.foomaa.jvchat.uicomponents.mainchat;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import org.foomaa.jvchat.structobjects.MessageStructObject;

@Component
@Profile("users")
public class RectMessageMainChatUIFactory {
    private final ObjectProvider<RectMessageMainChatUI> rectMessageObjectProvider;

    RectMessageMainChatUIFactory(ObjectProvider<RectMessageMainChatUI> rectMessageObjectProvider) {
        this.rectMessageObjectProvider = rectMessageObjectProvider;
    }

    public RectMessageMainChatUI create(MessageStructObject messageStructObject) {
        RectMessageMainChatUI rectChat = rectMessageObjectProvider.getObject();
        rectChat.setMessageObject(messageStructObject);
        return rectChat;
    }
}
