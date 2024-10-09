package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.cryptography.JvGetterCryptography;
import org.foomaa.jvchat.globaldefines.JvDbGlobalDefines;
import org.foomaa.jvchat.globaldefines.JvMainChatsGlobalDefines;
import org.foomaa.jvchat.messages.JvGetterMessages;
import org.foomaa.jvchat.messages.JvDefinesMessages;
import org.foomaa.jvchat.settings.JvGetterSettings;
import org.foomaa.jvchat.tools.JvGetterTools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JvTakeMessagesCtrl {
    private static JvTakeMessagesCtrl instance;

    private Thread threadFrom;

    private JvTakeMessagesCtrl() {}

    static JvTakeMessagesCtrl getInstance() {
        if (instance == null) {
            instance = new JvTakeMessagesCtrl();
        }
        return instance;
    }

    public void takeMessage(byte[] data) {
        JvDefinesMessages.TypeMessage type = JvGetterMessages.getInstance().getBeanDeserializatorDataMessages().getTypeMessage(data);
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
            case CheckOnlineUserRequest -> workCheckOnlineUserRequestMessage(getDeserializeMapData(type, data));
            case CheckOnlineUserReply -> workCheckOnlineUserReplyMessage(getDeserializeMapData(type, data));
            case ChatsLoadRequest -> workChatsLoadRequestMessage(getDeserializeMapData(type, data));
            case ChatsLoadReply -> workChatsLoadReplyMessage(getDeserializeMapData(type, data));
            case LoadUsersOnlineStatusRequest -> workLoadUsersOnlineStatusRequestMessage(getDeserializeMapData(type, data));
            case LoadUsersOnlineStatusReply -> workLoadUsersOnlineStatusReplyMessage(getDeserializeMapData(type, data));
        }
        clearThreadFromConnection();
    }

    private HashMap<JvDefinesMessages.TypeData, ?> getDeserializeMapData(JvDefinesMessages.TypeMessage type, byte[] data) {
        return JvGetterMessages.getInstance().getBeanDeserializatorDataMessages().deserializeData(type, data);
    }

    public void setThreadFromConnection(Thread thread) {
        if (threadFrom != thread) {
            threadFrom = thread;
        }
    }

    private void clearThreadFromConnection() {
        threadFrom = null;
    }

    private void workEntryRequestMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        String login = (String) map.get(JvDefinesMessages.TypeData.Login);
        String password = (String) map.get(JvDefinesMessages.TypeData.Password);

        String hashPassword = JvGetterCryptography.getInstance()
                .getBeanHashCryptography().getHash(password);

        boolean requestDB = JvGetterControls.getInstance()
                .getBeanDbCtrl().checkQueryToDB(JvDbCtrl.TypeExecutionCheck.UserPassword,
                        login,
                        hashPassword);
        JvGetterControls.getInstance().getBeanSendMessagesCtrl()
                .sendMessage(JvDefinesMessages.TypeMessage.EntryReply, requestDB);
    }

    private void workEntryReplyMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        if ((Boolean) map.get(JvDefinesMessages.TypeData.BoolReply)) {
            JvGetterControls.getInstance().getBeanMessagesDefinesCtrl()
                    .setEntryRequestFlag(JvMessagesDefinesCtrl.TypeFlags.TRUE);
        } else {
            JvGetterControls.getInstance().getBeanMessagesDefinesCtrl()
                    .setEntryRequestFlag(JvMessagesDefinesCtrl.TypeFlags.FALSE);
        }
    }

    private void workRegistrationRequestMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        boolean requestDB = false;
        JvDefinesMessages.TypeErrorRegistration typeError = JvDefinesMessages.TypeErrorRegistration.NoError;
        boolean checkLogin =  JvGetterControls.getInstance()
                .getBeanDbCtrl().checkQueryToDB(JvDbCtrl.TypeExecutionCheck.Login,
                        (String) map.get(JvDefinesMessages.TypeData.Login));
        boolean checkEmail =  JvGetterControls.getInstance()
                .getBeanDbCtrl().checkQueryToDB(JvDbCtrl.TypeExecutionCheck.Email,
                        (String) map.get(JvDefinesMessages.TypeData.Email));
        if (checkLogin) {
            typeError = JvDefinesMessages.TypeErrorRegistration.Login;
        }
        if (checkEmail) {
            typeError = JvDefinesMessages.TypeErrorRegistration.Email;
        }
        if (checkLogin && checkEmail) {
            typeError = JvDefinesMessages.TypeErrorRegistration.LoginAndEmail;
        }
        if (typeError == JvDefinesMessages.TypeErrorRegistration.NoError) {
            requestDB = JvGetterControls.getInstance()
                    .getBeanEmailCtrl().startVerifyRegEmail((String) map.get(JvDefinesMessages.TypeData.Email));
            typeError = JvDefinesMessages.TypeErrorRegistration.EmailSending;
        }
        JvGetterControls.getInstance().getBeanSendMessagesCtrl()
                .sendMessage(JvDefinesMessages.TypeMessage.RegistrationReply, requestDB, typeError);
    }

    private void workRegistrationReplyMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        if ((Boolean) map.get(JvDefinesMessages.TypeData.BoolReply)) {
            JvGetterControls.getInstance().getBeanMessagesDefinesCtrl()
                    .setRegistrationRequestFlag(JvMessagesDefinesCtrl.TypeFlags.TRUE);
        } else {
            JvGetterControls.getInstance().getBeanMessagesDefinesCtrl()
                    .setRegistrationRequestFlag(JvMessagesDefinesCtrl.TypeFlags.FALSE);
        }
        JvGetterControls.getInstance().getBeanMessagesDefinesCtrl()
                .setErrorRegistrationFlag((JvDefinesMessages.TypeErrorRegistration) map.get(JvDefinesMessages.TypeData.ErrorReg));
    }

    private void workVerifyRegistrationEmailRequestMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        boolean checkCode = JvGetterControls.getInstance()
                .getBeanDbCtrl().checkQueryToDB(JvDbCtrl.TypeExecutionCheck.VerifyRegistrationEmail,
                        (String) map.get(JvDefinesMessages.TypeData.Email),
                        (String) map.get(JvDefinesMessages.TypeData.VerifyCode));
        if (checkCode) {
            String login = (String) map.get(JvDefinesMessages.TypeData.Login);
            String email = (String) map.get(JvDefinesMessages.TypeData.Email);
            String password = (String) map.get(JvDefinesMessages.TypeData.Password);

            String hashPassword = JvGetterCryptography.getInstance()
                    .getBeanHashCryptography().getHash(password);

            boolean requestDB = JvGetterControls.getInstance()
                    .getBeanDbCtrl().insertQueryToDB(JvDbCtrl.TypeExecutionInsert.RegisterForm,
                            login,
                            email,
                            hashPassword);
            JvDefinesMessages.TypeErrorRegistration typeError = JvDefinesMessages.TypeErrorRegistration.NoError;
            if (!requestDB) {
                boolean checkLogin = JvGetterControls.getInstance()
                        .getBeanDbCtrl().checkQueryToDB(JvDbCtrl.TypeExecutionCheck.Login,
                                (String) map.get(JvDefinesMessages.TypeData.Login));
                boolean checkEmail = JvGetterControls.getInstance()
                        .getBeanDbCtrl().checkQueryToDB(JvDbCtrl.TypeExecutionCheck.Email,
                                (String) map.get(JvDefinesMessages.TypeData.Email));
                if (checkLogin) {
                    typeError = JvDefinesMessages.TypeErrorRegistration.Login;
                }
                if (checkEmail) {
                    typeError = JvDefinesMessages.TypeErrorRegistration.Email;
                }
                if (checkLogin && checkEmail) {
                    typeError = JvDefinesMessages.TypeErrorRegistration.LoginAndEmail;
                }
            }
            JvGetterControls.getInstance().getBeanSendMessagesCtrl()
                    .sendMessage(JvDefinesMessages.TypeMessage.VerifyRegistrationEmailReply, requestDB, typeError);
        } else {
            JvGetterControls.getInstance().getBeanSendMessagesCtrl()
                    .sendMessage(JvDefinesMessages.TypeMessage.VerifyRegistrationEmailReply, false, JvDefinesMessages.TypeErrorRegistration.Code);
        }
    }

    private void workVerifyRegistrationEmailReplyMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        if ((Boolean) map.get(JvDefinesMessages.TypeData.BoolReply)) {
            JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().
                    setVerifyRegistrationEmailRequestFlag(JvMessagesDefinesCtrl.TypeFlags.TRUE);
        } else {
            JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().
                    setVerifyRegistrationEmailRequestFlag(JvMessagesDefinesCtrl.TypeFlags.FALSE);
        }
        JvGetterControls.getInstance().getBeanMessagesDefinesCtrl()
                .setErrorVerifyRegEmailFlag((JvDefinesMessages.TypeErrorRegistration) map.get(JvDefinesMessages.TypeData.ErrorReg));
    }

    private void workResetPasswordRequestMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        String email = (String) map.get(JvDefinesMessages.TypeData.Email);
        boolean checkEmail = JvGetterControls.getInstance()
                .getBeanDbCtrl().checkQueryToDB(JvDbCtrl.TypeExecutionCheck.Email,
                        email);
        boolean reply = false;
        if (checkEmail) {
            reply = JvGetterControls.getInstance()
                    .getBeanEmailCtrl().startVerifyFamousEmail(email);
        }
        JvGetterControls.getInstance().getBeanSendMessagesCtrl()
                .sendMessage(JvDefinesMessages.TypeMessage.ResetPasswordReply, reply);
    }

    private void workResetPasswordReplyMessage( HashMap<JvDefinesMessages.TypeData, ?> map) {
        if ((Boolean) map.get(JvDefinesMessages.TypeData.BoolReply)) {
            JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().
                    setResetPasswordRequestFlag(JvMessagesDefinesCtrl.TypeFlags.TRUE);
        } else {
            JvGetterControls.getInstance().getBeanMessagesDefinesCtrl()
                    .setResetPasswordRequestFlag(JvMessagesDefinesCtrl.TypeFlags.FALSE);
        }
    }

    private void workVerifyFamousEmailRequestMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        boolean requestDB = JvGetterControls.getInstance()
                .getBeanDbCtrl().checkQueryToDB(JvDbCtrl.TypeExecutionCheck.VerifyFamousEmailCode,
                        (String) map.get(JvDefinesMessages.TypeData.Email),
                        (String) map.get(JvDefinesMessages.TypeData.VerifyCode));
        JvGetterControls.getInstance().getBeanSendMessagesCtrl()
                .sendMessage(JvDefinesMessages.TypeMessage.VerifyFamousEmailReply, requestDB);
    }

    private void workVerifyFamousEmailReplyMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        if ((Boolean) map.get(JvDefinesMessages.TypeData.BoolReply)) {
            JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().
                    setVerifyFamousEmailRequestFlag(JvMessagesDefinesCtrl.TypeFlags.TRUE);
        } else {
            JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().
                    setVerifyFamousEmailRequestFlag(JvMessagesDefinesCtrl.TypeFlags.FALSE);
        }
    }

    private void workChangePasswordRequestMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        String email = (String) map.get(JvDefinesMessages.TypeData.Email);
        String password = (String) map.get(JvDefinesMessages.TypeData.Password);

        String hashPassword = JvGetterCryptography.getInstance()
                .getBeanHashCryptography().getHash(password);

        boolean requestDB = JvGetterControls.getInstance()
                .getBeanDbCtrl().insertQueryToDB(JvDbCtrl.TypeExecutionInsert.ChangePassword,
                        email,
                        hashPassword);
        JvGetterControls.getInstance().getBeanSendMessagesCtrl()
                .sendMessage(JvDefinesMessages.TypeMessage.ChangePasswordReply, requestDB);
    }

    private void workChangePasswordReplyMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        if ((Boolean) map.get(JvDefinesMessages.TypeData.BoolReply)) {
            JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().
                    setChangePasswordRequest(JvMessagesDefinesCtrl.TypeFlags.TRUE);
        } else {
            JvGetterControls.getInstance().getBeanMessagesDefinesCtrl()
                    .setChangePasswordRequest(JvMessagesDefinesCtrl.TypeFlags.FALSE);
        }
    }

    private void workChatsLoadRequestMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        List<Map<JvDbGlobalDefines.LineKeys, String>> requestDB = JvGetterControls.getInstance()
                .getBeanDbCtrl().getMultipleInfoFromDb(JvDbCtrl.TypeExecutionGetMultiple.ChatsLoad,
                        (String) map.get(JvDefinesMessages.TypeData.ChatsLoad));
        JvGetterControls.getInstance().getBeanSendMessagesCtrl().
                sendMessage(JvDefinesMessages.TypeMessage.ChatsLoadReply, requestDB);
    }

    private void workChatsLoadReplyMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        Object objectFromMap = map.get(JvDefinesMessages.TypeData.ChatsInfoList);
        List<Map<JvDbGlobalDefines.LineKeys, String>> chatsInfo =
                JvGetterTools.getInstance().getBeanStructTools()
                    .objectInListMaps(objectFromMap, JvDbGlobalDefines.LineKeys.class, String.class);
        JvGetterControls.getInstance().getBeanChatsCtrl().setChatsInfo(chatsInfo);
        JvGetterControls.getInstance().getBeanMessagesDefinesCtrl()
                .setChatsLoadReplyFlag(JvMessagesDefinesCtrl.TypeFlags.TRUE);
    }

    private void workCheckOnlineUserRequestMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        @SuppressWarnings("unused")
        String ip = (String) map.get(JvDefinesMessages.TypeData.IP);
        String login = JvGetterSettings.getInstance().getBeanUsersInfoSettings().getLogin();
        JvGetterControls.getInstance().getBeanSendMessagesCtrl()
                .sendMessage(JvDefinesMessages.TypeMessage.CheckOnlineUserReply, login);
    }

    private void workCheckOnlineUserReplyMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        String login = (String) map.get(JvDefinesMessages.TypeData.Login);
        JvGetterControls.getInstance().getBeanOnlineServersCtrl().addUsersOnline(login, threadFrom);
    }

    private void workLoadUsersOnlineStatusRequestMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        Object objectList = map.get(JvDefinesMessages.TypeData.LoginsList);
        List<String> logins = JvGetterTools.getInstance().getBeanStructTools().checkedCastList(objectList, String.class);
        Map<String, JvMainChatsGlobalDefines.TypeStatusOnline> statusesUsers =
                JvGetterControls.getInstance().getBeanOnlineServersCtrl().getStatusesUsers(logins);
        Map<String, String> lastOnlineTimeUsers =
                JvGetterControls.getInstance().getBeanOnlineServersCtrl().getLastOnlineTimeUsers(logins);
        JvGetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(
                JvDefinesMessages.TypeMessage.LoadUsersOnlineStatusReply, statusesUsers, lastOnlineTimeUsers);
    }

    private void workLoadUsersOnlineStatusReplyMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        String login = (String) map.get(JvDefinesMessages.TypeData.Login);
        JvGetterControls.getInstance().getBeanOnlineServersCtrl().addUsersOnline(login, threadFrom);


        JvGetterControls.getInstance().getBeanMessagesDefinesCtrl()
                .setChatsLoadReplyFlag(JvMessagesDefinesCtrl.TypeFlags.TRUE);
    }
}