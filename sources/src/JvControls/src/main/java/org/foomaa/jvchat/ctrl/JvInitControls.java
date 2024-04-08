package org.foomaa.jvchat.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.IOException;

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

    private JvInitControls() throws IOException {
    }

    public static void getInstance() throws IOException {
        if (instance == null) {
            instance = new JvInitControls();
        }
    }
}