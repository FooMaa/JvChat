package org.foomaa.jvchat.startpoint;

import org.foomaa.jvchat.ctrl.JvInitControls;
import org.foomaa.jvchat.auth.JvStartAuthentication;


import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Objects;

import org.foomaa.jvchat.settings.JvMainSettings;
import org.foomaa.jvchat.tools.JvTools;

public class JvStartPoint {
    public static void main(String[] args) throws SQLException, URISyntaxException, IOException {
        JvTools.getProfileFromBuildDir();

//        JvMainSettings.setProfile();
        JvInitControls ctrls = JvInitControls.getInstance();
        JvStartAuthentication a = new JvStartAuthentication();
    }
}
