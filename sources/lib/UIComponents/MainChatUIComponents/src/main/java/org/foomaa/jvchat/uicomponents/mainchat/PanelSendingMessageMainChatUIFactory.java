package org.foomaa.jvchat.uicomponents.mainchat;

import lombok.Builder;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Profile("users")
public class PanelSendingMessageMainChatUIFactory {
    private final ObjectProvider<PanelSendingMessageMainChatUI> panelSendingMessageObjectProvider;

    @Builder
    PanelSendingMessageMainChatUIFactory(
            ObjectProvider<PanelSendingMessageMainChatUI> panelSendingMessageObjectProvider) {
        this.panelSendingMessageObjectProvider = Objects.requireNonNull(panelSendingMessageObjectProvider,
                "panelSendingMessageObjectProvider is mandatory");;
    }

    public PanelSendingMessageMainChatUI create() {
        return panelSendingMessageObjectProvider.getObject();
    }
}
