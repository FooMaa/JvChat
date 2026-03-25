package org.foomaa.jvchat.startpoint;

import java.io.IOException;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import org.foomaa.jvchat.settings.MainSettings;
import org.foomaa.jvchat.settings.UsersInfoSettings;
import org.foomaa.jvchat.tools.ServersTools;
import org.foomaa.jvchat.tools.MainTools;
import org.foomaa.jvchat.uilinks.ErrorStartUILinkFactory;
import org.foomaa.jvchat.uilinks.StartAuthenticationUILink;
import org.foomaa.jvchat.ctrl.NetworkCtrl;

@Component
@Slf4j
public class StartupRunner implements ApplicationRunner {
    private final ObjectProvider<ServersTools> serversToolsObjectProvider;
    private final MainTools mainTools;
    private final MainSettings mainSettings;
    private final ApplicationContext context;
    private final ObjectProvider<UsersInfoSettings> usersInfoSettingsObjectProvider;
    private final NetworkCtrl networkCtrl;
    private final ObjectProvider<StartAuthenticationUILink> startAuthenticationUILinkObjectProvider;
    private final ObjectProvider<ErrorStartUILinkFactory> errorStartUILinkFactoryObjectProvider;

    private StartupRunner(ObjectProvider<ServersTools> serversToolsObjectProvider,
                          MainTools mainTools,
                          MainSettings mainSettings,
                          ApplicationContext context,
                          ObjectProvider<UsersInfoSettings> usersInfoSettingsObjectProvider,
                          NetworkCtrl networkCtrl,
                          ObjectProvider<StartAuthenticationUILink> startAuthenticationUILinkObjectProvider,
                          ObjectProvider<ErrorStartUILinkFactory> errorStartUILinkFactoryObjectProvider) {
        this.serversToolsObjectProvider = serversToolsObjectProvider;
        this.mainTools = mainTools;
        this.mainSettings = mainSettings;
        this.context = context;
        this.usersInfoSettingsObjectProvider = usersInfoSettingsObjectProvider;
        this.networkCtrl = networkCtrl;
        this.startAuthenticationUILinkObjectProvider = startAuthenticationUILinkObjectProvider;
        this.errorStartUILinkFactoryObjectProvider = errorStartUILinkFactoryObjectProvider;
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
            ServersTools serversTools = serversToolsObjectProvider.getIfAvailable();
            if (serversTools == null) {
                log.error("serverTools is null");
                return;
            }

            serversTools.initServersParameters();
            return;
        }
        if (mainSettings.getProfile() == MainSettings.TypeProfiles.USERS) {
            if (args.getOptionValues("ipServer") == null) {
                errorStartUILinkFactoryObjectProvider.getObject().create("Enter the server IP address in the parameter!");
            }

            String argsIp = args.getOptionValues("ipServer").get(0);
            if (mainTools.validateInputIp(argsIp)) {
                usersInfoSettingsObjectProvider.getObject().setIpRemoteServer(argsIp);
            } else {
                errorStartUILinkFactoryObjectProvider.getObject().create("The startup parameter contains the wrong IP!");
            }

            String argsPort;
            try {
                argsPort = args.getOptionValues("portServer").get(0);
            } catch (NullPointerException exception) {
                return;
            }

            if (mainTools.validateInputPort(argsPort)) {
                usersInfoSettingsObjectProvider.getObject().setPortRemoteServer(Integer.parseInt(argsPort));
            } else {
                errorStartUILinkFactoryObjectProvider.getObject().create("The PORT in the launch parameter is not correct!");
            }
        }
    }

    private void launchApplication() {
        try {
            networkCtrl.startNetwork();
        } catch (IOException exception) {
            log.error("Failed to start network service");
            if (mainSettings.getProfile() == MainSettings.TypeProfiles.USERS) {
                errorStartUILinkFactoryObjectProvider.getObject().create(
                        "Failed to connect to the server.\nCheck your network availability and try again!");
            }
            System.exit(1);
        }

        if (mainSettings.getProfile() == MainSettings.TypeProfiles.USERS) {
            startAuthenticationUILinkObjectProvider.getObject();
        }
    }
}
