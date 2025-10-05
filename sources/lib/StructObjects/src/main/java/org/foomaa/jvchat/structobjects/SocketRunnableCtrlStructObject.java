package org.foomaa.jvchat.structobjects;


public class SocketRunnableCtrlStructObject extends BaseStructObject {
    private Runnable socketRunnableCtrl;

    SocketRunnableCtrlStructObject() {
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
