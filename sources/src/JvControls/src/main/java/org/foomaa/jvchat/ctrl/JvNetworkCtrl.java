package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.models.JvGetterModels;
import org.foomaa.jvchat.models.JvSocketRunnableCtrlModel;
import org.foomaa.jvchat.network.JvUsersSocket;
import org.foomaa.jvchat.settings.JvGetterSettings;
import org.foomaa.jvchat.settings.JvMainSettings;
import org.foomaa.jvchat.network.JvServersSocket;
import org.foomaa.jvchat.structobjects.JvSocketRunnableCtrlStructObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;


public class JvNetworkCtrl {
    private static JvNetworkCtrl instance;
    private JvServersSocket serversSocket;
    private JvUsersSocket usersSocket;
    private JvSocketRunnableCtrl currentSocketRunnableCtrl;

    private JvNetworkCtrl() {}

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
        JvGetterControls.getInstance().getBeanOnlineServersCtrl().loadDataOnlineUsers();
        runningErrorsControlSockets();
        while (true) {
            Socket fromSocketServer = socketServer.accept();
            JvSocketRunnableCtrl socketRunnableCtrl =
                    JvGetterControls.getInstance().getBeanSocketRunnableCtrl(fromSocketServer);
            Thread threadServers = new Thread(socketRunnableCtrl);
            threadServers.start();
        }
    }

    private void startUsersNetwork() throws IOException {
        currentSocketRunnableCtrl = JvGetterControls.getInstance().getBeanSocketRunnableCtrl(usersSocket.getCurrentSocket());
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
    private void setServersSocket(JvServersSocket newServersSocket) {
        if ( serversSocket !=  newServersSocket ) {
            serversSocket = newServersSocket;
        }
    }

    @Autowired(required = false)
    @Qualifier("beanUsersSocket")
    @Profile("users")
    @SuppressWarnings("unused")
    private void setUsersSocket(JvUsersSocket newUsersSocket) {
        if (usersSocket != newUsersSocket) {
            usersSocket = newUsersSocket;
        }
    }

    static JvNetworkCtrl getInstance() {
        if (instance == null) {
            instance = new JvNetworkCtrl();
        }
        return instance;
    }

    public void takeMessage(byte[] message, JvSocketRunnableCtrl runnableCtrl) {
        if (JvGetterSettings.getInstance().getBeanMainSettings().getProfile() == JvMainSettings.TypeProfiles.SERVERS) {
            currentSocketRunnableCtrl = runnableCtrl;
            JvGetterControls.getInstance().getBeanTakeMessagesCtrl().setRunnableCtrlFromConnection(currentSocketRunnableCtrl);
        }
        JvGetterControls.getInstance().getBeanTakeMessagesCtrl().takeMessage(message);
    }

    public void sendMessage(byte[] message) {
        currentSocketRunnableCtrl.send(message);
    }

    public void sendMessageByRunnableCtrl(byte[] message, Runnable runnable) {
        if (JvGetterSettings.getInstance().getBeanMainSettings().getProfile() == JvMainSettings.TypeProfiles.SERVERS) {
            JvSocketRunnableCtrl srvRunnable = (JvSocketRunnableCtrl) runnable;
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
            JvSocketRunnableCtrl socketRunnableCtrl = (JvSocketRunnableCtrl) socketCtrl.getSocketRunnableCtrl();

            if (socketRunnableCtrl != null && socketRunnableCtrl.isErrorsExceedsLimit()) {
                JvLog.write(JvLog.TypeLog.Warn, "Производим вычистку потока, который не отвечает долгое время");
                socketRunnableCtrlModel.removeItem(socketCtrl);
                JvLog.write(JvLog.TypeLog.Warn, "Количество активных подключений после вычистки: " +
                        socketRunnableCtrlModel.getCountConnections());
            }
        }

        try {
            Thread.sleep(milliSecondsSleepAfterOperation);
        } catch (InterruptedException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Здесь не удалось выполнить sleep()");
        }
    }
}