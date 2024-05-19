package org.foomaa.jvchat.tools;

import org.springframework.context.annotation.*;

@Configuration
public class JvToolsSpringConfig {
    public enum NameBeans {
        MainTools("beanMainTools");

        private final String value;

        NameBeans(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @Bean(name = "beanMainTools")
    @Scope("prototype")
    public JvMainTools mainTools() {
        return new JvMainTools();
    }
}
