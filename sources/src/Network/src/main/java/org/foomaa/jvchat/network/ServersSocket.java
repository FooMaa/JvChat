package org.foomaa.jvchat.network;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.settings.ServersInfoSettings;

@Component
@Profile("servers")
@Slf4j
public class ServersSocket {
    private static ServerSocket socketServers;
    private final ServersInfoSettings serversInfoSettings;

    private ServersSocket(ServersInfoSettings serversInfoSettings) {
        this.serversInfoSettings = serversInfoSettings;
    }

    public void start() throws IOException {
        if (serversInfoSettings.getIp().isEmpty()) {
            socketServers = new ServerSocket(serversInfoSettings.getPort());
        } else {
            socketServers = new ServerSocket(serversInfoSettings.getPort(),
                    serversInfoSettings.getQuantityConnections(), InetAddress.getByName(serversInfoSettings.getIp()));
        }

        log.info("IP: {}.", socketServers.getInetAddress().toString());
        log.info("PORT: {}.", socketServers.getLocalPort());

        log.info("Server is started.");
        closeSocketWhenKill();
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
