package org.foomaa.jvchat.structobjects;

import org.springframework.context.annotation.*;


@Configuration
class StructObjectsSpringConfig {
    public enum NameBeans {
        BeanMessageStructObject("beanMessageStructObject"),
        BeanRootStructObject("beanRootStructObject"),
        BeanChatStructObject("beanChatStructObject"),
        BeanUserStructObject("beanUserStructObject"),
        BeanCheckerOnlineStructObject("beanCheckerOnlineStructObject"),
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

    @Bean(name = "beanMessageStructObject")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public MessageStructObject beanBaseStructObject() {
        return new MessageStructObject();
    }

    @Bean(name = "beanRootStructObject")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public RootStructObject beanRootStructObject(String nameModel) {
        return new RootStructObject(nameModel);
    }

    @Bean(name = "beanChatStructObject")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public ChatStructObject beanChatStructObject() {
        return new ChatStructObject();
    }

    @Bean(name = "beanUserStructObject")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public UserStructObject beanUserStructObject() {
        return new UserStructObject();
    }

    @Bean(name = "beanCheckerOnlineStructObject")
    @Lazy
    @Profile("servers")
    @Scope("prototype")
    @SuppressWarnings("unused")
    public CheckerOnlineStructObject beanCheckerOnlineStructObject() {
        return new CheckerOnlineStructObject();
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