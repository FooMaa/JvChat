package org.foomaa.jvchat.uicomponents.mainchat;

import java.util.Objects;

import org.springframework.beans.factory.ObjectProvider;

import lombok.Builder;

public class SendButtonMainChatUIFactory {
    // DI ↓
    private final ObjectProvider<SendButtonMainChatUI> sendButtonObjectProvider;

    @Builder
    SendButtonMainChatUIFactory(ObjectProvider<SendButtonMainChatUI> sendButtonObjectProvider) {
        this.sendButtonObjectProvider =
                Objects.requireNonNull(sendButtonObjectProvider, "sendButtonObjectProvider is mandatory");
    }

    public SendButtonMainChatUI create(String text) {
        SendButtonMainChatUI sendButtonMainChatUI = sendButtonObjectProvider.getObject();
        sendButtonMainChatUI.setText(text);
        return sendButtonMainChatUI;
    }
}
