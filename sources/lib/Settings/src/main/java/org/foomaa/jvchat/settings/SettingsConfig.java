package org.foomaa.jvchat.settings;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class SettingsConfig {
    @Bean
    public DisplaySettings beanDisplaySettings() {
        return DisplaySettings.builder().build();
    }

    @Bean
    public MainSettings beanMainSettings() {
        return MainSettings.builder().build();
    }

    @Bean
    @Profile("servers")
    public ServersInfoSettings beanServersInfoSettings() {
        return ServersInfoSettings.builder().build();
    }

    @Bean
    @Profile("users")
    public UISettings beanUISettings() {
        return UISettings.builder().build();
    }

    @Bean
    @Profile("users")
    public UsersInfoSettings beanUsersInfoSettings() {
        return UsersInfoSettings.builder().build();
    }
}
