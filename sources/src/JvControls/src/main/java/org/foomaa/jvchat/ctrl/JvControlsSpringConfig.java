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
        BeanDbCtrl("beanDbCtrl"),
        BeanEmailCtrl("beanEmailCtrl"),
        BeanMessageCtrl("beanMessageCtrl"),
        BeanNetworkCtrl("beanNetworkCtrl"),
        BeanServersSocketThreadCtrl("beanServersSocketThreadCtrl"),
        BeanUsersSocketThreadCtrl("beanUsersSocketThreadCtrl");

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
    public JvDbCtrl beanDbCtrl() {
        return JvDbCtrl.getInstance();
    }

    @Bean(name = "beanEmailCtrl")
    @Scope("singleton")
    @Profile("servers")
    public JvEmailCtrl beanEmailCtrl() {
        return JvEmailCtrl.getInstance();
    }

    @Bean(name = "beanMessageCtrl")
    @Scope("singleton")
    public JvMessageCtrl beanMessageCtrl() {
        return JvMessageCtrl.getInstance();
    }

    @Bean(name = "beanNetworkCtrl")
    @Scope("singleton")
    public JvNetworkCtrl beanNetworkCtrl() {
        return JvNetworkCtrl.getInstance();
    }

    @Bean(name = "beanServersSocketThreadCtrl")
    @Scope("prototype")
    public JvServersSocketThreadCtrl beanServersSocketThreadCtrl(Socket fromSocketServer) {
        return new JvServersSocketThreadCtrl(fromSocketServer);
    }

    @Bean(name = "beanUsersSocketThreadCtrl")
    @Scope("prototype")
    public JvUsersSocketThreadCtrl beanUsersSocketThreadCtrl(Socket fromSocketUser) {
        return new JvUsersSocketThreadCtrl(fromSocketUser);
    }
}
