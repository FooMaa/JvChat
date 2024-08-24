package org.foomaa.jvchat.uilinks;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.Profile;


@Configuration
public class JvUILinksSpringConfig {
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
    public JvErrorStartUILink beanErrorStartUILinks(String msg) {
        return new JvErrorStartUILink(msg);
    }

    @Bean(name = "beanStartAuthenticationUILink")
    @Scope("singleton")
    @Lazy
    @Profile("users")
    @SuppressWarnings("unused")
    public JvStartAuthenticationUILink beanStartAuthenticationUILinks() {
        return JvStartAuthenticationUILink.getInstance();
    }
}