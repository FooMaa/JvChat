package org.foomaa.jvchat.events;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;
import java.util.UUID;


@Configuration
@EnableAspectJAutoProxy
@EnableAutoConfiguration // Will change it later
class JvEventsSpringConfig {
    public enum NameBeans {
        BeanBaseEvent("beanBaseEvent"),
        BeanPublisherEvents("beanPublisherEvents"),
        BeanMakerEvents("beanMakerEvents"),
        BeanAspectCompareEventsUuids("beanAspectCompareEventsUuids");

        private final String value;

        NameBeans(String newValue) {
            value = newValue;
        }

        public String getValue() {
            return value;
        }
    }

    @Bean(name = "beanBaseEvent")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public JvBaseEvent beanBaseEvent(Object source, Object destination, UUID uuidKey, Object data) {
        return new JvBaseEvent(source, destination, uuidKey, data);
    }

    @Bean(name = "beanPublisherEvents")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvPublisherEvents beanPublisherEvents(AnnotationConfigApplicationContext context) {
        return new JvPublisherEvents(context);
    }

    @Bean(name = "beanMakerEvents")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvMakerEvents beanMakerEvents() {
        return new JvMakerEvents();
    }

    @Bean(name = "beanAspectCompareEventsUuids")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvAspectCheckerEvents beanAspectCompareEventsUuids() {
        return new JvAspectCheckerEvents();
    }
}