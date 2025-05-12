package org.foomaa.jvchat.messages;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


@Configuration
class JvMessagesSpringConfig {
    public enum NameBeans {
        BeanDefinesMessages("beanDefinesMessages"),
        BeanSerializatorDataMessages("beanSerializatorDataMessages"),
        BeanDeserializatorDataMessages("beanDeserializatorDataMessages");

        private final String value;

        NameBeans(String newValue) {
            value = newValue;
        }

        public String getValue() {
            return value;
        }
    }

    @Bean(name = "beanSerializatorDataMessages")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvSerializatorDataMessages beanSerializatorDataMessages() {
        return new JvSerializatorDataMessages();
    }

    @Bean(name = "beanDeserializatorDataMessages")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvDeserializatorDataMessages beanDeserializatorDataMessages() {
        return new JvDeserializatorDataMessages();
    }
}
