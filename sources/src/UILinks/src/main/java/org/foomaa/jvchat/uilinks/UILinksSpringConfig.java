package org.foomaa.jvchat.uilinks;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.Profile;


@Configuration
class UILinksSpringConfig {
    @Bean
    @Scope("prototype")
    @Lazy
    @Profile("users")
    @SuppressWarnings("unused")
    public ErrorStartUILink beanErrorStartUILinks(String msg) {
        return new ErrorStartUILink(msg);
    }

    @Bean
    @Scope("singleton")
    @Lazy
    @Profile("users")
    @SuppressWarnings("unused")
    public StartAuthenticationUILink beanStartAuthenticationUILinks() {
        return new StartAuthenticationUILink();
    }
}