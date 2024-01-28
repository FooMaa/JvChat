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

public class JvStartPoint {
    public static void main(String[] args) throws SQLException, URISyntaxException, IOException {
//        Path buildPath = Paths.get(Objects.requireNonNull(JvStartPoint.class.getResource("/")).toURI()).getParent();
//        String content = Files.readAllLines(Paths.get(buildPath.toString() + "/profile/profile.txt"), StandardCharsets.UTF_8).toString();
//
//        System.out.println(content);

//        JvMainSettings.setProfile();
        JvInitControls ctrls = JvInitControls.getInstance();
        JvStartAuthentication a = new JvStartAuthentication();
    }
}
