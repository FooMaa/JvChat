package org.foomaa.jvchat.uicomponents.mainchat;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile("users")
public class ToolTipMainChatUIFactory {
    private final ObjectProvider<ToolTipMainChatUI> toolTipObjectProvider;

    ToolTipMainChatUIFactory(ObjectProvider<ToolTipMainChatUI> toolTipObjectProvider) {
        this.toolTipObjectProvider = toolTipObjectProvider;
    }

    public ToolTipMainChatUI create() {
        return toolTipObjectProvider.getObject();
    }
}
