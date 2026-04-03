package org.foomaa.jvchat.ctrl;

import java.net.Socket;
import java.util.Objects;

import org.springframework.beans.factory.ObjectProvider;

import lombok.Builder;

public class SocketRunnableCtrlFactory {
    // DI ↓
    private final ObjectProvider<SocketRunnableCtrl> socketRunnableCtrlObjectProvider;

    @Builder
    SocketRunnableCtrlFactory(ObjectProvider<SocketRunnableCtrl> socketRunnableCtrlObjectProvider) {
        this.socketRunnableCtrlObjectProvider = Objects.requireNonNull(
                socketRunnableCtrlObjectProvider, "socketRunnableCtrlObjectProvider is mandatory");
    }

    public SocketRunnableCtrl create(Socket socket) {
        SocketRunnableCtrl socketRunnableCtrl = socketRunnableCtrlObjectProvider.getObject();
        socketRunnableCtrl.setSocket(socket);
        return socketRunnableCtrl;
    }
}
