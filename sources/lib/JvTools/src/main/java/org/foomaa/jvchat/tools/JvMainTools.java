package org.foomaa.jvchat.tools;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import org.foomaa.jvchat.settings.GetterSettings;
import org.foomaa.jvchat.settings.MainSettings;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class JvMainTools {
    JvMainTools() {}

    private String getProfileFromBuildDir(Class<?> mainClass) throws IOException, URISyntaxException {
        Path buildPath = Paths.get(Objects.requireNonNull(
                mainClass.getResource("/")).toURI());

        String key = "/classes";
        if (!buildPath.toString().contains(key)) {
            key = "\\classes";
        }

        String dirToProfile = buildPath.toString().substring(0,
                buildPath.toString().lastIndexOf(key)) + "/profile/profile.txt";
        List<String> readingFile = Files.readAllLines(Path.of(dirToProfile),
                StandardCharsets.UTF_8);

        String flag = "target=";
        String profile = "";
        for (String element : readingFile) {
            profile = element.substring(element.lastIndexOf(flag) + flag.length());
        }

        return profile;
    }

    public void setProfileSetting(Class<?> mainClass) throws IOException, URISyntaxException {
        final String profile = getProfileFromBuildDir(mainClass);

        if (Objects.equals(profile, MainSettings.TypeProfiles.TESTS.toString())) {
            GetterSettings.getInstance().getBeanMainSettings().setProfile(MainSettings.TypeProfiles.TESTS);
        } else if (Objects.equals(profile, MainSettings.TypeProfiles.USERS.toString())) {
            GetterSettings.getInstance().getBeanMainSettings().setProfile(MainSettings.TypeProfiles.USERS);
        } else if (Objects.equals(profile, MainSettings.TypeProfiles.SERVERS.toString())) {
            GetterSettings.getInstance().getBeanMainSettings().setProfile(MainSettings.TypeProfiles.SERVERS);
        }
    }

    @SuppressWarnings("unused")
    public void setProfileSettingSpring() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                JvToolsSpringConfig.class);
        final String profile = context.getEnvironment().getActiveProfiles()[0];

        if (Objects.equals(profile, MainSettings.TypeProfiles.TESTS.toString())) {
            GetterSettings.getInstance().getBeanMainSettings().setProfile(MainSettings.TypeProfiles.TESTS);
        } else if (Objects.equals(profile, MainSettings.TypeProfiles.USERS.toString())) {
            GetterSettings.getInstance().getBeanMainSettings().setProfile(MainSettings.TypeProfiles.USERS);
        } else if (Objects.equals(profile, MainSettings.TypeProfiles.SERVERS.toString())) {
            GetterSettings.getInstance().getBeanMainSettings().setProfile(MainSettings.TypeProfiles.SERVERS);
        }
    }

    public boolean validateInputIp(String param) {
        Pattern regex = Pattern.compile(
                "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
        if (param.isEmpty()) {
            return true;
        }
        return regex.matcher(param).matches();
    }

    public boolean validateInputPort(String param) {
        Pattern regex = Pattern.compile(
                "^([1-9][0-9]{0,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$");
        if (param.isEmpty()) {
            return true;
        }
        return regex.matcher(param).matches();
    }
}