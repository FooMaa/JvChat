package org.foomaa.jvchat.models;

import org.foomaa.jvchat.structobjects.JvGetterStructObjects;

public class JvChatsModel extends JvBaseModel {
    private static JvChatsModel instance;

    private JvChatsModel() {
        setRootObject(JvGetterStructObjects.getInstance()
                .getBeanRootStructObject(getNameModel()));
    }

    static JvChatsModel getInstance() {
        if (instance == null) {
            instance = new JvChatsModel();
        }
        return instance;
    }
}
