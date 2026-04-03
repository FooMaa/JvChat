package org.foomaa.jvchat.uicomponents.auth;

import org.springframework.beans.factory.ObjectProvider;

import lombok.Builder;

public class TextFieldAuthUIFactory {
    // DI ↓
    private final ObjectProvider<TextFieldAuthUI> textFieldAuthUIObjectProvider;

    @Builder
    TextFieldAuthUIFactory(ObjectProvider<TextFieldAuthUI> textFieldAuthUIObjectProvider) {
        this.textFieldAuthUIObjectProvider = textFieldAuthUIObjectProvider;
    }

    public TextFieldAuthUI create(String defaultText) {
        TextFieldAuthUI textFieldAuthUI = textFieldAuthUIObjectProvider.getObject();
        textFieldAuthUI.setDefaultText(defaultText);
        return textFieldAuthUI;
    }
}
