package org.foomaa.jvchat.startpoint;

import org.foomaa.jvchat.ctrl.JvDbCtrl;
import org.foomaa.jvchat.auth.JvStartAuthentication;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JvStartPoint {
    public static void main(String[] args) throws SQLException {
//        JvDbCtrl db = JvDbCtrl.getInstance();
        JvStartAuthentication a = new JvStartAuthentication();
    }
}
