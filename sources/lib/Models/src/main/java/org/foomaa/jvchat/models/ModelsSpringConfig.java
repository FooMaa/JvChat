package org.foomaa.jvchat.models;

import org.springframework.context.annotation.*;

/* NOTE(VAD): All models except JvRootObjectsModel must be in the configuration
 * spring have @Lazy annotation for correctness
 * operation of the JvBaseModel.updateRootObjectsModel method
 * which are used to save all root models.
 */
@Configuration
class ModelsSpringConfig {
    public enum NameBeans {
        BeanRootObjectsModel("beanRootObjectsModel"),
        BeanMessagesModel("beanMessagesModel"),
        BeanChatsModel("beanChatsModel"),
        BeanCheckersOnlineModel("beanCheckersOnlineModel"),
        BeanSocketRunnableCtrlModel("beanSocketRunnableCtrlModel"),
        BeanUsersModel("beanUsersModel"),
        BeanConnectionsEventsModel("beanConnectionsEventsModel");

        private final String value;

        NameBeans(String newValue) {
            value = newValue;
        }

        public String getValue() {
            return value;
        }
    }

    @Bean(name = "beanRootObjectsModel")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public RootObjectsModel beanRootObjectsModel() {
        return new RootObjectsModel();
    }

    @Bean(name = "beanMessagesModel")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public MessagesModel beanMessagesModel() {
        return new MessagesModel();
    }

    @Bean(name = "beanChatsModel")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public ChatsModel beanChatsModel() {
        return new ChatsModel();
    }

    @Bean(name = "beanCheckersOnlineModel")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public CheckersOnlineModel beanCheckersOnlineModel() {
        return new CheckersOnlineModel();
    }

    @Bean(name = "beanSocketRunnableCtrlModel")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public SocketRunnableCtrlModel beanSocketRunnableCtrlModel() {
        return new SocketRunnableCtrlModel();
    }

    @Bean(name = "beanUsersModel")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public UsersModel beanUsersModel() {
        return new UsersModel();
    }

    @Bean(name = "beanConnectionsEventsModel")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public ConnectionsEventsModel beanConnectionsEventsModel() {
        return new ConnectionsEventsModel();
    }
}