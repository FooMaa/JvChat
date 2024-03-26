package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.dbworker.JvDbDefines;
import org.foomaa.jvchat.dbworker.JvDbWorker;
import org.foomaa.jvchat.settings.JvMainSettings;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JvDbCtrl
{
    private static JvDbCtrl instance;
    private static JvDbWorker db;

    public enum TypeExecutionInsert {
        RegisterForm,
        ChangePassword,
        VerifyFamousEmail,
        VerifyRegistrationEmail
    }

    public enum TypeExecutionCheck {
        UserPassword,
        Login,
        Email,
        VerifyFamousEmailCode,
        VerifyRegistrationEmail
    }

    public enum TypeExecutionGet {
        LoginByEmail,
        IdByEmail
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

    public boolean insertQueryToDB(TypeExecutionInsert type, String ... parameters) {
        switch (type) {
            case RegisterForm -> {
                if (parameters.length == 3) {
                    String login = parameters[0];
                    String email = parameters[1];
                    String password = parameters[2];
                    if (!checkQueryToDB(TypeExecutionCheck.Login, login) &&
                            !checkQueryToDB(TypeExecutionCheck.Email, email)) {
                        db.makeExecution(JvDbDefines.insertToRegForm(login, email, password));
                        return true;
                    } else {
                        return false;
                    }
                }
                return false;
            }
            case ChangePassword -> {
                if (parameters.length == 2) {
                    String email = parameters[0];
                    String password = parameters[1];
                    db.makeExecution(JvDbDefines.insertChangePassword(email, password));
                    return true;
                }
                return false;
            }
            case VerifyFamousEmail -> {
                if (parameters.length == 2) {
                    String email = parameters[0];
                    String code = parameters[1];
                    int userId;
                    if (checkQueryToDB(TypeExecutionCheck.Email, email)) {
                        userId = Integer.parseInt(getInfoFromDb(TypeExecutionGet.IdByEmail, email));
                        db.makeExecution(JvDbDefines.insertCodeVerifyFamousEmail(userId, code));
                        return true;
                    }
                    return false;
                }
            }
            case VerifyRegistrationEmail -> {
                if (parameters.length == 2) {
                    String email = parameters[0];
                    String code = parameters[1];
                    db.makeExecution(JvDbDefines.insertVerifyRegistrationEmail(email, code));
                    return true;
                }
                return false;
            }
        }
        return false;
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
                return false;
            }
            case Login -> {
                if (parameters.length == 1) {
                    String login = parameters[0];
                    ResultSet rs = db.makeExecution(JvDbDefines.checkLogin(login));
                    try {
                        return rs.next();
                    } catch (SQLException exception) {
                        System.out.println("Ошибка проверки запроса к БД");
                    }
                }
                return false;
            }
            case Email -> {
                if (parameters.length == 1) {
                    String email = parameters[0];
                    ResultSet rs = db.makeExecution(JvDbDefines.checkEmail(email));
                    try {
                        return rs.next();
                    } catch (SQLException exception) {
                        System.out.println("Ошибка проверки запроса к БД");
                    }
                }
                return false;
            }
            case VerifyFamousEmailCode -> {
                if (parameters.length == 2) {
                    String email = parameters[0];
                    String code = parameters[1];
                    ResultSet rs = db.makeExecution(JvDbDefines.checkVerifyFamousEmailCode(email, code));
                    try {
                        return rs.next();
                    } catch (SQLException exception) {
                        System.out.println("Ошибка проверки запроса к БД");
                    }
                }
                return false;
            }
            case VerifyRegistrationEmail -> {
                if (parameters.length == 2) {
                    String email = parameters[0];
                    String code = parameters[1];
                    ResultSet rs = db.makeExecution(JvDbDefines.checkVerifyRegistrationEmail(email, code));
                    try {
                        return rs.next();
                    } catch (SQLException exception) {
                        System.out.println("Ошибка проверки запроса к БД");
                    }
                }
                return false;
            }
        }
        return false;
    }

    public String getInfoFromDb(TypeExecutionGet type, String ... parameters) {
        switch (type) {
            case LoginByEmail -> {
                if (parameters.length == 1) {
                    String email = parameters[0];
                    ResultSet resultSet = db.makeExecution(JvDbDefines.getLogin(email));
                    List<String> result = db.getStrDataAtRow(resultSet, 1);
                    if (!result.isEmpty()) {
                        return result.stream().findFirst().get();
                    }
                }
                return null;
            }
            case IdByEmail -> {
                if (parameters.length == 1) {
                    String email = parameters[0];
                    ResultSet resultSet = db.makeExecution(JvDbDefines.getUserId(email));
                    List<String> result = db.getStrDataAtRow(resultSet, 1);
                    if (!result.isEmpty()) {
                        return result.stream().findFirst().get();
                    }
                }
                return null;
            }
        }
        return null;
    }
}