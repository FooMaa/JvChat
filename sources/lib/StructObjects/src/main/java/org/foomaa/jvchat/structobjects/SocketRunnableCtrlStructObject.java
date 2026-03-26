package org.foomaa.jvchat.structobjects;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Scope("prototype")
@Getter
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
}
