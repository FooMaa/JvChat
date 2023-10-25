package org.start;

import org.auth.JvStartAuthentication;
import org.dbworker.JvDbDefines;
import org.dbworker.JvDbWorker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JvStartPoint
{
    public static void main( String[] args ) throws SQLException {
        JvDbWorker db = new JvDbWorker();
        JvStartAuthentication a = new JvStartAuthentication();
        ResultSet rs = db.makeExecution( JvDbDefines.exec );
        List<String> al = db.getStrDataAtRow( rs,2 );
        db.endConnection();
    }
}
