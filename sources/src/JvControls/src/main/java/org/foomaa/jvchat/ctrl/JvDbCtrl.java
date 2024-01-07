package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.dbworker.JvDbDefines;
import org.foomaa.jvchat.dbworker.JvDbWorker;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JvDbCtrl
{
    private static JvDbCtrl instance;
    private static JvDbWorker db;
    public enum TypeExecutionInsert {
        RegisterForm
    }
    public enum TypeExecutionCheck {
        UserPassword
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

    public void insertQueryToDB(TypeExecutionInsert type, String ... parameters) throws SQLException {
        switch (type) {
            case RegisterForm -> {
                if (parameters.length == 2) {
                    String login = parameters[0];
                    String password = parameters[1];
                    db.makeExecution(JvDbDefines.insertToRegForm(login, password));
                }
            }

        }
    }

    public boolean checkQueryToDB(TypeExecutionCheck type, String ... parameters) throws SQLException {
        switch (type) {
            case UserPassword -> {
                if (parameters.length == 2) {
                    String login = parameters[0];
                    String password = parameters[1];
                    ResultSet rs = db.makeExecution(JvDbDefines.checkUserPassword(login, password));
                    return rs.next();
                }
            }
        }
        return false;
    }
}
