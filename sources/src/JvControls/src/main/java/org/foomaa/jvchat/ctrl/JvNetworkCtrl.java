package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.settings.JvGetterSettings;
import org.foomaa.jvchat.settings.JvMainSettings;
import org.foomaa.jvchat.network.JvServersSocket;
import org.foomaa.jvchat.network.JvUsersSocket;
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

    private JvUsersSocket usersSocket;
    private JvServersSocket serversSocket;

    private JvUsersSocketThreadCtrl usersThread;
    private JvServersSocketThreadCtrl serversThread;
    public LinkedList<JvServersSocketThreadCtrl> connectionList = new LinkedList<>();

    private JvNetworkCtrl() {}

    @SuppressWarnings("InfiniteLoopStatement")
    public void startNetwork() throws IOException {
        if (JvGetterSettings.getInstance().getBeanMainSettings().getProfile() == JvMainSettings.TypeProfiles.SERVERS) {
            ServerSocket socketServer = serversSocket.getSocketServers();
            JvGetterControls.getInstance().getBeanOnlineServersCtrl().loadDataOnlineUsers();
            runningThreadControlSockets();
            while (true) {
                Socket fromSocketServer = socketServer.accept();
                JvServersSocketThreadCtrl thread = JvGetterControls.getInstance()
                        .getBeanServersSocketThreadCtrl(fromSocketServer);
                connectionList.add(thread);
            }
        } else if (JvGetterSettings.getInstance().getBeanMainSettings().getProfile() == JvMainSettings.TypeProfiles.USERS) {
            Socket fromSocketUser = usersSocket.getCurrentSocket();
            if (!fromSocketUser.isConnected()) {
                throw new IOException();
            }
            usersThread = JvGetterControls.getInstance().getBeanUsersSocketThreadCtrl(fromSocketUser);
        }
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
        if ( usersSocket !=  newUsersSocket ) {
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
            serversThread = (JvServersSocketThreadCtrl) thr;
            JvGetterControls.getInstance().getBeanTakeMessagesCtrl().setThreadFromConnection(serversThread);
        } else if (JvGetterSettings.getInstance().getBeanMainSettings().getProfile() == JvMainSettings.TypeProfiles.USERS) {
            usersThread = (JvUsersSocketThreadCtrl) thr;
            JvGetterControls.getInstance().getBeanTakeMessagesCtrl().setThreadFromConnection(usersThread);
        }
        JvGetterControls.getInstance().getBeanTakeMessagesCtrl().takeMessage(message);
    }

    public void sendMessage(byte[] message) {
        if (JvGetterSettings.getInstance().getBeanMainSettings().getProfile() == JvMainSettings.TypeProfiles.SERVERS) {
            serversThread.send(message);
        } else if (JvGetterSettings.getInstance().getBeanMainSettings().getProfile() == JvMainSettings.TypeProfiles.USERS) {
            usersThread.send(message);
        }
    }

    public void sendMessageByThread(byte[] message, Thread thread) {
        if (JvGetterSettings.getInstance().getBeanMainSettings().getProfile() == JvMainSettings.TypeProfiles.SERVERS) {
            JvServersSocketThreadCtrl srvThr = (JvServersSocketThreadCtrl) thread;
            srvThr.send(message);
        } else if (JvGetterSettings.getInstance().getBeanMainSettings().getProfile() == JvMainSettings.TypeProfiles.USERS) {
            JvUsersSocketThreadCtrl usrThr = (JvUsersSocketThreadCtrl) thread;
            usrThr.send(message);
        }
    }

    public LinkedList<JvServersSocketThreadCtrl> getConnectionList() {
        return connectionList;
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
        List<JvServersSocketThreadCtrl> serversSocketThreadCtrlListRemove = new ArrayList<>();
        int milliSecondsSleepAfterOperation = 10000;

        for (JvServersSocketThreadCtrl socketThreadCtrl : connectionList) {
            if (socketThreadCtrl.isErrorsExceedsLimit()) {
                serversSocketThreadCtrlListRemove.add(socketThreadCtrl);
            }
        }

        for (JvServersSocketThreadCtrl socketThreadCtrl : serversSocketThreadCtrlListRemove) {
            JvLog.write(JvLog.TypeLog.Warn, "Производим вычистку потока, который не отвечает долгое время");
            connectionList.remove(socketThreadCtrl);
        }

        try {
            Thread.sleep(milliSecondsSleepAfterOperation);
        } catch (InterruptedException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Здесь не удалось выполнить sleep()");
        }
    }
}