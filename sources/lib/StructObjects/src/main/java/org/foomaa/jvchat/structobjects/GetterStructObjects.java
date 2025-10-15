package org.foomaa.jvchat.structobjects;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class GetterStructObjects {
    private static GetterStructObjects instance;
    private final AnnotationConfigApplicationContext context;

    private GetterStructObjects() {
        context = new AnnotationConfigApplicationContext(StructObjectsSpringConfig.class);
    }

    public static GetterStructObjects getInstance() {
        if (instance == null) {
            instance = new GetterStructObjects();
        }
        return instance;
    }

    public MessageStructObject getBeanMessageStructObject() {
        return context.getBean(StructObjectsSpringConfig.NameBeans.BeanMessageStructObject.getValue(),
                MessageStructObject.class);
    }

    public RootStructObject getBeanRootStructObject(String nameModel) {
        return (RootStructObject) context.getBean(
                StructObjectsSpringConfig.NameBeans.BeanRootStructObject.getValue(),
                nameModel);
    }

    public ChatStructObject getBeanChatStructObject() {
        return context.getBean(StructObjectsSpringConfig.NameBeans.BeanChatStructObject.getValue(),
                ChatStructObject.class);
    }

    public UserStructObject getBeanUserStructObject() {
        return context.getBean(StructObjectsSpringConfig.NameBeans.BeanUserStructObject.getValue(),
                UserStructObject.class);
    }

    public CheckerOnlineStructObject getBeanCheckerOnlineStructObject() {
        return context.getBean(StructObjectsSpringConfig.NameBeans.BeanCheckerOnlineStructObject.getValue(),
                CheckerOnlineStructObject.class);
    }

    public SocketRunnableCtrlStructObject getBeanSocketRunnableCtrlStructObject() {
        return context.getBean(StructObjectsSpringConfig.NameBeans.BeanSocketRunnableCtrlStructObject.getValue(),
                SocketRunnableCtrlStructObject.class);
    }

    public ConnectionEventStructObject getBeanConnectionEventStructObject() {
        return context.getBean(StructObjectsSpringConfig.NameBeans.BeanConnectionEventStructObject.getValue(),
                ConnectionEventStructObject.class);
    }
}