package org.foomaa.jvchat.structobjects;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ConnectionEventStructObjectFactory {
    private final ObjectProvider<ConnectionEventStructObject> connectionEventsObjectProvider;

    ConnectionEventStructObjectFactory(
            ObjectProvider<ConnectionEventStructObject> connectionEventsObjectProvider) {
        this.connectionEventsObjectProvider = connectionEventsObjectProvider;
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
