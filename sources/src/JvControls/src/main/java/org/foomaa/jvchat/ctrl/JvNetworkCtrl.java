package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.network.JvUsersSocket;
import org.foomaa.jvchat.settings.JvGetterSettings;
import org.foomaa.jvchat.settings.JvMainSettings;
import org.foomaa.jvchat.network.JvServersSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class JvNetworkCtrl {
    private static JvNetworkCtrl instance;

    private JvServersSocket serversSocket;
    private JvUsersSocket usersSocket;

    private JvSocketRunnableCtrl currentSocketRunnableCtrl;
    private JvServersSocketRunnableCtrl serversThread;

    private final LinkedList<JvServersSocketRunnableCtrl> activeRunnableList;

    private JvNetworkCtrl() {
        activeRunnableList = new LinkedList<>();
    }

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
        runningThreadControlSockets();
        while (true) {
            Socket fromSocketServer = socketServer.accept();
            JvServersSocketRunnableCtrl socketRunnableCtrl = JvGetterControls.getInstance().getBeanServersSocketRunnableCtrl(fromSocketServer);
            activeRunnableList.add(socketRunnableCtrl);
            //Thread threadUsers = new Thread(socketRunnableCtrl);
            //threadUsers.start();
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

    public void takeMessage(byte[] message, Thread thr) {
        if (JvGetterSettings.getInstance().getBeanMainSettings().getProfile() == JvMainSettings.TypeProfiles.SERVERS) {
            serversThread = (JvServersSocketRunnableCtrl) thr;
            JvGetterControls.getInstance().getBeanTakeMessagesCtrl().setThreadFromConnection(serversThread);
        }
        JvGetterControls.getInstance().getBeanTakeMessagesCtrl().takeMessage(message);
    }

    public void sendMessage(byte[] message) {
        if (JvGetterSettings.getInstance().getBeanMainSettings().getProfile() == JvMainSettings.TypeProfiles.SERVERS) {
            serversThread.send(message);
        } else if (JvGetterSettings.getInstance().getBeanMainSettings().getProfile() == JvMainSettings.TypeProfiles.USERS) {
            currentSocketRunnableCtrl.send(message);
        }
    }

    public void sendMessageByThread(byte[] message, Thread thread) {
        if (JvGetterSettings.getInstance().getBeanMainSettings().getProfile() == JvMainSettings.TypeProfiles.SERVERS) {
            JvServersSocketRunnableCtrl srvThr = (JvServersSocketRunnableCtrl) thread;
            srvThr.send(message);
        }
    }

    public LinkedList<JvServersSocketRunnableCtrl> getActiveRunnableList() {
        return activeRunnableList;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void runningThreadControlSockets() {
        Runnable listenErrorSocket = () -> {
            while (true) {
                controlErrorThreadSocket();
            }
        };

        Thread thread = new Thread(listenErrorSocket);
        thread.start();
    }

    private void controlErrorThreadSocket() {
        List<JvServersSocketRunnableCtrl> serversSocketThreadCtrlListRemove = new ArrayList<>();
        int milliSecondsSleepAfterOperation = 10000;

        for (JvServersSocketRunnableCtrl socketCtrl : activeRunnableList) {
            if (socketCtrl.isErrorsExceedsLimit()) {
                serversSocketThreadCtrlListRemove.add(socketCtrl);
            }
        }

        for (JvServersSocketRunnableCtrl socketCtrl : serversSocketThreadCtrlListRemove) {
            JvLog.write(JvLog.TypeLog.Warn, "Производим вычистку потока, который не отвечает долгое время");
            activeRunnableList.remove(socketCtrl);
        }

        if (!serversSocketThreadCtrlListRemove.isEmpty()) {
            JvLog.write(JvLog.TypeLog.Warn, "Количество активных подключений после вычистки: " + activeRunnableList.size());
        }

        try {
            Thread.sleep(milliSecondsSleepAfterOperation);
        } catch (InterruptedException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Здесь не удалось выполнить sleep()");
        }
    }
}