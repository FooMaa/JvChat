package org.foomaa.jvchat.startpoint;

import org.foomaa.jvchat.auth.JvErrorStart;
import org.foomaa.jvchat.ctrl.JvInitControls;
import org.foomaa.jvchat.auth.JvStartAuthentication;
import org.foomaa.jvchat.tools.JvTools;
import org.foomaa.jvchat.settings.JvMainSettings;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class JvStartPoint {
    public static void main(String[] args) throws SQLException, URISyntaxException, IOException {
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

        try {
            JvInitControls.getInstance();
        } catch (ConnectException | SocketTimeoutException exception) {
            new JvErrorStart(
                    "Не удалось подключиться к серверу.\nПроверьте наличие сети и попробуйте снова!");
        }

        if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.USERS) {
            new JvStartAuthentication();
        }
    }
}
