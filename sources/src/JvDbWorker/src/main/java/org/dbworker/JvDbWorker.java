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
        System.out.println("Testing connection to PostgreSQL JDBC");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
            return;
        }

        System.out.println("PostgreSQL JDBC Driver successfully connected");
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
            return;
        }
        Statement stmt = connection.createStatement();
        ResultSet rs;

        rs = stmt.executeQuery("select * from chat_schema.users ;");
        while ( rs.next() ) {
            String numID = rs.getString("name");
            System.out.println(numID);
        }

        if (connection != null) {
            System.out.println("You successfully connected to database now");
        } else {
            System.out.println("Failed to make connection to database");
        }
        connection.close();
    }
}

