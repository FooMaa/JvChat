package org.foomaa.jvchat.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.models.JvGetterModels;
import org.foomaa.jvchat.models.JvSocketRunnableCtrlModel;
import org.foomaa.jvchat.network.UsersSocket;
import org.foomaa.jvchat.settings.JvGetterSettings;
import org.foomaa.jvchat.settings.JvMainSettings;
import org.foomaa.jvchat.network.ServersSocket;
import org.foomaa.jvchat.structobjects.JvSocketRunnableCtrlStructObject;


public class NetworkCtrl {
    private ServersSocket serversSocket;
    private UsersSocket usersSocket;
    private SocketRunnableCtrl currentSocketRunnableCtrl;

    NetworkCtrl() {}

    public void startNetwork() throws IOException {
        if (JvGetterSettings.getInstance().getBeanMainSettings().getProfile() == JvMainSettings.TypeProfiles.SERVERS) {
            startServersNetwork();
        } else if (JvGetterSettings.getInstance().getBeanMainSettings().getProfile() == JvMainSettings.TypeProfiles.USERS) {
            startUsersNetwork();
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void startServersNetwork() throws IOException {
        ServerSocket socketServer = serversSocket.getSocketServers();
        GetterControls.getInstance().getBeanOnlineServersCtrl().loadDataOnlineUsers();
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

    @Autowired(required = false)
    @Qualifier("beanServersSocket")
    @Profile("servers")
    @SuppressWarnings("unused")
    private void setServersSocket(ServersSocket newServersSocket) {
        if ( serversSocket !=  newServersSocket ) {
            serversSocket = newServersSocket;
        }
    }

    @Autowired(required = false)
    @Qualifier("beanUsersSocket")
    @Profile("users")
    @SuppressWarnings("unused")
    private void setUsersSocket(UsersSocket newUsersSocket) {
        if (usersSocket != newUsersSocket) {
            usersSocket = newUsersSocket;
        }
    }

    public void takeMessage(byte[] message, SocketRunnableCtrl runnableCtrl) {
        if (JvGetterSettings.getInstance().getBeanMainSettings().getProfile() == JvMainSettings.TypeProfiles.SERVERS) {
            currentSocketRunnableCtrl = runnableCtrl;
            GetterControls.getInstance().getBeanTakeMessagesCtrl().setRunnableCtrlFromConnection(currentSocketRunnableCtrl);
        }
        GetterControls.getInstance().getBeanTakeMessagesCtrl().takeMessage(message);
    }

    public void sendMessage(byte[] message) {
        currentSocketRunnableCtrl.send(message);
    }

    public void sendMessageByRunnableCtrl(byte[] message, Runnable runnable) {
        if (JvGetterSettings.getInstance().getBeanMainSettings().getProfile() == JvMainSettings.TypeProfiles.SERVERS) {
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
        JvSocketRunnableCtrlModel socketRunnableCtrlModel =
                JvGetterModels.getInstance().getBeanSocketRunnableCtrlModel();
        List<JvSocketRunnableCtrlStructObject> listAllConnections =
                socketRunnableCtrlModel.getAllSocketRunnableCtrlStructObject();

        int milliSecondsSleepAfterOperation = 10000;

        for (JvSocketRunnableCtrlStructObject socketCtrl : listAllConnections) {
            SocketRunnableCtrl socketRunnableCtrl = (SocketRunnableCtrl) socketCtrl.getSocketRunnableCtrl();

            if (socketRunnableCtrl != null && socketRunnableCtrl.isErrorsExceedsLimit()) {
                JvLog.write(JvLog.TypeLog.Warn, "We clean up a thread that has not responded for a long time.");
                socketRunnableCtrlModel.removeItem(socketCtrl);
                JvLog.write(JvLog.TypeLog.Warn, "Number of active connections after cleaning: " +
                        socketRunnableCtrlModel.getCountConnections());
            }
        }

        try {
            Thread.sleep(milliSecondsSleepAfterOperation);
        } catch (InterruptedException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Sleep() failed to running here.");
        }
    }
}