package org.foomaa.jvchat.structobjects;

import java.util.UUID;


public class JvSocketRunnableCtrlStructObject extends JvBaseStructObject {
    private final UUID uuid;
    private Runnable socketRunnableCtrl;

    JvSocketRunnableCtrlStructObject() {
        uuid = UUID.randomUUID();
        socketRunnableCtrl = null;
        commitProperties();
    }

    public void setSocketRunnableCtrl(Runnable newSocketRunnableCtrl) {
        if (socketRunnableCtrl != newSocketRunnableCtrl) {
            socketRunnableCtrl = newSocketRunnableCtrl;
            commitProperties();
        }
    }

    public UUID getUuid() {
        return uuid;
    }

    public Runnable getSocketRunnableCtrl() {
        return socketRunnableCtrl;
    }
}
