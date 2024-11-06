package org.foomaa.jvchat.models;

import org.foomaa.jvchat.structobjects.JvBaseStructObject;
import org.foomaa.jvchat.structobjects.JvGetterStructObjects;
import org.foomaa.jvchat.structobjects.JvSocketRunnableCtrlStructObject;

import java.util.ArrayList;
import java.util.List;


public class JvSocketRunnableCtrlModel extends JvBaseModel {
    private static JvSocketRunnableCtrlModel instance;

    private JvSocketRunnableCtrlModel() {
        setRootObject(JvGetterStructObjects.getInstance()
                .getBeanRootStructObject(getNameModel()));
    }

    static JvSocketRunnableCtrlModel getInstance() {
        if (instance == null) {
            instance = new JvSocketRunnableCtrlModel();
        }
        return instance;
    }

    public void createSocketRunnableCtrlStructObject(Runnable socketRunnableCtrl) {
        JvSocketRunnableCtrlStructObject socketStreamsStructObject =
                JvGetterStructObjects.getInstance().getBeanSocketRunnableCtrlStructObject();
        socketStreamsStructObject.setSocketRunnableCtrl(socketRunnableCtrl);
        addItem(socketStreamsStructObject, getRootObject());
    }

    public List<JvSocketRunnableCtrlStructObject> getAllSocketRunnableCtrlStructObject() {
        List<JvSocketRunnableCtrlStructObject> resultList = new ArrayList<>();

        for (JvBaseStructObject baseStructObject : getRootObject().getChildren()) {
            JvSocketRunnableCtrlStructObject socketStreamsStructObject = (JvSocketRunnableCtrlStructObject) baseStructObject;
            resultList.add(socketStreamsStructObject);
        }

        return resultList;
    }

    private JvSocketRunnableCtrlStructObject findSocketRunnableCtrlStructObjectByRunnable(Runnable runnable) {
        for (JvBaseStructObject baseStructObject: getRootObject().getChildren()) {
            JvSocketRunnableCtrlStructObject socketRunnableCtrlStructObject =
                    (JvSocketRunnableCtrlStructObject) baseStructObject;
            if (socketRunnableCtrlStructObject != null &&
                    socketRunnableCtrlStructObject.getSocketRunnableCtrl() == runnable) {
                return socketRunnableCtrlStructObject;
            }
        }

        return null;
    }

    public JvSocketRunnableCtrlStructObject findCreateSocketRunnableCtrlStructObjectByRunnable(Runnable runnable) {
        JvSocketRunnableCtrlStructObject socketRunnableCtrlStructObject =
                findSocketRunnableCtrlStructObjectByRunnable(runnable);

        if (socketRunnableCtrlStructObject == null) {
            JvSocketRunnableCtrlStructObject newSocketRunnableCtrlStructObject =
                    JvGetterStructObjects.getInstance().getBeanSocketRunnableCtrlStructObject();
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
