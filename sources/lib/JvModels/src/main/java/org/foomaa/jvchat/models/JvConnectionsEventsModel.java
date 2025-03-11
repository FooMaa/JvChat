package org.foomaa.jvchat.models;

import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.structobjects.JvBaseStructObject;
import org.foomaa.jvchat.structobjects.JvConnectionEventStructObject;
import org.foomaa.jvchat.structobjects.JvGetterStructObjects;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class JvConnectionsEventsModel extends JvBaseModel {
    JvConnectionsEventsModel() {
        setRootObject(JvGetterStructObjects.getInstance()
                .getBeanRootStructObject(getNameModel()));
    }

    public UUID createNewConnection(Object objectSender,
                                    Object objectReceiver,
                                    String customNameEvent,
                                    AnnotationConfigApplicationContext context) {
        JvConnectionEventStructObject connectionObject =
                JvGetterStructObjects.getInstance().getBeanConnectionEventStructObject();

        connectionObject.setObjectSender(objectSender);
        connectionObject.setCustomNameEvent(customNameEvent);
        connectionObject.setObjectReceiver(objectReceiver);
        connectionObject.setContext(context);

        addItem(connectionObject, getRootObject());

        return connectionObject.getUuid();
    }

    public List<JvConnectionEventStructObject> findConnections(Object objectSender, String customNameEvent) {
        List<JvConnectionEventStructObject> resList = new ArrayList<>();

        for (JvBaseStructObject baseStructObject : getRootObject().getChildren()) {
            JvConnectionEventStructObject connectionEventStructObject = (JvConnectionEventStructObject) baseStructObject;
            if (connectionEventStructObject == null) {
                JvLog.write(JvLog.TypeLog.Error, "This includes the chatStructObject object, which is null.");
                continue;
            }
            if (connectionEventStructObject.getObjectSender() == objectSender &&
                    Objects.equals(connectionEventStructObject.getCustomNameEvent(), customNameEvent)) {
                resList.add(connectionEventStructObject);
            }
        }

        return resList;
    }
}
