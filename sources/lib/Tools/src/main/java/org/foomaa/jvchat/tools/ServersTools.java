package org.foomaa.jvchat.tools;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.foomaa.jvchat.logger.Log;
import org.springframework.stereotype.Component;

import org.foomaa.jvchat.settings.ServersInfoSettings;


@Component
public class ServersTools {
    private final MainTools mainTools;
    private final ServersInfoSettings serversInfoSettings;

    ServersTools(MainTools mainTools, ServersInfoSettings serversInfoSettings) {
        this.mainTools = mainTools;
        this.serversInfoSettings = serversInfoSettings;
    }

    public void initServersParameters() {
        Scanner in = new Scanner(System.in);

        Log.write(Log.TypeLog.Info, "Set the IP-address or push \"Enter\" for default value (default value \"auto\"): ");
        while (true) {
            String ip = in.nextLine();
            if (mainTools.validateInputIp(ip)) {
                setIpToSettings(ip);
                break;
            } else {
                Log.write(Log.TypeLog.Error, "Set the IP-address again or push \"Enter\" for default value (default value \"auto\"): ");
            }
        }

        Log.write(Log.TypeLog.Info, "Set the port or push \"Enter\" for default value (default value \"4004\"): ");
        while (true) {
            String port = in.nextLine();
            if (mainTools.validateInputPort(port)) {
                if (!port.isEmpty()) {
                    serversInfoSettings.setPort(Integer.parseInt(port));
                }
                break;
            } else {
                Log.write(Log.TypeLog.Error, "Set the port again or push \"Enter\" for default value (default value \"4004\"): ");
            }
        }

        Log.write(Log.TypeLog.Info, "Set the limit count connections or push \"Enter\" for default value (default value \"1000\"): ");
        while (true) {
            String limitConnection = in.nextLine();
            if (validateInputLimitConnections(limitConnection)) {
                if (!limitConnection.isEmpty()) {
                    serversInfoSettings.setQuantityConnections(Integer.parseInt(limitConnection));
                }
                break;
            } else {
                Log.write(Log.TypeLog.Error, "Set the limit count connections again or push \"Enter\" for default value (default value \"1000\"): ");
            }
        }
    }

    public boolean validateInputLimitConnections(String param) {
        Pattern regex = Pattern.compile(
                "^\\d+$");
        if (param.isEmpty()) {
            return true;
        }
        return regex.matcher(param).matches();
    }

    private void setIpToSettings(String ip) {
        if (!ip.isEmpty()) {
            serversInfoSettings.setIp(ip);
        } else {
            Log.write(Log.TypeLog.Info, "Wait! Searching for IP-address...");
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress("google.com", 80));
                serversInfoSettings.setIp(socket.getLocalAddress().getHostAddress());
            } catch (IOException exception) {
                Log.write(Log.TypeLog.Error, "Couldn't get online, check your connection and try again!");
                System.exit(1);
            }
        }
    }
}
