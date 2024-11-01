package org.foomaa.jvchat.models;

import org.foomaa.jvchat.structobjects.JvGetterStructObjects;

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

    public void createNewCheckersOnline(Thread thread) {
        return;
    }
}
