package org.foomaa.jvchat.ctrl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Objects;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.models.SocketRunnableCtrlModel;
import org.foomaa.jvchat.network.ServersSocket;
import org.foomaa.jvchat.network.UsersSocket;
import org.foomaa.jvchat.settings.MainSettings;
import org.foomaa.jvchat.structobjects.SocketRunnableCtrlStructObject;

@Slf4j
public class NetworkCtrl {
    // DI ↓
    private final ServersSocket serversSocket;
    private final UsersSocket usersSocket;
    private final TakeMessagesCtrl takeMessagesCtrl;
    private final OnlineServersCtrl onlineServersCtrl;
    private final MainSettings mainSettings;
    private final SocketRunnableCtrlModel socketRunnableCtrlModel;
    private final SocketRunnableCtrlFactory socketRunnableCtrlFactory;

    // DI(P) ↓
    private SocketRunnableCtrl currentSocketRunnableCtrl;

    @Builder
    NetworkCtrl(
            MainSettings mainSettings,
            SocketRunnableCtrlModel socketRunnableCtrlModel,
            TakeMessagesCtrl takeMessagesCtrl,
            ServersSocket serversSocket,
            UsersSocket usersSocket,
            OnlineServersCtrl onlineServersCtrl,
            SocketRunnableCtrlFactory socketRunnableCtrlFactory) {
        this.mainSettings = Objects.requireNonNull(mainSettings, "mainSettings is mandatory");
        this.socketRunnableCtrlModel =
                Objects.requireNonNull(socketRunnableCtrlModel, "socketRunnableCtrlModel is mandatory");
        this.takeMessagesCtrl = Objects.requireNonNull(takeMessagesCtrl, "takeMessagesCtrl is mandatory");
        this.socketRunnableCtrlFactory =
                Objects.requireNonNull(socketRunnableCtrlFactory, "socketRunnableCtrlFactory is mandatory");

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
            SocketRunnableCtrl socketRunnableCtrl = socketRunnableCtrlFactory.create(fromSocketServer);
            Thread threadServers = new Thread(socketRunnableCtrl);
            threadServers.start();
        }
    }

    private void startUsersNetwork() throws IOException {
        usersSocket.start();

        currentSocketRunnableCtrl = socketRunnableCtrlFactory.create(usersSocket.getCurrentSocket());
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
                log.warn(
                        "Number of active connections after cleaning: {}",
                        socketRunnableCtrlModel.getCountConnections());
            }
        }

        try {
            Thread.sleep(milliSecondsSleepAfterOperation);
        } catch (InterruptedException exception) {
            log.error("Thread.sleep() failed to running here.");
        }
    }
}
