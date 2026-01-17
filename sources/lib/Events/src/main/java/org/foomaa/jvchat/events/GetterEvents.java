package org.foomaa.jvchat.events;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.UUID;


public class GetterEvents {
    private static GetterEvents instance;
    private final AnnotationConfigApplicationContext context;

    private GetterEvents() {
        context = new AnnotationConfigApplicationContext(EventsSpringConfig.class);
    }

    public static GetterEvents getInstance() {
        if (instance == null) {
            instance = new GetterEvents();
        }
        return instance;
    }

    public AnnotationConfigApplicationContext getContext() {
        return context;
    }

    public BaseEvent getBeanBaseEvent(Object source, Object destination, UUID uuidKey, Object... data) {
        return (BaseEvent) context.getBean(EventsSpringConfig.NameBeans.BeanBaseEvent.getValue(),
                source, destination, uuidKey, data);
    }

    public PublisherEvents getBeanPublisherEvents(AnnotationConfigApplicationContext context) {
        return (PublisherEvents) context.getBean(EventsSpringConfig.NameBeans.BeanPublisherEvents.getValue(), context);
    }

    public MakerEvents getBeanMakerEvents() {
        return context.getBean(EventsSpringConfig.NameBeans.BeanMakerEvents.getValue(), MakerEvents.class);
    }

    public AspectCheckerEvents getBeanAspectCompareEventsUuids() {
        return context.getBean(EventsSpringConfig.NameBeans.BeanAspectCompareEventsUuids.getValue(), AspectCheckerEvents.class);
    }
}
