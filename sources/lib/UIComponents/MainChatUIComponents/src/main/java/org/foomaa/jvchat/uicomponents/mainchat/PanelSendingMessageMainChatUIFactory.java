package org.foomaa.jvchat.uicomponents.mainchat;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile("users")
public class PanelSendingMessageMainChatUIFactory {
    private final ObjectProvider<PanelSendingMessageMainChatUI> panelSendingMessageObjectProvider;

    PanelSendingMessageMainChatUIFactory(ObjectProvider<PanelSendingMessageMainChatUI> panelSendingMessageObjectProvider) {
        this.panelSendingMessageObjectProvider = panelSendingMessageObjectProvider;
    }

    public PanelSendingMessageMainChatUI create() {
        return panelSendingMessageObjectProvider.getObject();
    }
}
