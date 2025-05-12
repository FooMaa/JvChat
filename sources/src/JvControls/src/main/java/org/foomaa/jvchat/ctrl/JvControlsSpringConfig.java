package org.foomaa.jvchat.ctrl;

import org.springframework.context.annotation.*;
import java.net.Socket;


@Configuration
@ComponentScans ({
    @ComponentScan("org.foomaa.jvchat.dbworker"),
    @ComponentScan("org.foomaa.jvchat.network")
})
class JvControlsSpringConfig {
    public enum NameBeans {
        BeanDbCtrl("beanDbCtrl"),
        BeanEmailCtrl("beanEmailCtrl"),
        BeanMessagesDefinesCtrl("beanMessagesDefinesCtrl"),
        BeanSendMessagesCtrl("beanSendMessagesCtrl"),
        BeanTakeMessagesCtrl("beanTakeMessagesCtrl"),
        BeanNetworkCtrl("beanNetworkCtrl"),
        BeanSocketRunnableCtrl("beanSocketRunnableCtrl"),
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
        return new JvDbCtrl();
    }

    @Bean(name = "beanEmailCtrl")
    @Scope("singleton")
    @Profile("servers")
    @SuppressWarnings("unused")
    public JvEmailCtrl beanEmailCtrl() {
        return new JvEmailCtrl();
    }

    @Bean(name = "beanMessagesDefinesCtrl")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvMessagesDefinesCtrl beanMessagesDefinesCtrl() {
        return new JvMessagesDefinesCtrl();
    }

    @Bean(name = "beanSendMessagesCtrl")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvSendMessagesCtrl beanSendMessagesCtrl() {
        return new JvSendMessagesCtrl();
    }

    @Bean(name = "beanTakeMessagesCtrl")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvTakeMessagesCtrl beanTakeMessagesCtrl() {
        return new JvTakeMessagesCtrl();
    }

    @Bean(name = "beanNetworkCtrl")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvNetworkCtrl beanNetworkCtrl() {
        return new JvNetworkCtrl();
    }

    @Bean(name = "beanSocketRunnableCtrl")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public JvSocketRunnableCtrl beanSocketRunnableCtrl(Socket socket) {
        return new JvSocketRunnableCtrl(socket);
    }

    @Bean(name = "beanChatsCtrl")
    @Scope("singleton")
    @Profile("users")
    @SuppressWarnings("unused")
    public JvChatsCtrl beanChatsCtrl() {
        return new JvChatsCtrl();
    }

    @Bean(name = "beanMessagesDialogCtrl")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvMessagesDialogCtrl beanMessagesDialogCtrl() {
        return new JvMessagesDialogCtrl();
    }

    @Bean(name = "beanOnlineServersCtrl")
    @Scope("singleton")
    @Profile("servers")
    @SuppressWarnings("unused")
    public JvOnlineServersCtrl beanOnlineServersCtrl() {
        return new JvOnlineServersCtrl();
    }
}