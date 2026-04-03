package org.foomaa.jvchat.uicomponents.auth;

import java.util.Objects;

import org.springframework.beans.factory.ObjectProvider;

import lombok.Builder;

public class ToolTipAuthUIFactory {
    // DI ↓
    private final ObjectProvider<ToolTipAuthUI> toolTipAuthUIObjectProvider;

    @Builder
    ToolTipAuthUIFactory(ObjectProvider<ToolTipAuthUI> toolTipAuthUIObjectProvider) {
        this.toolTipAuthUIObjectProvider =
                Objects.requireNonNull(toolTipAuthUIObjectProvider, "toolTipAuthUIObjectProvider is mandatory");
    }

    public ToolTipAuthUI create() {
        return toolTipAuthUIObjectProvider.getObject();
    }
}
