package org.foomaa.jvchat.events;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;
import java.util.UUID;


@Configuration
@EnableAspectJAutoProxy
@EnableAutoConfiguration // Will change it later
class EventsSpringConfig {
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
    public BaseEvent beanBaseEvent(Object source, Object destination, UUID uuidKey, Object... data) {
        return new BaseEvent(source, destination, uuidKey, data);
    }

    @Bean(name = "beanPublisherEvents")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public PublisherEvents beanPublisherEvents(AnnotationConfigApplicationContext context) {
        return new PublisherEvents(context);
    }

//    @Bean(name = "beanMakerEvents")
//    @Scope("singleton")
//    @SuppressWarnings("unused")
//    public MakerEvents beanMakerEvents() {
//        return new MakerEvents();
//    }

    @Bean(name = "beanAspectCompareEventsUuids")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public AspectCheckerEvents beanAspectCompareEventsUuids() {
        return new AspectCheckerEvents();
    }
}