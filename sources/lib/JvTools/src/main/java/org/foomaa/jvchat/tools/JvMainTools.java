package org.foomaa.jvchat.tools;

import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.settings.JvGetterSettings;
import org.foomaa.jvchat.settings.JvMainSettings;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;


public class JvMainTools {
    private static JvMainTools instance;

    private JvMainTools() {}

    public static JvMainTools getInstance() {
        if (instance == null) {
            instance = new JvMainTools();
        }
        return instance;
    }

    private String getProfileFromBuildDir(Class<?> mainClass) throws IOException, URISyntaxException {
        Path buildPath = Paths.get(Objects.requireNonNull(
                mainClass.getResource("/")).toURI());

        String key = "/classes";
        if (!buildPath.toString().contains(key)) {
            key = "\\classes";
        }

        String dirToProfile = buildPath.toString().substring(0,
                buildPath.toString().lastIndexOf(key)) + "/profile/profile.txt";
        List<String> readingFile = Files.readAllLines(Path.of(dirToProfile),
                StandardCharsets.UTF_8);

        String flag = "target=";
        String profile = "";
        for (String element : readingFile) {
            profile = element.substring(element.lastIndexOf(flag) + flag.length());
        }

        return profile;
    }

    public void setProfileSetting(Class<?> mainClass) throws IOException, URISyntaxException {
        final String profile = getProfileFromBuildDir(mainClass);

        if (Objects.equals(profile, JvMainSettings.TypeProfiles.TESTS.toString())) {
            JvGetterSettings.getInstance().getBeanMainSettings().setProfile(JvMainSettings.TypeProfiles.TESTS);
        } else if (Objects.equals(profile, JvMainSettings.TypeProfiles.USERS.toString())) {
            JvGetterSettings.getInstance().getBeanMainSettings().setProfile(JvMainSettings.TypeProfiles.USERS);
        } else if (Objects.equals(profile, JvMainSettings.TypeProfiles.SERVERS.toString())) {
            JvGetterSettings.getInstance().getBeanMainSettings().setProfile(JvMainSettings.TypeProfiles.SERVERS);
        }
    }

    public void setProfileSettingSpring() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                JvToolsSpringConfig.class);
        final String profile = context.getEnvironment().getActiveProfiles()[0];

        if (Objects.equals(profile, JvMainSettings.TypeProfiles.TESTS.toString())) {
            JvGetterSettings.getInstance().getBeanMainSettings().setProfile(JvMainSettings.TypeProfiles.TESTS);
        } else if (Objects.equals(profile, JvMainSettings.TypeProfiles.USERS.toString())) {
            JvGetterSettings.getInstance().getBeanMainSettings().setProfile(JvMainSettings.TypeProfiles.USERS);
        } else if (Objects.equals(profile, JvMainSettings.TypeProfiles.SERVERS.toString())) {
            JvGetterSettings.getInstance().getBeanMainSettings().setProfile(JvMainSettings.TypeProfiles.SERVERS);
        }
    }

    public void initServersParameters() {
        Scanner in = new Scanner(System.in);

        JvLog.write(JvLog.TypeLog.Info, "Введи IP-адрес или нажми Enter для значения по умолчанию (по умолчанию auto): ");
        while (true) {
            String ip = in.nextLine();
            if (validateInputIp(ip)) {
                setIpToSettings(ip);
                break;
            } else {
                JvLog.write(JvLog.TypeLog.Error, "Это не похоже на IP-адрес. Введи снова IP-адрес или нажми Enter для значения по умолчанию: ");
            }
        }

        JvLog.write(JvLog.TypeLog.Info, "Введи порт или нажми Enter для значения по умолчанию (по умолчанию = 4004): ");
        while (true) {
            String port = in.nextLine();
            if (validateInputPort(port)) {
                if (!port.isEmpty()) {
                    JvGetterSettings.getInstance().getBeanMainSettings().setPort(Integer.parseInt(port));
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
                    JvGetterSettings.getInstance().getBeanMainSettings().setQuantityConnections(Integer.parseInt(limitConnection));
                }
                break;
            } else {
                JvLog.write(JvLog.TypeLog.Error, "Введи заново лимит подключений или нажми Enter для значения по умолчанию (по умолчанию = 1000): ");
            }
        }
    }

    public boolean validateInputIp(String param) {
        Pattern regex = Pattern.compile(
                "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
        if (param.isEmpty()) {
            return true;
        }
        return regex.matcher(param).matches();
    }

    public boolean validateInputPort(String param) {
        Pattern regex = Pattern.compile(
                "^([1-9][0-9]{0,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$");
        if (param.isEmpty()) {
            return true;
        }
        return regex.matcher(param).matches();
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
            JvGetterSettings.getInstance().getBeanMainSettings().setIp(ip);
        } else {
            JvLog.write(JvLog.TypeLog.Info, "Жди автоопределения IP-адреса ...");
            Socket socket = new Socket();
            try {
                socket.connect(new InetSocketAddress("google.com", 80));
            } catch (IOException exception) {
                JvLog.write(JvLog.TypeLog.Error, "Не получилось выйти в сеть, проверь подключение и попытайся снова!");
                System.exit(1);
            }
            JvGetterSettings.getInstance().getBeanMainSettings().setIp(socket.getLocalAddress().getHostAddress());
        }
    }

    public boolean validateInputEmail(String param) {
        Pattern regex = Pattern.compile(
                "^(?=.{1,64}@)[A-Za-z0-9+_-]+(\\.[A-Za-z0-9+_-]+)*@"
                        + "[^-][A-Za-z0-9+-]+(\\.[A-Za-z0-9+-]+)*(\\.[A-Za-z]{2,})$");
        if (param.isEmpty()) {
            return true;
        }
        return regex.matcher(param).matches();
    }
}