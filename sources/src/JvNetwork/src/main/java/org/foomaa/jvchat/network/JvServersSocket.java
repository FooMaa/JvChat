package org.foomaa.jvchat.network;

import org.foomaa.jvchat.settings.JvMainSettings;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;


public class JvServersSocket {
    private static JvServersSocket instance;
    private static ServerSocket socketServers;

    private JvServersSocket() {
        System.out.println("Server is started");

        try {
            if (JvMainSettings.getIp().isEmpty()) {
                socketServers = new ServerSocket(JvMainSettings.getPort());
            } else {
                socketServers = new ServerSocket(JvMainSettings.getPort(),
                        1000, InetAddress.getByName(JvMainSettings.getIp()));
            }

            System.out.println(socketServers.getInetAddress().toString());
            System.out.println(socketServers.getLocalPort());

            closeSocketWhenKill();

            while (true) {
                Socket fromSocketUser = socketServers.accept();
                new JvServersListenThread(fromSocketUser).start();
                new JvServersWriteThread(fromSocketUser).start();
            }
        } catch (IOException exception) {
            System.out.println("Server is aborted");;
        }
    }

    public static JvServersSocket getInstance() {
        if(instance == null){
            instance = new JvServersSocket();
        }
        return instance;
    }

    private void closeSocketWhenKill() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                System.out.println("Закрываем серверный сокет ...");
                socketServers.close();
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }));
    }
}



