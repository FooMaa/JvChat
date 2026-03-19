package org.foomaa.jvchat.structobjects;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
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
