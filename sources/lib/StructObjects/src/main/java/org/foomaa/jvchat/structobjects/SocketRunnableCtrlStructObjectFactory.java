package org.foomaa.jvchat.structobjects;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

@Component
public class SocketRunnableCtrlStructObjectFactory {
    private final ObjectProvider<SocketRunnableCtrlStructObject> ctrlStructObjectObjectProvider;

    SocketRunnableCtrlStructObjectFactory(
            ObjectProvider<SocketRunnableCtrlStructObject> ctrlStructObjectObjectProvider) {
        this.ctrlStructObjectObjectProvider = ctrlStructObjectObjectProvider;
    }

    public SocketRunnableCtrlStructObject create() {
        return ctrlStructObjectObjectProvider.getObject();
    }

    public SocketRunnableCtrlStructObject create(Runnable socketRunnableCtrl) {
        SocketRunnableCtrlStructObject structObject = ctrlStructObjectObjectProvider.getObject();

        structObject.setSocketRunnableCtrl(socketRunnableCtrl);

        return structObject;
    }
}
