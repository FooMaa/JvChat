package org.foomaa.jvchat.network;

import org.foomaa.jvchat.settings.JvGetterSettings;
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
            if (JvGetterSettings.getInstance().getBeanMainSettings().getIp().isEmpty()) {
                socketServers = new ServerSocket(JvGetterSettings.getInstance().getBeanMainSettings().getPort());
            } else {
                socketServers = new ServerSocket(JvGetterSettings.getInstance().getBeanMainSettings().getPort(),
                        JvGetterSettings.getInstance().getBeanMainSettings().getQuantityConnections(),
                        InetAddress.getByName(JvGetterSettings.getInstance().getBeanMainSettings().getIp()));
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