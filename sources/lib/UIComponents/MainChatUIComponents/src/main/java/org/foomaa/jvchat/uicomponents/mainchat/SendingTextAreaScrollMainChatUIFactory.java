package org.foomaa.jvchat.uicomponents.mainchat;

import java.util.Objects;

import org.springframework.beans.factory.ObjectProvider;

import lombok.Builder;

public class SendingTextAreaScrollMainChatUIFactory {
    private final ObjectProvider<SendingTextAreaScrollMainChatUI> sendingTextAreaScrollObjectProvider;

    @Builder
    SendingTextAreaScrollMainChatUIFactory(
            ObjectProvider<SendingTextAreaScrollMainChatUI> sendingTextAreaScrollObjectProvider) {
        this.sendingTextAreaScrollObjectProvider = Objects.requireNonNull(sendingTextAreaScrollObjectProvider,
                "sendingTextAreaScrollObjectProvider is mandatory");
    }

    public SendingTextAreaScrollMainChatUI create() {
        return sendingTextAreaScrollObjectProvider.getObject();
    }
}
