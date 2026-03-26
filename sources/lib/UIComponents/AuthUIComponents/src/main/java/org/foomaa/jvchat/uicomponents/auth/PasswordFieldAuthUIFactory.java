package org.foomaa.jvchat.uicomponents.auth;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("users")
public class PasswordFieldAuthUIFactory {
    private final ObjectProvider<PasswordFieldAuthUI> passwordFieldAuthUIObjectProvider;

    PasswordFieldAuthUIFactory(ObjectProvider<PasswordFieldAuthUI> passwordFieldAuthUIObjectProvider) {
        this.passwordFieldAuthUIObjectProvider = passwordFieldAuthUIObjectProvider;
    }

    public PasswordFieldAuthUI create(String defaultText) {
        PasswordFieldAuthUI passwordFieldAuthUI = passwordFieldAuthUIObjectProvider.getObject();
        passwordFieldAuthUI.setDefaultText(defaultText);
        return passwordFieldAuthUI;
    }
}
