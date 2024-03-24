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
    private TypeFlags VerifyEmailRequestFlag = TypeFlags.DEFAULT;
    private JvSerializatorData.TypeErrorRegistration errorRegistrationFlag =
            JvSerializatorData.TypeErrorRegistration.NoError;
    private TypeFlags ChangePasswordRequest = TypeFlags.DEFAULT;
    // FLAGS

    private JvMessageCtrl() {}

    public static JvMessageCtrl getInstance() {
        if(instance == null){
            instance = new JvMessageCtrl();
        }
        return instance;
    }

    public final void sendMessage(JvSerializatorData.TypeMessage type, Object... parameters) {
        switch (type) {
            case EntryRequest -> {
                if (parameters.length == 2) {
                    Object login = parameters[0];
                    Object password = parameters[1];
                    byte[] bodyMessage = createBodyEntryRequestMessage(type,
                            (String) login,
                            (String) password);
                    sendReadyMessageNetwork(bodyMessage);
                    EntryRequestFlag = TypeFlags.DEFAULT;
                }
            }
            case RegistrationRequest -> {
                if (parameters.length == 3) {
                    Object login = parameters[0];
                    Object email = parameters[1];
                    Object password = parameters[2];
                    byte[] bodyMessage = createBodyRegistrationRequestMessage(type,
                            (String) login,
                            (String) email,
                            (String) password);
                    sendReadyMessageNetwork(bodyMessage);
                    RegistratonRequestFlag = TypeFlags.DEFAULT;
                }
            }
            case EntryReply -> {
                if (parameters.length == 1) {
                    Object reply = parameters[0];
                    byte[] bodyMessage = createBodyEntryReplyMessage(type,
                            (Boolean) reply);
                    sendReadyMessageNetwork(bodyMessage);
                }
            }
            case RegistrationReply -> {
                if (parameters.length == 2) {
                    Object reply = parameters[0];
                    Object error = parameters[1];
                    byte[] bodyMessage = createBodyRegistrationReplyMessage(type,
                            (Boolean) reply, (JvSerializatorData.TypeErrorRegistration) error);
                    sendReadyMessageNetwork(bodyMessage);
                }
            }
            case ResetPasswordRequest -> {
                if (parameters.length == 1) {
                    Object email = parameters[0];
                    byte[] bodyMessage = createBodyResetPasswordRequestMessage(type,
                            (String) email);
                    sendReadyMessageNetwork(bodyMessage);
                    ResetPasswordRequestFlag = TypeFlags.DEFAULT;
                }
            }
            case ResetPasswordReply -> {
                if (parameters.length == 1) {
                    Object reply = parameters[0];
                    byte[] bodyMessage = createBodyResetPasswordReplyMessage(type,
                            (Boolean) reply);
                    sendReadyMessageNetwork(bodyMessage);
                }
            }
            case VerifyEmailRequest -> {
                if (parameters.length == 2) {
                    Object email = parameters[0];
                    Object code = parameters[1];
                    byte[] bodyMessage = createBodyVerifyEmailRequestMessage(type,
                            (String) email, (String) code);
                    sendReadyMessageNetwork(bodyMessage);
                    VerifyEmailRequestFlag = TypeFlags.DEFAULT;
                }
            }
            case VerifyEmailReply -> {
                if (parameters.length == 1) {
                    Object reply = parameters[0];
                    byte[] bodyMessage = createBodyVerifyEmailReplyMessage(type,
                            (Boolean) reply);
                    sendReadyMessageNetwork(bodyMessage);
                }
            }
            case ChangePasswordRequest -> {
                if (parameters.length == 2) {
                    Object email = parameters[0];
                    Object password = parameters[1];
                    byte[] bodyMessage = createBodyChangePasswordRequestMessage(type,
                            (String) email, (String) password);
                    sendReadyMessageNetwork(bodyMessage);
                    ChangePasswordRequest = TypeFlags.DEFAULT;
                }
            }
            case ChangePasswordReply -> {
                if (parameters.length == 1) {
                    Object reply = parameters[0];
                    byte[] bodyMessage = createBodyChangePasswordReplyMessage(type,
                            (Boolean) reply);
                    sendReadyMessageNetwork(bodyMessage);
                }
            }
        }

    }

    public void takeMessage(byte[] data) {
        JvSerializatorData.TypeMessage type = JvSerializatorData.getTypeMessage(data);
        switch (type) {
            case EntryRequest -> workEntryRequestMessage(getDeserializeMapData(type, data));
            case RegistrationRequest -> workRegistrationRequestMessage(getDeserializeMapData(type, data));
            case EntryReply -> workEntryReplyMessage(getDeserializeMapData(type, data));
            case RegistrationReply -> workRegistrationReplyMessage(getDeserializeMapData(type, data));
            case ResetPasswordRequest -> workResetPasswordRequestMessage(getDeserializeMapData(type, data));
            case ResetPasswordReply -> workResetPasswordReplyMessage(getDeserializeMapData(type, data));
            case VerifyEmailRequest -> workVerifyEmailRequestMessage(getDeserializeMapData(type, data));
            case VerifyEmailReply -> workVerifyEmailReplyMessage(getDeserializeMapData(type, data));
            case ChangePasswordRequest -> workChangePasswordRequestMessage(getDeserializeMapData(type, data));
            case ChangePasswordReply -> workChangePasswordReplyMessage(getDeserializeMapData(type, data));
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

    private byte[] createBodyRegistrationReplyMessage(JvSerializatorData.TypeMessage type, Boolean reply, JvSerializatorData.TypeErrorRegistration error) {
        return JvSerializatorData.serialiseData(type, reply, error);
    }

    private byte[] createBodyResetPasswordRequestMessage(JvSerializatorData.TypeMessage type, String email) {
        return JvSerializatorData.serialiseData(type, email);
    }

    private byte[] createBodyResetPasswordReplyMessage(JvSerializatorData.TypeMessage type, Boolean reply) {
        return JvSerializatorData.serialiseData(type, reply);
    }

    private byte[] createBodyVerifyEmailRequestMessage(JvSerializatorData.TypeMessage type, String email, String code) {
        return JvSerializatorData.serialiseData(type, email, code);
    }

    private byte[] createBodyVerifyEmailReplyMessage(JvSerializatorData.TypeMessage type, Boolean reply) {
        return JvSerializatorData.serialiseData(type, reply);
    }

    private byte[] createBodyChangePasswordRequestMessage(JvSerializatorData.TypeMessage type, String email, String password) {
        return JvSerializatorData.serialiseData(type, email, password);
    }

    private byte[] createBodyChangePasswordReplyMessage(JvSerializatorData.TypeMessage type, Boolean reply) {
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
        JvSerializatorData.TypeErrorRegistration typeError = JvSerializatorData.TypeErrorRegistration.NoError;
        if (!requestDB) {
            boolean checkLogin =  JvDbCtrl.getInstance().checkQueryToDB(JvDbCtrl.TypeExecutionCheck.Login,
                    (String) map.get(JvSerializatorData.TypeData.Login));
            boolean checkEmail =  JvDbCtrl.getInstance().checkQueryToDB(JvDbCtrl.TypeExecutionCheck.Email,
                    (String) map.get(JvSerializatorData.TypeData.Email));
            if (!checkLogin && !checkEmail) {
                typeError = JvSerializatorData.TypeErrorRegistration.NoError;
            }
            if (checkLogin) {
                typeError = JvSerializatorData.TypeErrorRegistration.Login;
            }
            if (checkEmail) {
                typeError = JvSerializatorData.TypeErrorRegistration.Email;
            }
            if (checkLogin && checkEmail) {
                typeError = JvSerializatorData.TypeErrorRegistration.LoginAndEmail;
            }
        }
        sendMessage(JvSerializatorData.TypeMessage.RegistrationReply, requestDB, typeError);
    }

    private void workResetPasswordRequestMessage(HashMap<JvSerializatorData.TypeData, ?> map) {
        String email = (String) map.get(JvSerializatorData.TypeData.Email);
        boolean checkEmail = JvDbCtrl.getInstance().checkQueryToDB(JvDbCtrl.TypeExecutionCheck.Email,
                email);
        boolean reply = false;
        if (checkEmail) {
            JvEmailCtrl.getInstance().startVerifyEmail(email);
            reply = true;
        }
        sendMessage(JvSerializatorData.TypeMessage.ResetPasswordReply, reply);
    }

    private void workVerifyEmailRequestMessage(HashMap<JvSerializatorData.TypeData, ?> map) {
        boolean requestDB = JvDbCtrl.getInstance().checkQueryToDB(JvDbCtrl.TypeExecutionCheck.VerifyEmailCode,
                (String) map.get(JvSerializatorData.TypeData.Email),
                (String) map.get(JvSerializatorData.TypeData.VerifyCode));
        sendMessage(JvSerializatorData.TypeMessage.VerifyEmailReply, requestDB);
    }

    private void workEntryReplyMessage(HashMap<JvSerializatorData.TypeData, ?> map) {
        if ((Boolean) map.get(JvSerializatorData.TypeData.BoolReply)) {
            EntryRequestFlag = TypeFlags.TRUE;
        } else {
            EntryRequestFlag = TypeFlags.FALSE;
        }
    }

    private void workRegistrationReplyMessage(HashMap<JvSerializatorData.TypeData, ?> map) {
        if ((Boolean) map.get(JvSerializatorData.TypeData.BoolReply)) {
            RegistratonRequestFlag = TypeFlags.TRUE;
        } else {
            RegistratonRequestFlag = TypeFlags.FALSE;
        }
        errorRegistrationFlag = (JvSerializatorData.TypeErrorRegistration) map.get(JvSerializatorData.TypeData.ErrorReg);
    }

    private void workResetPasswordReplyMessage( HashMap<JvSerializatorData.TypeData, ?> map) {
        if ((Boolean) map.get(JvSerializatorData.TypeData.BoolReply)) {
            ResetPasswordRequestFlag = TypeFlags.TRUE;
        } else {
            ResetPasswordRequestFlag = TypeFlags.FALSE;
        }
    }

    private void workChangePasswordRequestMessage(HashMap<JvSerializatorData.TypeData, ?> map) {
        boolean requestDB = JvDbCtrl.getInstance().insertQueryToDB(JvDbCtrl.TypeExecutionInsert.ChangePassword,
                (String) map.get(JvSerializatorData.TypeData.Email),
                (String) map.get(JvSerializatorData.TypeData.Password));
        sendMessage(JvSerializatorData.TypeMessage.VerifyEmailReply, requestDB);
    }

    private void workChangePasswordReplyMessage( HashMap<JvSerializatorData.TypeData, ?> map) {
        if ((Boolean) map.get(JvSerializatorData.TypeData.BoolReply)) {
            ChangePasswordRequest = TypeFlags.TRUE;
        } else {
            ChangePasswordRequest = TypeFlags.FALSE;
        }
    }

    private void workVerifyEmailReplyMessage(HashMap<JvSerializatorData.TypeData, ?> map) {
        if ((Boolean) map.get(JvSerializatorData.TypeData.BoolReply)) {
            VerifyEmailRequestFlag = TypeFlags.TRUE;
        } else {
            VerifyEmailRequestFlag = TypeFlags.FALSE;
        }
    }

    public TypeFlags getEntryRequestFlag() {
        return EntryRequestFlag;
    }

    public TypeFlags getRegistrationRequestFlag() {
        return RegistratonRequestFlag;
    }

    public JvSerializatorData.TypeErrorRegistration getErrorRegistrationFlag() {
        return errorRegistrationFlag;
    }

    public TypeFlags getResetPasswordRequestFlag() {
        return ResetPasswordRequestFlag;
    }

    public TypeFlags getVerifyEmailRequestFlag() {
        return VerifyEmailRequestFlag;
    }

    public TypeFlags getChangePasswordRequest() {
        return ChangePasswordRequest;
    }
}
