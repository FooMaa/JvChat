package org.foomaa.jvchat.uicomponents.mainchat;

import lombok.Builder;
import org.springframework.beans.factory.ObjectProvider;

import java.util.Objects;

public class PanelSendingMessageMainChatUIFactory {
    private final ObjectProvider<PanelSendingMessageMainChatUI> panelSendingMessageObjectProvider;

    @Builder
    PanelSendingMessageMainChatUIFactory(
            ObjectProvider<PanelSendingMessageMainChatUI> panelSendingMessageObjectProvider) {
        this.panelSendingMessageObjectProvider = Objects.requireNonNull(panelSendingMessageObjectProvider,
                "panelSendingMessageObjectProvider is mandatory");
    }

    public PanelSendingMessageMainChatUI create() {
        return panelSendingMessageObjectProvider.getObject();
    }
}
