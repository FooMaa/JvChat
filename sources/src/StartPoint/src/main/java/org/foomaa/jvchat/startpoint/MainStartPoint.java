package org.foomaa.jvchat.startpoint;

import java.io.InputStream;
import java.util.Properties;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@ComponentScan("org.foomaa.jvchat")
@EnableAsync
@Slf4j
public class MainStartPoint {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MainStartPoint.class);
        installProfile(app);

        app.setBannerMode(Banner.Mode.OFF);
        app.setHeadless(false);

        // NOTE(VAD): app.run(args);
        try {
            app.run(args);
        } catch (Throwable e) {
            e.printStackTrace();
            throw e;
        }
    }

    private static void installProfile(SpringApplication app) {
        String profile = loadProfile();
        setProfileSettingSpring(profile, app);

        log.info("Active profile is \"{}\"", profile);
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
            log.error("Cannot install active profile to SpringApplication");
        }
    }
}
