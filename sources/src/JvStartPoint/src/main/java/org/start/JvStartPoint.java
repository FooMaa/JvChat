package org.start;

import org.auth.JvStartAuthentication;
import org.dbworker.JvDbWorker;

import java.sql.SQLException;

public class JvStartPoint
{
    public static void main( String[] args ) throws SQLException {
        JvStartAuthentication a = new JvStartAuthentication();
        JvDbWorker db = new JvDbWorker();
    }
}
