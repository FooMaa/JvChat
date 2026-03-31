package org.foomaa.jvchat.uicomponents.auth;

import java.util.Objects;

import org.springframework.beans.factory.ObjectProvider;

import lombok.Builder;

public class ErrorLabelAuthUIFactory {
    private final ObjectProvider<ErrorLabelAuthUI> errorLabelAuthUIObjectProvider;

    @Builder
    ErrorLabelAuthUIFactory(ObjectProvider<ErrorLabelAuthUI> errorLabelAuthUIObjectProvider) {
        this.errorLabelAuthUIObjectProvider = Objects.requireNonNull(errorLabelAuthUIObjectProvider,
                "errorLabelAuthUIObjectProvider is mandatory");
    }

    public ErrorLabelAuthUI create(String text) {
        ErrorLabelAuthUI errorLabelAuthUI = errorLabelAuthUIObjectProvider.getObject();
        errorLabelAuthUI.setText(text);
        return errorLabelAuthUI;
    }
}
