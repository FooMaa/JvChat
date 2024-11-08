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
        BeanUsersModel("beanUsersModel");

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
        return JvRootObjectsModel.getInstance();
    }

    @Bean(name = "beanMessagesModel")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvMessagesModel beanMessagesModel() {
        return JvMessagesModel.getInstance();
    }

    @Bean(name = "beanChatsModel")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvChatsModel beanChatsModel() {
        return JvChatsModel.getInstance();
    }

    @Bean(name = "beanCheckersOnlineModel")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvCheckersOnlineModel beanCheckersOnlineModel() {
        return JvCheckersOnlineModel.getInstance();
    }

    @Bean(name = "beanSocketRunnableCtrlModel")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvSocketRunnableCtrlModel beanSocketRunnableCtrlModel() {
        return JvSocketRunnableCtrlModel.getInstance();
    }

    @Bean(name = "beanUsersModel")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvUsersModel beanUsersModel() {
        return JvUsersModel.getInstance();
    }
}