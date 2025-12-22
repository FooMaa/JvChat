package org.foomaa.jvchat.dbworker;

import org.foomaa.jvchat.settings.ServersInfoSettings;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import org.foomaa.jvchat.logger.Log;


@Component
@Profile("servers")
public class DbWorker {
    private static Connection connection;

    private DbWorker(ServersInfoSettings serversInfoSettings) {
        getConnection(serversInfoSettings);
    }

    public void getConnection(ServersInfoSettings serversInfoSettings) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            Log.write(Log.TypeLog.Error, "Error in connect to DB.");
            return;
        }

        connection = null;

        try {
            connection = DriverManager.getConnection(serversInfoSettings.getDbUrl(),
                    serversInfoSettings.getDbUser(),
                    serversInfoSettings.getMagicStringDb());
        } catch (SQLException e) {
            Log.write(Log.TypeLog.Error, "Error in connect to DB.");
            return;
        }

        assert connection != null;
    }

    public void closeResultSet(ResultSet rs) {
        try {
            rs.close();
        } catch (SQLException exception) {
            Log.write(Log.TypeLog.Error, "Error closing ResultSet.");
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
            Log.write(Log.TypeLog.Error, "The database returned an error, the request cannot be executed.");
        }
        return resultSet;
    }
}