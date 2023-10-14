package org.start;

import org.auth.JvStartAuthentication;
import org.dbworker.JvDbWorker;

import java.sql.SQLException;

public class JvStartPoint
{
    public static void main( String[] args ) throws SQLException {
        JvDbWorker db = new JvDbWorker();
        JvStartAuthentication a = new JvStartAuthentication();
        //rs = db.makeExecution("select * from chat_schema.users ;");
        //rs =
        db.endConnection();
    }
}
