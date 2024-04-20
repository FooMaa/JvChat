package org.foomaa.jvchat.startpoint;

import org.foomaa.jvchat.auth.JvErrorStart;
import org.foomaa.jvchat.auth.JvStartAuthentication;
import org.foomaa.jvchat.ctrl.JvControlsSpringConfig;
import org.foomaa.jvchat.ctrl.JvInitControls;
import org.foomaa.jvchat.tools.JvTools;
import org.foomaa.jvchat.settings.JvMainSettings;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.io.IOException;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.util.Objects;

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
            if (args.getOptionValues("ip") == null) {
                new JvErrorStart("Дайте в параметр IP-адрес сервера!");
            }
            argsIp = args.getOptionValues("ip").get(0);
            if (JvTools.validateInputIp(argsIp)) {
                JvMainSettings.setIp(argsIp);
            } else {
                new JvErrorStart("В параметре запуска не верный IP!");
            }
        }

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                JvControlsSpringConfig.class);
        context.getBean("initControls", JvInitControls.class);


        if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.USERS) {
            new JvStartAuthentication();
        }
    }
}