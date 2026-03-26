package org.foomaa.jvchat.uilinks;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import org.foomaa.jvchat.uicomponents.auth.OptionPaneAuthUI;
import org.foomaa.jvchat.uicomponents.auth.OptionPaneAuthUIFactory;

@Component
@Lazy
@Scope("prototype")
@Profile("users")
public class ErrorStartUILink {
    private final OptionPaneAuthUIFactory optionPaneAuthUIFactory;

    ErrorStartUILink(OptionPaneAuthUIFactory optionPaneAuthUIFactory) {
        this.optionPaneAuthUIFactory = optionPaneAuthUIFactory;
    }

    public void show(String message) {
        optionPaneAuthUIFactory.create().show(message, OptionPaneAuthUI.TypeDlg.ERROR);
    }
}
