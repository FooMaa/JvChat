package org.foomaa.jvchat.uicomponents.mainchat;

import java.util.Objects;

import org.springframework.beans.factory.ObjectProvider;

import lombok.Builder;

public class PanelSendingMessageMainChatUIFactory {
    private final ObjectProvider<PanelSendingMessageMainChatUI> panelSendingMessageObjectProvider;

    @Builder
    PanelSendingMessageMainChatUIFactory(
            ObjectProvider<PanelSendingMessageMainChatUI> panelSendingMessageObjectProvider) {
        this.panelSendingMessageObjectProvider = Objects.requireNonNull(
                panelSendingMessageObjectProvider, "panelSendingMessageObjectProvider is mandatory");
    }

    public PanelSendingMessageMainChatUI create() {
        return panelSendingMessageObjectProvider.getObject();
    }
}
