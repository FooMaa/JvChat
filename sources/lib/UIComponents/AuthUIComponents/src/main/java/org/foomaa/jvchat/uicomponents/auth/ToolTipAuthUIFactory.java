package org.foomaa.jvchat.uicomponents.auth;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("users")
public class ToolTipAuthUIFactory {
    private final ObjectProvider<ToolTipAuthUI> toolTipAuthUIObjectProvider;

    ToolTipAuthUIFactory(ObjectProvider<ToolTipAuthUI> toolTipAuthUIObjectProvider) {
        this.toolTipAuthUIObjectProvider = toolTipAuthUIObjectProvider;
    }

    public ToolTipAuthUI create() {
        ToolTipAuthUI toolTipAuthUI = toolTipAuthUIObjectProvider.getObject();
        return toolTipAuthUI;
    }
}
