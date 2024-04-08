package org.foomaa.jvchat.startpoint;

import org.foomaa.jvchat.auth.JvErrorStart;
import org.foomaa.jvchat.auth.JvStartAuthentication;
import org.foomaa.jvchat.ctrl.JvControlsSpringConfig;
import org.foomaa.jvchat.ctrl.JvInitControls;
import org.foomaa.jvchat.tools.JvTools;
import org.foomaa.jvchat.settings.JvMainSettings;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;

public class JvStartPoint {
    public static void main(String[] args) throws URISyntaxException, IOException {
        JvTools.setProfileSetting(JvStartPoint.class);

        if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.SERVERS) {
            JvTools.initServersParameters();
        }
        if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.USERS) {
            if (args.length == 0) {
                new JvErrorStart("Дайте в параметр IP-адрес сервера!");
            }
            if (JvTools.validateInputIp(args[0])) {
                JvMainSettings.setIp(args[0]);
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