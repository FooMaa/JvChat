package org.foomaa.jvchat.uicomponents.auth;

import java.util.Objects;

import org.springframework.beans.factory.ObjectProvider;

import lombok.Builder;

public class PasswordFieldAuthUIFactory {
    private final ObjectProvider<PasswordFieldAuthUI> passwordFieldAuthUIObjectProvider;

    @Builder
    PasswordFieldAuthUIFactory(ObjectProvider<PasswordFieldAuthUI> passwordFieldAuthUIObjectProvider) {
        this.passwordFieldAuthUIObjectProvider = Objects.requireNonNull(
                passwordFieldAuthUIObjectProvider, "passwordFieldAuthUIObjectProvider is mandatory");
    }

    public PasswordFieldAuthUI create(String defaultText) {
        PasswordFieldAuthUI passwordFieldAuthUI = passwordFieldAuthUIObjectProvider.getObject();
        passwordFieldAuthUI.setDefaultText(defaultText);
        return passwordFieldAuthUI;
    }
}
