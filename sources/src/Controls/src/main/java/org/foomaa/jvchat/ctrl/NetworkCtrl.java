package org.foomaa.jvchat.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import org.foomaa.jvchat.logger.Log;
import org.foomaa.jvchat.models.GetterModels;
import org.foomaa.jvchat.models.SocketRunnableCtrlModel;
import org.foomaa.jvchat.network.UsersSocket;
import org.foomaa.jvchat.settings.GetterSettings;
import org.foomaa.jvchat.settings.MainSettings;
import org.foomaa.jvchat.network.ServersSocket;
import org.foomaa.jvchat.structobjects.SocketRunnableCtrlStructObject;
import org.springframework.stereotype.Component;


@Component
public class NetworkCtrl {
    private final ServersSocket serversSocket;
    private  final UsersSocket usersSocket;
    private SocketRunnableCtrl currentSocketRunnableCtrl;
    private final OnlineServersCtrl onlineServersCtrl;

    NetworkCtrl(@Autowired(required = false) ServersSocket serversSocket,
                @Autowired(required = false) UsersSocket usersSocket,
                @Autowired(required = false) OnlineServersCtrl onlineServersCtrl) {
        this.serversSocket = serversSocket;
        this.usersSocket = usersSocket;
        this.onlineServersCtrl = onlineServersCtrl;
    }

    public void startNetwork() throws IOException {
        if (GetterSettings.getInstance().getBeanMainSettings().getProfile() == MainSettings.TypeProfiles.SERVERS) {
            startServersNetwork();
        } else if (GetterSettings.getInstance().getBeanMainSettings().getProfile() == MainSettings.TypeProfiles.USERS) {
            startUsersNetwork();
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void startServersNetwork() throws IOException {
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
        if (GetterSettings.getInstance().getBeanMainSettings().getProfile() == MainSettings.TypeProfiles.SERVERS) {
            currentSocketRunnableCtrl = runnableCtrl;
            GetterControls.getInstance().getBeanTakeMessagesCtrl().setRunnableCtrlFromConnection(currentSocketRunnableCtrl);
        }
        GetterControls.getInstance().getBeanTakeMessagesCtrl().takeMessage(message);
    }

    public void sendMessage(byte[] message) {
        currentSocketRunnableCtrl.send(message);
    }

    public void sendMessageByRunnableCtrl(byte[] message, Runnable runnable) {
        if (GetterSettings.getInstance().getBeanMainSettings().getProfile() == MainSettings.TypeProfiles.SERVERS) {
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
        SocketRunnableCtrlModel socketRunnableCtrlModel =
                GetterModels.getInstance().getBeanSocketRunnableCtrlModel();
        List<SocketRunnableCtrlStructObject> listAllConnections =
                socketRunnableCtrlModel.getAllSocketRunnableCtrlStructObject();

        int milliSecondsSleepAfterOperation = 10000;

        for (SocketRunnableCtrlStructObject socketCtrl : listAllConnections) {
            SocketRunnableCtrl socketRunnableCtrl = (SocketRunnableCtrl) socketCtrl.getSocketRunnableCtrl();

            if (socketRunnableCtrl != null && socketRunnableCtrl.isErrorsExceedsLimit()) {
                Log.write(Log.TypeLog.Warn, "We clean up a thread that has not responded for a long time.");
                socketRunnableCtrlModel.removeItem(socketCtrl);
                Log.write(Log.TypeLog.Warn, "Number of active connections after cleaning: " +
                        socketRunnableCtrlModel.getCountConnections());
            }
        }

        try {
            Thread.sleep(milliSecondsSleepAfterOperation);
        } catch (InterruptedException exception) {
            Log.write(Log.TypeLog.Error, "Sleep() failed to running here.");
        }
    }
}