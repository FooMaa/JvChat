package org.foomaa.jvchat.dbworker;

import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.settings.JvGetterSettings;

import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;


@Component("beanDbWorker")
@Scope("singleton")
@Profile("servers")
public class JvDbWorker {
    private static Connection connection;

    private JvDbWorker() {
        getConnection();
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
            connection = DriverManager.getConnection(JvGetterSettings.getInstance().getBeanMainSettings().getDbUrl(),
                    JvGetterSettings.getInstance().getBeanMainSettings().getDbUser(),
                    JvGetterSettings.getInstance().getBeanMainSettings().getMagicStringDb());
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
            JvLog.write(JvLog.TypeLog.Error, "Ошибка закрытия ResultSet");
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
            JvLog.write(JvLog.TypeLog.Error, "БД вернула ошибку, невозможно выполнить запрос");
        }
        return resultSet;
    }
}