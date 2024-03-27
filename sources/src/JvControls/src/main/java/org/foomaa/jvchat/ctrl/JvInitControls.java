package org.foomaa.jvchat.ctrl;

import java.io.IOException;

public class JvInitControls {
    private static JvInitControls instance;
    private JvInitControls() throws IOException {
        instanceAllControls();
    }

    public static void getInstance() throws IOException {
        if (instance == null) {
            instance = new JvInitControls();
        }
    }

    private void instanceAllControls() throws IOException {
        JvDbCtrl.getInstance();
        JvMessageCtrl.getInstance();
        JvNetworkCtrl.getInstance();
        JvEmailCtrl.getInstance();
    }
}