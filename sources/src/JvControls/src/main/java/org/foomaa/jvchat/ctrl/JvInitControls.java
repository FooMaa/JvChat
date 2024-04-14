package org.foomaa.jvchat.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;

public class JvInitControls {
    private static JvInitControls instance;

    @Autowired
    @Qualifier("networkCtrl")
    private static JvNetworkCtrl networkCtrl;

    @Autowired
    @Qualifier("messageCtrl")
    private static JvMessageCtrl messageCtrl;

    @Autowired(required = false)
    @Qualifier("dbCtrl")
    private static JvDbCtrl dbCtrl;

    @Autowired
    @Qualifier("emailCtrl")
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