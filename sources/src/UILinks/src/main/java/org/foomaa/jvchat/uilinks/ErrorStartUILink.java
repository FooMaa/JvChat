package org.foomaa.jvchat.uilinks;

import org.foomaa.jvchat.uicomponents.auth.OptionPaneAuthUI;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Lazy
@Scope("prototype")
@Profile("users")
public class ErrorStartUILink {
    private final ObjectProvider<OptionPaneAuthUI> optionPaneAuthUIObjectProvider;

    ErrorStartUILink(ObjectProvider<OptionPaneAuthUI> optionPaneAuthUIObjectProvider) {
        this.optionPaneAuthUIObjectProvider = optionPaneAuthUIObjectProvider;
        System.exit(1);
    }

    public void show(String message) {
        optionPaneAuthUIObjectProvider.getObject().show(message, OptionPaneAuthUI.TypeDlg.ERROR);
    }
}