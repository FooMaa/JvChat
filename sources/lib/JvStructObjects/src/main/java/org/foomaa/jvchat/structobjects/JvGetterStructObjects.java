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

    public JvCheckerOnlineStructObject getBeanCheckerOnlineStructObject() {
        return context.getBean(JvStructObjectsSpringConfig.NameBeans.BeanCheckerOnlineStructObject.getValue(),
                JvCheckerOnlineStructObject.class);
    }

    public JvSocketRunnableCtrlStructObject getBeanSocketRunnableCtrlStructObject() {
        return context.getBean(JvStructObjectsSpringConfig.NameBeans.BeanSocketRunnableCtrlStructObject.getValue(),
                JvSocketRunnableCtrlStructObject.class);
    }

    public JvConnectionEventStructObject getBeanConnectionEventStructObject() {
        return context.getBean(JvStructObjectsSpringConfig.NameBeans.BeanConnectionEventStructObject.getValue(),
                JvConnectionEventStructObject.class);
    }
}