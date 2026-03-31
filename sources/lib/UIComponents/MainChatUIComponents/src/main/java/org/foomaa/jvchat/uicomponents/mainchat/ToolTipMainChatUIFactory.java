package org.foomaa.jvchat.uicomponents.mainchat;

import java.util.Objects;

import org.springframework.beans.factory.ObjectProvider;

import lombok.Builder;

public class ToolTipMainChatUIFactory {
    private final ObjectProvider<ToolTipMainChatUI> toolTipObjectProvider;

    @Builder
    ToolTipMainChatUIFactory(ObjectProvider<ToolTipMainChatUI> toolTipObjectProvider) {
        this.toolTipObjectProvider = Objects.requireNonNull(toolTipObjectProvider,
                "toolTipObjectProvider is mandatory");
    }

    public ToolTipMainChatUI create() {
        return toolTipObjectProvider.getObject();
    }
}
