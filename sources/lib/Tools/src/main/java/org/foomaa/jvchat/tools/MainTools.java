package org.foomaa.jvchat.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Pattern;

import org.foomaa.jvchat.settings.GetterSettings;
import org.foomaa.jvchat.settings.MainSettings;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class MainTools {
    MainTools() {}

    private String getProfileFromBuildDir(Class<?> mainClass) {
        try (InputStream in = mainClass.getClassLoader().getResourceAsStream("profile.properties")) {
            if (in == null) {
                return null;
            }
            Properties props = new Properties();
            props.load(in);
            return props.getProperty("Profile", null);
        } catch (IOException e) {
            throw new RuntimeException("Cannot load profile.properties", e);
        }
    }

    public void setProfileSetting(Class<?> mainClass) {
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
                ToolsSpringConfig.class);
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