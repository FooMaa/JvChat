package org.foomaa.jvchat.dbworker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.settings.ServersInfoSettings;

@Component
@Profile("servers")
@Slf4j
public class DbWorker {
    private static Connection connection;

    private DbWorker(ServersInfoSettings serversInfoSettings) {
        getConnection(serversInfoSettings);
    }

    public void getConnection(ServersInfoSettings serversInfoSettings) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            log.error("Error in connect to DB.");
            return;
        }

        connection = null;

        try {
            connection = DriverManager.getConnection(serversInfoSettings.getDbUrl(), serversInfoSettings.getDbUser(),
                    serversInfoSettings.getMagicStringDb());
        } catch (SQLException e) {
            log.error("Error in connect to DB.");
            return;
        }

        assert connection != null;
    }

    public void closeResultSet(ResultSet rs) {
        try {
            rs.close();
        } catch (SQLException exception) {
            log.error("Error closing ResultSet.");
        }
    }

    public void endConnection() throws SQLException {
        connection.close();
    }

    public ResultSet makeExecution(String execution) {
        ResultSet resultSet = null;
        try {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = stmt.executeQuery(execution);
        } catch (SQLException exception) {
            log.error("The database returned an error, the request cannot be executed.");
        }
        return resultSet;
    }
}
