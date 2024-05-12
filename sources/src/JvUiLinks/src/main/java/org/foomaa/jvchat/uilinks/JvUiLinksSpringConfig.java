package org.foomaa.jvchat.uilinks;

import org.springframework.context.annotation.*;

@Configuration
public class JvUiLinksSpringConfig {
    public enum NameBeans {
        ErrorStart("beanErrorStart"),
        StartAuthentication("beanStartAuthentication");

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
    public JvErrorStart errorStart(String msg) {
        return new JvErrorStart(msg);
    }

    @Bean(name = "beanStartAuthentication")
    @Scope("singleton")
    @Lazy
    @Profile("users")
    public JvStartAuthentication startAuthentication() {
        return new JvStartAuthentication();
    }
}
