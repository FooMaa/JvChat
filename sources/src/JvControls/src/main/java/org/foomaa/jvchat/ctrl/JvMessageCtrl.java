package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.messages.JvSerializatorData;

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

    public static void sendMessage(JvSerializatorData.TypeMessage type, String ... parameters) {
        switch (type) {
            case Entry:
                if (parameters.length == 2) {
                    String login = parameters[0];
                    String password = parameters[1];
                    sendEntryMessage(type, login, password);
                } else {
                    break;
                }
            case Registration:
                if (parameters.length == 2) {
                    String login = parameters[0];
                    String password = parameters[1];
                    sendRegistrationMessage(type, login, password);
                } else {
                    break;
                }
        }

    }

    public static void sendEntryMessage(JvSerializatorData.TypeMessage type, String login, String password) {
        byte[] bodyMessage = JvSerializatorData.serialiseData(type, login, password);
    }

    public static void sendRegistrationMessage(JvSerializatorData.TypeMessage type, String login, String password) {
        byte[] bodyMessage = JvSerializatorData.serialiseData(type, login, password);
    }
}
