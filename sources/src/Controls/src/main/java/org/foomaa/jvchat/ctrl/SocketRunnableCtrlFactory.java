package org.foomaa.jvchat.ctrl;

import java.net.Socket;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

@Component
public class SocketRunnableCtrlFactory {
    private final ObjectProvider<SocketRunnableCtrl> socketRunnableCtrlObjectProvider;

    SocketRunnableCtrlFactory(ObjectProvider<SocketRunnableCtrl> socketRunnableCtrlObjectProvider) {
        this.socketRunnableCtrlObjectProvider = socketRunnableCtrlObjectProvider;
    }

    public SocketRunnableCtrl create(Socket socket) {
        SocketRunnableCtrl socketRunnableCtrl = socketRunnableCtrlObjectProvider.getObject();
        socketRunnableCtrl.setSocket(socket);
        return socketRunnableCtrl;
    }
}
