package org.foomaa.jvchat.uicomponents.auth;

import java.util.Objects;

import org.springframework.beans.factory.ObjectProvider;

import lombok.Builder;

public class OptionPaneAuthUIFactory {
    private final ObjectProvider<OptionPaneAuthUI> optionPaneAuthUIObjectProvider;

    @Builder
    OptionPaneAuthUIFactory(ObjectProvider<OptionPaneAuthUI> optionPaneAuthUIObjectProvider) {
        this.optionPaneAuthUIObjectProvider =
                Objects.requireNonNull(optionPaneAuthUIObjectProvider, "optionPaneAuthUIObjectProvider is mandatory");
    }

    public OptionPaneAuthUI create() {
        return optionPaneAuthUIObjectProvider.getObject();
    }
}
