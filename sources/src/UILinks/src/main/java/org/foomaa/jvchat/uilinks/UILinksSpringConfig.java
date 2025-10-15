package org.foomaa.jvchat.uilinks;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.Profile;


@Configuration
class UILinksSpringConfig {
    public enum NameBeans {
        BeanErrorStartUILink("beanErrorStartUILink"),
        BeanStartAuthenticationUILink("beanStartAuthenticationUILink");

        private final String value;

        NameBeans(String newValue) {
            value = newValue;
        }

        public String getValue() {
            return value;
        }
    }

    @Bean(name = "beanErrorStartUILink")
    @Scope("prototype")
    @Lazy
    @Profile("users")
    @SuppressWarnings("unused")
    public ErrorStartUILink beanErrorStartUILinks(String msg) {
        return new ErrorStartUILink(msg);
    }

    @Bean(name = "beanStartAuthenticationUILink")
    @Scope("singleton")
    @Lazy
    @Profile("users")
    @SuppressWarnings("unused")
    public StartAuthenticationUILink beanStartAuthenticationUILinks() {
        return new StartAuthenticationUILink();
    }
}