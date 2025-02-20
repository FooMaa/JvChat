package org.foomaa.jvchat.models;

import org.springframework.context.annotation.*;

/* NOTE(VAD): All models except JvRootObjectsModel must be in the configuration
 * spring have @Lazy annotation for correctness
 * operation of the JvBaseModel.updateRootObjectsModel method
 * which are used to save all root models.
 */
@Configuration
class JvModelsSpringConfig {
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
    public JvRootObjectsModel beanRootObjectsModel() {
        return new JvRootObjectsModel();
    }

    @Bean(name = "beanMessagesModel")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvMessagesModel beanMessagesModel() {
        return new JvMessagesModel();
    }

    @Bean(name = "beanChatsModel")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvChatsModel beanChatsModel() {
        return new JvChatsModel();
    }

    @Bean(name = "beanCheckersOnlineModel")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvCheckersOnlineModel beanCheckersOnlineModel() {
        return new JvCheckersOnlineModel();
    }

    @Bean(name = "beanSocketRunnableCtrlModel")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvSocketRunnableCtrlModel beanSocketRunnableCtrlModel() {
        return new JvSocketRunnableCtrlModel();
    }

    @Bean(name = "beanUsersModel")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvUsersModel beanUsersModel() {
        return new JvUsersModel();
    }

    @Bean(name = "beanConnectionsEventsModel")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvConnectionsEventsModel beanConnectionsEventsModel() {
        return new JvConnectionsEventsModel();
    }
}