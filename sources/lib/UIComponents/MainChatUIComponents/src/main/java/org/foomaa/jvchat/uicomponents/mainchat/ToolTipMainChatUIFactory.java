package org.foomaa.jvchat.uicomponents.mainchat;

import org.springframework.beans.factory.ObjectProvider;

import lombok.Builder;

public class ToolTipMainChatUIFactory {
    private final ObjectProvider<ToolTipMainChatUI> toolTipObjectProvider;

    @Builder
    ToolTipMainChatUIFactory(ObjectProvider<ToolTipMainChatUI> toolTipObjectProvider) {
        this.toolTipObjectProvider = toolTipObjectProvider;
    }

    public ToolTipMainChatUI create() {
        return toolTipObjectProvider.getObject();
    }
}
