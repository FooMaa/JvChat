package org.foomaa.jvchat.models;

import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.structobjects.JvBaseStructObject;
import org.foomaa.jvchat.structobjects.JvConnectionEventStructObject;
import org.foomaa.jvchat.structobjects.JvGetterStructObjects;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class JvConnectionsEventsModel extends JvBaseModel {
    JvConnectionsEventsModel() {
        setRootObject(JvGetterStructObjects.getInstance()
                .getBeanRootStructObject(getNameModel()));
    }

    public UUID createNewConnection(Class<?> classSender,
                                    Class<?> classReceiver,
                                    AnnotationConfigApplicationContext context) {
        JvConnectionEventStructObject connectionObject =
                JvGetterStructObjects.getInstance().getBeanConnectionEventStructObject();

        connectionObject.setClassSender(classSender);
        connectionObject.setClassReceiver(classReceiver);
        connectionObject.setContext(context);

        addItem(connectionObject, getRootObject());

        return connectionObject.getUuid();
    }

    public void createNewConnection(Object objectSender,
                                    Object objectReceiver,
                                    AnnotationConfigApplicationContext context) {
        JvConnectionEventStructObject connectionObject =
                JvGetterStructObjects.getInstance().getBeanConnectionEventStructObject();

        connectionObject.setClassSender(objectSender.getClass());
        connectionObject.setClassReceiver(objectReceiver.getClass());
        connectionObject.setObjectSender(objectSender);
        connectionObject.setObjectReceiver(objectReceiver);
        connectionObject.setContext(context);

        addItem(connectionObject, getRootObject());
    }

    public List<JvConnectionEventStructObject> findConnectionsByObjectSender(Class<?> classSender) {
        List<JvConnectionEventStructObject> resList = new ArrayList<>();

        for (JvBaseStructObject baseStructObject : getRootObject().getChildren()) {
            JvConnectionEventStructObject connectionEventStructObject = (JvConnectionEventStructObject) baseStructObject;
            if (connectionEventStructObject == null) {
                JvLog.write(JvLog.TypeLog.Error, "Сюда попал объект chatStructObject, который null");
                continue;
            }
            if (connectionEventStructObject.getClassSender() == classSender) {
                resList.add(connectionEventStructObject);
            }
        }

        return resList;
    }
}
