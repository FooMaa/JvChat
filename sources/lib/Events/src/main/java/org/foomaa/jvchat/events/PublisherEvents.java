package org.foomaa.jvchat.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class PublisherEvents {
    private final ApplicationEventPublisher publisher;

    PublisherEvents(AnnotationConfigApplicationContext context) {
        publisher = context;
    }

    public void publish(BaseEvent event) {
        publisher.publishEvent(event);
    }
}
