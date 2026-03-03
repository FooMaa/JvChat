package org.foomaa.jvchat.network;

import org.foomaa.jvchat.settings.ServersInfoSettings;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.io.*;
import java.net.ServerSocket;
import java.net.InetAddress;
import lombok.extern.slf4j.Slf4j;


@Component
@Profile("servers")
@Slf4j
public class ServersSocket {
    private static ServerSocket socketServers;
    private final ServersInfoSettings serversInfoSettings;

    private ServersSocket(ServersInfoSettings serversInfoSettings) {
        this.serversInfoSettings = serversInfoSettings;
    }

    public void start() {
        try {
            if (serversInfoSettings.getIp().isEmpty()) {
                socketServers = new ServerSocket(serversInfoSettings.getPort());
            } else {
                socketServers = new ServerSocket(serversInfoSettings.getPort(),
                        serversInfoSettings.getQuantityConnections(),
                        InetAddress.getByName(serversInfoSettings.getIp()));
            }

            log.info("IP: {}.", socketServers.getInetAddress().toString());
            log.info("PORT: {}.", socketServers.getLocalPort());

            log.info("Server is started.");
            closeSocketWhenKill();
        } catch (IOException exception) {
            log.error("Error creating server socket.");
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