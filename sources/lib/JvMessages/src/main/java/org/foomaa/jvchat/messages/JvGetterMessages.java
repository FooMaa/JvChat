package org.foomaa.jvchat.messages;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class JvGetterMessages {
    private static JvGetterMessages instance;
    private final AnnotationConfigApplicationContext context;

    private JvGetterMessages() {
        context = new AnnotationConfigApplicationContext(
                JvMessagesSpringConfig.class);
    }

    public static JvGetterMessages getInstance() {
        if (instance == null) {
            instance = new JvGetterMessages();
        }
        return instance;
    }

    public JvDefinesMessages getBeanDefinesMessages() {
        return context.getBean(
                JvMessagesSpringConfig.NameBeans.BeanDefinesMessages.getValue(),
                JvDefinesMessages.class);
    }

    public JvSerializatorDataMessages getBeanSerializatorDataMessages() {
        return context.getBean(
                JvMessagesSpringConfig.NameBeans.BeanSerializatorDataMessages.getValue(),
                JvSerializatorDataMessages.class);
    }

    public JvDeserializatorDataMessages getBeanDeserializatorDataMessages() {
        return context.getBean(
                JvMessagesSpringConfig.NameBeans.BeanDeserializatorDataMessages.getValue(),
                JvDeserializatorDataMessages.class);
    }
}