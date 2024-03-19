package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.messages.JvSerializatorData;

import java.util.HashMap;

public class JvMessageCtrl {
    private static JvMessageCtrl instance;

    // FLAGS
    public enum TypeFlags {
        TRUE,
        FALSE,
        DEFAULT
    }
    private TypeFlags EntryRequestFlag = TypeFlags.DEFAULT;
    private TypeFlags RegistratonRequestFlag = TypeFlags.DEFAULT;
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
                    EntryRequestFlag = TypeFlags.DEFAULT;
                }
                break;
            case RegistrationRequest:
                if (parameters.length == 3) {
                    TYPEPARAM login = parameters[0];
                    TYPEPARAM email = parameters[1];
                    TYPEPARAM password = parameters[2];
                    byte[] bodyMessage = createBodyRegistrationRequestMessage(type,
                            (String) login,
                            (String) email,
                            (String) password);
                    sendReadyMessageNetwork(bodyMessage);
                    RegistratonRequestFlag = TypeFlags.DEFAULT;
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
        JvSerializatorData.TypeMessage type = JvSerializatorData.getTypeMessage(dataMsg);
        switch (type) {
            case EntryRequest:
                workEntryRequestMessage(getDeserializeMapData(type, dataMsg));
                break;
            case RegistrationRequest:
                workRegistrationRequestMessage(getDeserializeMapData(type, dataMsg));
                break;
            case EntryReply:
                workEntryReplyMessage(getDeserializeMapData(type, dataMsg));
                break;
            case RegistrationReply:
                workRegistrationReplyMessage(getDeserializeMapData(type, dataMsg));
                break;
        }
    }

    private <TYPEPARAM> HashMap<JvSerializatorData.TypeData, TYPEPARAM>
    getDeserializeMapData(JvSerializatorData.TypeMessage type, byte[] data) {
        return JvSerializatorData.deserializeData(type, data);
    }

    private void sendReadyMessageNetwork(byte[] bodyMessage) {
        JvNetworkCtrl.sendMessage(bodyMessage);
    }

    private byte[] createBodyEntryRequestMessage(JvSerializatorData.TypeMessage type, String login, String password) {
        return JvSerializatorData.serialiseData(type, login, password);
    }

    private byte[] createBodyRegistrationRequestMessage(JvSerializatorData.TypeMessage type, String login, String email, String password) {
        return JvSerializatorData.serialiseData(type, login, email, password);
    }

    private byte[] createBodyEntryReplyMessage(JvSerializatorData.TypeMessage type, Boolean reply) {
        return JvSerializatorData.serialiseData(type, reply);
    }

    private byte[] createBodyRegistrationReplyMessage(JvSerializatorData.TypeMessage type, Boolean reply) {
        return JvSerializatorData.serialiseData(type, reply);
    }

    private void workEntryRequestMessage(HashMap<JvSerializatorData.TypeData, String> map) {
        boolean requestDB = JvDbCtrl.getInstance().checkQueryToDB(JvDbCtrl.TypeExecutionCheck.UserPassword,
                map.get(JvSerializatorData.TypeData.Login),
                map.get(JvSerializatorData.TypeData.Password));
        sendMessage(JvSerializatorData.TypeMessage.EntryReply, requestDB);
    }

    private void workRegistrationRequestMessage(HashMap<JvSerializatorData.TypeData, String> map) {
        boolean requestDB = JvDbCtrl.getInstance().insertQueryToDB(JvDbCtrl.TypeExecutionInsert.RegisterForm,
                map.get(JvSerializatorData.TypeData.Login),
                map.get(JvSerializatorData.TypeData.Email),
                map.get(JvSerializatorData.TypeData.Password));
        sendMessage(JvSerializatorData.TypeMessage.RegistrationReply, requestDB);
    }

    private void workEntryReplyMessage(HashMap<JvSerializatorData.TypeData, Boolean> map) {
        if (map.get(JvSerializatorData.TypeData.BoolReply)) {
            EntryRequestFlag = TypeFlags.TRUE;
        } else {
            EntryRequestFlag = TypeFlags.FALSE;
        }
    }

    private void workRegistrationReplyMessage( HashMap<JvSerializatorData.TypeData, Boolean> map) {
        if (map.get(JvSerializatorData.TypeData.BoolReply)) {
            RegistratonRequestFlag = TypeFlags.TRUE;
        } else {
            RegistratonRequestFlag = TypeFlags.FALSE;
        }
    }

    public TypeFlags getEntryRequestFlag() {
        return EntryRequestFlag;
    }

    public TypeFlags getRegistrationRequestFlag() {
        return RegistratonRequestFlag;
    }
}
