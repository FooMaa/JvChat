package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.dbworker.JvDbDefines;
import org.foomaa.jvchat.dbworker.JvDbWorker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JvDbCtrl
{
    private static JvDbCtrl instance;

    private JvDbCtrl() throws SQLException {
        JvDbWorker db = JvDbWorker.getInstance();
        ResultSet rs = db.makeExecution(JvDbDefines.exec);
        List<String> al = db.getStrDataAtRow(rs, 1);
        db.endConnection();
    }

    public static JvDbCtrl getInstance() throws SQLException {
        if(instance == null){
            instance = new JvDbCtrl();
        }
        return instance;
    }
}
