package org.foomaa.jvchat.uicomponents.auth;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile("users")
public class TextFieldAuthUIFactory {
    private final ObjectProvider<TextFieldAuthUI> textFieldAuthUIObjectProvider;

    TextFieldAuthUIFactory(ObjectProvider<TextFieldAuthUI> textFieldAuthUIObjectProvider) {
        this.textFieldAuthUIObjectProvider = textFieldAuthUIObjectProvider;
    }

    public TextFieldAuthUI create(String defaultText) {
        TextFieldAuthUI textFieldAuthUI = textFieldAuthUIObjectProvider.getObject();
        textFieldAuthUI.setDefaultText(defaultText);
        return textFieldAuthUI;
    }
}
