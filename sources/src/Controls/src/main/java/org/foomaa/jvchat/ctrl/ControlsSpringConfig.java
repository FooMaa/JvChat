package org.foomaa.jvchat.ctrl;

import org.springframework.context.annotation.*;
import java.net.Socket;


@Configuration
@ComponentScans ({
    @ComponentScan("org.foomaa.jvchat.dbworker"),
    @ComponentScan("org.foomaa.jvchat.network")
})
class ControlsSpringConfig {
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

    @Bean(name = "beanMessagesDefinesCtrl")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public MessagesDefinesCtrl beanMessagesDefinesCtrl() {
        return new MessagesDefinesCtrl();
    }

    @Bean(name = "beanSendMessagesCtrl")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public SendMessagesCtrl beanSendMessagesCtrl() {
        return new SendMessagesCtrl();
    }

    @Bean(name = "beanSocketRunnableCtrl")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public SocketRunnableCtrl beanSocketRunnableCtrl(Socket socket) {
        return new SocketRunnableCtrl(socket);
    }

    @Bean(name = "beanMessagesDialogCtrl")
    @Scope("singleton")
    @SuppressWarnings("unused")
    public MessagesDialogCtrl beanMessagesDialogCtrl() {
        return new MessagesDialogCtrl();
    }
}