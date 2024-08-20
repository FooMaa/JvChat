package org.foomaa.jvchat.uilinks;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.Profile;


@Configuration
public class JvUiLinksSpringConfig {
    public enum NameBeans {
        BeanErrorStart("beanErrorStart"),
        BeanStartAuthentication("beanStartAuthentication");

        private final String value;

        NameBeans(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @Bean(name = "beanErrorStart")
    @Scope("prototype")
    @Lazy
    @Profile("users")
    public JvErrorStart beanErrorStart(String msg) {
        return new JvErrorStart(msg);
    }

    @Bean(name = "beanStartAuthentication")
    @Scope("singleton")
    @Lazy
    @Profile("users")
    public JvStartAuthentication beanStartAuthentication() {
        return JvStartAuthentication.getInstance();
    }
}