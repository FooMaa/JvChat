package org.dbworker;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class JvDbWorker extends JvDbDefines
{
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/chat";
    static final String USER = "jvchat";
    static final String PASS = "1111";
    static Statement stmt;
    static  Connection connection;
    public JvDbWorker() throws SQLException {
        getConnection();
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
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        assert connection != null;

        stmt = connection.createStatement();
        ResultSet rs;

        rs = stmt.executeQuery("select * from chat_schema.users ;");
        while ( rs.next() ) {
            String numID = rs.getString("name");
            System.out.println(numID);
        }
    }

    public void endConnection() throws SQLException {
        connection.close();
    }

    public ResultSet makeExecution( String execution ) throws SQLException {
        stmt = connection.createStatement();
        ResultSet resultSet;

        resultSet = stmt.executeQuery("select * from chat_schema.users ;");
        return resultSet;
//        while ( resultSet.next() ) {
//            String numID = resultSet.getString("name");
//            System.out.println(numID);
//        }
    }
}

