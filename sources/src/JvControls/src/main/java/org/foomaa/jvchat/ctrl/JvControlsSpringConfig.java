package org.foomaa.jvchat.ctrl;

import org.springframework.context.annotation.*;
import java.net.Socket;


@Configuration
@ComponentScans ({
    @ComponentScan("org.foomaa.jvchat.dbworker"),
    @ComponentScan("org.foomaa.jvchat.network")
})
public class JvControlsSpringConfig {
    public enum NameBeans {
        DbCtrl("beanDbCtrl"),
        EmailCtrl("beanEmailCtrl"),
        MessageCtrl("beanMessageCtrl"),
        NetworkCtrl("beanNetworkCtrl"),
        ServersSocketThreadCtrl("beanServersSocketThreadCtrl"),
        UsersSocketThreadCtrl("beanUsersSocketThreadCtrl");

        private final String value;

        NameBeans(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @Bean(name = "beanDbCtrl")
    @Scope("singleton")
    @Profile("servers")
    public JvDbCtrl dbCtrl() {
        return JvDbCtrl.getInstance();
    }

    @Bean(name = "beanEmailCtrl")
    @Scope("singleton")
    @Profile("servers")
    public JvEmailCtrl emailCtrl() {
        return JvEmailCtrl.getInstance();
    }

    @Bean(name = "beanMessageCtrl")
    @Scope("singleton")
    public JvMessageCtrl messageCtrl() {
        return JvMessageCtrl.getInstance();
    }

    @Bean(name = "beanNetworkCtrl")
    @Scope("singleton")
    public JvNetworkCtrl networkCtrl() {
        return JvNetworkCtrl.getInstance();
    }

    @Bean(name = "beanServersSocketThreadCtrl")
    @Scope("prototype")
    public JvServersSocketThreadCtrl serversSocketThreadCtrl(Socket fromSocketServer) {
        return new JvServersSocketThreadCtrl(fromSocketServer);
    }

    @Bean(name = "beanUsersSocketThreadCtrl")
    @Scope("prototype")
    public JvUsersSocketThreadCtrl usersSocketThreadCtrl(Socket fromSocketUser) {
        return new JvUsersSocketThreadCtrl(fromSocketUser);
    }
}
