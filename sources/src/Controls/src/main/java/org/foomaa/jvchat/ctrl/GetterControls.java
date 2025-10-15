package org.foomaa.jvchat.ctrl;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.net.Socket;
import java.util.Arrays;

import org.foomaa.jvchat.logger.Log;


public class GetterControls {
    private static GetterControls instance;
    private final AnnotationConfigApplicationContext context;

    private GetterControls() {
        context = new AnnotationConfigApplicationContext(
                ControlsSpringConfig.class);
        Log.write(Log.TypeLog.Info, Arrays.toString(context.getEnvironment().getActiveProfiles()));
    }

    public static GetterControls getInstance() {
        if (instance == null) {
            instance = new GetterControls();
        }
        return instance;
    }

    public NetworkCtrl getBeanNetworkCtrl() {
        return context.getBean(ControlsSpringConfig.NameBeans.BeanNetworkCtrl.getValue(),
                NetworkCtrl.class);
    }

    public MessagesDefinesCtrl getBeanMessagesDefinesCtrl() {
        return context.getBean(ControlsSpringConfig.NameBeans.BeanMessagesDefinesCtrl.getValue(),
                MessagesDefinesCtrl.class);
    }

    public SendMessagesCtrl getBeanSendMessagesCtrl() {
        return context.getBean(ControlsSpringConfig.NameBeans.BeanSendMessagesCtrl.getValue(),
                SendMessagesCtrl.class);
    }

    public TakeMessagesCtrl getBeanTakeMessagesCtrl() {
        return context.getBean(ControlsSpringConfig.NameBeans.BeanTakeMessagesCtrl.getValue(),
                TakeMessagesCtrl.class);
    }

    public DbCtrl getBeanDbCtrl() {
        if (context.containsBeanDefinition(ControlsSpringConfig.NameBeans.BeanDbCtrl.getValue())) {
            return context.getBean(
                    ControlsSpringConfig.NameBeans.BeanDbCtrl.getValue(),
                    DbCtrl.class);
        }
        return null;
    }

    public EmailCtrl getBeanEmailCtrl() {
        if (context.containsBeanDefinition(ControlsSpringConfig.NameBeans.BeanEmailCtrl.getValue())) {
            return context.getBean(
                    ControlsSpringConfig.NameBeans.BeanEmailCtrl.getValue(),
                    EmailCtrl.class);
        }
        return null;
    }

    public SocketRunnableCtrl getBeanSocketRunnableCtrl(Socket socket) {
        return (SocketRunnableCtrl) context.getBean(ControlsSpringConfig.NameBeans.BeanSocketRunnableCtrl.getValue(),
                socket);
    }

    public ChatsCtrl getBeanChatsCtrl() {
        return context.getBean(ControlsSpringConfig.NameBeans.BeanChatsCtrl.getValue(),
                ChatsCtrl.class);
    }

    public MessagesDialogCtrl getBeanMessagesDialogCtrl() {
        return context.getBean(ControlsSpringConfig.NameBeans.BeanMessagesDialogCtrl.getValue(),
                MessagesDialogCtrl.class);
    }

    public OnlineServersCtrl getBeanOnlineServersCtrl() {
        return context.getBean(ControlsSpringConfig.NameBeans.BeanOnlineServersCtrl.getValue(),
                OnlineServersCtrl.class);
    }
}