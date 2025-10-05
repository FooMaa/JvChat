package org.foomaa.jvchat.models;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class GetterModels {
    private static GetterModels instance;
    private final AnnotationConfigApplicationContext context;

    private GetterModels() {
        context = new AnnotationConfigApplicationContext(ModelsSpringConfig.class);
    }

    public static GetterModels getInstance() {
        if (instance == null) {
            instance = new GetterModels();
        }
        return instance;
    }

    public RootObjectsModel getBeanRootObjectsModel() {
        return context.getBean(ModelsSpringConfig.NameBeans.BeanRootObjectsModel.getValue(),
                RootObjectsModel.class);
    }

    public MessagesModel getBeanMessagesModel() {
        return context.getBean(ModelsSpringConfig.NameBeans.BeanMessagesModel.getValue(),
                MessagesModel.class);
    }

    public ChatsModel getBeanChatsModel() {
        return context.getBean(ModelsSpringConfig.NameBeans.BeanChatsModel.getValue(),
                ChatsModel.class);
    }

    public CheckersOnlineModel getBeanCheckersOnlineModel() {
        return context.getBean(ModelsSpringConfig.NameBeans.BeanCheckersOnlineModel.getValue(),
                CheckersOnlineModel.class);
    }

    public SocketRunnableCtrlModel getBeanSocketRunnableCtrlModel() {
        return context.getBean(ModelsSpringConfig.NameBeans.BeanSocketRunnableCtrlModel.getValue(),
                SocketRunnableCtrlModel.class);
    }

    public UsersModel getBeanUsersModel() {
        return context.getBean(ModelsSpringConfig.NameBeans.BeanUsersModel.getValue(),
                UsersModel.class);
    }

    public ConnectionsEventsModel getBeanConnectionsEventsModel() {
        return context.getBean(ModelsSpringConfig.NameBeans.BeanConnectionsEventsModel.getValue(),
                ConnectionsEventsModel.class);
    }
}