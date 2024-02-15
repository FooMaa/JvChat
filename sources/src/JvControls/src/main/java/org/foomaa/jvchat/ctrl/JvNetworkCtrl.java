package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.network.JvServersThread;
import org.foomaa.jvchat.network.JvUsersThread;
import org.foomaa.jvchat.settings.JvMainSettings;

import org.foomaa.jvchat.network.JvServersSocket;
import org.foomaa.jvchat.network.JvUsersSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.function.BiConsumer;

public class JvNetworkCtrl {
    private static JvNetworkCtrl instance;
    private static Thread currentThread;
    public static LinkedList<JvServersThread> connectionListServer = new LinkedList<>(); // список всех подключений

    private JvNetworkCtrl() throws IOException {
        BiConsumer<String,Thread> connectFunction = JvNetworkCtrl::takeMessage;
        if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.SERVERS) {
            ServerSocket serverSocket =  JvServersSocket.getInstance().getSocket();
            while (true) {
                Socket fromSocketUser = serverSocket.accept();
                JvServersThread thread = new JvServersThread(fromSocketUser, connectFunction);
                connectionListServer.add(thread);
                thread.send("IT.S SERVER".getBytes());
            }
        } else if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.USERS) {
            Socket usersSocket = JvUsersSocket.getInstance().getSocket();
            JvUsersThread thread = new JvUsersThread(usersSocket, connectFunction);
        }
    }

    public static JvNetworkCtrl getInstance() throws IOException {
        if(instance == null){
            instance = new JvNetworkCtrl();
        }
        return instance;
    }

    public static void takeMessage(byte[] message, Thread thr) {
            thread = thr;
    }

    public static void putMessage(byte[] message) {
        if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.SERVERS) {
            ((JvServersThread) currentThread).send(message);
        } else if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.USERS) {
            ((JvUsersThread) currentThread).send(message);;
        }
    }

}
