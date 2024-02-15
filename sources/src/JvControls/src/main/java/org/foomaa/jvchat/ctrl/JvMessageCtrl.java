package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.messages.JvSerializatorData;

public class JvMessageCtrl {
    private static JvMessageCtrl instance;

    private JvMessageCtrl() {}

    public static JvMessageCtrl getInstance() {
        if(instance == null){
            instance = new JvMessageCtrl();
        }
        return instance;
    }

    public static void sendMessage(JvSerializatorData.TypeMessage type, String ... parameters) {
        switch (type) {
            case EntryRequest:
                if (parameters.length == 2) {
                    String login = parameters[0];
                    String password = parameters[1];
                    sendEntryMessage(type, login, password);
                    break;
                } else {
                    break;
                }
            case RegistrationRequest:
                if (parameters.length == 2) {
                    String login = parameters[0];
                    String password = parameters[1];
                    sendRegistrationMessage(type, login, password);
                    break;
                } else {
                    break;
                }
        }

    }

    private static void sendEntryMessage(JvSerializatorData.TypeMessage type, String login, String password) {
        byte[] bodyMessage = JvSerializatorData.serialiseData(type, login, password);
        JvNetworkCtrl.putMessage(bodyMessage);
    }

    private static void sendRegistrationMessage(JvSerializatorData.TypeMessage type, String login, String password) {
        byte[] bodyMessage = JvSerializatorData.serialiseData(type, login, password);
        JvNetworkCtrl.putMessage(bodyMessage);
    }


}
