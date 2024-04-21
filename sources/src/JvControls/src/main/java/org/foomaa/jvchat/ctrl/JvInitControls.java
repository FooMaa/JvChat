package org.foomaa.jvchat.ctrl;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class JvInitControls {
    private static JvInitControls instance;
    private static AnnotationConfigApplicationContext context;

    private static JvDbCtrl dbCtrl;
    private static JvEmailCtrl emailCtrl;
    private static JvNetworkCtrl networkCtrl;
    private static JvMessageCtrl messageCtrl;

    private JvInitControls() {
        context = new AnnotationConfigApplicationContext(
                JvControlsSpringConfig.class);

        if (context.containsBeanDefinition(JvControlsSpringConfig.NameBeans.DbCtrl.getValue())) {
            dbCtrl = context.getBean(
                    (JvControlsSpringConfig.NameBeans.DbCtrl.getValue()), JvDbCtrl.class);
        }
        if (context.containsBeanDefinition(JvControlsSpringConfig.NameBeans.EmailCtrl.getValue())) {
            emailCtrl = context.getBean(
                    JvControlsSpringConfig.NameBeans.EmailCtrl.getValue(), JvEmailCtrl.class);
        }
        messageCtrl = context.getBean(JvControlsSpringConfig.NameBeans.MessageCtrl.getValue(), JvMessageCtrl.class);
        networkCtrl = context.getBean(JvControlsSpringConfig.NameBeans.NetworkCtrl.getValue(), JvNetworkCtrl.class);
    }

    public static JvInitControls getInstance() {
        if (instance == null) {
            instance = new JvInitControls();
        }
        return instance;
    }

    public static JvNetworkCtrl getNetworkCtrl() {
        return networkCtrl;
    }

    public static JvMessageCtrl getMessageCtrl() {
        return messageCtrl;
    }

    public static JvDbCtrl getDbCtrl() {
        return dbCtrl;
    }

    public static JvEmailCtrl getEmailCtrl() {
        return emailCtrl;
    }
}