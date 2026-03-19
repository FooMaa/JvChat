package org.foomaa.jvchat.structobjects;

import org.springframework.context.annotation.*;


@Configuration
class StructObjectsSpringConfig {
    public enum NameBeans {
        BeanRootStructObject("beanRootStructObject"),
        BeanUserStructObject("beanUserStructObject"),
        BeanSocketRunnableCtrlStructObject("beanSocketRunnableCtrlStructObject"),
        BeanConnectionEventStructObject("beanConnectionEventStructObject");

        private final String value;

        NameBeans(String newValue) {
            value = newValue;
        }

        public String getValue() {
            return value;
        }
    }

    @Bean(name = "beanRootStructObject")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public RootStructObject beanRootStructObject(String nameModel) {
        return new RootStructObject(nameModel);
    }

    @Bean(name = "beanSocketRunnableCtrlStructObject")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public SocketRunnableCtrlStructObject beanSocketRunnableCtrlStructObject() {
        return new SocketRunnableCtrlStructObject();
    }

    @Bean(name = "beanConnectionEventStructObject")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public ConnectionEventStructObject beanConnectionEventStructObject() {
        return new ConnectionEventStructObject();
    }
}