package org.foomaa.jvchat.uicomponents.mainchat;

import lombok.Builder;
import org.springframework.beans.factory.ObjectProvider;

import java.util.Objects;

public class SendButtonMainChatUIFactory {
    private final ObjectProvider<SendButtonMainChatUI> sendButtonObjectProvider;

    @Builder
    SendButtonMainChatUIFactory(ObjectProvider<SendButtonMainChatUI> sendButtonObjectProvider) {
        this.sendButtonObjectProvider = Objects.requireNonNull(sendButtonObjectProvider,
                "sendButtonObjectProvider is mandatory");
    }

    public SendButtonMainChatUI create(String text) {
        SendButtonMainChatUI sendButtonMainChatUI = sendButtonObjectProvider.getObject();
        sendButtonMainChatUI.setText(text);
        return sendButtonMainChatUI;
    }
}
