package org.foomaa.jvchat.uicomponents.auth;

import java.util.Objects;

import org.springframework.beans.factory.ObjectProvider;

import lombok.Builder;

public class ButtonAuthUIFactory {
    // DI ↓
    private final ObjectProvider<ButtonAuthUI> buttonAuthUIObjectProvider;

    @Builder
    ButtonAuthUIFactory(ObjectProvider<ButtonAuthUI> buttonAuthUIObjectProvider) {
        this.buttonAuthUIObjectProvider =
                Objects.requireNonNull(buttonAuthUIObjectProvider, "buttonAuthUIObjectProvider is mandatory");
    }

    public ButtonAuthUI create(String text) {
        ButtonAuthUI buttonAuthUI = buttonAuthUIObjectProvider.getObject();
        buttonAuthUI.setText(text);
        return buttonAuthUI;
    }
}
