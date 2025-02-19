package org.foomaa.jvchat.events;

import org.springframework.context.ApplicationEvent;

import java.util.UUID;


public class JvBaseEvent extends ApplicationEvent {
    private final UUID uuidKey;
    private final Object destination;
    private final Object data;

    JvBaseEvent(Object source, Object newDestination, UUID newUuidKey, Object newData) {
        super(source);
        destination = newDestination;
        data = newData;
        uuidKey = newUuidKey;
    }

    public UUID getUuidKey() {
        return uuidKey;
    }

    public Object getDestination() {
        return destination;
    }

    public Object getData() {
        return data;
    }
}