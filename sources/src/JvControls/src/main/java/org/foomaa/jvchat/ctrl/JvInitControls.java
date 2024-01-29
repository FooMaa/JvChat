package org.foomaa.jvchat.ctrl;

import java.sql.SQLException;
public class JvInitControls {
    private static JvInitControls instance;
    private JvInitControls() throws SQLException {
        JvDbCtrl dbCtrl = JvDbCtrl.getInstance();
    }

    public static JvInitControls getInstance() throws SQLException {
        if(instance == null){
            instance = new JvInitControls();
        }
        return instance;
    }
}
