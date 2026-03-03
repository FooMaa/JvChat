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
}