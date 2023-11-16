package org.foomaa.jvchat.startpoint;

import org.foomaa.jvchat.dbworker.JvDbDefines;
import org.foomaa.jvchat.dbworker.JvDbWorker;
import org.foomaa.jvchat.auth.JvStartAuthentication;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JvStartPoint {
    public static void main(String[] args) throws SQLException {
        System.out.println("lolololol");
        JvDbWorker db = new JvDbWorker();
        JvStartAuthentication a = new JvStartAuthentication();
        ResultSet rs = db.makeExecution(JvDbDefines.exec);
        List<String> al = db.getStrDataAtRow(rs, 1);
        db.endConnection();
    }
}
