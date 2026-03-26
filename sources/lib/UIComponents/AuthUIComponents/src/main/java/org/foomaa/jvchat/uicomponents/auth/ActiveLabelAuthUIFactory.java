package org.foomaa.jvchat.uicomponents.auth;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("users")
public class ActiveLabelAuthUIFactory {
    private final ObjectProvider<ActiveLabelAuthUI> activeLabelAuthUIObjectProvider;

    ActiveLabelAuthUIFactory(ObjectProvider<ActiveLabelAuthUI> activeLabelAuthUIObjectProvider) {
        this.activeLabelAuthUIObjectProvider = activeLabelAuthUIObjectProvider;
    }

    public ActiveLabelAuthUI create(String text) {
        ActiveLabelAuthUI activeLabelAuthUI = activeLabelAuthUIObjectProvider.getObject();
        activeLabelAuthUI.setText(text);
        return activeLabelAuthUI;
    }
}
