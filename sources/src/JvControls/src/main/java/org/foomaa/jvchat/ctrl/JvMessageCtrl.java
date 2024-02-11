package org.foomaa.jvchat.ctrl;

import java.sql.SQLException;

public class JvMessageCtrl {
    private static JvMessageCtrl instance;

    private JvMessageCtrl() {
        System.out.println("hello msg");
        // hello
    }

    public static JvMessageCtrl getInstance() {
        if(instance == null){
            instance = new JvMessageCtrl();
        }
        return instance;
    }
}
