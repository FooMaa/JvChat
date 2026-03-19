package org.foomaa.jvchat.models;

import org.foomaa.jvchat.structobjects.BaseStructObject;
import org.foomaa.jvchat.structobjects.GetterStructObjects;
import org.foomaa.jvchat.structobjects.SocketRunnableCtrlStructObject;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class SocketRunnableCtrlModel extends BaseModel {
    private final ObjectProvider<SocketRunnableCtrlStructObject> socketRunnableCtrlStructObjectObjectProvider;

    SocketRunnableCtrlModel(ObjectProvider<SocketRunnableCtrlStructObject> socketRunnableCtrlStructObjectObjectProvider) {
        this.socketRunnableCtrlStructObjectObjectProvider = socketRunnableCtrlStructObjectObjectProvider;

        setRootObject(GetterStructObjects.getInstance()
                .getBeanRootStructObject(getNameModel()));
    }

    public void createSocketRunnableCtrlStructObject(Runnable socketRunnableCtrl) {
        SocketRunnableCtrlStructObject socketStreamsStructObject =
                socketRunnableCtrlStructObjectObjectProvider.getObject();
        socketStreamsStructObject.setSocketRunnableCtrl(socketRunnableCtrl);
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
        for (BaseStructObject baseStructObject: getRootObject().getChildren()) {
            SocketRunnableCtrlStructObject socketRunnableCtrlStructObject =
                    (SocketRunnableCtrlStructObject) baseStructObject;
            if (socketRunnableCtrlStructObject != null &&
                    socketRunnableCtrlStructObject.getSocketRunnableCtrl() == runnable) {
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
                    socketRunnableCtrlStructObjectObjectProvider.getObject();
            newSocketRunnableCtrlStructObject.setSocketRunnableCtrl(runnable);
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
