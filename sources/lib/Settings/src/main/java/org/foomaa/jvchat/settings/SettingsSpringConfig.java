package org.foomaa.jvchat.settings;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;


@Configuration
class SettingsSpringConfig {
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
    public MainSettings beanMainSettings() {
        return new MainSettings();
    }

    @Bean(name = "beanDisplaySettings")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public DisplaySettings beanDisplaySettings() {
        return new DisplaySettings();
    }

    @Bean(name = "beanUsersInfoSettings")
    @Profile("users")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public UsersInfoSettings beanUsersInfoSettings() {
        return new UsersInfoSettings();
    }

    @Bean(name = "beanServersInfoSettings")
    @Profile("servers")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public ServersInfoSettings beanServersInfoSettings() {
        return new ServersInfoSettings();
    }

    @Bean(name = "beanUISettings")
    @Profile("users")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public UISettings beanUISettings() {
        return new UISettings();
    }
}