package org.foomaa.jvchat.network;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import org.foomaa.jvchat.settings.ServersInfoSettings;
import org.foomaa.jvchat.settings.UsersInfoSettings;

@Configuration
public class NetworkConfig {
    @Bean
    @Profile("servers")
    public EmailProcessor beanEmailProcessor(ServersInfoSettings serversInfoSettings) {
        return EmailProcessor.builder().serversInfoSettings(serversInfoSettings).build();
    }

    @Bean
    @Profile("servers")
    public ServersSocket beanServersSocket(ServersInfoSettings serversInfoSettings) {
        return ServersSocket.builder().serversInfoSettings(serversInfoSettings).build();
    }

    @Bean
    @Profile("users")
    public UsersSocket beanUsersSocket(UsersInfoSettings usersInfoSettings) {
        return UsersSocket.builder().usersInfoSettings(usersInfoSettings).build();
    }
}
