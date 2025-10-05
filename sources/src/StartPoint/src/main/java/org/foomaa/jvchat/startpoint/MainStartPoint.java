package org.foomaa.jvchat.startpoint;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import java.io.IOException;
import java.net.URISyntaxException;

import org.foomaa.jvchat.settings.GetterSettings;
import org.foomaa.jvchat.tools.JvGetterTools;
import org.foomaa.jvchat.uilinks.GetterUILinks;
import org.foomaa.jvchat.ctrl.GetterControls;
import org.foomaa.jvchat.settings.MainSettings;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class MainStartPoint implements ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run( MainStartPoint.class, args );
    }

    @Override
    public void run(ApplicationArguments args) {
        workingArgs(args);
        launchApplication();
    }


    private void workingArgs(ApplicationArguments args) {
        try {
            JvGetterTools.getInstance().getBeanMainTools().setProfileSetting(MainStartPoint.class);
//            NOTE(VAD): Set profile by spring.
//            JvGetterTools.getInstance().getBeanMainTools().setProfileSettingSpring();
        } catch (IOException | URISyntaxException exception) {
            GetterUILinks.getInstance().getBeanErrorStartUILink(
                    "Failed to set the correct profile for the application!");
        }

        if (GetterSettings.getInstance().getBeanMainSettings().getProfile() == MainSettings.TypeProfiles.SERVERS) {
            JvGetterTools.getInstance().getBeanServersTools().initServersParameters();
            return;
        }
        if (GetterSettings.getInstance().getBeanMainSettings().getProfile() == MainSettings.TypeProfiles.USERS) {
            if (args.getOptionValues("ipServer") == null) {
                GetterUILinks.getInstance().getBeanErrorStartUILink(
                        "Enter the server IP address in the parameter!");
            }

            String argsIp = args.getOptionValues("ipServer").get(0);
            if (JvGetterTools.getInstance().getBeanMainTools().validateInputIp(argsIp)) {
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

            if (JvGetterTools.getInstance().getBeanMainTools().validateInputPort(argsPort)) {
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