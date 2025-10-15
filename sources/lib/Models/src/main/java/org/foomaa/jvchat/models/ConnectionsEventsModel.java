package org.foomaa.jvchat.models;

import org.foomaa.jvchat.logger.Log;
import org.foomaa.jvchat.structobjects.BaseStructObject;
import org.foomaa.jvchat.structobjects.ConnectionEventStructObject;
import org.foomaa.jvchat.structobjects.GetterStructObjects;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class ConnectionsEventsModel extends BaseModel {
    ConnectionsEventsModel() {
        setRootObject(GetterStructObjects.getInstance()
                .getBeanRootStructObject(getNameModel()));
    }

    public UUID createNewConnection(Object objectSender,
                                    Object objectReceiver,
                                    String customNameEvent,
                                    AnnotationConfigApplicationContext context) {
        ConnectionEventStructObject connectionObject =
                GetterStructObjects.getInstance().getBeanConnectionEventStructObject();

        connectionObject.setObjectSender(objectSender);
        connectionObject.setCustomNameEvent(customNameEvent);
        connectionObject.setObjectReceiver(objectReceiver);
        connectionObject.setContext(context);

        addItem(connectionObject, getRootObject());

        return connectionObject.getUuid();
    }

    public List<ConnectionEventStructObject> findConnections(Object objectSender, String customNameEvent) {
        List<ConnectionEventStructObject> resList = new ArrayList<>();

        for (BaseStructObject baseStructObject : getRootObject().getChildren()) {
            ConnectionEventStructObject connectionEventStructObject = (ConnectionEventStructObject) baseStructObject;
            if (connectionEventStructObject == null) {
                Log.write(Log.TypeLog.Error, "This includes the chatStructObject object, which is null.");
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
