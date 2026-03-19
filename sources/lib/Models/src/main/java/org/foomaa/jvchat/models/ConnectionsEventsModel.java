package org.foomaa.jvchat.models;

import org.foomaa.jvchat.structobjects.BaseStructObject;
import org.foomaa.jvchat.structobjects.ConnectionEventStructObject;
import org.foomaa.jvchat.structobjects.RootStructObject;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
@Lazy
@Slf4j
public class ConnectionsEventsModel extends BaseModel {
    private final ObjectProvider<ConnectionEventStructObject> connectionEventStructObjectObjectProvider;

    ConnectionsEventsModel(ObjectProvider<RootStructObject> rootStructObjectObjectProvider,
                           RootObjectsModel rootObjectsModel,
                           ObjectProvider<ConnectionEventStructObject> connectionEventStructObjectObjectProvider) {
        super(rootObjectsModel, rootStructObjectObjectProvider);

        this.connectionEventStructObjectObjectProvider = connectionEventStructObjectObjectProvider;
    }

    public UUID createNewConnection(Object objectSender,
                                    Object objectReceiver,
                                    String customNameEvent,
                                    AnnotationConfigApplicationContext context) {
        ConnectionEventStructObject connectionObject = connectionEventStructObjectObjectProvider.getObject();

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
                log.error("This includes the chatStructObject object, which is null.");
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
