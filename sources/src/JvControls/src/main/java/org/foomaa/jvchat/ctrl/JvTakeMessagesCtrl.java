package org.foomaa.jvchat.ctrl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.foomaa.jvchat.cryptography.JvGetterCryptography;
import org.foomaa.jvchat.globaldefines.JvDbGlobalDefines;
import org.foomaa.jvchat.globaldefines.JvMainChatsGlobalDefines;
import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.messages.JvGetterMessages;
import org.foomaa.jvchat.messages.JvDefinesMessages;
import org.foomaa.jvchat.settings.JvGetterSettings;
import org.foomaa.jvchat.structobjects.JvMessageStructObject;
import org.foomaa.jvchat.tools.JvGetterTools;


public class JvTakeMessagesCtrl {
    private static JvTakeMessagesCtrl instance;

    private Runnable runnableCtrlFrom;

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
            case TextMessageSendUserToServer -> workTextMessageSendUserToServerMessage(getDeserializeMapData(type, data));
            case TextMessagesChangingStatusFromServer -> workTextMessagesChangingStatusFromServerMessage(getDeserializeMapData(type, data));
            case TextMessageRedirectServerToUser -> workTextMessageRedirectServerToUserMessage(getDeserializeMapData(type, data));
            case MessagesLoadRequest -> workMessagesLoadRequestMessage(getDeserializeMapData(type, data));
            case MessagesLoadReply -> workMessagesLoadReplyMessage(getDeserializeMapData(type, data));
        }

        clearRunnableCtrlFromConnection();
    }

    private HashMap<JvDefinesMessages.TypeData, ?> getDeserializeMapData(JvDefinesMessages.TypeMessage type, byte[] data) {
        return JvGetterMessages.getInstance().getBeanDeserializatorDataMessages().deserializeData(type, data);
    }

    public void setRunnableCtrlFromConnection(Runnable runnable) {
        if (runnableCtrlFrom != runnable) {
            runnableCtrlFrom = runnable;
        }
    }

    private void clearRunnableCtrlFromConnection() {
        runnableCtrlFrom = null;
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
        JvGetterControls.getInstance().getBeanChatsCtrl().createChatsObjects(chatsInfo);
        JvGetterControls.getInstance().getBeanMessagesDefinesCtrl()
                .setChatsLoadReplyFlag(JvMessagesDefinesCtrl.TypeFlags.TRUE);
    }

    private void workCheckOnlineUserRequestMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        @SuppressWarnings("unused")
        String ip = (String) map.get(JvDefinesMessages.TypeData.IP);
        String login = JvGetterSettings.getInstance().getBeanUsersInfoSettings().getLogin();

        if (Objects.equals(login, "") || login == null) {
            JvLog.write(JvLog.TypeLog.Warn, "Здесь login не задан");
            return;
        }

        JvGetterControls.getInstance().getBeanSendMessagesCtrl()
                .sendMessage(JvDefinesMessages.TypeMessage.CheckOnlineUserReply, login);
    }

    private void workCheckOnlineUserReplyMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        String login = (String) map.get(JvDefinesMessages.TypeData.Login);
        JvGetterControls.getInstance().getBeanOnlineServersCtrl().addUsersOnline(login, runnableCtrlFrom);
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
        Object objectMapStatusesUsers = map.get(JvDefinesMessages.TypeData.UsersOnlineInfoList);
        Object objectMapLastOnlineTimeUsers = map.get(JvDefinesMessages.TypeData.TimeStampLastOnlineString);

        Map<String, JvMainChatsGlobalDefines.TypeStatusOnline> mapStatusesUsers =
                JvGetterTools.getInstance().getBeanStructTools().objectInMap(objectMapStatusesUsers, String.class,
                        JvMainChatsGlobalDefines.TypeStatusOnline.class);
        Map<String, String> mapLastOnlineTimeUsers =
                JvGetterTools.getInstance().getBeanStructTools().objectInMap(objectMapLastOnlineTimeUsers, String.class,
                        String.class);

        JvGetterControls.getInstance().getBeanChatsCtrl().setOnlineStatusesUsers(mapStatusesUsers);
        JvGetterControls.getInstance().getBeanChatsCtrl().setLastOnlineTimeUsersByStrings(mapLastOnlineTimeUsers);

        JvGetterControls.getInstance().getBeanMessagesDefinesCtrl()
                .setLoadUsersOnlineReplyFlag(JvMessagesDefinesCtrl.TypeFlags.TRUE);
    }

    private void workTextMessageSendUserToServerMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        String loginSender = (String) map.get(JvDefinesMessages.TypeData.LoginSender);
        String loginReceiver = (String) map.get(JvDefinesMessages.TypeData.LoginReceiver);
        String uuid = (String) map.get(JvDefinesMessages.TypeData.Uuid);
        String text = (String) map.get(JvDefinesMessages.TypeData.Text);
        String timestampStr = (String) map.get(JvDefinesMessages.TypeData.TimeStampMessageSend);

        JvMainChatsGlobalDefines.TypeStatusMessage status = JvMainChatsGlobalDefines.TypeStatusMessage.Delivered;
        String statusString = status.toString();
        Map<UUID, JvMainChatsGlobalDefines.TypeStatusMessage> mapStatusMessages = new HashMap<>();
        mapStatusMessages.put(UUID.fromString(uuid), status);
        LocalDateTime timestamp = JvGetterTools.getInstance().getBeanFormatTools()
                .stringToLocalDateTime(timestampStr, 3);
        JvMessageStructObject messageObj = JvGetterControls.getInstance().getBeanMessagesDialogCtrl().createMessageByData(
                loginSender, loginReceiver, text, status, UUID.fromString(uuid), timestamp);

        // записываем в первую очередь в БД
        JvGetterControls.getInstance().getBeanDbCtrl().insertQueryToDB(JvDbCtrl.TypeExecutionInsert.ChatMessagesSentMessage,
                loginSender, loginReceiver, statusString, text, uuid, timestampStr);
        // отправляем статус "доставлено"
        JvGetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(
                JvDefinesMessages.TypeMessage.TextMessagesChangingStatusFromServer, loginSender, loginReceiver, mapStatusMessages);
        // отправляем пользователю, если он в сети
        JvGetterControls.getInstance().getBeanMessagesDialogCtrl().redirectMessageToOnlineUser(messageObj);
    }

    private void workTextMessagesChangingStatusFromServerMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        String loginSender = (String) map.get(JvDefinesMessages.TypeData.LoginSender);
        String loginReceiver = (String) map.get(JvDefinesMessages.TypeData.LoginReceiver);

        Object statusesMap = map.get(JvDefinesMessages.TypeData.MapStatusMessages);
        Map<UUID, JvMainChatsGlobalDefines.TypeStatusMessage> mapStatusesMessages = JvGetterTools.getInstance()
                .getBeanStructTools().objectInMap(statusesMap, UUID.class, JvMainChatsGlobalDefines.TypeStatusMessage.class);

        JvGetterControls.getInstance().getBeanMessagesDialogCtrl()
                .setDirtyStatusToMessage( loginSender, loginReceiver, mapStatusesMessages);
    }

    private void workTextMessageRedirectServerToUserMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        String loginSender = (String) map.get(JvDefinesMessages.TypeData.LoginSender);
        String loginReceiver = (String) map.get(JvDefinesMessages.TypeData.LoginReceiver);
        String uuid = (String) map.get(JvDefinesMessages.TypeData.Uuid);
        String text = (String) map.get(JvDefinesMessages.TypeData.Text);
        String timestamp = (String) map.get(JvDefinesMessages.TypeData.TimeStampMessageSend);

        JvMainChatsGlobalDefines.TypeStatusMessage status = JvMainChatsGlobalDefines.TypeStatusMessage.Delivered;
        String statusString = status.toString();
        // TODO(VAD): add to widget
    }

    private void workMessagesLoadRequestMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        String loginRequesting = (String) map.get(JvDefinesMessages.TypeData.LoginRequesting);
        String loginDialog = (String) map.get(JvDefinesMessages.TypeData.LoginDialog);
        int quantityMessages = (Integer) map.get(JvDefinesMessages.TypeData.QuantityMessages);

        List<Map<JvDbGlobalDefines.LineKeys, String>> requestDB = JvGetterControls.getInstance()
                .getBeanDbCtrl().getMultipleInfoFromDb(JvDbCtrl.TypeExecutionGetMultiple.ChatsLoad,
                        loginRequesting, loginDialog, String.valueOf(quantityMessages));
        JvGetterControls.getInstance().getBeanSendMessagesCtrl().
                sendMessage(JvDefinesMessages.TypeMessage.MessagesLoadReply, requestDB);
    }

    private void workMessagesLoadReplyMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        Object objectFromMap = map.get(JvDefinesMessages.TypeData.MessagesInfoList);
        List<Map<JvDbGlobalDefines.LineKeys, String>> msgInfo =
                JvGetterTools.getInstance().getBeanStructTools()
                        .objectInListMaps(objectFromMap, JvDbGlobalDefines.LineKeys.class, String.class);
        System.out.println(msgInfo);
//        JvGetterControls.getInstance().getBeanChatsCtrl().createChatsObjects(msgInfo);
//        JvGetterControls.getInstance().getBeanMessagesDefinesCtrl()
//                .setChatsLoadReplyFlag(JvMessagesDefinesCtrl.TypeFlags.TRUE);
    }
}