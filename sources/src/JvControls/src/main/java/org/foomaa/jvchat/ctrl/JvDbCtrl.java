package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.dbworker.JvDbDefines;
import org.foomaa.jvchat.dbworker.JvDbWorker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JvDbCtrl
{
    private static JvDbCtrl instance;
    private static JvDbWorker db;
    public enum TypeExecution {
        InsertRegisterForm
    }

    private JvDbCtrl() throws SQLException {
        db = JvDbWorker.getInstance();
        //ResultSet rs = db.makeExecution(JvDbDefines.exec);
        //List<String> al = db.getStrDataAtRow(rs, 1);
    }

    public static JvDbCtrl getInstance() throws SQLException {
        if(instance == null){
            instance = new JvDbCtrl();
        }
        return instance;
    }

    public void queryToDataBase(TypeExecution type, String ... parameters) throws SQLException {
        switch (type) {
            case InsertRegisterForm -> {
                if (parameters.length == 2) {
                    String login = parameters[0];
                    String password = parameters[1];
                    db.makeExecution(JvDbDefines.insertToRegForm(login, password));
                }
            }
        }
    }
}
