package org.foomaa.jvchat.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.models.SocketRunnableCtrlModel;
import org.foomaa.jvchat.network.UsersSocket;
import org.foomaa.jvchat.settings.MainSettings;
import org.foomaa.jvchat.network.ServersSocket;
import org.foomaa.jvchat.structobjects.SocketRunnableCtrlStructObject;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class NetworkCtrl {
    private final ServersSocket serversSocket;
    private  final UsersSocket usersSocket;
    private SocketRunnableCtrl currentSocketRunnableCtrl;
    private final TakeMessagesCtrl takeMessagesCtrl;
    private final OnlineServersCtrl onlineServersCtrl;
    private final MainSettings mainSettings;
    private final SocketRunnableCtrlModel socketRunnableCtrlModel;

    NetworkCtrl(MainSettings mainSettings,
                SocketRunnableCtrlModel socketRunnableCtrlModel,
                TakeMessagesCtrl takeMessagesCtrl,
                @Autowired(required = false) ServersSocket serversSocket,
                @Autowired(required = false) UsersSocket usersSocket,
                @Autowired(required = false) OnlineServersCtrl onlineServersCtrl) {
        this.mainSettings = mainSettings;
        this.socketRunnableCtrlModel = socketRunnableCtrlModel;
        this.takeMessagesCtrl = takeMessagesCtrl;
        this.serversSocket = serversSocket;
        this.usersSocket = usersSocket;
        this.onlineServersCtrl = onlineServersCtrl;
    }

    public void startNetwork() throws IOException {
        if (mainSettings.getProfile() == MainSettings.TypeProfiles.SERVERS) {
            startServersNetwork();
        } else if (mainSettings.getProfile() == MainSettings.TypeProfiles.USERS) {
            startUsersNetwork();
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void startServersNetwork() throws IOException {
        serversSocket.start();

        ServerSocket socketServer = serversSocket.getSocketServers();
        onlineServersCtrl.loadDataOnlineUsers();
        runningErrorsControlSockets();
        while (true) {
            Socket fromSocketServer = socketServer.accept();
            SocketRunnableCtrl socketRunnableCtrl =
                    GetterControls.getInstance().getBeanSocketRunnableCtrl(fromSocketServer);
            Thread threadServers = new Thread(socketRunnableCtrl);
            threadServers.start();
        }
    }

    private void startUsersNetwork() throws IOException {
        currentSocketRunnableCtrl = GetterControls.getInstance().getBeanSocketRunnableCtrl(usersSocket.getCurrentSocket());
        if (!usersSocket.getCurrentSocket().isConnected()) {
            throw new IOException();
        }
        Thread threadUsers = new Thread(currentSocketRunnableCtrl);
        threadUsers.start();
    }

    public void takeMessage(byte[] message, SocketRunnableCtrl runnableCtrl) {
        if (mainSettings.getProfile() == MainSettings.TypeProfiles.SERVERS) {
            currentSocketRunnableCtrl = runnableCtrl;
            takeMessagesCtrl.setRunnableCtrlFromConnection(currentSocketRunnableCtrl);
        }
        takeMessagesCtrl.takeMessage(message);
    }

    public void sendMessage(byte[] message) {
        currentSocketRunnableCtrl.send(message);
    }

    public void sendMessageByRunnableCtrl(byte[] message, Runnable runnable) {
        if (mainSettings.getProfile() == MainSettings.TypeProfiles.SERVERS) {
            SocketRunnableCtrl srvRunnable = (SocketRunnableCtrl) runnable;
            srvRunnable.send(message);
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void runningErrorsControlSockets() {
        Runnable listenErrorSocket = () -> {
            while (true) {
                controlErrorConnectionSocket();
            }
        };

        Thread thread = new Thread(listenErrorSocket);
        thread.start();
    }

    private void controlErrorConnectionSocket() {
        List<SocketRunnableCtrlStructObject> listAllConnections =
                socketRunnableCtrlModel.getAllSocketRunnableCtrlStructObject();

        int milliSecondsSleepAfterOperation = 10000;

        for (SocketRunnableCtrlStructObject socketCtrl : listAllConnections) {
            SocketRunnableCtrl socketRunnableCtrl = (SocketRunnableCtrl) socketCtrl.getSocketRunnableCtrl();

            if (socketRunnableCtrl != null && socketRunnableCtrl.isErrorsExceedsLimit()) {
                log.warn("We clean up a thread that has not responded for a long time.");
                socketRunnableCtrlModel.removeItem(socketCtrl);
                log.warn("Number of active connections after cleaning: {}", socketRunnableCtrlModel.getCountConnections());
            }
        }

        try {
            Thread.sleep(milliSecondsSleepAfterOperation);
        } catch (InterruptedException exception) {
            log.error("Thread.sleep() failed to running here.");
        }
    }
}