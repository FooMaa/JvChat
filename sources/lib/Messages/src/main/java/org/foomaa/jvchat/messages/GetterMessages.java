package org.foomaa.jvchat.messages;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class GetterMessages {
    private static GetterMessages instance;
    private final AnnotationConfigApplicationContext context;

    private GetterMessages() {
        context = new AnnotationConfigApplicationContext(
                MessagesSpringConfig.class);
    }

    public static GetterMessages getInstance() {
        if (instance == null) {
            instance = new GetterMessages();
        }
        return instance;
    }

    public SerializatorDataMessages getBeanSerializatorDataMessages() {
        return context.getBean(
                MessagesSpringConfig.NameBeans.BeanSerializatorDataMessages.getValue(),
                SerializatorDataMessages.class);
    }

    public DeserializatorDataMessages getBeanDeserializatorDataMessages() {
        return context.getBean(
                MessagesSpringConfig.NameBeans.BeanDeserializatorDataMessages.getValue(),
                DeserializatorDataMessages.class);
    }
}