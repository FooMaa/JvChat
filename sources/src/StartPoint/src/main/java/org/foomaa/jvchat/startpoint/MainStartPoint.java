package org.foomaa.jvchat.startpoint;

import org.foomaa.jvchat.logger.Log;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.boot.SpringApplication;

import java.io.InputStream;
import java.util.Properties;


@SpringBootApplication
@ComponentScan("org.foomaa.jvchat")
@EnableAsync
public class MainStartPoint {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MainStartPoint.class);
        installProfile(app);

        app.setBannerMode(Banner.Mode.OFF);
        app.setHeadless(false);
        app.run(args);
    }

    private static String loadProfile() {
        try (InputStream is = MainStartPoint.class.getClassLoader().getResourceAsStream("profile.properties")) {

            if (is == null) {
                throw new IllegalStateException("profile.properties not found in classpath");
            }

            Properties p = new Properties();
            p.load(is);

            return p.getProperty("Profile");
        } catch (Exception e) {
            throw new RuntimeException("Failed to load profile.properties", e);
        }
    }

    private static void setProfileSettingSpring(String profile, SpringApplication app) {
        if (profile != null && !profile.isBlank()) {
            app.setAdditionalProfiles(profile);
        } else {
            Log.write(Log.TypeLog.Error, "Cannot install active profile to SpringApplication");
        }
    }

    private static void installProfile(SpringApplication app) {
        String profile = loadProfile();

        setProfileSettingSpring(profile, app);

        Log.write(Log.TypeLog.Info, String.format("Active profile is \"%s\"", profile));
    }
}
