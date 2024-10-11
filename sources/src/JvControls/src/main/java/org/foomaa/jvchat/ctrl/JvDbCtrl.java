package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.dbworker.JvDbRequests;
import org.foomaa.jvchat.dbworker.JvDbWorker;
import org.foomaa.jvchat.globaldefines.JvDbGlobalDefines;
import org.foomaa.jvchat.logger.JvLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JvDbCtrl {
    private static JvDbCtrl instance;
    private JvDbWorker db;
    private JvDbRequests dbRequests;

    public enum TypeExecutionInsert {
        RegisterForm,
        ChangePassword,
        VerifyFamousEmail,
        VerifyRegistrationEmail,
        OnlineUsersInfo,
    }

    public enum TypeExecutionCheck {
        UserPassword,
        Login,
        Email,
        VerifyFamousEmailCode,
        VerifyRegistrationEmail,
    }

    public enum TypeExecutionGetSingle {
        LoginByEmail,
        IdByEmail,
        LastOnlineTimeUser,
    }

    public enum TypeExecutionGetMultiple {
        ChatsLoad,
        StatusOnlineTimeUser,
        OnlineUsers,
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
    @SuppressWarnings("unused")
    private void setDb(JvDbWorker newDb) {
        if (db != newDb) {
            db = newDb;
        }
    }

    @Autowired(required = false)
    @Qualifier("beanDbRequests")
    @Profile("servers")
    @SuppressWarnings("unused")
    private void setDbRequests(JvDbRequests newDbRequests) {
        if (dbRequests != newDbRequests) {
            dbRequests = newDbRequests;
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

    @Deprecated
    public boolean ifExistsLineInTable(ResultSet resultSet) {
        boolean res = false;
        try {
            res = resultSet.next();
        } catch (SQLException exception) {
            JvLog.write(JvLog.TypeLog.Error, "БД при проверке вернула исключение, что-то не так");
        }
        return res;
    }

    public boolean insertQueryToDB(TypeExecutionInsert type, String... parameters) {
        switch (type) {
            case RegisterForm -> {
                if (parameters.length == 3) {
                    String login = parameters[0];
                    String email = parameters[1];
                    String hashPassword = parameters[2];
                    if (!checkQueryToDB(TypeExecutionCheck.Login, login) &&
                            !checkQueryToDB(TypeExecutionCheck.Email, email)) {
                        ResultSet rs = db.makeExecution(dbRequests.insertToRegForm(login, email, hashPassword));
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
                    String hashPassword = parameters[1];
                    ResultSet rs = db.makeExecution(dbRequests.insertChangePassword(email, hashPassword));
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
                        userId = Integer.parseInt(getSingleDataFromDb(TypeExecutionGetSingle.IdByEmail, email));
                        ResultSet rs = db.makeExecution(dbRequests.insertCodeVerifyFamousEmail(userId, code));
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
                    ResultSet rs = db.makeExecution(dbRequests.insertVerifyRegistrationEmail(email, code));
                    db.closeResultSet(rs);
                    return true;
                }
                return false;
            }
            case OnlineUsersInfo -> {
                if (parameters.length == 2) {
                    String login = parameters[0];
                    String status = parameters[1];
                    System.out.println(dbRequests.insertOnlineUsersInfo(login, status));
                    ResultSet rs = db.makeExecution(dbRequests.insertOnlineUsersInfo(login, status));
                    db.closeResultSet(rs);
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public boolean checkQueryToDB(TypeExecutionCheck type, String... parameters) {
        switch (type) {
            case UserPassword -> {
                if (parameters.length == 2) {
                    String login = parameters[0];
                    String hashPassword = parameters[1];
                    ResultSet rs = db.makeExecution(dbRequests.checkUserPassword(login, hashPassword));
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
                    ResultSet rs = db.makeExecution(dbRequests.checkLogin(login));
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
                    ResultSet rs = db.makeExecution(dbRequests.checkEmail(email));
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
                    ResultSet rs = db.makeExecution(dbRequests.checkVerifyFamousEmailCode(email, code));
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
                    ResultSet rs = db.makeExecution(dbRequests.checkVerifyRegistrationEmail(email, code));
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

    public String getSingleDataFromDb(TypeExecutionGetSingle type, String... parameters) {
        switch (type) {
            case LoginByEmail -> {
                if (parameters.length == 1) {
                    String email = parameters[0];
                    ResultSet resultSet = db.makeExecution(dbRequests.getLogin(email));
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
                    ResultSet resultSet = db.makeExecution(dbRequests.getUserId(email));
                    List<String> result = getStrDataAtRow(resultSet, 1);
                    db.closeResultSet(resultSet);
                    if (!result.isEmpty()) {
                        return result.stream().findFirst().get();
                    }
                }
                return null;
            }
            case LastOnlineTimeUser -> {
                if (parameters.length == 1) {
                    String login = parameters[0];
                    ResultSet resultSet = db.makeExecution(dbRequests.getLastOnlineTimeUser(login));
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

    public List<Map<JvDbGlobalDefines.LineKeys, String>> getMultipleInfoFromDb(TypeExecutionGetMultiple type, String... parameters) {
        switch (type) {
            case ChatsLoad -> {
                if (parameters.length == 1) {
                    String sender = parameters[0];
                    ResultSet resultSet = db.makeExecution(dbRequests.getChats(sender));
                    List<Map<JvDbGlobalDefines.LineKeys, String>> result = multipleDataFromResultSet(resultSet);

                    db.closeResultSet(resultSet);

                    if (!result.isEmpty()) {
                        return result;
                    }
                }
                return null;
            }
            case StatusOnlineTimeUser -> {
                if (parameters.length == 1) {
                    String login = parameters[0];
                    ResultSet resultSet = db.makeExecution(dbRequests.getStatusOnlineTimeUser(login));
                    List<Map<JvDbGlobalDefines.LineKeys, String>> result = multipleDataFromResultSet(resultSet);

                    db.closeResultSet(resultSet);

                    if (!result.isEmpty()) {
                        return result;
                    }
                }
                return null;
            }
            case OnlineUsers -> {
                if (parameters.length == 0) {
                    ResultSet resultSet = db.makeExecution(dbRequests.getOnlineUsers());
                    List<Map<JvDbGlobalDefines.LineKeys, String>> result = multipleDataFromResultSet(resultSet);

                    db.closeResultSet(resultSet);

                    if (!result.isEmpty()) {
                        return result;
                    }
                }
                return null;
            }
        }
        return null;
    }

    public List<Map<JvDbGlobalDefines.LineKeys, String>> multipleDataFromResultSet(ResultSet resultSet) {
        List<Map<JvDbGlobalDefines.LineKeys, String>> result = new ArrayList<>();

        try {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();

            while (resultSet.next()) {
                Map<JvDbGlobalDefines.LineKeys, String> row = new HashMap<>();

                for (int i = 1; i <= columnCount; i++) {
                    String columnName = resultSetMetaData.getColumnName(i);
                    String value = resultSet.getObject(i).toString();
                    row.put(JvDbGlobalDefines.LineKeys.getTypeLineKey(columnName), value);
                }

                result.add(row);
            }
        } catch (SQLException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Ошибка при работе с ResultSet из БД");
        }

        return result;
    }
}