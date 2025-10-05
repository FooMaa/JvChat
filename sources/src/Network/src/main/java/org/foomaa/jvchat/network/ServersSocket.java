package org.foomaa.jvchat.network;

import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.io.*;
import java.net.ServerSocket;
import java.net.InetAddress;

import org.foomaa.jvchat.logger.Log;
import org.foomaa.jvchat.settings.JvGetterSettings;


@Component("beanServersSocket")
@Scope("singleton")
@Profile("servers")
public class ServersSocket {
    private static ServerSocket socketServers;

    private ServersSocket() {
        try {
            if (JvGetterSettings.getInstance().getBeanServersInfoSettings().getIp().isEmpty()) {
                socketServers = new ServerSocket(JvGetterSettings.getInstance().getBeanServersInfoSettings().getPort());
            } else {
                socketServers = new ServerSocket(JvGetterSettings.getInstance().getBeanServersInfoSettings().getPort(),
                        JvGetterSettings.getInstance().getBeanServersInfoSettings().getQuantityConnections(),
                        InetAddress.getByName(JvGetterSettings.getInstance().getBeanServersInfoSettings().getIp()));
            }

            Log.write(Log.TypeLog.Info, "IP: " + socketServers.getInetAddress().toString() + ".");
            Log.write(Log.TypeLog.Info, "PORT: " + String.valueOf(socketServers.getLocalPort()) + ".");

            Log.write(Log.TypeLog.Info, "Server is started.");
            closeSocketWhenKill();
        } catch (IOException exception) {
            Log.write(Log.TypeLog.Error, "Error creating server socket.");
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