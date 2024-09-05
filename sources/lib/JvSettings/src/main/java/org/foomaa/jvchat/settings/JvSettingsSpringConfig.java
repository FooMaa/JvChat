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
        BeanUserInfoSettings("beanUserInfoSettings"),
        BeanServerInfoSettings("beanServerInfoSettings");

        private final String value;

        NameBeans(String newValue) {
            value = newValue;
        }

        public String getValue() {
            return value;
        }
    }

    @Bean(name = "beanMainSettings")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvMainSettings beanMainSettings() {
        return JvMainSettings.getInstance();
    }

    @Bean(name = "beanDisplaySettings")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvDisplaySettings beanDisplaySettings() {
        return JvDisplaySettings.getInstance();
    }

    @Bean(name = "beanUserInfoSettings")
    @Profile("users")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvUserInfoSettings beanUserInfoSettings() {
        return JvUserInfoSettings.getInstance();
    }

    @Bean(name = "beanServerInfoSettings")
    @Profile("servers")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvServerInfoSettings beanServerInfoSettings() {
        return JvServerInfoSettings.getInstance();
    }
}