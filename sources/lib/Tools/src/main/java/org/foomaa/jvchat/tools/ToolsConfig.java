package org.foomaa.jvchat.tools;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import org.foomaa.jvchat.settings.MainSettings;
import org.foomaa.jvchat.settings.ServersInfoSettings;

@Configuration
public class ToolsConfig {
    @Bean
    public FormatTools beanFormatTools() {
        return FormatTools.builder().build();
    }

    @Bean
    public MainTools beanMainTools(MainSettings mainSettings) {
        return MainTools.builder().mainSettings(mainSettings).build();
    }

    @Bean
    @Profile("servers")
    public ServersTools beanServersTools(MainTools mainTools, ServersInfoSettings serversInfoSettings) {
        return ServersTools.builder().mainTools(mainTools).serversInfoSettings(serversInfoSettings).build();
    }

    @Bean
    public StructTools beanStructTools() {
        return StructTools.builder().build();
    }

    @Bean
    @Profile("users")
    public UsersTools beanUsersTools() {
        return UsersTools.builder().build();
    }
}
