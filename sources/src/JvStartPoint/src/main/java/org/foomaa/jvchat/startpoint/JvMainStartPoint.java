package org.foomaa.jvchat.startpoint;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import java.io.IOException;
import java.net.URISyntaxException;

import org.foomaa.jvchat.settings.JvGetterSettings;
import org.foomaa.jvchat.tools.JvGetterTools;
import org.foomaa.jvchat.uilinks.JvGetterUILinks;
import org.foomaa.jvchat.ctrl.JvGetterControls;
import org.foomaa.jvchat.settings.JvMainSettings;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class JvMainStartPoint implements ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run( JvMainStartPoint.class, args );
    }

    @Override
    public void run(ApplicationArguments args) {
        workingArgs(args);
        launchApplication();
    }


    private void workingArgs(ApplicationArguments args) {
        try {
            JvGetterTools.getInstance().getBeanMainTools().setProfileSetting(JvMainStartPoint.class);
//            NOTE(VAD): Set profile by spring.
//            JvGetterTools.getInstance().getBeanMainTools().setProfileSettingSpring();
        } catch (IOException | URISyntaxException exception) {
            JvGetterUILinks.getInstance().getBeanErrorStartUILink(
                    "Failed to set the correct profile for the application!");
        }

        if (JvGetterSettings.getInstance().getBeanMainSettings().getProfile() == JvMainSettings.TypeProfiles.SERVERS) {
            JvGetterTools.getInstance().getBeanServersTools().initServersParameters();
            return;
        }
        if (JvGetterSettings.getInstance().getBeanMainSettings().getProfile() == JvMainSettings.TypeProfiles.USERS) {
            if (args.getOptionValues("ipServer") == null) {
                JvGetterUILinks.getInstance().getBeanErrorStartUILink(
                        "Enter the server IP address in the parameter!");
            }

            String argsIp = args.getOptionValues("ipServer").get(0);
            if (JvGetterTools.getInstance().getBeanMainTools().validateInputIp(argsIp)) {
                JvGetterSettings.getInstance().getBeanUsersInfoSettings().setIpRemoteServer(argsIp);
            } else {
                JvGetterUILinks.getInstance().getBeanErrorStartUILink(
                        "The startup parameter contains the wrong IP!");
            }

            String argsPort;
            try {
                argsPort = args.getOptionValues("portServer").get(0);
            } catch (NullPointerException exception) {
                return;
            }

            if (JvGetterTools.getInstance().getBeanMainTools().validateInputPort(argsPort)) {
                JvGetterSettings.getInstance().getBeanUsersInfoSettings().setPortRemoteServer(Integer.parseInt(argsPort));
            } else {
                JvGetterUILinks.getInstance().getBeanErrorStartUILink(
                        "The PORT in the launch parameter is not correct!");
            }
        }
    }

    private void launchApplication() {
        JvGetterControls.getInstance();

        try {
            JvGetterControls.getInstance().getBeanNetworkCtrl().startNetwork();
        } catch (IOException exception) {
            JvGetterUILinks.getInstance().getBeanErrorStartUILink(
                    "Failed to connect to the server.\nCheck your network availability and try again!");
        }

        if (JvGetterSettings.getInstance().getBeanMainSettings().getProfile() == JvMainSettings.TypeProfiles.USERS) {
            JvGetterUILinks.getInstance().getBeanStartAuthenticationUILink();
        }
    }
}