package org.foomaa.jvchat.tools;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@Configuration
public class JvToolsSpringConfig {
    public enum NameBeans {
        BeanMainTools("beanMainTools");

        private final String value;

        NameBeans(String value) {
            this.value = value;
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
}
