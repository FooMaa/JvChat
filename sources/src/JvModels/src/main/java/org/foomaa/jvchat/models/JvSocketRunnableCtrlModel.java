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

    public void createSocketStreams(Runnable socketRunnableCtrl) {
        JvSocketRunnableCtrlStructObject socketStreamsStructObject =
                JvGetterStructObjects.getInstance().getBeanSocketRunnableCtrlStructObject();

        socketStreamsStructObject.setSocketRunnableCtrl(socketRunnableCtrl);
        addItem(socketStreamsStructObject, getRootObject());
    }

    public List<JvSocketRunnableCtrlStructObject> getAllSocketStreams() {
        List<JvSocketRunnableCtrlStructObject> resultList = new ArrayList<>();

        for (JvBaseStructObject baseStructObject : getRootObject().getChildren()) {
            JvSocketRunnableCtrlStructObject socketStreamsStructObject = (JvSocketRunnableCtrlStructObject) baseStructObject;
            resultList.add(socketStreamsStructObject);
        }

        return resultList;
    }
}
