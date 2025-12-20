package org.foomaa.jvchat.network;

import org.foomaa.jvchat.settings.ServersInfoSettings;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.io.*;
import java.net.ServerSocket;
import java.net.InetAddress;

import org.foomaa.jvchat.logger.Log;


@Component
@Profile("servers")
public class ServersSocket {
    private static ServerSocket socketServers;

    private ServersSocket(ServersInfoSettings serversInfoSettings) {
        try {
            if (serversInfoSettings.getIp().isEmpty()) {
                socketServers = new ServerSocket(serversInfoSettings.getPort());
            } else {
                socketServers = new ServerSocket(serversInfoSettings.getPort(),
                        serversInfoSettings.getQuantityConnections(),
                        InetAddress.getByName(serversInfoSettings.getIp()));
            }

            Log.write(Log.TypeLog.Info, "IP: " + socketServers.getInetAddress().toString() + ".");
            Log.write(Log.TypeLog.Info, "PORT: " + socketServers.getLocalPort() + ".");

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