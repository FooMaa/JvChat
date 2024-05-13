package org.foomaa.jvchat.ctrl;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.net.Socket;
import java.util.Arrays;

public class JvGetterControls {
    private static JvGetterControls instance;
    private AnnotationConfigApplicationContext context;

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

    public JvNetworkCtrl getNetworkCtrl() {
        return context.getBean(JvControlsSpringConfig.NameBeans.NetworkCtrl.getValue(),
                JvNetworkCtrl.class);
    }

    public JvMessageCtrl getMessageCtrl() {
        return context.getBean(JvControlsSpringConfig.NameBeans.MessageCtrl.getValue(),
                JvMessageCtrl.class);
    }

    public JvDbCtrl getDbCtrl() {
        if (context.containsBeanDefinition(JvControlsSpringConfig.NameBeans.DbCtrl.getValue())) {
            return context.getBean(
                    JvControlsSpringConfig.NameBeans.DbCtrl.getValue(),
                    JvDbCtrl.class);
        }
        return null;
    }

    public JvEmailCtrl getEmailCtrl() {
        if (context.containsBeanDefinition(JvControlsSpringConfig.NameBeans.EmailCtrl.getValue())) {
            return context.getBean(
                    JvControlsSpringConfig.NameBeans.EmailCtrl.getValue(),
                    JvEmailCtrl.class);
        }
        return null;
    }

    public JvServersSocketThreadCtrl getServersSocketThreadCtrl(Socket fromSocketServer) {
        return (JvServersSocketThreadCtrl) context.getBean(
                JvControlsSpringConfig.NameBeans.ServersSocketThreadCtrl.getValue(),
                fromSocketServer);
    }

    public JvUsersSocketThreadCtrl getUsersSocketThreadCtrl(Socket usersSocket) {
        return (JvUsersSocketThreadCtrl) context.getBean(
                JvControlsSpringConfig.NameBeans.UsersSocketThreadCtrl.getValue(),
                usersSocket);
    }
}