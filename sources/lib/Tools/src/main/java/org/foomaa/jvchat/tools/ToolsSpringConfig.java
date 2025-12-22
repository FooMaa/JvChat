package org.foomaa.jvchat.tools;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;


@Configuration
class ToolsSpringConfig {
    public enum NameBeans {
        BeanMainTools("beanMainTools"),
        BeanStructTools("beanStructTools"),
        BeanServersTools("beanServersTools"),
        BeanUsersTools("beanUsersTools"),
        BeanFormatTools("beanFormatTools");

        private final String value;

        NameBeans(String newValue) {
            value = newValue;
        }

        public String getValue() {
            return value;
        }
    }

    @Bean(name = "beanStructTools")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public StructTools beanStructTools() {
        return new StructTools();
    }

    @Bean(name = "beanUsersTools")
    @Profile("users")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public UsersTools beanUsersTools() {
        return new UsersTools();
    }

    @Bean(name = "beanFormatTools")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public FormatTools beanFormatTools() {
        return new FormatTools();
    }
}