package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.messages.JvSerializatorData;

import java.io.IOException;
import java.util.HashMap;

public class JvMessageCtrl {
    private static JvMessageCtrl instance;

    // FLAGS
    public enum TypeFlags {
        TRUE,
        FALSE,
        DEFAULT
    }
    public TypeFlags ENTRYREQUEST = TypeFlags.DEFAULT;
    public TypeFlags REGISTRATIONREQUEST = TypeFlags.DEFAULT;
    // FLAGS


    private JvMessageCtrl() {}

    public static JvMessageCtrl getInstance() {
        if(instance == null){
            instance = new JvMessageCtrl();
        }
        return instance;
    }

    @SafeVarargs
    public final <TYPEPARAM> void sendMessage(JvSerializatorData.TypeMessage type, TYPEPARAM... parameters) {
        switch (type) {
            case EntryRequest:
                if (parameters.length == 2) {
                    TYPEPARAM login = parameters[0];
                    TYPEPARAM password = parameters[1];
                    byte[] bodyMessage = createBodyEntryRequestMessage(type,
                            (String) login,
                            (String) password);
                    sendReadyMessageNetwork(bodyMessage);
                }
                break;
            case RegistrationRequest:
                if (parameters.length == 2) {
                    TYPEPARAM login = parameters[0];
                    TYPEPARAM password = parameters[1];
                    byte[] bodyMessage = createBodyRegistrationRequestMessage(type,
                            (String) login,
                            (String) password);
                    sendReadyMessageNetwork(bodyMessage);
                }
                break;
            case EntryReply:
                if (parameters.length == 1) {
                    TYPEPARAM reply = parameters[0];
                    byte[] bodyMessage = createBodyEntryReplyMessage(type,
                            (Boolean) reply);
                    sendReadyMessageNetwork(bodyMessage);
                }
                break;
            case RegistrationReply:
                if (parameters.length == 1) {
                    TYPEPARAM reply = parameters[0];
                    byte[] bodyMessage = createBodyRegistrationReplyMessage(type,
                            (Boolean) reply);
                    sendReadyMessageNetwork(bodyMessage);
                }
                break;
        }

    }

    public void takeMessage(byte[] dataMsg) {
        System.out.println(dataMsg);
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

    private void sendReadyMessageNetwork(byte[] bodyMessage) {
        try {
            JvNetworkCtrl.getInstance().sendMessage(bodyMessage);
        } catch (IOException exception) {
            System.out.println("Error send");
        }
    }

    private byte[] createBodyEntryRequestMessage(JvSerializatorData.TypeMessage type, String login, String password) {
        return JvSerializatorData.serialiseData(type, login, password);
    }

    private byte[] createBodyRegistrationRequestMessage(JvSerializatorData.TypeMessage type, String login, String password) {
        return JvSerializatorData.serialiseData(type, login, password);
    }

    private byte[] createBodyEntryReplyMessage(JvSerializatorData.TypeMessage type, Boolean reply) {
        return JvSerializatorData.serialiseData(type, reply);
    }

    private byte[] createBodyRegistrationReplyMessage(JvSerializatorData.TypeMessage type, Boolean reply) {
        return JvSerializatorData.serialiseData(type, reply);
    }

    private void workEntryRequestMessage(byte[] dataMsg) {
        HashMap<JvSerializatorData.TypeData, String> map = JvSerializatorData.takeEntryRequestMessage(dataMsg);
        boolean requestDB = JvDbCtrl.getInstance().checkQueryToDB(JvDbCtrl.TypeExecutionCheck.UserPassword,
                map.get(JvSerializatorData.TypeData.Login),
                map.get(JvSerializatorData.TypeData.Password));
        sendMessage(JvSerializatorData.TypeMessage.EntryReply, requestDB);
    }

    private void workRegistrationRequestMessage(byte[] dataMsg) {
        HashMap<JvSerializatorData.TypeData, String> map = JvSerializatorData.takeRegistrationRequestMessage(dataMsg);
        boolean requestDB = JvDbCtrl.getInstance().insertQueryToDB(JvDbCtrl.TypeExecutionInsert.RegisterForm,
                map.get(JvSerializatorData.TypeData.Login),
                map.get(JvSerializatorData.TypeData.Password));
        sendMessage(JvSerializatorData.TypeMessage.RegistrationReply, requestDB);
    }

    private void workEntryReplyMessage(byte[] dataMsg) {
        HashMap<JvSerializatorData.TypeData, Boolean> map = JvSerializatorData.takeEntryReplyMessage(dataMsg);
        if (map.get(JvSerializatorData.TypeData.BoolReply)) {
            ENTRYREQUEST = TypeFlags.TRUE;
        } else {
            ENTRYREQUEST = TypeFlags.FALSE;
        }
    }

    private void workRegistrationReplyMessage(byte[] dataMsg) {
        HashMap<JvSerializatorData.TypeData, Boolean> map = JvSerializatorData.takeRegistrationReplyMessage(dataMsg);
        if (map.get(JvSerializatorData.TypeData.BoolReply)) {
            REGISTRATIONREQUEST = TypeFlags.TRUE;
        } else {
            REGISTRATIONREQUEST = TypeFlags.FALSE;
        }
    }
}
