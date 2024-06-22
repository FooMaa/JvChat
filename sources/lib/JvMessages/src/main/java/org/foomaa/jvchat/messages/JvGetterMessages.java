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

    public JvMessagesDefines getBeanMessagesDefines() {
        return context.getBean(
                JvMessagesSpringConfig.NameBeans.BeanMessagesDefines.getValue(),
                JvMessagesDefines.class);
    }

    public JvMessagesSerializatorData getBeanMessagesSerializatorData() {
        return context.getBean(
                JvMessagesSpringConfig.NameBeans.BeanMessagesSerializatorData.getValue(),
                JvMessagesSerializatorData.class);
    }

    public JvMessagesDeserializatorData getBeanMessagesDeserializatorData() {
        return context.getBean(
                JvMessagesSpringConfig.NameBeans.BeanMessagesDeserializatorData.getValue(),
                JvMessagesDeserializatorData.class);
    }
}
