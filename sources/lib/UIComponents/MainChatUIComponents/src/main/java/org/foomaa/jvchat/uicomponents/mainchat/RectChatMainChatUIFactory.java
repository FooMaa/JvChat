package org.foomaa.jvchat.uicomponents.mainchat;

import org.foomaa.jvchat.structobjects.ChatStructObject;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile("users")
public class RectChatMainChatUIFactory {
    private final ObjectProvider<RectChatMainChatUI> rectChatObjectProvider;

    RectChatMainChatUIFactory(ObjectProvider<RectChatMainChatUI> rectChatObjectProvider) {
        this.rectChatObjectProvider = rectChatObjectProvider;
    }

    public RectChatMainChatUI create(ChatStructObject chatStructObject) {
        RectChatMainChatUI rectChat = rectChatObjectProvider.getObject();
        rectChat.setChatObject(chatStructObject);
        return rectChat;
    }
}
