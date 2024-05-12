package org.foomaa.jvchat.ctrl;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.net.Socket;
import java.util.Arrays;

public class JvGetterControls {
    private static JvGetterControls instance;

    private JvDbCtrl dbCtrl;
    private JvEmailCtrl emailCtrl;
    private JvNetworkCtrl networkCtrl;
    private JvMessageCtrl messageCtrl;
    private AnnotationConfigApplicationContext context;

    private JvGetterControls() {
        context = new AnnotationConfigApplicationContext(
                JvControlsSpringConfig.class);
        System.out.println(Arrays.toString(context.getEnvironment().getActiveProfiles()));
        if (context.containsBeanDefinition(JvControlsSpringConfig.NameBeans.DbCtrl.getValue())) {
            dbCtrl = context.getBean(
                    JvControlsSpringConfig.NameBeans.DbCtrl.getValue(), JvDbCtrl.class);
        }
        if (context.containsBeanDefinition(JvControlsSpringConfig.NameBeans.EmailCtrl.getValue())) {
            emailCtrl = context.getBean(
                    JvControlsSpringConfig.NameBeans.EmailCtrl.getValue(), JvEmailCtrl.class);
        }
        messageCtrl = context.getBean(JvControlsSpringConfig.NameBeans.MessageCtrl.getValue(), JvMessageCtrl.class);
        networkCtrl = context.getBean(JvControlsSpringConfig.NameBeans.NetworkCtrl.getValue(), JvNetworkCtrl.class);
    }

    public static JvGetterControls getInstance() {
        if (instance == null) {
            instance = new JvGetterControls();
        }
        return instance;
    }

    public JvNetworkCtrl getNetworkCtrl() {
        return networkCtrl;
    }

    public JvMessageCtrl getMessageCtrl() {
        return messageCtrl;
    }

    public JvDbCtrl getDbCtrl() {
        return dbCtrl;
    }

    public JvEmailCtrl getEmailCtrl() {
        return emailCtrl;
    }

    public JvServersSocketThreadCtrl getServersSocketThreadCtrl(Socket fromSocketServer) {
        return (JvServersSocketThreadCtrl) context.getBean(
                JvControlsSpringConfig.NameBeans.ServersSocketThreadCtrl.getValue(), fromSocketServer);
    }

    public JvUsersSocketThreadCtrl getUsersSocketThreadCtrl(Socket usersSocket) {
        return (JvUsersSocketThreadCtrl) context.getBean(
                JvControlsSpringConfig.NameBeans.UsersSocketThreadCtrl.getValue(), usersSocket);
    }
}