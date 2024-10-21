package org.foomaa.jvchat.structobjects;

import org.springframework.context.annotation.*;


@Configuration
public class JvStructObjectsSpringConfig {
    public enum NameBeans {
        BeanMessageStructObject("beanMessageStructObject");

        private final String value;

        NameBeans(String newValue) {
            value = newValue;
        }

        public String getValue() {
            return value;
        }
    }

    @Bean(name = "beanMessageStructObject")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public JvBaseStructObject beanBaseStructObject(String text) {
        return new JvMessageStructObject();
    }
}