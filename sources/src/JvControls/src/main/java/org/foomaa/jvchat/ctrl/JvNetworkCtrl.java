package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.settings.JvMainSettings;
import org.foomaa.jvchat.network.JvServersSocket;
import org.foomaa.jvchat.network.JvUsersSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class JvNetworkCtrl {
    private static JvNetworkCtrl instance;
    private JvUsersSocketThreadCtrl usersThread;
    private JvServersSocketThreadCtrl serversThread;
    public LinkedList<JvServersSocketThreadCtrl> connectionList = new LinkedList<>();

    private JvNetworkCtrl() {}

    public void startNetwork() throws IOException {
        if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.SERVERS) {
            ServerSocket socketServers = JvServersSocket.getInstance().getSocketServers();
            while (true) {
                Socket fromSocketServer = socketServers.accept();
                JvServersSocketThreadCtrl thread = JvGetterControls.getServersSocketThreadCtrl(fromSocketServer);
                connectionList.add(thread);
            }
        } else if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.USERS) {
            Socket fromSocketUsers = JvUsersSocket.getInstance().getCurrentSocket();
            usersThread = JvGetterControls.getUsersSocketThreadCtrl(fromSocketUsers);
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
        JvGetterControls.getMessageCtrl().takeMessage(message);
    }

    public void sendMessage(byte[] message) {
        if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.SERVERS) {
            serversThread.send(message);
        } else if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.USERS) {
            usersThread.send(message);
        }
    }
}