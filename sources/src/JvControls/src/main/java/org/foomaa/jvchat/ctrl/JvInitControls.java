package org.foomaa.jvchat.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class JvInitControls {
    private static JvInitControls instance;

    @Autowired
    @Qualifier("jvNetworkCtrl")
    private static JvNetworkCtrl networkCtrl;

    @Autowired
    @Qualifier("jvMessageCtrl")
    private static JvMessageCtrl messageCtrl;

    @Autowired
    @Qualifier("jvDbCtrl")
    private static JvDbCtrl dbCtrl;

    @Autowired
    @Qualifier("jvEmailCtrl")
    private static JvEmailCtrl emailCtrl;

    private JvInitControls() {
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