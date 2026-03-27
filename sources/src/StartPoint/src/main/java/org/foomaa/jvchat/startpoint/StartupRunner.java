package org.foomaa.jvchat.startpoint;

import java.io.IOException;
import java.util.Objects;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.ctrl.NetworkCtrl;
import org.foomaa.jvchat.settings.MainSettings;
import org.foomaa.jvchat.settings.UsersInfoSettings;
import org.foomaa.jvchat.tools.MainTools;
import org.foomaa.jvchat.tools.ServersTools;
import org.foomaa.jvchat.uilinks.ErrorStartUILinkFactory;
import org.foomaa.jvchat.uilinks.StartAuthenticationUILink;

@Slf4j
public class StartupRunner implements ApplicationRunner {
    private final ServersTools serversTools;
    private final MainTools mainTools;
    private final MainSettings mainSettings;
    private final ApplicationContext context;
    private final UsersInfoSettings usersInfoSettings;
    private final NetworkCtrl networkCtrl;
    private final StartAuthenticationUILink startAuthenticationUILink;
    private final ErrorStartUILinkFactory errorStartUILinkFactory;

    @Builder
    private StartupRunner(ServersTools serversTools, MainTools mainTools, MainSettings mainSettings,
            ApplicationContext context, UsersInfoSettings usersInfoSettings,
            NetworkCtrl networkCtrl, StartAuthenticationUILink startAuthenticationUILink,
            ErrorStartUILinkFactory errorStartUILinkFactory) {
        this.mainTools = Objects.requireNonNull(mainTools, "mainTool is mandatory");
        this.mainSettings = Objects.requireNonNull(mainSettings, "mainSettings is mandatory");
        this.context = Objects.requireNonNull(context, "context is mandatory");
        this.networkCtrl = Objects.requireNonNull(networkCtrl, "networkCtrl is mandatory");

        this.serversTools = serversTools;
        this.usersInfoSettings = usersInfoSettings;
        this.startAuthenticationUILink = startAuthenticationUILink;
        this.errorStartUILinkFactory = errorStartUILinkFactory;
    }

    @Override
    public void run(ApplicationArguments args) {
        saveAppProfile();
        workingArgs(args);
        launchApplication();
    }

    private void saveAppProfile() {
        String profile = context.getEnvironment().getActiveProfiles()[0];

        if (profile.isEmpty()) {
            log.error("Not found profile");
            return;
        }

        mainTools.setProfileSetting(profile);
    }

    private void workingArgs(ApplicationArguments args) {
        if (mainSettings.getProfile() == MainSettings.TypeProfiles.SERVERS) {
            if (serversTools == null) {
                log.error("serverTools is null");
                return;
            }

            serversTools.initServersParameters();
            return;
        }
        if (mainSettings.getProfile() == MainSettings.TypeProfiles.USERS) {
            if (args.getOptionValues("ipServer") == null) {
                errorStartUILinkFactory.create("Enter the server IP address in the parameter!");
            }

            String argsIp = args.getOptionValues("ipServer").get(0);
            if (mainTools.validateInputIp(argsIp)) {
                usersInfoSettings.setIpRemoteServer(argsIp);
            } else {
                errorStartUILinkFactory.create("The startup parameter contains the wrong IP!");
            }

            String argsPort;
            try {
                argsPort = args.getOptionValues("portServer").get(0);
            } catch (NullPointerException exception) {
                return;
            }

            if (mainTools.validateInputPort(argsPort)) {
                usersInfoSettings.setPortRemoteServer(Integer.parseInt(argsPort));
            } else {
                errorStartUILinkFactory.create("The PORT in the launch parameter is not correct!");
            }
        }
    }

    private void launchApplication() {
        try {
            networkCtrl.startNetwork();
        } catch (IOException exception) {
            log.error("Failed to start network service");
            if (mainSettings.getProfile() == MainSettings.TypeProfiles.USERS) {
                errorStartUILinkFactory.create(
                        "Failed to connect to the server.\nCheck your network availability and try again!");
            }
            System.exit(1);
        }

        if (mainSettings.getProfile() == MainSettings.TypeProfiles.USERS) {
            startAuthenticationUILink.openFrame();
        }
    }
}
