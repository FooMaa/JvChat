package org.foomaa.jvchat.events;

import org.foomaa.jvchat.models.ConnectionsEventsModel;
import org.foomaa.jvchat.models.GetterModels;
import org.foomaa.jvchat.structobjects.JvConnectionEventStructObject;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.*;


public class MakerEvents {
    private final ConnectionsEventsModel connectionsEventsModel;

    MakerEvents() {
        connectionsEventsModel = GetterModels.getInstance().getBeanConnectionsEventsModel();
    }

    public void event(Object objectSender, String customNameEvent, Object... data) {
        List<JvConnectionEventStructObject> connections =
                connectionsEventsModel.findConnections(objectSender, customNameEvent);
        for (JvConnectionEventStructObject connection : connections) {
            UUID uuidKey = connection.getUuid();
            BaseEvent baseEvent = GetterEvents.getInstance().getBeanBaseEvent(objectSender, connection.getObjectReceiver(), uuidKey, data);
            publishEvent(baseEvent, connection.getContext());
        }
    }

    private void publishEvent(BaseEvent event, AnnotationConfigApplicationContext context) {
        GetterEvents.getInstance().getBeanPublisherEvents(context).publish(event);
    }

    public UUID addConnect(Object objectSender,
                           Object objectReceiver,
                           String customNameEvent,
                           AnnotationConfigApplicationContext context) {
        return connectionsEventsModel.createNewConnection(objectSender, objectReceiver, customNameEvent, context);
    }
}
