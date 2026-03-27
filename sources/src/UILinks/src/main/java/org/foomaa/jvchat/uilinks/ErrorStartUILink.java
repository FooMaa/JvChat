package org.foomaa.jvchat.uilinks;

import java.util.Objects;

import lombok.Builder;

import org.foomaa.jvchat.uicomponents.auth.OptionPaneAuthUI;
import org.foomaa.jvchat.uicomponents.auth.OptionPaneAuthUIFactory;

public class ErrorStartUILink {
    private final OptionPaneAuthUIFactory optionPaneAuthUIFactory;

    @Builder
    ErrorStartUILink(OptionPaneAuthUIFactory optionPaneAuthUIFactory) {
        this.optionPaneAuthUIFactory = Objects.requireNonNull(optionPaneAuthUIFactory,
                "mainTool is mandatory");
    }

    public void show(String message) {
        optionPaneAuthUIFactory.create().show(message, OptionPaneAuthUI.TypeDlg.ERROR);
    }
}
