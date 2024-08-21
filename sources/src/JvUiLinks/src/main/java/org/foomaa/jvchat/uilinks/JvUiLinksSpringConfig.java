package org.foomaa.jvchat.uilinks;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.Profile;


@Configuration
public class JvUiLinksSpringConfig {
    public enum NameBeans {
        BeanErrorStartUiLinks("beanErrorStartUiLinks"),
        BeanStartAuthenticationUiLinks("beanStartAuthenticationUiLinks");

        private final String value;

        NameBeans(String newValue) {
            value = newValue;
        }

        public String getValue() {
            return value;
        }
    }

    @Bean(name = "beanErrorStartUiLinks")
    @Scope("prototype")
    @Lazy
    @Profile("users")
    @SuppressWarnings("unused")
    public JvErrorStartUiLinks beanErrorStartUiLinks(String msg) {
        return new JvErrorStartUiLinks(msg);
    }

    @Bean(name = "beanStartAuthenticationUiLinks")
    @Scope("singleton")
    @Lazy
    @Profile("users")
    @SuppressWarnings("unused")
    public JvStartAuthenticationUiLinks beanStartAuthenticationUiLinks() {
        return JvStartAuthenticationUiLinks.getInstance();
    }
}