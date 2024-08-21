package org.foomaa.jvchat.messages;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


@Configuration
public class JvMessagesSpringConfig {
    public enum NameBeans {
        BeanMessagesDefines("beanMessagesDefines"),
        BeanMessagesSerializatorData("beanMessagesSerializatorData"),
        BeanMessagesDeserializatorData("beanMessagesDeserializatorData");

        private final String value;

        NameBeans(String newValue) {
            value = newValue;
        }

        public String getValue() {
            return value;
        }
    }

    @Bean(name = "beanMessagesDefines")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvMessagesDefines beanMessagesDefines() {
        return JvMessagesDefines.getInstance();
    }

    @Bean(name = "beanMessagesSerializatorData")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvMessagesSerializatorData beanMessagesSerializatorData() {
        return JvMessagesSerializatorData.getInstance();
    }

    @Bean(name = "beanMessagesDeserializatorData")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvMessagesDeserializatorData beanMessagesDeserializatorData() {
        return JvMessagesDeserializatorData.getInstance();
    }
}
