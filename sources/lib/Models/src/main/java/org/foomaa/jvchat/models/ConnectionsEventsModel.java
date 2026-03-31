package org.foomaa.jvchat.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.structobjects.*;

@Slf4j
public class ConnectionsEventsModel extends BaseModel {
    // DI ↓
    private final ConnectionEventStructObjectFactory connectionEventStructObjectFactory;

    @Builder
    ConnectionsEventsModel(RootStructObjectFactory rootStructObjectFactory, RootObjectsModel rootObjectsModel,
            ConnectionEventStructObjectFactory connectionEventStructObjectFactory) {
        super(Objects.requireNonNull(rootObjectsModel, "rootObjectsModel is mandatory"), Objects.requireNonNull(
                rootStructObjectFactory, "rootStructObjectFactory is mandatory"));

        this.connectionEventStructObjectFactory = Objects.requireNonNull(connectionEventStructObjectFactory,
                "connectionEventStructObjectFactory is mandatory");
    }

    public UUID createNewConnection(Object objectSender, Object objectReceiver, String customNameEvent,
            AnnotationConfigApplicationContext context) {
        ConnectionEventStructObject connectionObject = connectionEventStructObjectFactory.create(customNameEvent,
                objectReceiver, context, objectSender);

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
            if (connectionEventStructObject.getObjectSender() == objectSender && Objects.equals(
                    connectionEventStructObject.getCustomNameEvent(), customNameEvent)) {
                resList.add(connectionEventStructObject);
            }
        }

        return resList;
    }
}
