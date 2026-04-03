package org.foomaa.jvchat.structobjects;

import java.util.Objects;

import org.springframework.beans.factory.ObjectProvider;

import lombok.Builder;

public class SocketRunnableCtrlStructObjectFactory {
    // DI ↓
    private final ObjectProvider<SocketRunnableCtrlStructObject> ctrlStructObjectObjectProvider;

    @Builder
    SocketRunnableCtrlStructObjectFactory(
            ObjectProvider<SocketRunnableCtrlStructObject> ctrlStructObjectObjectProvider) {
        this.ctrlStructObjectObjectProvider =
                Objects.requireNonNull(ctrlStructObjectObjectProvider, "ctrlStructObjectObjectProvider is mandatory");
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
