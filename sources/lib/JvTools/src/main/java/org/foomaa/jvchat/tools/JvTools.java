package org.foomaa.jvchat.tools;

import org.foomaa.jvchat.settings.JvMainSettings;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class JvTools
{
    private static String getProfileFromBuildDir(Class<?> mainClass) throws IOException, URISyntaxException {
        Path buildPath = Paths.get(Objects.requireNonNull(
                mainClass.getResource("/")).toURI());
        String dirToProfile = buildPath.toString().substring(0,
                buildPath.toString().lastIndexOf("/classes")) + "/profile/profile.txt";
        List<String> readingFile = Files.readAllLines(Path.of(dirToProfile),
                StandardCharsets.UTF_8);

        String flag = "target=";
        String profile = "";
        for (String element : readingFile) {
            profile = element.substring(element.lastIndexOf(flag) + flag.length());
        }

        return profile;
    }

    public static void setProfileSetting(Class<?> mainClass) throws IOException, URISyntaxException {
        final String profile = getProfileFromBuildDir(mainClass);

        if (Objects.equals(profile, JvMainSettings.TypeProfiles.TESTS.toString())) {
            JvMainSettings.setProfile(JvMainSettings.TypeProfiles.TESTS);
        } else if (Objects.equals(profile, JvMainSettings.TypeProfiles.USERS.toString())) {
            JvMainSettings.setProfile(JvMainSettings.TypeProfiles.TESTS);
        } else if (Objects.equals(profile, JvMainSettings.TypeProfiles.SERVERS.toString())) {
            JvMainSettings.setProfile(JvMainSettings.TypeProfiles.SERVERS);
        } else {
            return;
        }
    }
}
