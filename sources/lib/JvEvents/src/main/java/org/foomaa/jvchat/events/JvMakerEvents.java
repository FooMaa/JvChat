package org.foomaa.jvchat.events;

import org.foomaa.jvchat.models.JvConnectionsEventsModel;
import org.foomaa.jvchat.models.JvGetterModels;
import org.foomaa.jvchat.structobjects.JvConnectionEventStructObject;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.*;


public class JvMakerEvents {
    private final JvConnectionsEventsModel connectionsEventsModel;

    JvMakerEvents() {
        connectionsEventsModel = JvGetterModels.getInstance().getBeanConnectionsEventsModel();
    }

    public void event(Object objectSender, Object data) {
        List<JvConnectionEventStructObject> connections =
                connectionsEventsModel.findConnectionsByObjectSender(objectSender.getClass());
        for (JvConnectionEventStructObject connection : connections) {
            UUID uuidKey = connection.getUuid();
            JvBaseEvent baseEvent = JvGetterEvents.getInstance().getBeanBaseEvent(objectSender, uuidKey, data);
            publishEvent(baseEvent, connection.getContext());
        }
    }

    private void publishEvent(JvBaseEvent event, AnnotationConfigApplicationContext context) {
        JvGetterEvents.getInstance().getBeanPublisherEvents(context).publish(event);
    }

    public UUID addConnect(Class<?> clazzSender, Class<?> clazzReceiver, AnnotationConfigApplicationContext context) {
        return connectionsEventsModel.createNewConnection(clazzSender, clazzReceiver, context);
    }
}
