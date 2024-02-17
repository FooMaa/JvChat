package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.messages.JvSerializatorData;

import java.util.HashMap;

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
        JvNetworkCtrl.setMessage(bodyMessage);
    }

    private static void sendRegistrationMessage(JvSerializatorData.TypeMessage type, String login, String password) {
        byte[] bodyMessage = JvSerializatorData.serialiseData(type, login, password);
        JvNetworkCtrl.setMessage(bodyMessage);
    }
    
    public static void takeMessage(byte[] dataMsg) {
        JvSerializatorData.TypeMessage type = JvSerializatorData.getTypeMessage(dataMsg);
        HashMap<JvSerializatorData.TypeData, String> map;
        switch (type) {
            case EntryRequest:
                map = JvSerializatorData.takeEntryRequestMessage(dataMsg);
                boolean requestDB = JvDbCtrl.getInstance().checkQueryToDB(JvDbCtrl.TypeExecutionCheck.UserPassword,
                        map.get(JvSerializatorData.TypeData.Login),  map.get(JvSerializatorData.TypeData.Password));
                System.out.println(requestDB);
                break;
            case RegistrationRequest:
                map = JvSerializatorData.takeRegistrationRequestMessage(dataMsg);
                break;
        }
    }
}
