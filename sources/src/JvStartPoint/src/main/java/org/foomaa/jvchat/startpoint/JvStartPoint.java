package org.foomaa.jvchat.startpoint;

import org.foomaa.jvchat.ctrl.JvInitControls;
import org.foomaa.jvchat.auth.JvStartAuthentication;
import org.foomaa.jvchat.tools.JvTools;
import org.foomaa.jvchat.settings.JvMainSettings;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class JvStartPoint {
    public static void main(String[] args) throws SQLException, URISyntaxException, IOException {
        JvTools.setProfileSetting(JvStartPoint.class);
        JvInitControls.getInstance();
        if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.USERS) {
            JvStartAuthentication a = new JvStartAuthentication();
        }
    }
}
