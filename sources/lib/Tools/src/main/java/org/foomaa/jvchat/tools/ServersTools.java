package org.foomaa.jvchat.tools;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.settings.ServersInfoSettings;

@Slf4j
public class ServersTools {
    // DI ↓
    private final MainTools mainTools;
    private final ServersInfoSettings serversInfoSettings;

    @Builder
    ServersTools(MainTools mainTools, ServersInfoSettings serversInfoSettings) {
        this.mainTools = Objects.requireNonNull(mainTools, "mainTools is mandatory");
        this.serversInfoSettings = Objects.requireNonNull(serversInfoSettings, "serversInfoSettings is mandatory");
    }

    public void initServersParameters() {
        setServerIp();
        setServerPort();
        setConnectionLimit();
    }

    private void setServerIp() {
        String ip = System.getenv("SERVER_IP");
        if (ip != null && mainTools.validateInputIp(ip)) {
            serversInfoSettings.setIp(ip);
            return;
        }

        log.info("Set the IP-address or push \"Enter\" for default value (default value \"auto\"): ");

        while (true) {
            Scanner in = new Scanner(System.in);
            ip = in.nextLine();
            if (mainTools.validateInputIp(ip)) {
                setIpToSettings(ip);
                return;
            }
            log.error("Set the IP-address again or push \"Enter\" for default value (default value \"auto\"): ");
        }
    }

    private void setServerPort() {
        String port = System.getenv("SERVER_PORT");
        if (port != null && mainTools.validateInputPort(port)) {
            serversInfoSettings.setPort(Integer.parseInt(port));
            return;
        }

        log.info("Set the port or push \"Enter\" for default value (default value \"4004\"): ");

        while (true) {
            Scanner in = new Scanner(System.in);
            port = in.nextLine();
            if (mainTools.validateInputPort(port)) {
                if (!port.isEmpty()) {
                    serversInfoSettings.setPort(Integer.parseInt(port));
                }
                return;
            }
            log.error("Set the port again or push \"Enter\" for default value (default value \"4004\"): ");
        }
    }

    private void setConnectionLimit() {
        String limit = System.getenv("MAX_CONNECTIONS");

        if (limit != null && validateInputLimitConnections(limit)) {
            serversInfoSettings.setQuantityConnections(Integer.parseInt(limit));
            return;
        }

        log.info("Set the limit count connections or push \"Enter\" for default value (default value \"1000\"): ");

        while (true) {
            Scanner in = new Scanner(System.in);
            limit = in.nextLine();
            if (validateInputLimitConnections(limit)) {
                if (!limit.isEmpty()) {
                    serversInfoSettings.setQuantityConnections(Integer.parseInt(limit));
                }
                return;
            }
            log.error(
                    "Set the limit count connections again or push \"Enter\" for default value (default value \"1000\"): ");
        }
    }

    public boolean validateInputLimitConnections(String param) {
        Pattern regex = Pattern.compile("^\\d+$");
        if (param.isEmpty()) {
            return true;
        }
        return regex.matcher(param).matches();
    }

    private void setIpToSettings(String ip) {
        if (!ip.isEmpty()) {
            serversInfoSettings.setIp(ip);
        } else {
            log.info("Wait! Searching for IP-address...");
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress("google.com", 80));
                serversInfoSettings.setIp(socket.getLocalAddress().getHostAddress());
            } catch (IOException exception) {
                log.error("Couldn't get online, check your connection and try again!");
                System.exit(1);
            }
        }
    }
}
