package org.foomaa.jvchat.startpoint;

import org.foomaa.jvchat.ctrl.NetworkCtrl;
import org.foomaa.jvchat.logger.Log;
import org.foomaa.jvchat.settings.UsersInfoSettings;
import org.foomaa.jvchat.tools.MainTools;
import org.foomaa.jvchat.uilinks.ErrorStartUILink;
import org.foomaa.jvchat.uilinks.StartAuthenticationUILink;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.io.IOException;

import org.foomaa.jvchat.settings.MainSettings;
import org.foomaa.jvchat.tools.ServersTools;


@Component
@ComponentScan("org.foomaa.jvchat")
public class StartupRunner implements ApplicationRunner {
    private final ServersTools serversTools;
    private final MainTools mainTools;
    private final MainSettings mainSettings;
    private final ApplicationContext context;
    private final ObjectProvider<UsersInfoSettings> usersInfoSettingsObjectProvider;
    private final NetworkCtrl networkCtrl;
    private final ObjectProvider<StartAuthenticationUILink> startAuthenticationUILinkObjectProvider;
    private final ObjectProvider<ErrorStartUILink> errorStartUILinkObjectProvider;

    private StartupRunner(ServersTools serversTools,
                          MainTools mainTools,
                          MainSettings mainSettings,
                          ApplicationContext context,
                          ObjectProvider<UsersInfoSettings> usersInfoSettingsObjectProvider,
                          NetworkCtrl networkCtrl,
                          ObjectProvider<StartAuthenticationUILink> startAuthenticationUILinkObjectProvider,
                          ObjectProvider<ErrorStartUILink> errorStartUILinkObjectProvider) {
        this.serversTools = serversTools;
        this.mainTools = mainTools;
        this.mainSettings = mainSettings;
        this.context = context;
        this.usersInfoSettingsObjectProvider = usersInfoSettingsObjectProvider;
        this.networkCtrl = networkCtrl;
        this.startAuthenticationUILinkObjectProvider = startAuthenticationUILinkObjectProvider;
        this.errorStartUILinkObjectProvider = errorStartUILinkObjectProvider;
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
            Log.write(Log.TypeLog.Error, "Not found profile");
            return;
        }

        mainTools.setProfileSetting(profile);
    }

    private void workingArgs(ApplicationArguments args) {
        if (mainSettings.getProfile() == MainSettings.TypeProfiles.SERVERS) {
            serversTools.initServersParameters();
            return;
        }
        if (mainSettings.getProfile() == MainSettings.TypeProfiles.USERS) {
            if (args.getOptionValues("ipServer") == null) {
                errorStartUILinkObjectProvider.getObject().show("Enter the server IP address in the parameter!");
            }

            String argsIp = args.getOptionValues("ipServer").get(0);
            if (mainTools.validateInputIp(argsIp)) {
                usersInfoSettingsObjectProvider.getObject().setIpRemoteServer(argsIp);
            } else {
                errorStartUILinkObjectProvider.getObject().show("The startup parameter contains the wrong IP!");
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
                errorStartUILinkObjectProvider.getObject().show("The PORT in the launch parameter is not correct!");
            }
        }
    }

    private void launchApplication() {
        try {
            networkCtrl.startNetwork();
        } catch (IOException exception) {
            errorStartUILinkObjectProvider.getObject().show(
                    "Failed to connect to the server.\nCheck your network availability and try again!");
        }

        if (mainSettings.getProfile() == MainSettings.TypeProfiles.USERS) {
            startAuthenticationUILinkObjectProvider.getObject();
        }
    }
}
