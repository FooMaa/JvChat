package org.foomaa.jvchat.structobjects;

import org.springframework.context.annotation.*;


@Configuration
public class JvStructObjectsSpringConfig {
    public enum NameBeans {
        BeanMessageStructObject("beanMessageStructObject"),
        BeanRootStructObject("beanRootStructObject");

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
    public JvMessageStructObject beanBaseStructObject() {
        return new JvMessageStructObject();
    }

    @Bean(name = "beanRootStructObject")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public JvRootStructObject beanRootStructObject(String nameModel) {
        return new JvRootStructObject(nameModel);
    }
}