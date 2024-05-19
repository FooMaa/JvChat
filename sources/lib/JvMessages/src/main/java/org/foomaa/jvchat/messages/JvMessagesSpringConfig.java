package org.foomaa.jvchat.messages;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

public class JvMessagesSpringConfig {
    public enum NameBeans {
        BeanSerializatorData("beanSerializatorData");

        private final String value;

        NameBeans(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @Bean(name = "beanSerializatorData")
    @Scope("singleton")
    public JvSerializatorData beanMainSettings() {
        return new JvSerializatorData();
    }
}
