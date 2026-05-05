package org.foomaa.jvchat.structobjects;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SocketRunnableCtrlStructObject extends BaseStructObject {
    private Runnable socketRunnableCtrl;

    @Builder
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
}
