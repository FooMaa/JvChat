package org.foomaa.jvchat.network;

import org.foomaa.jvchat.settings.JvMainSettings;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;
import java.net.InetAddress;

@Component("beanServersSocket")
@Scope("singleton")
@Profile("servers")
public class JvServersSocket {
    private static ServerSocket socketServers;

    private JvServersSocket() {
        System.out.println("Server is started");

        try {
            if (JvMainSettings.getIp().isEmpty()) {
                socketServers = new ServerSocket(JvMainSettings.getPort());
            } else {
                socketServers = new ServerSocket(JvMainSettings.getPort(),
                        JvMainSettings.getQuantityConnections(),
                        InetAddress.getByName(JvMainSettings.getIp()));
            }

            System.out.println(socketServers.getInetAddress().toString());
            System.out.println(socketServers.getLocalPort());

            closeSocketWhenKill();
        } catch (IOException exception) {
            System.out.println("Ошибка при создании сокета сервера");
        }
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

    public ServerSocket getSocketServers() {
        return socketServers;
    }
}