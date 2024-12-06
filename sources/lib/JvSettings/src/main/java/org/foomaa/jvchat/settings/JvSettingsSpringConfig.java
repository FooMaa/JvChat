package org.foomaa.jvchat.settings;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;


@Configuration
class JvSettingsSpringConfig {
    public enum NameBeans {
        BeanMainSettings("beanMainSettings"),
        BeanDisplaySettings("beanDisplaySettings"),
        BeanUsersInfoSettings("beanUsersInfoSettings"),
        BeanServersInfoSettings("beanServersInfoSettings"),
        BeanUISettings("beanUISettings");

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
        return new JvMainSettings();
    }

    @Bean(name = "beanDisplaySettings")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvDisplaySettings beanDisplaySettings() {
        return new JvDisplaySettings();
    }

    @Bean(name = "beanUsersInfoSettings")
    @Profile("users")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvUsersInfoSettings beanUsersInfoSettings() {
        return new JvUsersInfoSettings();
    }

    @Bean(name = "beanServersInfoSettings")
    @Profile("servers")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvServersInfoSettings beanServersInfoSettings() {
        return new JvServersInfoSettings();
    }

    @Bean(name = "beanUISettings")
    @Profile("users")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvUISettings beanUISettings() {
        return new JvUISettings();
    }
}