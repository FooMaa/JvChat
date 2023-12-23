package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.dbworker.JvDbDefines;
import org.foomaa.jvchat.dbworker.JvDbWorker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JvDbCtrl
{
    private static JvDbCtrl instance;
    private static JvDbWorker db;
    enum TypeExecution {
        InsertRegisterForm
    }

    private JvDbCtrl() throws SQLException {
        db = JvDbWorker.getInstance();
        ResultSet rs = db.makeExecution(JvDbDefines.exec);
        List<String> al = db.getStrDataAtRow(rs, 1);
        db.endConnection();
    }

    public static JvDbCtrl getInstance() throws SQLException {
        if(instance == null){
            instance = new JvDbCtrl();
        }
        return instance;
    }

    public String makeQuery(TypeExecution type) {
        switch (type) {
            case InsertRegisterForm -> {
                return JvDbDefines.insertFromRegForm("aaa", "aaa");
            }
        }
        return null;
    }

    public void query(String qr) throws SQLException {
        db.makeExecution(makeQuery(TypeExecution.InsertRegisterForm));
    }
}
