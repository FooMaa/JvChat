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

    @Bean(name = "beanConnectionsEventsModel")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public ConnectionsEventsModel beanConnectionsEventsModel() {
        return new ConnectionsEventsModel();
    }
}