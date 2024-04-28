package org.foomaa.jvchat.ctrl;

import org.springframework.context.annotation.*;
import java.net.Socket;

@Configuration
@ComponentScan("org.foomaa.jvchat.ctrl")
public class JvControlsSpringConfig {
    public enum NameBeans {
        DbCtrl("dbCtrl"),
        EmailCtrl("emailCtrl"),
        MessageCtrl("messageCtrl"),
        NetworkCtrl("networkCtrl"),
        ServersSocketThreadCtrl("serversSocketThreadCtrl"),
        UsersSocketThreadCtrl("usersSocketThreadCtrl");

        private final String value;

        NameBeans(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @Bean(name = "dbCtrl")
    @Profile("servers")
    @Scope("singleton")
    public JvDbCtrl dbCtrl() {
        return JvDbCtrl.getInstance();
    }

    @Bean(name = "emailCtrl")
    @Profile("servers")
    @Scope("singleton")
    public JvEmailCtrl emailCtrl() {
        return JvEmailCtrl.getInstance();
    }

    @Bean(name = "messageCtrl")
    @Scope("singleton")
    public JvMessageCtrl messageCtrl() {
        return JvMessageCtrl.getInstance();
    }

    @Bean(name = "networkCtrl")
    @Scope("singleton")
    public JvNetworkCtrl networkCtrl() {
        return JvNetworkCtrl.getInstance();
    }

    @Bean(name = "serversSocketThreadCtrl")
    @Scope("prototype")
    public JvServersSocketThreadCtrl serversSocketThreadCtrl(Socket fromSocketServer) {
        return new JvServersSocketThreadCtrl(fromSocketServer);
    }

    @Bean(name = "usersSocketThreadCtrl")
    @Scope("prototype")
    public JvUsersSocketThreadCtrl usersSocketThreadCtrl(Socket fromSocketUser) {
        return new JvUsersSocketThreadCtrl(fromSocketUser);
    }
}
