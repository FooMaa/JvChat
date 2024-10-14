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
        BeanMessagesDefinesCtrl("beanMessagesDefinesCtrl"),
        BeanSendMessagesCtrl("beanSendMessagesCtrl"),
        BeanTakeMessagesCtrl("beanTakeMessagesCtrl"),
        BeanNetworkCtrl("beanNetworkCtrl"),
        BeanServersSocketThreadCtrl("beanServersSocketThreadCtrl"),
        BeanUsersSocketThreadCtrl("beanUsersSocketThreadCtrl"),
        BeanChatsCtrl("beanChatsCtrl"),
        BeanMessagesDialogCtrl("beanMessagesDialogCtrl"),
        BeanOnlineServersCtrl("beanOnlineServersCtrl");

        private final String value;

        NameBeans(String newValue) {
            value = newValue;
        }

        public String getValue() {
            return value;
        }
    }

    @Bean(name = "beanDbCtrl")
    @Scope("singleton")
    @Profile("servers")
    @SuppressWarnings("unused")
    public JvDbCtrl beanDbCtrl() {
        return JvDbCtrl.getInstance();
    }

    @Bean(name = "beanEmailCtrl")
    @Scope("singleton")
    @Profile("servers")
    @SuppressWarnings("unused")
    public JvEmailCtrl beanEmailCtrl() {
        return JvEmailCtrl.getInstance();
    }

    @Bean(name = "beanMessagesDefinesCtrl")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvMessagesDefinesCtrl beanMessagesDefinesCtrl() {
        return JvMessagesDefinesCtrl.getInstance();
    }

    @Bean(name = "beanSendMessagesCtrl")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvSendMessagesCtrl beanSendMessagesCtrl() {
        return JvSendMessagesCtrl.getInstance();
    }

    @Bean(name = "beanTakeMessagesCtrl")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvTakeMessagesCtrl beanTakeMessagesCtrl() {
        return JvTakeMessagesCtrl.getInstance();
    }

    @Bean(name = "beanNetworkCtrl")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvNetworkCtrl beanNetworkCtrl() {
        return JvNetworkCtrl.getInstance();
    }

    @Bean(name = "beanServersSocketThreadCtrl")
    @Scope("prototype")
    @SuppressWarnings("unused")
    public JvServersSocketThreadCtrl beanServersSocketThreadCtrl(Socket fromSocketServer) {
        return new JvServersSocketThreadCtrl(fromSocketServer);
    }

    @Bean(name = "beanUsersSocketThreadCtrl")
    @Scope("prototype")
    @SuppressWarnings("unused")
    public JvUsersSocketThreadCtrl beanUsersSocketThreadCtrl(Socket fromSocketUser) {
        return new JvUsersSocketThreadCtrl(fromSocketUser);
    }

    @Bean(name = "beanChatsCtrl")
    @Scope("singleton")
    @Profile("users")
    @SuppressWarnings("unused")
    public JvChatsCtrl beanChatsCtrl() {
        return JvChatsCtrl.getInstance();
    }

    @Bean(name = "beanMessagesDialogCtrl")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvMessagesDialogCtrl beanMessagesDialogCtrl() {
        return JvMessagesDialogCtrl.getInstance();
    }

    @Bean(name = "beanOnlineServersCtrl")
    @Scope("singleton")
    @Profile("servers")
    @SuppressWarnings("unused")
    public JvOnlineServersCtrl beanOnlineServersCtrl() {
        return JvOnlineServersCtrl.getInstance();
    }
}