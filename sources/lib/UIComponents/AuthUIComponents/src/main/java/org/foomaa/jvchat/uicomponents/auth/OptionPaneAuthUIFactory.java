package org.foomaa.jvchat.uicomponents.auth;


import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile("users")
public class OptionPaneAuthUIFactory {
    private final ObjectProvider<OptionPaneAuthUI> optionPaneAuthUIObjectProvider;

    OptionPaneAuthUIFactory(ObjectProvider<OptionPaneAuthUI> optionPaneAuthUIObjectProvider) {
        this.optionPaneAuthUIObjectProvider = optionPaneAuthUIObjectProvider;
    }

    public OptionPaneAuthUI create() {
        return optionPaneAuthUIObjectProvider.getObject();
    }
}
