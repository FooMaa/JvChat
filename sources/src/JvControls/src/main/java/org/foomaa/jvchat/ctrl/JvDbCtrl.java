package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.dbworker.JvDbDefines;
import org.foomaa.jvchat.dbworker.JvDbWorker;
import org.foomaa.jvchat.network.JvEmailProcessor;
import org.foomaa.jvchat.settings.JvMainSettings;

import javax.mail.MessagingException;
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
        UserPassword,
        Login
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
        try {JvEmailProcessor.getInstance().sendEmail();} catch (MessagingException e) { e.printStackTrace(); }
        return instance;
    }

    public boolean insertQueryToDB(TypeExecutionInsert type, String ... parameters) {
        switch (type) {
            case RegisterForm:
                if (parameters.length == 3) {
                    String login = parameters[0];
                    String email = parameters[1];
                    String password = parameters[2];
                    if (!checkQueryToDB(TypeExecutionCheck.Login, login)) {
                        db.makeExecution(JvDbDefines.insertToRegForm(login, email, password));
                        return true;
                    } else {
                        return false;
                    }
                }
        }
        return false;
    }

    public boolean checkQueryToDB(TypeExecutionCheck type, String ... parameters) {
        switch (type) {
            case UserPassword:
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
            case Login:
                if (parameters.length == 1) {
                    String login = parameters[0];
                    ResultSet rs = db.makeExecution(JvDbDefines.checkLogin(login));
                    try {
                        return rs.next();
                    } catch (SQLException exception) {
                        System.out.println("Ошибка проверки запроса к БД");
                    }
                }
        }
        return false;
    }
}
