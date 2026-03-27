package org.foomaa.jvchat.uilinks;

import org.foomaa.jvchat.uicomponents.auth.OptionPaneAuthUIFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.*;

import org.foomaa.jvchat.uicomponents.auth.MainFrameAuthUI;

@Configuration
public class UILinksConfig {
    @Bean
    @Lazy
    @Profile("users")
    public ErrorStartUILink beanErrorStartUILink(OptionPaneAuthUIFactory optionPaneAuthUIFactory) {
        return ErrorStartUILink.builder().optionPaneAuthUIFactory(optionPaneAuthUIFactory).build();
    }

    @Bean
    @Profile("users")
    public ErrorStartUILinkFactory beanErrorStartUILinkFactory(
            ObjectProvider<ErrorStartUILink> errorStartUILinkObjectProvider) {
        return ErrorStartUILinkFactory.builder().errorStartUILinkObjectProvider(errorStartUILinkObjectProvider).build();
    }

    @Bean
    @Lazy
    @Profile("users")
    public StartAuthenticationUILink beanStartAuthenticationUILink(MainFrameAuthUI mainFrameAuthUI) {
        return StartAuthenticationUILink.builder().mainFrameAuthUI(mainFrameAuthUI).build();
    }
}
