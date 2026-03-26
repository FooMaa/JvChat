package org.foomaa.jvchat.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import org.foomaa.jvchat.structobjects.*;

@Component
@Lazy
public class SocketRunnableCtrlModel extends BaseModel {
    private final SocketRunnableCtrlStructObjectFactory socketRunnableCtrlStructObjectFactory;

    SocketRunnableCtrlModel(SocketRunnableCtrlStructObjectFactory socketRunnableCtrlStructObjectFactory,
            RootStructObjectFactory rootStructObjectFactory, RootObjectsModel rootObjectsModel) {
        super(rootObjectsModel, rootStructObjectFactory);

        this.socketRunnableCtrlStructObjectFactory = socketRunnableCtrlStructObjectFactory;
    }

    public void createSocketRunnableCtrlStructObject(Runnable socketRunnableCtrl) {
        SocketRunnableCtrlStructObject socketStreamsStructObject = socketRunnableCtrlStructObjectFactory
                .create(socketRunnableCtrl);
        addItem(socketStreamsStructObject, getRootObject());
    }

    public List<SocketRunnableCtrlStructObject> getAllSocketRunnableCtrlStructObject() {
        List<SocketRunnableCtrlStructObject> resultList = new ArrayList<>();

        for (BaseStructObject baseStructObject : getRootObject().getChildren()) {
            SocketRunnableCtrlStructObject socketStreamsStructObject = (SocketRunnableCtrlStructObject) baseStructObject;
            resultList.add(socketStreamsStructObject);
        }

        return resultList;
    }

    private SocketRunnableCtrlStructObject findSocketRunnableCtrlStructObjectByRunnable(Runnable runnable) {
        for (BaseStructObject baseStructObject : getRootObject().getChildren()) {
            SocketRunnableCtrlStructObject socketRunnableCtrlStructObject = (SocketRunnableCtrlStructObject) baseStructObject;
            if (socketRunnableCtrlStructObject != null
                    && socketRunnableCtrlStructObject.getSocketRunnableCtrl() == runnable) {
                return socketRunnableCtrlStructObject;
            }
        }

        return null;
    }

    public SocketRunnableCtrlStructObject findCreateSocketRunnableCtrlStructObjectByRunnable(Runnable runnable) {
        SocketRunnableCtrlStructObject socketRunnableCtrlStructObject = findSocketRunnableCtrlStructObjectByRunnable(
                runnable);

        if (socketRunnableCtrlStructObject == null) {
            SocketRunnableCtrlStructObject newSocketRunnableCtrlStructObject = socketRunnableCtrlStructObjectFactory
                    .create(runnable);
            addItem(newSocketRunnableCtrlStructObject, getRootObject());
            return newSocketRunnableCtrlStructObject;
        }

        return socketRunnableCtrlStructObject;
    }

    public int getCountConnections() {
        return getRootObject().getChildren().size();
    }

    public boolean isEmpty() {
        return getRootObject().getChildren().isEmpty();
    }
}
