package org.foomaa.jvchat.uilinks;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.Profile;


@Configuration
public class JvUiLinksSpringConfig {
    public enum NameBeans {
        BeanErrorStartUiLink("beanErrorStartUiLink"),
        BeanStartAuthenticationUiLink("beanStartAuthenticationUiLink");

        private final String value;

        NameBeans(String newValue) {
            value = newValue;
        }

        public String getValue() {
            return value;
        }
    }

    @Bean(name = "beanErrorStartUiLink")
    @Scope("prototype")
    @Lazy
    @Profile("users")
    @SuppressWarnings("unused")
    public JvErrorStartUiLink beanErrorStartUiLinks(String msg) {
        return new JvErrorStartUiLink(msg);
    }

    @Bean(name = "beanStartAuthenticationUiLink")
    @Scope("singleton")
    @Lazy
    @Profile("users")
    @SuppressWarnings("unused")
    public JvStartAuthenticationUiLink beanStartAuthenticationUiLinks() {
        return JvStartAuthenticationUiLink.getInstance();
    }
}