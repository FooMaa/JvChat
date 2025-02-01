package org.foomaa.jvchat.models;

import org.springframework.context.annotation.*;

/* NOTE(VAD): Все модели кроме JvRootObjectsModel должны в конфигурации
 * спринга иметь @Lazy анннотацию для корректности
 * работы метода JvBaseModel.updateRootObjectsModel
 * который служат для сохранения всех рутов моделей
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