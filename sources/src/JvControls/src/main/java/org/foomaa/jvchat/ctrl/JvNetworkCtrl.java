package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.settings.JvMainSettings;
import org.foomaa.jvchat.network.JvServersSocket;
import org.foomaa.jvchat.network.JvUsersSocket;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import jakarta.annotation.PostConstruct;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class JvNetworkCtrl {
    private static JvNetworkCtrl instance;
    private static JvUsersSocketThreadCtrl usersThread;
    private static JvServersSocketThreadCtrl serversThread;
    public static LinkedList<JvServersSocketThreadCtrl> connectionList = new LinkedList<>();

    private JvNetworkCtrl() {
        if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.SERVERS) {
            ServerSocket socketServers = JvServersSocket.getInstance().getSocketServers();
            System.out.println("ALOLOLOL");
            while (true) {
                System.out.println("GOOOOOOOOOOOO");
                Socket fromSocketUser = socketServers.accept();
                JvServersSocketThreadCtrl thread = new JvServersSocketThreadCtrl(fromSocketUser);
                connectionList.add(thread);
            }
        } else if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.USERS) {
            Socket socketUsers = JvUsersSocket.getInstance().getCurrentSocket();
            usersThread = new JvUsersSocketThreadCtrl(socketUsers);
        }
    }

    public static void start() throws IOException {

    }

    public static JvNetworkCtrl getInstance() throws IOException {
        if (instance == null) {
            instance = new JvNetworkCtrl();
        }
        return instance;
    }

    public static void takeMessage(byte[] message, Thread thr) {
        if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.SERVERS) {
            serversThread = (JvServersSocketThreadCtrl) thr;
        } else if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.USERS) {
            usersThread = (JvUsersSocketThreadCtrl) thr;
        }
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                JvControlsSpringConfig.class);
        context.getBean("messageCtrl", JvMessageCtrl.class).takeMessage(message);
    }

    public static void sendMessage(byte[] message) {
        if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.SERVERS) {
            serversThread.send(message);
        } else if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.USERS) {
            usersThread.send(message);
        }
    }
}