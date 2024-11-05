package org.foomaa.jvchat.models;

import org.foomaa.jvchat.structobjects.JvBaseStructObject;
import org.foomaa.jvchat.structobjects.JvCheckerOnlineStructObject;
import org.foomaa.jvchat.structobjects.JvGetterStructObjects;

import java.util.ArrayList;
import java.util.List;

public class JvCheckersOnlineModel extends JvBaseModel {
    private static JvCheckersOnlineModel instance;

    private JvCheckersOnlineModel() {
        setRootObject(JvGetterStructObjects.getInstance()
                .getBeanRootStructObject(getNameModel()));
    }

    static JvCheckersOnlineModel getInstance() {
        if (instance == null) {
            instance = new JvCheckersOnlineModel();
        }
        return instance;
    }

    //NOTE(VAD): заполнение из JvCheckerOnlineCtrl
    public JvCheckerOnlineStructObject createNewCheckersOnline() {
        JvCheckerOnlineStructObject checkerOnlineStructObject =
                JvGetterStructObjects.getInstance().getBeanCheckerOnlineStructObject();

        addItem(checkerOnlineStructObject, getRootObject());
        return checkerOnlineStructObject;
    }

    public List<JvCheckerOnlineStructObject> getAllCheckersOnline() {
        List<JvCheckerOnlineStructObject> resultList = new ArrayList<>();

        for (JvBaseStructObject baseStructObject : getRootObject().getChildren()) {
            JvCheckerOnlineStructObject checkerOnlineStructObject =
                    (JvCheckerOnlineStructObject) baseStructObject;
            resultList.add(checkerOnlineStructObject);
        }

        return resultList;
    }
}
