package org.foomaa.jvchat.uicomponents.mainchat;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("users")
public class SendingTextAreaScrollMainChatUIFactory {
    private final ObjectProvider<SendingTextAreaScrollMainChatUI> sendingTextAreaScrollObjectProvider;

    SendingTextAreaScrollMainChatUIFactory(
            ObjectProvider<SendingTextAreaScrollMainChatUI> sendingTextAreaScrollObjectProvider) {
        this.sendingTextAreaScrollObjectProvider = sendingTextAreaScrollObjectProvider;
    }

    public SendingTextAreaScrollMainChatUI create() {
        return sendingTextAreaScrollObjectProvider.getObject();
    }
}
