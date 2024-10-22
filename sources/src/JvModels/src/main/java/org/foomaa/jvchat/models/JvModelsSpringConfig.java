package org.foomaa.jvchat.models;

import org.springframework.context.annotation.*;


@Configuration
public class JvModelsSpringConfig {
    public enum NameBeans {
        BeanRootObjectsModel("beanRootObjectsModel"),
        BeanMessagesModel("beanMessagesModel");

        private final String value;

        NameBeans(String newValue) {
            value = newValue;
        }

        public String getValue() {
            return value;
        }
    }

    @Bean(name = "beanRootObjectsModel")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvRootObjectsModel beanRootObjectsModel() {
        return JvRootObjectsModel.getInstance();
    }

    @Bean(name = "beanMessagesModel")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvMessagesModel beanMessagesModel() {
        return JvMessagesModel.getInstance();
    }
}