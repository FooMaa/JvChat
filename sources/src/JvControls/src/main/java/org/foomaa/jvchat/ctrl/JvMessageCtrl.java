package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.messages.JvSerializatorData;

import java.sql.SQLException;

public class JvMessageCtrl {
    private static JvMessageCtrl instance;

    private JvMessageCtrl() {
    }

    public static JvMessageCtrl getInstance() {
        if(instance == null){
            instance = new JvMessageCtrl();
        }
        return instance;
    }

    public static void sendEntryMessage(String login, String password) {
        byte[] bodyMessage = JvSerializatorData.serialiseData(JvSerializatorData.TypeMessage.Entry, login, password);
    }
}
