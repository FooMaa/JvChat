package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.messages.JvGetterMessages;
import org.foomaa.jvchat.messages.JvMessagesDefines;

import java.util.HashMap;
import java.util.List;

public class JvTakeMessagesCtrl {
    private static JvTakeMessagesCtrl instance;

    private JvTakeMessagesCtrl() {}

    static JvTakeMessagesCtrl getInstance() {
        if (instance == null) {
            instance = new JvTakeMessagesCtrl();
        }
        return instance;
    }

    public void takeMessage(byte[] data) {
        JvMessagesDefines.TypeMessage type = JvGetterMessages.getInstance().getBeanMessagesDeserializatorData().getTypeMessage(data);
        switch (type) {
            case EntryRequest -> workEntryRequestMessage(getDeserializeMapData(type, data));
            case EntryReply -> workEntryReplyMessage(getDeserializeMapData(type, data));
            case RegistrationRequest -> workRegistrationRequestMessage(getDeserializeMapData(type, data));
            case RegistrationReply -> workRegistrationReplyMessage(getDeserializeMapData(type, data));
            case VerifyRegistrationEmailRequest -> workVerifyRegistrationEmailRequestMessage(getDeserializeMapData(type, data));
            case VerifyRegistrationEmailReply -> workVerifyRegistrationEmailReplyMessage(getDeserializeMapData(type, data));
            case ResetPasswordRequest -> workResetPasswordRequestMessage(getDeserializeMapData(type, data));
            case ResetPasswordReply -> workResetPasswordReplyMessage(getDeserializeMapData(type, data));
            case VerifyFamousEmailRequest -> workVerifyFamousEmailRequestMessage(getDeserializeMapData(type, data));
            case VerifyFamousEmailReply -> workVerifyFamousEmailReplyMessage(getDeserializeMapData(type, data));
            case ChangePasswordRequest -> workChangePasswordRequestMessage(getDeserializeMapData(type, data));
            case ChangePasswordReply -> workChangePasswordReplyMessage(getDeserializeMapData(type, data));
            case ChatsLoadRequest -> workChatsLoadRequestMessage(getDeserializeMapData(type, data));
            case ChatsLoadReply -> workChatsLoadReplyMessage(getDeserializeMapData(type, data));
        }
    }

    private HashMap<JvMessagesDefines.TypeData, ?> getDeserializeMapData(JvMessagesDefines.TypeMessage type, byte[] data) {
        return JvGetterMessages.getInstance().getBeanMessagesDeserializatorData().deserializeData(type, data);
    }

    private void workEntryRequestMessage(HashMap<JvMessagesDefines.TypeData, ?> map) {
        boolean requestDB = JvGetterControls.getInstance()
                .getBeanDbCtrl().checkQueryToDB(JvDbCtrl.TypeExecutionCheck.UserPassword,
                        (String) map.get(JvMessagesDefines.TypeData.Login),
                        (String) map.get(JvMessagesDefines.TypeData.Password));
        JvGetterControls.getInstance().getBeanSendMessagesCtrl().
                sendMessage(JvMessagesDefines.TypeMessage.EntryReply, requestDB);
    }

    private void workEntryReplyMessage(HashMap<JvMessagesDefines.TypeData, ?> map) {
        if ((Boolean) map.get(JvMessagesDefines.TypeData.BoolReply)) {
            JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().
                    setEntryRequestFlag(JvMessagesDefinesCtrl.TypeFlags.TRUE);
        } else {
            JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().
                    setEntryRequestFlag(JvMessagesDefinesCtrl.TypeFlags.FALSE);
        }
    }

    private void workRegistrationRequestMessage(HashMap<JvMessagesDefines.TypeData, ?> map) {
        boolean requestDB = false;
        JvMessagesDefines.TypeErrorRegistration typeError = JvMessagesDefines.TypeErrorRegistration.NoError;
        boolean checkLogin =  JvGetterControls.getInstance()
                .getBeanDbCtrl().checkQueryToDB(JvDbCtrl.TypeExecutionCheck.Login,
                        (String) map.get(JvMessagesDefines.TypeData.Login));
        boolean checkEmail =  JvGetterControls.getInstance()
                .getBeanDbCtrl().checkQueryToDB(JvDbCtrl.TypeExecutionCheck.Email,
                        (String) map.get(JvMessagesDefines.TypeData.Email));
        if (checkLogin) {
            typeError = JvMessagesDefines.TypeErrorRegistration.Login;
        }
        if (checkEmail) {
            typeError = JvMessagesDefines.TypeErrorRegistration.Email;
        }
        if (checkLogin && checkEmail) {
            typeError = JvMessagesDefines.TypeErrorRegistration.LoginAndEmail;
        }
        if (typeError == JvMessagesDefines.TypeErrorRegistration.NoError) {
            requestDB = JvGetterControls.getInstance()
                    .getBeanEmailCtrl().startVerifyRegEmail((String) map.get(JvMessagesDefines.TypeData.Email));
            typeError = JvMessagesDefines.TypeErrorRegistration.EmailSending;
        }
        JvGetterControls.getInstance().getBeanSendMessagesCtrl().
                sendMessage(JvMessagesDefines.TypeMessage.RegistrationReply, requestDB, typeError);
    }

    private void workRegistrationReplyMessage(HashMap<JvMessagesDefines.TypeData, ?> map) {
        if ((Boolean) map.get(JvMessagesDefines.TypeData.BoolReply)) {
            JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().
                    setRegistrationRequestFlag(JvMessagesDefinesCtrl.TypeFlags.TRUE);
        } else {
            JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().
                    setRegistrationRequestFlag(JvMessagesDefinesCtrl.TypeFlags.FALSE);
        }
        JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().
                setErrorRegistrationFlag((JvMessagesDefines.TypeErrorRegistration) map.get(JvMessagesDefines.TypeData.ErrorReg));
    }

    private void workVerifyRegistrationEmailRequestMessage(HashMap<JvMessagesDefines.TypeData, ?> map) {
        boolean checkCode = JvGetterControls.getInstance()
                .getBeanDbCtrl().checkQueryToDB(JvDbCtrl.TypeExecutionCheck.VerifyRegistrationEmail,
                        (String) map.get(JvMessagesDefines.TypeData.Email),
                        (String) map.get(JvMessagesDefines.TypeData.VerifyCode));
        if (checkCode) {
            boolean requestDB = JvGetterControls.getInstance()
                    .getBeanDbCtrl().insertQueryToDB(JvDbCtrl.TypeExecutionInsert.RegisterForm,
                            (String) map.get(JvMessagesDefines.TypeData.Login),
                            (String) map.get(JvMessagesDefines.TypeData.Email),
                            (String) map.get(JvMessagesDefines.TypeData.Password));
            JvMessagesDefines.TypeErrorRegistration typeError = JvMessagesDefines.TypeErrorRegistration.NoError;
            if (!requestDB) {
                boolean checkLogin = JvGetterControls.getInstance()
                        .getBeanDbCtrl().checkQueryToDB(JvDbCtrl.TypeExecutionCheck.Login,
                                (String) map.get(JvMessagesDefines.TypeData.Login));
                boolean checkEmail = JvGetterControls.getInstance()
                        .getBeanDbCtrl().checkQueryToDB(JvDbCtrl.TypeExecutionCheck.Email,
                                (String) map.get(JvMessagesDefines.TypeData.Email));
                if (checkLogin) {
                    typeError = JvMessagesDefines.TypeErrorRegistration.Login;
                }
                if (checkEmail) {
                    typeError = JvMessagesDefines.TypeErrorRegistration.Email;
                }
                if (checkLogin && checkEmail) {
                    typeError = JvMessagesDefines.TypeErrorRegistration.LoginAndEmail;
                }
            }
            JvGetterControls.getInstance().getBeanSendMessagesCtrl().
                    sendMessage(JvMessagesDefines.TypeMessage.VerifyRegistrationEmailReply, requestDB, typeError);
        } else {
            JvGetterControls.getInstance().getBeanSendMessagesCtrl().
                    sendMessage(JvMessagesDefines.TypeMessage.VerifyRegistrationEmailReply, false, JvMessagesDefines.TypeErrorRegistration.Code);
        }
    }

    private void workVerifyRegistrationEmailReplyMessage(HashMap<JvMessagesDefines.TypeData, ?> map) {
        if ((Boolean) map.get(JvMessagesDefines.TypeData.BoolReply)) {
            JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().
                    setVerifyRegistrationEmailRequestFlag(JvMessagesDefinesCtrl.TypeFlags.TRUE);
        } else {
            JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().
                    setVerifyRegistrationEmailRequestFlag(JvMessagesDefinesCtrl.TypeFlags.FALSE);
        }
        JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().
                setErrorVerifyRegEmailFlag((JvMessagesDefines.TypeErrorRegistration) map.get(JvMessagesDefines.TypeData.ErrorReg));
    }

    private void workResetPasswordRequestMessage(HashMap<JvMessagesDefines.TypeData, ?> map) {
        String email = (String) map.get(JvMessagesDefines.TypeData.Email);
        boolean checkEmail = JvGetterControls.getInstance()
                .getBeanDbCtrl().checkQueryToDB(JvDbCtrl.TypeExecutionCheck.Email,
                        email);
        boolean reply = false;
        if (checkEmail) {
            reply = JvGetterControls.getInstance()
                    .getBeanEmailCtrl().startVerifyFamousEmail(email);
        }
        JvGetterControls.getInstance().getBeanSendMessagesCtrl().
                sendMessage(JvMessagesDefines.TypeMessage.ResetPasswordReply, reply);
    }

    private void workResetPasswordReplyMessage( HashMap<JvMessagesDefines.TypeData, ?> map) {
        if ((Boolean) map.get(JvMessagesDefines.TypeData.BoolReply)) {
            JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().
                    setResetPasswordRequestFlag(JvMessagesDefinesCtrl.TypeFlags.TRUE);
        } else {
            JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().
                    setResetPasswordRequestFlag(JvMessagesDefinesCtrl.TypeFlags.FALSE);
        }
    }

    private void workVerifyFamousEmailRequestMessage(HashMap<JvMessagesDefines.TypeData, ?> map) {
        boolean requestDB = JvGetterControls.getInstance()
                .getBeanDbCtrl().checkQueryToDB(JvDbCtrl.TypeExecutionCheck.VerifyFamousEmailCode,
                        (String) map.get(JvMessagesDefines.TypeData.Email),
                        (String) map.get(JvMessagesDefines.TypeData.VerifyCode));
        JvGetterControls.getInstance().getBeanSendMessagesCtrl().
                sendMessage(JvMessagesDefines.TypeMessage.VerifyFamousEmailReply, requestDB);
    }

    private void workVerifyFamousEmailReplyMessage(HashMap<JvMessagesDefines.TypeData, ?> map) {
        if ((Boolean) map.get(JvMessagesDefines.TypeData.BoolReply)) {
            JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().
                    setVerifyFamousEmailRequestFlag(JvMessagesDefinesCtrl.TypeFlags.TRUE);
        } else {
            JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().
                    setVerifyFamousEmailRequestFlag(JvMessagesDefinesCtrl.TypeFlags.FALSE);
        }
    }

    private void workChangePasswordRequestMessage(HashMap<JvMessagesDefines.TypeData, ?> map) {
        boolean requestDB = JvGetterControls.getInstance()
                .getBeanDbCtrl().insertQueryToDB(JvDbCtrl.TypeExecutionInsert.ChangePassword,
                        (String) map.get(JvMessagesDefines.TypeData.Email),
                        (String) map.get(JvMessagesDefines.TypeData.Password));
        JvGetterControls.getInstance().getBeanSendMessagesCtrl().
                sendMessage(JvMessagesDefines.TypeMessage.ChangePasswordReply, requestDB);
    }

    private void workChangePasswordReplyMessage(HashMap<JvMessagesDefines.TypeData, ?> map) {
        if ((Boolean) map.get(JvMessagesDefines.TypeData.BoolReply)) {
            JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().
                    setChangePasswordRequest(JvMessagesDefinesCtrl.TypeFlags.TRUE);
        } else {
            JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().
                    setChangePasswordRequest(JvMessagesDefinesCtrl.TypeFlags.FALSE);
        }
    }

    private void workChatsLoadRequestMessage(HashMap<JvMessagesDefines.TypeData, ?> map) {
        List<String> requestDB = JvGetterControls.getInstance()
                .getBeanDbCtrl().getMultipleInfoFromDb(JvDbCtrl.TypeExecutionGetMultiple.ChatsLoad,
                        (String) map.get(JvMessagesDefines.TypeData.ChatsLoad));
        JvGetterControls.getInstance().getBeanSendMessagesCtrl().
                sendMessage(JvMessagesDefines.TypeMessage.ChatsLoadReply, requestDB);
    }

    private void workChatsLoadReplyMessage(HashMap<JvMessagesDefines.TypeData, ?> map) {
        System.out.println("Zaglushka");
    }
}
