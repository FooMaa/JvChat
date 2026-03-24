package org.foomaa.jvchat.uicomponents.auth;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile("users")
public class ErrorLabelAuthUIFactory {
    private final ObjectProvider<ErrorLabelAuthUI> errorLabelAuthUIObjectProvider;

    ErrorLabelAuthUIFactory(ObjectProvider<ErrorLabelAuthUI> errorLabelAuthUIObjectProvider) {
        this.errorLabelAuthUIObjectProvider = errorLabelAuthUIObjectProvider;
    }

    public ErrorLabelAuthUI create(String text) {
        ErrorLabelAuthUI errorLabelAuthUI = errorLabelAuthUIObjectProvider.getObject();
        errorLabelAuthUI.setText(text);
        return errorLabelAuthUI;
    }
}
