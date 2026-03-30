package org.foomaa.jvchat.uicomponents.mainchat;

import lombok.Builder;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Objects;

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
