package org.foomaa.jvchat.tools;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;


@Configuration
public class JvToolsSpringConfig {
    public enum NameBeans {
        BeanMainTools("beanMainTools"),
        BeanStructTools("beanStructTools");

        private final String value;

        NameBeans(String newValue) {
            value = newValue;
        }

        public String getValue() {
            return value;
        }
    }

    @Bean(name = "beanMainTools")
    @Scope("singleton")
    public JvMainTools beanMainTools() {
        return JvMainTools.getInstance();
    }

    @Bean(name = "beanStructTools")
    @Scope("singleton")
    public JvStructTools beanStructTools() {
        return JvStructTools.getInstance();
    }
}