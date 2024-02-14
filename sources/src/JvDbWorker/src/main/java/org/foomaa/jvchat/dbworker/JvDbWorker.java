package org.foomaa.jvchat.dbworker;

import org.foomaa.jvchat.settings.JvMainSettings;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class JvDbWorker extends JvDbDefines {
    private static Statement stmt;
    private static Connection connection;
    private static JvDbWorker instance;

    private JvDbWorker() throws SQLException {
        getConnection();
    }

    public static JvDbWorker getInstance() throws SQLException {
        if(instance == null){
            instance = new JvDbWorker();
        }
        return instance;
    }

    public void getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        connection = null;

        try {
            connection = DriverManager.getConnection(JvMainSettings.getDbUrl(),
                    JvMainSettings.getDbUser(), JvMainSettings.getMagicStringDb());
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        assert connection != null;
    }

    public void endConnection() throws SQLException {
        connection.close();
    }

    public ResultSet makeExecution(String execution) throws SQLException {
        stmt = connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet;

        resultSet = stmt.executeQuery(execution);
        return resultSet;
    }

    public List<String> getStrDataAtRow(ResultSet resultSet, int row) throws SQLException {
        // в БД нумерация рядов и столбцов не с 0, а с 1
        ResultSetMetaData metadata = resultSet.getMetaData();
        List<String> columns = new ArrayList<String>();

        int columnCount = metadata.getColumnCount();
        List<String> result = new ArrayList<String>(columnCount);

        resultSet.absolute(row);

        for (int i = 1; i <= columnCount; i++) {
            result.add(resultSet.getString(i));
        }

        return result;
    }

    public boolean ifExistsLineInTable(ResultSet resultSet) throws SQLException {
        return resultSet.next();
    }
}

