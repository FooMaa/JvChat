package org.foomaa.jvchat.dbworker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import org.foomaa.jvchat.settings.ServersInfoSettings;

@Configuration
public class DbWorkerConfig {
    @Bean
    @Profile("servers")
    public DbWorker beanDbWorker(ServersInfoSettings serversInfoSettings) {
        return DbWorker.builder().serversInfoSettings(serversInfoSettings).build();
    }

    @Bean
    @Profile("servers")
    public DbRequests beanDbRequests() {
        return DbRequests.builder().build();
    }
}
