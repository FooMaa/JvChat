package org.foomaa.jvchat.ctrl;

import java.io.IOException;
import java.sql.SQLException;
public class JvInitControls {
    private static JvInitControls instance;
    private JvInitControls() throws SQLException, IOException {
        JvDbCtrl.getInstance();
        JvMessageCtrl.getInstance();
        JvNetworkCtrl.getInstance();
    }

    public static JvInitControls getInstance() throws SQLException, IOException {
        if(instance == null){
            instance = new JvInitControls();
        }
        return instance;
    }
}
