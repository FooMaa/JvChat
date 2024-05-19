package org.foomaa.jvchat.messages;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class JvGetterMessages {
    private static JvGetterMessages instance;
    private static AnnotationConfigApplicationContext context;

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

    public JvSerializatorData getBeanMainSettings() {
        return context.getBean(JvMessagesSpringConfig.NameBeans.BeanSerializatorData.getValue(), JvSerializatorData.class);
    }
}
