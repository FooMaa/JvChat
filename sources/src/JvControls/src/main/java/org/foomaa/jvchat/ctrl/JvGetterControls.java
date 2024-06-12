package org.foomaa.jvchat.ctrl;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.net.Socket;
import java.util.Arrays;

public class JvGetterControls {
    private static JvGetterControls instance;
    private final AnnotationConfigApplicationContext context;

    private JvGetterControls() {
        context = new AnnotationConfigApplicationContext(
                JvControlsSpringConfig.class);
        System.out.println(Arrays.toString(context.getEnvironment().getActiveProfiles()));
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

    public JvServersSocketThreadCtrl getBeanServersSocketThreadCtrl(Socket fromSocketServer) {
        return (JvServersSocketThreadCtrl) context.getBean(
                JvControlsSpringConfig.NameBeans.BeanServersSocketThreadCtrl.getValue(),
                fromSocketServer);
    }

    public JvUsersSocketThreadCtrl getBeanUsersSocketThreadCtrl(Socket usersSocket) {
        return (JvUsersSocketThreadCtrl) context.getBean(
                JvControlsSpringConfig.NameBeans.BeanUsersSocketThreadCtrl.getValue(),
                usersSocket);
    }
}