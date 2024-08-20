package org.foomaa.jvchat.startpoint;

import org.foomaa.jvchat.settings.JvGetterSettings;
import org.foomaa.jvchat.tools.JvGetterTools;
import org.foomaa.jvchat.uilinks.JvGetterUiLinks;
import org.foomaa.jvchat.ctrl.JvGetterControls;
import org.foomaa.jvchat.settings.JvMainSettings;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import java.io.IOException;
import java.net.URISyntaxException;


@SpringBootApplication
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
//            NOTE(VAD): Установить профиль по спрингу
//            JvGetterTools.getInstance().getBeanMainTools().setProfileSettingSpring();
        } catch (IOException | URISyntaxException exception) {
            JvGetterUiLinks.getInstance().getBeanErrorStartUiLinks(
                    "Не удалось выставить верный профиль для приложения!");
        }

        if (JvGetterSettings.getInstance().getBeanMainSettings().getProfile() == JvMainSettings.TypeProfiles.SERVERS) {
            JvGetterTools.getInstance().getBeanMainTools().initServersParameters();
        }
        if (JvGetterSettings.getInstance().getBeanMainSettings().getProfile() == JvMainSettings.TypeProfiles.USERS) {
            if (args.getOptionValues("ipServer") == null) {
                JvGetterUiLinks.getInstance().getBeanErrorStartUiLinks(
                        "Дайте в параметр IP-адрес сервера!");
            }
            String argsIp = args.getOptionValues("ipServer").get(0);
            if (JvGetterTools.getInstance().getBeanMainTools().validateInputIp(argsIp)) {
                JvGetterSettings.getInstance().getBeanMainSettings().setIp(argsIp);
            } else {
                JvGetterUiLinks.getInstance().getBeanErrorStartUiLinks(
                        "В параметре запуска не верный IP!");
            }
        }
    }

    private void launchApplication() {
        JvGetterControls.getInstance();

        try {
            JvGetterControls.getInstance().getBeanNetworkCtrl().startNetwork();
        } catch (IOException exception) {
            JvGetterUiLinks.getInstance().getBeanErrorStartUiLinks(
                    "Не удалось подключиться к серверу.\nПроверьте наличие сети и попробуйте снова!");
        }

        if (JvGetterSettings.getInstance().getBeanMainSettings().getProfile() == JvMainSettings.TypeProfiles.USERS) {
            JvGetterUiLinks.getInstance().getBeanStartAuthenticationUiLinks();
        }
    }
}