package org.foomaa.jvchat.ctrl;

import java.io.IOException;

public class JvInitControls {
    private static JvInitControls instance;
    private JvInitControls() throws IOException {
        JvDbCtrl.getInstance();
        JvMessageCtrl.getInstance();
        JvNetworkCtrl.getInstance();
    }

    public static JvInitControls getInstance() throws IOException {
        if(instance == null){
            instance = new JvInitControls();
        }
        return instance;
    }
}
