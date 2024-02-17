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

    public static <TYPEPARAM> void sendMessage(JvSerializatorData.TypeMessage type, TYPEPARAM... parameters) {
        switch (type) {
            case EntryRequest:
                if (parameters.length == 2) {
                    TYPEPARAM login = parameters[0];
                    TYPEPARAM password = parameters[1];
                    sendEntryRequestMessage(type,
                            (String) login, (String) password);
                    break;
                } else {
                    break;
                }
            case RegistrationRequest:
                if (parameters.length == 2) {
                    TYPEPARAM login = parameters[0];
                    TYPEPARAM password = parameters[1];
                    sendRegistrationRequestMessage(type,
                            (String) login, (String) password);
                    break;
                } else {
                    break;
                }
            case EntryReply:
                if (parameters.length == 1) {
                    TYPEPARAM reply = parameters[0];
                    sendEntryReplyMessage(type, (Boolean) reply);
                    break;
                } else {
                    break;
                }
            case RegistrationReply:
                if (parameters.length == 1) {
                    TYPEPARAM reply = parameters[0];
                    sendRegistrationReplyMessage(type, (Boolean) reply);
                    break;
                } else {
                    break;
                }
        }

    }

    public static void takeMessage(byte[] dataMsg) {
        JvSerializatorData.TypeMessage type = JvSerializatorData.getTypeMessage(dataMsg);
        switch (type) {
            case EntryRequest:
                workEntryRequestMessage(dataMsg);
                break;
            case RegistrationRequest:
                workRegistrationRequestMessage(dataMsg);
                break;
            case EntryReply:
                workEntryReplyMessage(dataMsg);
                break;
            case RegistrationReply:
                workRegistrationReplyMessage(dataMsg);
                break;
        }
    }

    private static void sendEntryRequestMessage(JvSerializatorData.TypeMessage type, String login, String password) {
        byte[] bodyMessage = JvSerializatorData.serialiseData(type, login, password);
        JvNetworkCtrl.setMessage(bodyMessage);
    }

    private static void sendRegistrationRequestMessage(JvSerializatorData.TypeMessage type, String login, String password) {
        byte[] bodyMessage = JvSerializatorData.serialiseData(type, login, password);
        JvNetworkCtrl.setMessage(bodyMessage);
    }

    private static void sendEntryReplyMessage(JvSerializatorData.TypeMessage type, Boolean reply) {
        byte[] bodyMessage = JvSerializatorData.serialiseData(type, reply);
        JvNetworkCtrl.setMessage(bodyMessage);
    }

    private static void sendRegistrationReplyMessage(JvSerializatorData.TypeMessage type, Boolean reply) {
        byte[] bodyMessage = JvSerializatorData.serialiseData(type, reply);
        JvNetworkCtrl.setMessage(bodyMessage);
    }

    private static void workEntryRequestMessage(byte[] dataMsg) {
        HashMap<JvSerializatorData.TypeData, String> map = JvSerializatorData.takeEntryRequestMessage(dataMsg);
        boolean requestDB = JvDbCtrl.getInstance().checkQueryToDB(JvDbCtrl.TypeExecutionCheck.UserPassword,
                map.get(JvSerializatorData.TypeData.Login),
                map.get(JvSerializatorData.TypeData.Password));
        sendMessage(JvSerializatorData.TypeMessage.EntryReply, requestDB);
    }

    private static void workRegistrationRequestMessage(byte[] dataMsg) {
        HashMap<JvSerializatorData.TypeData, String> map = JvSerializatorData.takeRegistrationRequestMessage(dataMsg);
        boolean requestDB = JvDbCtrl.getInstance().insertQueryToDB(JvDbCtrl.TypeExecutionInsert.RegisterForm,
                map.get(JvSerializatorData.TypeData.Login),
                map.get(JvSerializatorData.TypeData.Password));
        sendMessage(JvSerializatorData.TypeMessage.RegistrationReply, requestDB);
    }

    private static void workEntryReplyMessage(byte[] dataMsg) {
        HashMap<JvSerializatorData.TypeData, Boolean> map = JvSerializatorData.takeEntryReplyMessage(dataMsg);
        if (map.get(JvSerializatorData.TypeData.BoolReply)) {
            System.out.println("Вход разрешен");
        } else {
            System.out.println("Вход запрещен");
        }
    }

    private static void workRegistrationReplyMessage(byte[] dataMsg) {
        HashMap<JvSerializatorData.TypeData, Boolean> map = JvSerializatorData.takeRegistrationReplyMessage(dataMsg);
        if (map.get(JvSerializatorData.TypeData.BoolReply)) {
            System.out.println("Зарегистрирован");
        } else {
            System.out.println("Пользователь с таким ником уже есть");
        }
    }
}
