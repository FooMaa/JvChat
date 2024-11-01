package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.logger.JvLog;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.net.Socket;
import java.util.Arrays;


public class JvGetterControls {
    private static JvGetterControls instance;
    private final AnnotationConfigApplicationContext context;

    private JvGetterControls() {
        context = new AnnotationConfigApplicationContext(
                JvControlsSpringConfig.class);
        JvLog.write(JvLog.TypeLog.Info, Arrays.toString(context.getEnvironment().getActiveProfiles()));
    }

    public static JvGetterControls getInstance() {
        if (instance == null) {
            instance = new JvGetterControls();
        }
        return instance;
    }

    public JvNetworkCtrl getBeanNetworkCtrl() {
        return context.getBean(JvControlsSpringConfig.NameBeans.BeanNetworkCtrl.getValue(),
                JvNetworkCtrl.class);
    }

    public JvMessagesDefinesCtrl getBeanMessagesDefinesCtrl() {
        return context.getBean(JvControlsSpringConfig.NameBeans.BeanMessagesDefinesCtrl.getValue(),
                JvMessagesDefinesCtrl.class);
    }

    public JvSendMessagesCtrl getBeanSendMessagesCtrl() {
        return context.getBean(JvControlsSpringConfig.NameBeans.BeanSendMessagesCtrl.getValue(),
                JvSendMessagesCtrl.class);
    }

    public JvTakeMessagesCtrl getBeanTakeMessagesCtrl() {
        return context.getBean(JvControlsSpringConfig.NameBeans.BeanTakeMessagesCtrl.getValue(),
                JvTakeMessagesCtrl.class);
    }

    public JvDbCtrl getBeanDbCtrl() {
        if (context.containsBeanDefinition(JvControlsSpringConfig.NameBeans.BeanDbCtrl.getValue())) {
            return context.getBean(
                    JvControlsSpringConfig.NameBeans.BeanDbCtrl.getValue(),
                    JvDbCtrl.class);
        }
        return null;
    }

    public JvEmailCtrl getBeanEmailCtrl() {
        if (context.containsBeanDefinition(JvControlsSpringConfig.NameBeans.BeanEmailCtrl.getValue())) {
            return context.getBean(
                    JvControlsSpringConfig.NameBeans.BeanEmailCtrl.getValue(),
                    JvEmailCtrl.class);
        }
        return null;
    }

    public JvServersSocketRunnableCtrl getBeanServersSocketRunnableCtrl(Socket fromSocketServer) {
        return (JvServersSocketRunnableCtrl) context.getBean(
                JvControlsSpringConfig.NameBeans.BeanServersSocketThreadCtrl.getValue(),
                fromSocketServer);
    }

    public JvSocketRunnableCtrl getBeanSocketRunnableCtrl(Socket socket) {
        return (JvSocketRunnableCtrl) context.getBean(JvControlsSpringConfig.NameBeans.BeanSocketRunnableCtrl.getValue(),
                socket);
    }

    public JvChatsCtrl getBeanChatsCtrl() {
        return context.getBean(JvControlsSpringConfig.NameBeans.BeanChatsCtrl.getValue(),
                JvChatsCtrl.class);
    }

    public JvMessagesDialogCtrl getBeanMessagesDialogCtrl() {
        return context.getBean(JvControlsSpringConfig.NameBeans.BeanMessagesDialogCtrl.getValue(),
                JvMessagesDialogCtrl.class);
    }

    public JvOnlineServersCtrl getBeanOnlineServersCtrl() {
        return context.getBean(JvControlsSpringConfig.NameBeans.BeanOnlineServersCtrl.getValue(),
                JvOnlineServersCtrl.class);
    }
}