package org.foomaa.jvchat.models;

import org.foomaa.jvchat.structobjects.JvGetterStructObjects;
import org.foomaa.jvchat.structobjects.JvServerConnectionThread;

public class JvServersConnectionsModel extends JvBaseModel {
    private static JvServersConnectionsModel instance;

    private JvServersConnectionsModel() {
        setRootObject(JvGetterStructObjects.getInstance()
                .getBeanRootStructObject(getNameModel()));
    }

    static JvServersConnectionsModel getInstance() {
        if (instance == null) {
            instance = new JvServersConnectionsModel();
        }
        return instance;
    }

    public void createNewServerConnection(Thread thread) {
        JvServerConnectionThread serverConnectionThread = JvGetterStructObjects.getInstance().getBeanServerConnectionThread();
        serverConnectionThread.setThread(thread);
    }
}
