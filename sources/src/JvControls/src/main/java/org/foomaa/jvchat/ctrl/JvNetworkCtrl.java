package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.settings.JvMainSettings;
import org.foomaa.jvchat.network.JvServersSocket;
import org.foomaa.jvchat.network.JvUsersSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class JvNetworkCtrl {
    private static JvNetworkCtrl instance;

    private JvUsersSocket usersSocket;
    private JvServersSocket serversSocket;

    private JvUsersSocketThreadCtrl usersThread;
    private JvServersSocketThreadCtrl serversThread;
    public LinkedList<JvServersSocketThreadCtrl> connectionList = new LinkedList<>();

    private JvNetworkCtrl() {}

    public void startNetwork() throws IOException {
        if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.SERVERS) {
            ServerSocket socketServer = serversSocket.getSocketServers();
            while (true) {
                Socket fromSocketServer = socketServer.accept();
                JvServersSocketThreadCtrl thread = JvGetterControls.getInstance()
                        .getServersSocketThreadCtrl(fromSocketServer);
                connectionList.add(thread);
            }
        } else if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.USERS) {
            Socket fromSocketUser = usersSocket.getCurrentSocket();
            if (!fromSocketUser.isConnected()) {
                throw new IOException();
            }
            usersThread = JvGetterControls.getInstance().getUsersSocketThreadCtrl(fromSocketUser);
        }
    }

    @Autowired(required = false)
    @Qualifier("beanServersSocket")
    @Profile("servers")
    private void setServersSocket(JvServersSocket newServersSocket) {
        if ( serversSocket !=  newServersSocket ) {
            serversSocket = newServersSocket;
        }
    }

    @Autowired(required = false)
    @Qualifier("beanUsersSocket")
    @Profile("users")
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
        if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.SERVERS) {
            serversThread = (JvServersSocketThreadCtrl) thr;
        } else if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.USERS) {
            usersThread = (JvUsersSocketThreadCtrl) thr;
        }
        JvGetterControls.getInstance().getMessageCtrl().takeMessage(message);
    }

    public void sendMessage(byte[] message) {
        if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.SERVERS) {
            serversThread.send(message);
        } else if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.USERS) {
            usersThread.send(message);
        }
    }
}