package org.foomaa.jvchat.uicomponents.auth;

import java.util.Objects;

import org.springframework.beans.factory.ObjectProvider;

import lombok.Builder;

public class ActiveLabelAuthUIFactory {
    // DI ↓
    private final ObjectProvider<ActiveLabelAuthUI> activeLabelAuthUIObjectProvider;

    @Builder
    ActiveLabelAuthUIFactory(ObjectProvider<ActiveLabelAuthUI> activeLabelAuthUIObjectProvider) {
        this.activeLabelAuthUIObjectProvider =
                Objects.requireNonNull(activeLabelAuthUIObjectProvider, "activeLabelAuthUIObjectProvider is mandatory");
    }

    public ActiveLabelAuthUI create(String text) {
        ActiveLabelAuthUI activeLabelAuthUI = activeLabelAuthUIObjectProvider.getObject();
        activeLabelAuthUI.setText(text);
        return activeLabelAuthUI;
    }
}
