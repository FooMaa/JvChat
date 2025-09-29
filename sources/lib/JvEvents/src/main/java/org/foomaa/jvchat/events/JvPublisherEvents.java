package org.foomaa.jvchat.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class JvPublisherEvents {
    private final ApplicationEventPublisher publisher;

    JvPublisherEvents(AnnotationConfigApplicationContext context) {
        publisher = context;
    }

    public void publish(JvBaseEvent event) {
        publisher.publishEvent(event);
    }
}
