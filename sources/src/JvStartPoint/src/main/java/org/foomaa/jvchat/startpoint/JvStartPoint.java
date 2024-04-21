package org.foomaa.jvchat.startpoint;

import org.foomaa.jvchat.auth.JvErrorStart;
import org.foomaa.jvchat.auth.JvStartAuthentication;
import org.foomaa.jvchat.ctrl.JvGetterControls;
import org.foomaa.jvchat.tools.JvTools;
import org.foomaa.jvchat.settings.JvMainSettings;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JvStartPoint implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run( JvStartPoint.class, args );
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.setProperty("java.awt.headless", "false"); //Disables headless
        JvTools.setProfileSetting(JvStartPoint.class);
        String argsIp = "";

        if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.SERVERS) {
            JvTools.initServersParameters();
        }
        if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.USERS) {
            if (args.getOptionValues("ipServer") == null) {
                new JvErrorStart("Дайте в параметр IP-адрес сервера!");
            }
            argsIp = args.getOptionValues("ipServer").get(0);
            if (JvTools.validateInputIp(argsIp)) {
                JvMainSettings.setIp(argsIp);
            } else {
                new JvErrorStart("В параметре запуска не верный IP!");
            }
        }

        JvGetterControls.getInstance();


        if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.USERS) {
            new JvStartAuthentication();
        }
    }
}