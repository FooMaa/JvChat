package org.foomaa.jvchat.messages;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

public class JvMessagesSpringConfig {
    public enum NameBeans {
        BeanMessagesDefines("beanMessagesDefines"),
        BeanMessagesSerializatorData("beanMessagesSerializatorData"),
        BeanMessagesDeserializatorData("beanMessagesDeserializatorData");

        private final String value;

        NameBeans(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @Bean(name = "beanMessagesDefines")
    @Scope("singleton")
    public JvMessagesDefines beanMessagesDefines() {
        return new JvMessagesDefines();
    }

    @Bean(name = "beanMessagesSerializatorData")
    @Scope("singleton")
    public JvMessagesSerializatorData beanMessagesSerializatorData() {
        return new JvMessagesSerializatorData();
    }

    @Bean(name = "beanMessagesDeserializatorData")
    @Scope("singleton")
    public JvMessagesDeserializatorData beanMessagesDeserializatorData() {
        return new JvMessagesDeserializatorData();
    }
}
