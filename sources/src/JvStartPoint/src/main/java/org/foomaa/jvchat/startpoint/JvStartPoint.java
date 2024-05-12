package org.foomaa.jvchat.startpoint;

import org.foomaa.jvchat.uilinks.JvGetterUiLinks;
import org.foomaa.jvchat.ctrl.JvGetterControls;
import org.foomaa.jvchat.tools.JvTools;
import org.foomaa.jvchat.settings.JvMainSettings;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;

import java.io.IOException;
import java.net.URISyntaxException;

@SpringBootApplication
public class JvStartPoint implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run( JvStartPoint.class, args );
    }

    @Override
    public void run(ApplicationArguments args) {
        workingArgs(args);
        launchApplication();
    }

    private void workingArgs(ApplicationArguments args) {
        try {
            JvTools.setProfileSetting(JvStartPoint.class);
        } catch (IOException | URISyntaxException exception) {
            JvGetterUiLinks.getInstance().getErrorStart(
                    "Не удалось выставить верный профиль для приложения!");
        }

        if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.SERVERS) {
            JvTools.initServersParameters();
        }
        if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.USERS) {
            if (args.getOptionValues("ipServer") == null) {
                JvGetterUiLinks.getInstance().getErrorStart(
                        "Дайте в параметр IP-адрес сервера!");
            }
            String argsIp = args.getOptionValues("ipServer").get(0);
            if (JvTools.validateInputIp(argsIp)) {
                JvMainSettings.setIp(argsIp);
            } else {
                JvGetterUiLinks.getInstance().getErrorStart(
                        "В параметре запуска не верный IP!");
            }
        }
    }

    private void launchApplication() {
        JvGetterControls.getInstance();

        try {
            JvGetterControls.getInstance().getNetworkCtrl().startNetwork();
        } catch (IOException exception) {
            JvGetterUiLinks.getInstance().getErrorStart(
                    "Не удалось подключиться к серверу.\nПроверьте наличие сети и попробуйте снова!");
        }

        if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.USERS) {
            JvGetterUiLinks.getInstance().getStartAuthentication();
        }
    }
}