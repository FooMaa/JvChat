package org.foomaa.jvchat.ctrl;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.net.Socket;


@Component
@Profile("users")
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
