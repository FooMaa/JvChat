package org.foomaa.jvchat.settings;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

@Configuration
public class JvSettingsSpringConfig {
    public enum NameBeans {
        BeanMainSettings("beanMainSettings"),
        BeanDisplaySettings("beanDisplaySettings"),
        BeanUserInfoSettings("beanUserInfoSettings");

        private final String value;

        NameBeans(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @Bean(name = "beanMainSettings")
    @Scope("singleton")
    public JvMainSettings beanMainSettings() {
        return JvMainSettings.getInstance();
    }

    @Bean(name = "beanDisplaySettings")
    @Scope("singleton")
    public JvDisplaySettings beanDisplaySettings() {
        return JvDisplaySettings.getInstance();
    }

    @Bean(name = "beanUserInfoSettings")
    @Profile("users")
    @Scope("singleton")
    public JvUserInfoSettings beanUserInfoSettings() {
        return JvUserInfoSettings.getInstance();
    }
}
