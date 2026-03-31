package org.foomaa.jvchat.structobjects;

import java.util.Objects;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import lombok.Builder;

public class ConnectionEventStructObjectFactory {
    private final ObjectProvider<ConnectionEventStructObject> connectionEventsObjectProvider;

    @Builder
    ConnectionEventStructObjectFactory(ObjectProvider<ConnectionEventStructObject> connectionEventsObjectProvider) {
        this.connectionEventsObjectProvider = Objects.requireNonNull(connectionEventsObjectProvider,
                "connectionEventsObjectProvider is mandatory");
    }

    public ConnectionEventStructObject create() {
        return connectionEventsObjectProvider.getObject();
    }

    public ConnectionEventStructObject create(String customNameEvent, Object objectReceiver,
            AnnotationConfigApplicationContext context, Object objectSender) {
        ConnectionEventStructObject connectionEventStructObject = connectionEventsObjectProvider.getObject();

        connectionEventStructObject.setCustomNameEvent(customNameEvent);
        connectionEventStructObject.setObjectReceiver(objectReceiver);
        connectionEventStructObject.setContext(context);
        connectionEventStructObject.setObjectSender(objectSender);

        return connectionEventStructObject;
    }
}
