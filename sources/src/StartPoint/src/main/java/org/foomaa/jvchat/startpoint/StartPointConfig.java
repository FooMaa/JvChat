package org.foomaa.jvchat.startpoint;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;

import org.foomaa.jvchat.ctrl.NetworkCtrl;
import org.foomaa.jvchat.settings.MainSettings;
import org.foomaa.jvchat.settings.UsersInfoSettings;
import org.foomaa.jvchat.tools.MainTools;
import org.foomaa.jvchat.tools.ServersTools;
import org.foomaa.jvchat.uilinks.ErrorStartUILinkFactory;
import org.foomaa.jvchat.uilinks.StartAuthenticationUILink;

@Configuration
public class StartPointConfig {
    @Bean
    @Profile("users")
    public StartupRunner beanUsersStartupRunner(
            @Lazy StartAuthenticationUILink startAuthenticationUILink,
            MainTools mainTools,
            MainSettings mainSettings,
            ApplicationContext context,
            UsersInfoSettings usersInfoSettings,
            NetworkCtrl networkCtrl,
            ErrorStartUILinkFactory errorStartUILinkFactory) {
        return StartupRunner.builder()
                .mainTools(mainTools)
                .mainSettings(mainSettings)
                .context(context)
                .usersInfoSettings(usersInfoSettings)
                .networkCtrl(networkCtrl)
                .startAuthenticationUILink(startAuthenticationUILink)
                .errorStartUILinkFactory(errorStartUILinkFactory)
                .build();
    }

    @Bean
    @Profile("servers")
    public StartupRunner beanServersStartupRunner(
            ServersTools serversTools,
            MainTools mainTools,
            MainSettings mainSettings,
            ApplicationContext context,
            NetworkCtrl networkCtrl) {
        return StartupRunner.builder()
                .serversTools(serversTools)
                .mainTools(mainTools)
                .mainSettings(mainSettings)
                .context(context)
                .networkCtrl(networkCtrl)
                .build();
    }
}
