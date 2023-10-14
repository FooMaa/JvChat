package org.dbworker;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class JvDbWorker
{
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/chat";
    static final String USER = "jvchat";
    static final String PASS = "1111";
    public JvDbWorker() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        assert connection != null;
        Statement stmt = connection.createStatement();
        ResultSet rs;

        rs = stmt.executeQuery("select * from chat_schema.users ;");
        while ( rs.next() ) {
            String numID = rs.getString("name");
            System.out.println(numID);
        }


        connection.close();
    }
}

