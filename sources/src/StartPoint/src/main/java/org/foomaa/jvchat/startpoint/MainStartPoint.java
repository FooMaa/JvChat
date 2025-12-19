package org.foomaa.jvchat.startpoint;

import org.foomaa.jvchat.logger.Log;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.foomaa.jvchat.settings.GetterSettings;
import org.foomaa.jvchat.tools.GetterTools;
import org.foomaa.jvchat.uilinks.GetterUILinks;
import org.foomaa.jvchat.ctrl.GetterControls;
import org.foomaa.jvchat.settings.MainSettings;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@ComponentScan(basePackages = "org.foomaa.jvchat")
@EnableAsync
public class MainStartPoint implements ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MainStartPoint.class);
        installProfile(app);
        app.run(args);
    }

    @Override
    public void run(ApplicationArguments args) {
        GetterTools.getInstance().getBeanMainTools().setProfileSetting(loadProfile());
        workingArgs(args);
        launchApplication();
    }

    private static String loadProfile() {
        try (InputStream is = MainStartPoint.class.getClassLoader().getResourceAsStream("profile.properties")) {

            if (is == null) {
                throw new IllegalStateException("profile.properties not found in classpath");
            }

            Properties p = new Properties();
            p.load(is);

            return p.getProperty("Profile");
        } catch (Exception e) {
            throw new RuntimeException("Failed to load profile.properties", e);
        }
    }

    private static void setProfileSettingSpring(String profile, SpringApplication app) {
        if (profile != null && !profile.isBlank()) {
            app.setAdditionalProfiles(profile);
        } else {
            Log.write(Log.TypeLog.Error, "Cannot install active profile to SpringApplication");
        }
    }

    private static void installProfile(SpringApplication app) {
        String profile = loadProfile();

        setProfileSettingSpring(profile, app);

        Log.write(Log.TypeLog.Info, String.format("Active profile is %s", profile));
    }

    private void workingArgs(ApplicationArguments args) {
        if (GetterSettings.getInstance().getBeanMainSettings().getProfile() == MainSettings.TypeProfiles.SERVERS) {
            GetterTools.getInstance().getBeanServersTools().initServersParameters();
            return;
        }
        if (GetterSettings.getInstance().getBeanMainSettings().getProfile() == MainSettings.TypeProfiles.USERS) {
            if (args.getOptionValues("ipServer") == null) {
                GetterUILinks.getInstance().getBeanErrorStartUILink(
                        "Enter the server IP address in the parameter!");
            }

            String argsIp = args.getOptionValues("ipServer").get(0);
            if (GetterTools.getInstance().getBeanMainTools().validateInputIp(argsIp)) {
                GetterSettings.getInstance().getBeanUsersInfoSettings().setIpRemoteServer(argsIp);
            } else {
                GetterUILinks.getInstance().getBeanErrorStartUILink(
                        "The startup parameter contains the wrong IP!");
            }

            String argsPort;
            try {
                argsPort = args.getOptionValues("portServer").get(0);
            } catch (NullPointerException exception) {
                return;
            }

            if (GetterTools.getInstance().getBeanMainTools().validateInputPort(argsPort)) {
                GetterSettings.getInstance().getBeanUsersInfoSettings().setPortRemoteServer(Integer.parseInt(argsPort));
            } else {
                GetterUILinks.getInstance().getBeanErrorStartUILink(
                        "The PORT in the launch parameter is not correct!");
            }
        }
    }

    private void launchApplication() {
        GetterControls.getInstance();

        try {
            GetterControls.getInstance().getBeanNetworkCtrl().startNetwork();
        } catch (IOException exception) {
            GetterUILinks.getInstance().getBeanErrorStartUILink(
                    "Failed to connect to the server.\nCheck your network availability and try again!");
        }

        if (GetterSettings.getInstance().getBeanMainSettings().getProfile() == MainSettings.TypeProfiles.USERS) {
            GetterUILinks.getInstance().getBeanStartAuthenticationUILink();
        }
    }
}