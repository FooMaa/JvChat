package org.foomaa.jvchat.events;

import java.util.*;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.foomaa.jvchat.models.ConnectionsEventsModel;
import org.foomaa.jvchat.structobjects.ConnectionEventStructObject;

public class MakerEvents {
    private final ConnectionsEventsModel connectionsEventsModel;

    MakerEvents(ConnectionsEventsModel connectionsEventsModel) {
        this.connectionsEventsModel = connectionsEventsModel;
    }

    public void event(Object objectSender, String customNameEvent, Object... data) {
        List<ConnectionEventStructObject> connections =
                connectionsEventsModel.findConnections(objectSender, customNameEvent);
        for (ConnectionEventStructObject connection : connections) {
            UUID uuidKey = connection.getUuid();
            BaseEvent baseEvent = GetterEvents.getInstance()
                    .getBeanBaseEvent(objectSender, connection.getObjectReceiver(), uuidKey, data);
            publishEvent(baseEvent, connection.getContext());
        }
    }

    private void publishEvent(BaseEvent event, AnnotationConfigApplicationContext context) {
        GetterEvents.getInstance().getBeanPublisherEvents(context).publish(event);
    }

    public UUID addConnect(
            Object objectSender,
            Object objectReceiver,
            String customNameEvent,
            AnnotationConfigApplicationContext context) {
        return connectionsEventsModel.createNewConnection(objectSender, objectReceiver, customNameEvent, context);
    }
}
