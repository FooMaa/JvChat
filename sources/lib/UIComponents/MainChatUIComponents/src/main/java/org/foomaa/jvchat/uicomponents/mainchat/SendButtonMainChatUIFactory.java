package org.foomaa.jvchat.uicomponents.mainchat;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile("users")
public class SendButtonMainChatUIFactory {
    private final ObjectProvider<SendButtonMainChatUI> sendButtonObjectProvider;

    SendButtonMainChatUIFactory(ObjectProvider<SendButtonMainChatUI> sendButtonObjectProvider) {
        this.sendButtonObjectProvider = sendButtonObjectProvider;
    }

    public SendButtonMainChatUI create(String text) {
        SendButtonMainChatUI sendButtonMainChatUI = sendButtonObjectProvider.getObject();
        sendButtonMainChatUI.setText(text);
        return sendButtonMainChatUI;
    }
}
