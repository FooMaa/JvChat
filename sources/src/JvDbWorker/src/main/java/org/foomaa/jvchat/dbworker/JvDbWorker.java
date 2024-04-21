package org.foomaa.jvchat.dbworker;

import org.foomaa.jvchat.settings.JvMainSettings;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class JvDbWorker extends JvDbDefines {
    private static Connection connection;
    private static JvDbWorker instance;

    private JvDbWorker() {
        getConnection();
    }

    public static JvDbWorker getInstance() {
        if(instance == null){
            instance = new JvDbWorker();
        }
        return instance;
    }

    public void getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error in connect to DB");
            return;
        }

        connection = null;

        try {
            connection = DriverManager.getConnection(JvMainSettings.getDbUrl(),
                    JvMainSettings.getDbUser(), JvMainSettings.getMagicStringDb());
        } catch (SQLException e) {
            System.out.println("Error in connect to DB");
            return;
        }

        assert connection != null;
    }

    public void closeResultSet(ResultSet rs) {
        try {
            rs.close();
        } catch (SQLException exception) {
            System.out.println("Ошибка закрытия ResultSet");
        }
    }

    public void endConnection() throws SQLException {
        connection.close();
    }

    public ResultSet makeExecution(String execution) {
        ResultSet resultSet = null;
        try {
            Statement stmt = connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            resultSet = stmt.executeQuery(execution);
        } catch (SQLException exception) {
            System.out.println("БД вернула ошибку, невозможно выполнить запрос");
        }
        return resultSet;
    }

    public List<String> getStrDataAtRow(ResultSet resultSet, int row) {
        // в БД нумерация рядов и столбцов не с 0, а с 1
        ResultSetMetaData metadata;
        int columnCount = 0;
        try {
            metadata = resultSet.getMetaData();
            columnCount = metadata.getColumnCount();
        } catch (SQLException exception) {
            System.out.println("Не возможно получить данные по столбцам и метаданные");
        }

        // List<String> columns = new ArrayList<>();
        List<String> result = new ArrayList<>(columnCount);

        try {
            resultSet.absolute(row);

            for (int i = 1; i <= columnCount; i++) {
                result.add(resultSet.getString(i));
            }
        } catch (SQLException exception) {
            System.out.println("Не вышло получить данные по ряду");
        }

        return result;
    }

    public boolean ifExistsLineInTable(ResultSet resultSet) {
        boolean res = false;
        try {
            res = resultSet.next();
        } catch (SQLException exception) {
            System.out.println("БД при проверке вернула исключение, что-то не так");
        }
        return res;
    }
}