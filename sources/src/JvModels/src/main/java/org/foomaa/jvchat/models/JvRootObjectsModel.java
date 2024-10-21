package org.foomaa.jvchat.models;

import org.foomaa.jvchat.structobjects.JvGetterStructObjects;

public class JvRootObjectsModel extends JvBaseModel {
    private static JvRootObjectsModel instance;

    private JvRootObjectsModel() {
        setRootObject(JvGetterStructObjects.getInstance()
                .getBeanRootStructObject(getNameModel()));
    }

    static JvRootObjectsModel getInstance() {
        if (instance == null) {
            instance = new JvRootObjectsModel();
        }
        return instance;
    }
}
