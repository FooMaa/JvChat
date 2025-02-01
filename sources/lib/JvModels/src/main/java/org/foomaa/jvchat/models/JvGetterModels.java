package org.foomaa.jvchat.models;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class JvGetterModels {
    private static JvGetterModels instance;
    private final AnnotationConfigApplicationContext context;

    private JvGetterModels() {
        context = new AnnotationConfigApplicationContext(JvModelsSpringConfig.class);
    }

    public static JvGetterModels getInstance() {
        if (instance == null) {
            instance = new JvGetterModels();
        }
        return instance;
    }

    public JvRootObjectsModel getBeanRootObjectsModel() {
        return context.getBean(JvModelsSpringConfig.NameBeans.BeanRootObjectsModel.getValue(),
                JvRootObjectsModel.class);
    }

    public JvMessagesModel getBeanMessagesModel() {
        return context.getBean(JvModelsSpringConfig.NameBeans.BeanMessagesModel.getValue(),
                JvMessagesModel.class);
    }

    public JvChatsModel getBeanChatsModel() {
        return context.getBean(JvModelsSpringConfig.NameBeans.BeanChatsModel.getValue(),
                JvChatsModel.class);
    }

    public JvCheckersOnlineModel getBeanCheckersOnlineModel() {
        return context.getBean(JvModelsSpringConfig.NameBeans.BeanCheckersOnlineModel.getValue(),
                JvCheckersOnlineModel.class);
    }

    public JvSocketRunnableCtrlModel getBeanSocketRunnableCtrlModel() {
        return context.getBean(JvModelsSpringConfig.NameBeans.BeanSocketRunnableCtrlModel.getValue(),
                JvSocketRunnableCtrlModel.class);
    }

    public JvUsersModel getBeanUsersModel() {
        return context.getBean(JvModelsSpringConfig.NameBeans.BeanUsersModel.getValue(),
                JvUsersModel.class);
    }

    public JvConnectionsEventsModel getBeanConnectionsEventsModel() {
        return context.getBean(JvModelsSpringConfig.NameBeans.BeanConnectionsEventsModel.getValue(),
                JvConnectionsEventsModel.class);
    }
}