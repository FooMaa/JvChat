package org.foomaa.jvchat.structobjects;


public class JvSocketRunnableCtrlStructObject extends JvBaseStructObject {
    private Runnable socketRunnableCtrl;

    JvSocketRunnableCtrlStructObject() {
        socketRunnableCtrl = null;
        commitProperties();
    }

    public void setSocketRunnableCtrl(Runnable newSocketRunnableCtrl) {
        if (socketRunnableCtrl != newSocketRunnableCtrl) {
            socketRunnableCtrl = newSocketRunnableCtrl;
            commitProperties();
        }
    }

    public Runnable getSocketRunnableCtrl() {
        return socketRunnableCtrl;
    }
}
