package org.foomaa.jvchat.structobjects;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class JvGetterStructObjects {
    private static JvGetterStructObjects instance;
    private final AnnotationConfigApplicationContext context;

    private JvGetterStructObjects() {
        context = new AnnotationConfigApplicationContext(JvStructObjectsSpringConfig.class);
    }

    public static JvGetterStructObjects getInstance() {
        if (instance == null) {
            instance = new JvGetterStructObjects();
        }
        return instance;
    }

    public JvMessageStructObject getBeanMessageStructObject() {
        return context.getBean(JvStructObjectsSpringConfig.NameBeans.BeanMessageStructObject.getValue(),
                JvMessageStructObject.class);
    }

    public JvRootStructObject getBeanRootStructObject(String nameModel) {
        return (JvRootStructObject) context.getBean(
                JvStructObjectsSpringConfig.NameBeans.BeanRootStructObject.getValue(),
                nameModel);
    }

    public JvChatStructObject getBeanChatStructObject() {
        return context.getBean(JvStructObjectsSpringConfig.NameBeans.BeanChatStructObject.getValue(),
                JvChatStructObject.class);
    }

    public JvUserStructObject getBeanUserStructObject() {
        return context.getBean(JvStructObjectsSpringConfig.NameBeans.BeanUserStructObject.getValue(),
                JvUserStructObject.class);
    }

    public JvServerConnectionThread getBeanServerConnectionThread() {
        return context.getBean(JvStructObjectsSpringConfig.NameBeans.BeanServerConnectionThread.getValue(),
                JvServerConnectionThread.class);
    }

    public JvCheckerOnlineStructObject getBeanCheckerOnlineStructObject() {
        return context.getBean(JvStructObjectsSpringConfig.NameBeans.BeanCheckerOnlineStructObject.getValue(),
                JvCheckerOnlineStructObject.class);
    }
}