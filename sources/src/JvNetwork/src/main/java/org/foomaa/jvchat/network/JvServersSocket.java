package org.foomaa.jvchat.network;

import org.foomaa.jvchat.logger.JvLog;
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
        JvLog.write(JvLog.TypeLog.Info, "Server is started");

        try {
            if (JvGetterSettings.getInstance().getBeanServerInfoSettings().getIp().isEmpty()) {
                socketServers = new ServerSocket(JvGetterSettings.getInstance().getBeanServerInfoSettings().getPort());
            } else {
                socketServers = new ServerSocket(JvGetterSettings.getInstance().getBeanServerInfoSettings().getPort(),
                        JvGetterSettings.getInstance().getBeanServerInfoSettings().getQuantityConnections(),
                        InetAddress.getByName(JvGetterSettings.getInstance().getBeanServerInfoSettings().getIp()));
            }

            JvLog.write(JvLog.TypeLog.Info, "IP: " + socketServers.getInetAddress().toString());
            JvLog.write(JvLog.TypeLog.Info, "PORT: " + String.valueOf(socketServers.getLocalPort()));

            closeSocketWhenKill();
        } catch (IOException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Ошибка при создании сокета сервера");
        }
    }

    private void closeSocketWhenKill() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
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