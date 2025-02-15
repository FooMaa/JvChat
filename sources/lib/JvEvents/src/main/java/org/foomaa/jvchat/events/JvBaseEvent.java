package org.foomaa.jvchat.events;

import org.springframework.context.ApplicationEvent;

import java.util.UUID;


public class JvBaseEvent extends ApplicationEvent {
    private final UUID uuidKey;
    private final Object data;

    JvBaseEvent(Object source, UUID newUuidKey, Object newData) {
        super(source);

        data = newData;
        uuidKey = newUuidKey;
    }

    public Object getData() {
        return data;
    }

    public UUID getUuidKey() {
        return uuidKey;
    }
}