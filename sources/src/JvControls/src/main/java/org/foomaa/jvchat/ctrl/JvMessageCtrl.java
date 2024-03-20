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
    private TypeFlags ResetPasswordRequestFlag = TypeFlags.DEFAULT;
    private TypeFlags VerifyResetPasswordRequestFlag = TypeFlags.DEFAULT;
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
            case ResetPasswordRequest:
                if (parameters.length == 1) {
                    TYPEPARAM email = parameters[0];
                    byte[] bodyMessage = createBodyResetPasswordRequestMessage(type,
                            (String) email);
                    sendReadyMessageNetwork(bodyMessage);
                }
                break;
            case ResetPasswordReply:
                if (parameters.length == 1) {
                    TYPEPARAM reply = parameters[0];
                    byte[] bodyMessage = createBodyResetPasswordReplyMessage(type,
                            (Boolean) reply);
                    sendReadyMessageNetwork(bodyMessage);
                }
                break;
            case VerifyResetPasswordRequest:
                if (parameters.length == 1) {
                    TYPEPARAM code = parameters[0];
                    byte[] bodyMessage = createBodyVerifyResetPasswordRequestMessage(type,
                            (String) code);
                    sendReadyMessageNetwork(bodyMessage);
                }
                break;
            case VerifyResetPasswordReply:
                if (parameters.length == 1) {
                    TYPEPARAM reply = parameters[0];
                    byte[] bodyMessage = createBodyVerifyResetPasswordReplyMessage(type,
                            (Boolean) reply);
                    sendReadyMessageNetwork(bodyMessage);
                }
                break;
        }

    }

    public void takeMessage(byte[] data) {
        JvSerializatorData.TypeMessage type = JvSerializatorData.getTypeMessage(data);
        switch (type) {
            case EntryRequest:
                workEntryRequestMessage(getDeserializeMapData(type, data));
                break;
            case RegistrationRequest:
                workRegistrationRequestMessage(getDeserializeMapData(type, data));
                break;
            case EntryReply:
                workEntryReplyMessage(getDeserializeMapData(type, data));
                break;
            case RegistrationReply:
                workRegistrationReplyMessage(getDeserializeMapData(type, data));
                break;
            case ResetPasswordRequest:
                workResetPasswordRequestMessage(getDeserializeMapData(type, data));
                break;
            case ResetPasswordReply:
                workResetPasswordReplyMessage(getDeserializeMapData(type, data));
                break;
            case VerifyResetPasswordRequest:
                workVerifyResetPasswordRequestMessage(getDeserializeMapData(type, data));
                break;
            case VerifyResetPasswordReply:
                workVerifyResetPasswordReplyMessage(getDeserializeMapData(type, data));
                break;
        }
    }

    private HashMap<JvSerializatorData.TypeData, ?>
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

    private byte[] createBodyResetPasswordRequestMessage(JvSerializatorData.TypeMessage type, String email) {
        return JvSerializatorData.serialiseData(type, email);
    }

    private byte[] createBodyResetPasswordReplyMessage(JvSerializatorData.TypeMessage type, Boolean reply) {
        return JvSerializatorData.serialiseData(type, reply);
    }

    private byte[] createBodyVerifyResetPasswordRequestMessage(JvSerializatorData.TypeMessage type, String code) {
        return JvSerializatorData.serialiseData(type, code);
    }

    private byte[] createBodyVerifyResetPasswordReplyMessage(JvSerializatorData.TypeMessage type, Boolean reply) {
        return JvSerializatorData.serialiseData(type, reply);
    }

    private void workEntryRequestMessage(HashMap<JvSerializatorData.TypeData, ?> map) {
        boolean requestDB = JvDbCtrl.getInstance().checkQueryToDB(JvDbCtrl.TypeExecutionCheck.UserPassword,
                (String) map.get(JvSerializatorData.TypeData.Login),
                (String) map.get(JvSerializatorData.TypeData.Password));
        sendMessage(JvSerializatorData.TypeMessage.EntryReply, requestDB);
    }

    private void workRegistrationRequestMessage(HashMap<JvSerializatorData.TypeData, ?> map) {
        boolean requestDB = JvDbCtrl.getInstance().insertQueryToDB(JvDbCtrl.TypeExecutionInsert.RegisterForm,
                (String) map.get(JvSerializatorData.TypeData.Login),
                (String) map.get(JvSerializatorData.TypeData.Email),
                (String) map.get(JvSerializatorData.TypeData.Password));
        sendMessage(JvSerializatorData.TypeMessage.RegistrationReply, requestDB);
    }

    private void workResetPasswordRequestMessage(HashMap<JvSerializatorData.TypeData, ?> map) {
//        boolean requestDB = JvDbCtrl.getInstance().insertQueryToDB(JvDbCtrl.TypeExecutionInsert.RegisterForm,
//                (String) map.get(JvSerializatorData.TypeData.Login),
//                (String) map.get(JvSerializatorData.TypeData.Email),
//                (String) map.get(JvSerializatorData.TypeData.Password));
//        sendMessage(JvSerializatorData.TypeMessage.RegistrationReply, requestDB);
    }

    private void workVerifyResetPasswordRequestMessage(HashMap<JvSerializatorData.TypeData, ?> map) {
//        boolean requestDB = JvDbCtrl.getInstance().insertQueryToDB(JvDbCtrl.TypeExecutionInsert.RegisterForm,
//                (String) map.get(JvSerializatorData.TypeData.Login),
//                (String) map.get(JvSerializatorData.TypeData.Email),
//                (String) map.get(JvSerializatorData.TypeData.Password));
//        sendMessage(JvSerializatorData.TypeMessage.RegistrationReply, requestDB);
    }

    private void workEntryReplyMessage(HashMap<JvSerializatorData.TypeData, ?> map) {
        if ((Boolean) map.get(JvSerializatorData.TypeData.BoolReply)) {
            EntryRequestFlag = TypeFlags.TRUE;
        } else {
            EntryRequestFlag = TypeFlags.FALSE;
        }
    }

    private void workRegistrationReplyMessage( HashMap<JvSerializatorData.TypeData, ?> map) {
        if ((Boolean) map.get(JvSerializatorData.TypeData.BoolReply)) {
            RegistratonRequestFlag = TypeFlags.TRUE;
        } else {
            RegistratonRequestFlag = TypeFlags.FALSE;
        }
    }

    private void workResetPasswordReplyMessage( HashMap<JvSerializatorData.TypeData, ?> map) {
        if ((Boolean) map.get(JvSerializatorData.TypeData.BoolReply)) {
            ResetPasswordRequestFlag = TypeFlags.TRUE;
        } else {
            ResetPasswordRequestFlag = TypeFlags.FALSE;
        }
    }

    private void workVerifyResetPasswordReplyMessage( HashMap<JvSerializatorData.TypeData, ?> map) {
        if ((Boolean) map.get(JvSerializatorData.TypeData.BoolReply)) {
            VerifyResetPasswordRequestFlag = TypeFlags.TRUE;
        } else {
            VerifyResetPasswordRequestFlag = TypeFlags.FALSE;
        }
    }

    public TypeFlags getEntryRequestFlag() {
        return EntryRequestFlag;
    }

    public TypeFlags getRegistrationRequestFlag() {
        return RegistratonRequestFlag;
    }
}
