package org.foomaa.jvchat.tools;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.settings.JvGetterSettings;


public class JvServersTools {
    JvServersTools() {}

    public void initServersParameters() {
        Scanner in = new Scanner(System.in);

        JvLog.write(JvLog.TypeLog.Info, "Set the IP-address or push \"Enter\" for default value (default value \"auto\"): ");
        while (true) {
            String ip = in.nextLine();
            if (JvGetterTools.getInstance().getBeanMainTools().validateInputIp(ip)) {
                setIpToSettings(ip);
                break;
            } else {
                JvLog.write(JvLog.TypeLog.Error, "Set the IP-address again or push \"Enter\" for default value (default value \"auto\"): ");
            }
        }

        JvLog.write(JvLog.TypeLog.Info, "Set the port or push \"Enter\" for default value (default value \"4004\"): ");
        while (true) {
            String port = in.nextLine();
            if (JvGetterTools.getInstance().getBeanMainTools().validateInputPort(port)) {
                if (!port.isEmpty()) {
                    JvGetterSettings.getInstance().getBeanServersInfoSettings().setPort(Integer.parseInt(port));
                }
                break;
            } else {
                JvLog.write(JvLog.TypeLog.Error, "Set the port again or push \"Enter\" for default value (default value \"4004\"): ");
            }
        }

        JvLog.write(JvLog.TypeLog.Info, "Set the limit count connections or push \"Enter\" for default value (default value \"1000\"): ");
        while (true) {
            String limitConnection = in.nextLine();
            if (validateInputLimitConnections(limitConnection)) {
                if (!limitConnection.isEmpty()) {
                    JvGetterSettings.getInstance().getBeanServersInfoSettings().setQuantityConnections(Integer.parseInt(limitConnection));
                }
                break;
            } else {
                JvLog.write(JvLog.TypeLog.Error, "Set the limit count connections again or push \"Enter\" for default value (default value \"1000\"): ");
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
            JvGetterSettings.getInstance().getBeanServersInfoSettings().setIp(ip);
        } else {
            JvLog.write(JvLog.TypeLog.Info, "Wait! Searching for IP-address...");
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress("google.com", 80));
                JvGetterSettings.getInstance().getBeanServersInfoSettings().setIp(socket.getLocalAddress().getHostAddress());
            } catch (IOException exception) {
                JvLog.write(JvLog.TypeLog.Error, "Couldn't get online, check your connection and try again!");
                System.exit(1);
            }
        }
    }
}
