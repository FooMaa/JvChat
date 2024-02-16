package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.dbworker.JvDbDefines;
import org.foomaa.jvchat.dbworker.JvDbWorker;
import org.foomaa.jvchat.settings.JvMainSettings;

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

    private JvDbCtrl() {
        if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.SERVERS) {
            db = JvDbWorker.getInstance();
        }
    }

    public static JvDbCtrl getInstance() {
        if(instance == null){
            instance = new JvDbCtrl();
        }
        return instance;
    }

    public void insertQueryToDB(TypeExecutionInsert type, String ... parameters) {
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

    public boolean checkQueryToDB(TypeExecutionCheck type, String ... parameters) {
        switch (type) {
            case UserPassword -> {
                if (parameters.length == 2) {
                    String login = parameters[0];
                    String password = parameters[1];
                    ResultSet rs = db.makeExecution(JvDbDefines.checkUserPassword(login, password));
                    try {
                        return rs.next();
                    } catch (SQLException exception) {
                        System.out.println("Ошибка проверки запроса к БД");
                    }
                }
            }
        }
        return false;
    }
}
