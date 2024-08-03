package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.dbworker.JvDbDefines;
import org.foomaa.jvchat.dbworker.JvDbWorker;
import org.foomaa.jvchat.logger.JvLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class JvDbCtrl {
    private static JvDbCtrl instance;
    private JvDbWorker db;
    private JvDbDefines dbDefines;

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
        IdByEmail,
        Chats
    }

    private JvDbCtrl() {}

    static JvDbCtrl getInstance() {
        if (instance == null) {
            instance = new JvDbCtrl();
        }
        return instance;
    }

    @Autowired(required = false)
    @Qualifier("beanDbWorker")
    @Profile("servers")
    private void setDb(JvDbWorker newDb) {
        if ( db !=  newDb ) {
            db = newDb;
        }
    }

    @Autowired(required = false)
    @Qualifier("beanDbDefines")
    @Profile("servers")
    private void setDbDefines(JvDbDefines newDbDefines){
        if (dbDefines != newDbDefines) {
            dbDefines = newDbDefines;
        }
    }

    public List<String> getStrDataAtRow(ResultSet resultSet, int row) {
        // в БД нумерация рядов и столбцов не с 0, а с 1
        ResultSetMetaData metadata;
        int columnCount = 0;
        try {
            metadata = resultSet.getMetaData();
            columnCount = metadata.getColumnCount();
        } catch (SQLException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Не возможно получить данные по столбцам и метаданные");
        }

        List<String> result = new ArrayList<>(columnCount);

        try {
            resultSet.absolute(row);

            for (int i = 1; i <= columnCount; i++) {
                result.add(resultSet.getString(i));
            }
        } catch (SQLException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Не вышло получить данные по ряду");
        }

        return result;
    }

    public boolean ifExistsLineInTable(ResultSet resultSet) {
        boolean res = false;
        try {
            res = resultSet.next();
        } catch (SQLException exception) {
            JvLog.write(JvLog.TypeLog.Error, "БД при проверке вернула исключение, что-то не так");
        }
        return res;
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
                        ResultSet rs = db.makeExecution(dbDefines.insertToRegForm(login, email, password));
                        db.closeResultSet(rs);
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
                    ResultSet rs = db.makeExecution(dbDefines.insertChangePassword(email, password));
                    db.closeResultSet(rs);
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
                        ResultSet rs = db.makeExecution(dbDefines.insertCodeVerifyFamousEmail(userId, code));
                        db.closeResultSet(rs);
                        return true;
                    }
                    return false;
                }
            }
            case VerifyRegistrationEmail -> {
                if (parameters.length == 2) {
                    String email = parameters[0];
                    String code = parameters[1];
                    ResultSet rs = db.makeExecution(dbDefines.insertVerifyRegistrationEmail(email, code));
                    db.closeResultSet(rs);
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
                    ResultSet rs = db.makeExecution(dbDefines.checkUserPassword(login, password));
                    try {
                        boolean result = rs.next();
                        db.closeResultSet(rs);
                        return result;
                    } catch (SQLException exception) {
                        JvLog.write(JvLog.TypeLog.Error, "Ошибка проверки запроса к БД");
                    }
                }
                return false;
            }
            case Login -> {
                if (parameters.length == 1) {
                    String login = parameters[0];
                    ResultSet rs = db.makeExecution(dbDefines.checkLogin(login));
                    try {
                        boolean result = rs.next();
                        db.closeResultSet(rs);
                        return result;
                    } catch (SQLException exception) {
                        JvLog.write(JvLog.TypeLog.Error, "Ошибка проверки запроса к БД");
                    }
                }
                return false;
            }
            case Email -> {
                if (parameters.length == 1) {
                    String email = parameters[0];
                    ResultSet rs = db.makeExecution(dbDefines.checkEmail(email));
                    try {
                        boolean result = rs.next();
                        db.closeResultSet(rs);
                        return result;
                    } catch (SQLException exception) {
                        JvLog.write(JvLog.TypeLog.Error, "Ошибка проверки запроса к БД");
                    }
                }
                return false;
            }
            case VerifyFamousEmailCode -> {
                if (parameters.length == 2) {
                    String email = parameters[0];
                    String code = parameters[1];
                    ResultSet rs = db.makeExecution(dbDefines.checkVerifyFamousEmailCode(email, code));
                    try {
                        boolean result = rs.next();
                        db.closeResultSet(rs);
                        return result;
                    } catch (SQLException exception) {
                        JvLog.write(JvLog.TypeLog.Error, "Ошибка проверки запроса к БД");
                    }
                }
                return false;
            }
            case VerifyRegistrationEmail -> {
                if (parameters.length == 2) {
                    String email = parameters[0];
                    String code = parameters[1];
                    ResultSet rs = db.makeExecution(dbDefines.checkVerifyRegistrationEmail(email, code));
                    try {
                        boolean result = rs.next();
                        db.closeResultSet(rs);
                        return result;
                    } catch (SQLException exception) {
                        JvLog.write(JvLog.TypeLog.Error, "Ошибка проверки запроса к БД");
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
                    ResultSet resultSet = db.makeExecution(dbDefines.getLogin(email));
                    List<String> result = getStrDataAtRow(resultSet, 1);
                    db.closeResultSet(resultSet);
                    if (!result.isEmpty()) {
                        return result.stream().findFirst().get();
                    }
                }
                return null;
            }
            case IdByEmail -> {
                if (parameters.length == 1) {
                    String email = parameters[0];
                    ResultSet resultSet = db.makeExecution(dbDefines.getUserId(email));
                    List<String> result = getStrDataAtRow(resultSet, 1);
                    db.closeResultSet(resultSet);
                    if (!result.isEmpty()) {
                        return result.stream().findFirst().get();
                    }
                }
                return null;
            }
            case Chats-> {
                if (parameters.length == 1) {
                    String sender = parameters[0];
                    ResultSet resultSet = db.makeExecution(dbDefines.getChats(sender));
                    List<String> result = getStrDataAtRow(resultSet, 1);
                    db.closeResultSet(resultSet);
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