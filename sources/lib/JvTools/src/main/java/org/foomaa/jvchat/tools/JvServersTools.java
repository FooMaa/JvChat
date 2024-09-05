package org.foomaa.jvchat.tools;

import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.settings.JvGetterSettings;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Pattern;

public class JvServersTools {
    private static JvServersTools instance;

    private JvServersTools() {}

    static JvServersTools getInstance() {
        if (instance == null) {
            instance = new JvServersTools();
        }
        return instance;
    }

    public void initServersParameters() {
        Scanner in = new Scanner(System.in);

        JvLog.write(JvLog.TypeLog.Info, "Введи IP-адрес или нажми Enter для значения по умолчанию (по умолчанию auto): ");
        while (true) {
            String ip = in.nextLine();
            if (JvGetterTools.getInstance().getBeanMainTools().validateInputIp(ip)) {
                setIpToSettings(ip);
                break;
            } else {
                JvLog.write(JvLog.TypeLog.Error, "Это не похоже на IP-адрес. Введи снова IP-адрес или нажми Enter для значения по умолчанию: ");
            }
        }

        JvLog.write(JvLog.TypeLog.Info, "Введи порт или нажми Enter для значения по умолчанию (по умолчанию = 4004): ");
        while (true) {
            String port = in.nextLine();
            if (JvGetterTools.getInstance().getBeanMainTools().validateInputPort(port)) {
                if (!port.isEmpty()) {
                    JvGetterSettings.getInstance().getBeanServersInfoSettings().setPort(Integer.parseInt(port));
                }
                break;
            } else {
                JvLog.write(JvLog.TypeLog.Error, "Введи заново порт или нажми Enter для значения по умолчанию (по умолчанию = 4004): ");
            }
        }

        JvLog.write(JvLog.TypeLog.Info, "Введи лимит подключений или нажми Enter для значения по умолчанию (по умолчанию = 1000): ");
        while (true) {
            String limitConnection = in.nextLine();
            if (validateInputLimitConnections(limitConnection)) {
                if (!limitConnection.isEmpty()) {
                    JvGetterSettings.getInstance().getBeanServersInfoSettings().setQuantityConnections(Integer.parseInt(limitConnection));
                }
                break;
            } else {
                JvLog.write(JvLog.TypeLog.Error, "Введи заново лимит подключений или нажми Enter для значения по умолчанию (по умолчанию = 1000): ");
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
            JvLog.write(JvLog.TypeLog.Info, "Жди автоопределения IP-адреса ...");
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress("google.com", 80));
                JvGetterSettings.getInstance().getBeanServersInfoSettings().setIp(socket.getLocalAddress().getHostAddress());
            } catch (IOException exception) {
                JvLog.write(JvLog.TypeLog.Error, "Не получилось выйти в сеть, проверь подключение и попытайся снова!");
                System.exit(1);
            }
        }
    }
}
