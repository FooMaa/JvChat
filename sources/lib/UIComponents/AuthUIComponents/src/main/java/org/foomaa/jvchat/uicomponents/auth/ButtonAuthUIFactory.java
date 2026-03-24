package org.foomaa.jvchat.uicomponents.auth;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile("users")
public class ButtonAuthUIFactory {
    private final ObjectProvider<ButtonAuthUI> buttonAuthUIObjectProvider;

    ButtonAuthUIFactory(ObjectProvider<ButtonAuthUI> buttonAuthUIObjectProvider) {
        this.buttonAuthUIObjectProvider = buttonAuthUIObjectProvider;
    }

    public ButtonAuthUI create(String text) {
        ButtonAuthUI buttonAuthUI = buttonAuthUIObjectProvider.getObject();
        buttonAuthUI.setText(text);
        return buttonAuthUI;
    }
}
