package org.foomaa.jvchat.events;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.UUID;


public class JvGetterEvents {
    private static JvGetterEvents instance;
    private final AnnotationConfigApplicationContext context;

    private JvGetterEvents() {
        context = new AnnotationConfigApplicationContext(
                JvEventsSpringConfig.class);
    }

    public static JvGetterEvents getInstance() {
        if (instance == null) {
            instance = new JvGetterEvents();
        }
        return instance;
    }

    public AnnotationConfigApplicationContext getContext() {
        return context;
    }

    public JvBaseEvent getBeanBaseEvent(Object source, Object destination, UUID uuidKey, Object... data) {
        return (JvBaseEvent) context.getBean(JvEventsSpringConfig.NameBeans.BeanBaseEvent.getValue(),
                source, destination, uuidKey, data);
    }

    public JvPublisherEvents getBeanPublisherEvents(AnnotationConfigApplicationContext context) {
        return (JvPublisherEvents) context.getBean(JvEventsSpringConfig.NameBeans.BeanPublisherEvents.getValue(), context);
    }

    public JvMakerEvents getBeanMakerEvents() {
        return context.getBean(JvEventsSpringConfig.NameBeans.BeanMakerEvents.getValue(), JvMakerEvents.class);
    }

    public JvAspectCheckerEvents getBeanAspectCompareEventsUuids() {
        return context.getBean(JvEventsSpringConfig.NameBeans.BeanAspectCompareEventsUuids.getValue(), JvAspectCheckerEvents.class);
    }
}
