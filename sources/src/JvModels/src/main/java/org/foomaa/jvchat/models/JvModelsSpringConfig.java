package org.foomaa.jvchat.models;

import org.foomaa.jvchat.structobjects.JvRootStructObject;
import org.springframework.context.annotation.*;


@Configuration
public class JvModelsSpringConfig {
    public enum NameBeans {
        BeanRootObjectsModel("beanRootObjectsModel");

        private final String value;

        NameBeans(String newValue) {
            value = newValue;
        }

        public String getValue() {
            return value;
        }
    }

    @Bean(name = "beanRootObjectsModel")
    @Scope("prototype")
    @SuppressWarnings("unused")
    public JvRootObjectsModel beanRootObjectsModel() {
        return JvRootObjectsModel.getInstance();
    }
}