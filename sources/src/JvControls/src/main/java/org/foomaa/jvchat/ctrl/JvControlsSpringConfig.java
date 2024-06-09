package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.messages.JvMessagesDefines;
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
        BeanMessagesDefinesCtrl("beanMessagesDefinesCtrl"),
        BeanSendMessagesCtrl("beanSendMessagesCtrl"),
        BeanTakeMessagesCtrl("beanTakeMessagesCtrl"),
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

    @Bean(name = "beanMessagesDefinesCtrl")
    @Scope("singleton")
    public JvMessagesDefinesCtrl beanMessagesDefinesCtrl() {
        return JvMessagesDefinesCtrl.getInstance();
    }

    @Bean(name = "beanSendMessagesCtrl")
    @Scope("singleton")
    public JvSendMessagesCtrl beanSendMessagesCtrl() {
        return JvSendMessagesCtrl.getInstance();
    }

    @Bean(name = "beanTakeMessagesCtrl")
    @Scope("singleton")
    public JvTakeMessagesCtrl beanTakeMessagesCtrl() {
        return JvTakeMessagesCtrl.getInstance();
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
