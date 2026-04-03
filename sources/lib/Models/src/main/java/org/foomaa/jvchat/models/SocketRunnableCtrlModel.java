package org.foomaa.jvchat.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Builder;

import org.foomaa.jvchat.structobjects.*;

public class SocketRunnableCtrlModel extends BaseModel {
    // DI ↓
    private final SocketRunnableCtrlStructObjectFactory socketRunnableCtrlStructObjectFactory;

    @Builder
    SocketRunnableCtrlModel(
            RootObjectsModel rootObjectsModel,
            SocketRunnableCtrlStructObjectFactory socketRunnableCtrlStructObjectFactory,
            RootStructObjectFactory rootStructObjectFactory) {
        super(
                Objects.requireNonNull(rootObjectsModel, "rootObjectsModel is mandatory"),
                Objects.requireNonNull(rootStructObjectFactory, "rootStructObjectFactory is mandatory"));

        this.socketRunnableCtrlStructObjectFactory = Objects.requireNonNull(
                socketRunnableCtrlStructObjectFactory, "socketRunnableCtrlStructObjectFactory is mandatory");
    }

    public void createSocketRunnableCtrlStructObject(Runnable socketRunnableCtrl) {
        SocketRunnableCtrlStructObject socketStreamsStructObject =
                socketRunnableCtrlStructObjectFactory.create(socketRunnableCtrl);
        addItem(socketStreamsStructObject, getRootObject());
    }

    public List<SocketRunnableCtrlStructObject> getAllSocketRunnableCtrlStructObject() {
        List<SocketRunnableCtrlStructObject> resultList = new ArrayList<>();

        for (BaseStructObject baseStructObject : getRootObject().getChildren()) {
            SocketRunnableCtrlStructObject socketStreamsStructObject =
                    (SocketRunnableCtrlStructObject) baseStructObject;
            resultList.add(socketStreamsStructObject);
        }

        return resultList;
    }

    private SocketRunnableCtrlStructObject findSocketRunnableCtrlStructObjectByRunnable(Runnable runnable) {
        for (BaseStructObject baseStructObject : getRootObject().getChildren()) {
            SocketRunnableCtrlStructObject socketRunnableCtrlStructObject =
                    (SocketRunnableCtrlStructObject) baseStructObject;
            if (socketRunnableCtrlStructObject != null
                    && socketRunnableCtrlStructObject.getSocketRunnableCtrl() == runnable) {
                return socketRunnableCtrlStructObject;
            }
        }

        return null;
    }

    public SocketRunnableCtrlStructObject findCreateSocketRunnableCtrlStructObjectByRunnable(Runnable runnable) {
        SocketRunnableCtrlStructObject socketRunnableCtrlStructObject =
                findSocketRunnableCtrlStructObjectByRunnable(runnable);

        if (socketRunnableCtrlStructObject == null) {
            SocketRunnableCtrlStructObject newSocketRunnableCtrlStructObject =
                    socketRunnableCtrlStructObjectFactory.create(runnable);
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
