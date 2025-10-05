package org.foomaa.jvchat.models;

import org.foomaa.jvchat.structobjects.BaseStructObject;
import org.foomaa.jvchat.structobjects.GetterStructObjects;
import org.foomaa.jvchat.structobjects.SocketRunnableCtrlStructObject;

import java.util.ArrayList;
import java.util.List;


public class SocketRunnableCtrlModel extends BaseModel {
    SocketRunnableCtrlModel() {
        setRootObject(GetterStructObjects.getInstance()
                .getBeanRootStructObject(getNameModel()));
    }

    public void createSocketRunnableCtrlStructObject(Runnable socketRunnableCtrl) {
        SocketRunnableCtrlStructObject socketStreamsStructObject =
                GetterStructObjects.getInstance().getBeanSocketRunnableCtrlStructObject();
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
                    GetterStructObjects.getInstance().getBeanSocketRunnableCtrlStructObject();
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
