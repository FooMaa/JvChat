package org.foomaa.jvchat.ctrl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.foomaa.jvchat.cryptography.JvGetterCryptography;
import org.foomaa.jvchat.globaldefines.JvDbGlobalDefines;
import org.foomaa.jvchat.globaldefines.JvMainChatsGlobalDefines;
import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.messages.JvGetterMessages;
import org.foomaa.jvchat.messages.JvDefinesMessages;
import org.foomaa.jvchat.settings.JvGetterSettings;
import org.foomaa.jvchat.tools.JvGetterTools;


public class TakeMessagesCtrl {
    private Runnable runnableCtrlFrom;

    TakeMessagesCtrl() {}

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
            case TextMessageSendUserToServerVerification -> workTextMessageSendUserToServerVerificationMessage(getDeserializeMapData(type, data));
            case TextMessagesChangingStatusFromServer -> workTextMessagesChangingStatusFromServerMessage(getDeserializeMapData(type, data));
            case TextMessagesChangingStatusFromServerVerification -> workTextMessagesChangingStatusFromServerVerificationMessage(getDeserializeMapData(type, data));
            case TextMessagesChangingStatusFromUser -> workTextMessagesChangingStatusFromUserMessage(getDeserializeMapData(type, data));
            case TextMessagesChangingStatusFromUserVerification -> workTextMessagesChangingStatusFromUserVerificationMessage(getDeserializeMapData(type, data));
            case TextMessageRedirectServerToUser -> workTextMessageRedirectServerToUserMessage(getDeserializeMapData(type, data));
            case TextMessageRedirectServerToUserVerification -> workTextMessageRedirectServerToUserVerificationMessage(getDeserializeMapData(type, data));
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

        boolean requestDB = GetterControls.getInstance()
                .getBeanDbCtrl().checkQueryToDB(DbCtrl.TypeExecutionCheck.UserPassword,
                        login,
                        hashPassword);

        String uuidUserStr = GetterControls.getInstance()
                .getBeanDbCtrl().getSingleDataFromDb(DbCtrl.TypeExecutionGetSingle.UuidUserByLogin,
                        login);
        UUID uuidUser = UUID.fromString(uuidUserStr);

        GetterControls.getInstance().getBeanSendMessagesCtrl()
                .sendMessage(JvDefinesMessages.TypeMessage.EntryReply, requestDB, uuidUser);
    }

    private void workEntryReplyMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        UUID uuidUser = (UUID) map.get(JvDefinesMessages.TypeData.UuidUser);
        JvGetterSettings.getInstance().getBeanUsersInfoSettings().setUuid(uuidUser);

        if ((Boolean) map.get(JvDefinesMessages.TypeData.BoolReply)) {
            GetterControls.getInstance().getBeanMessagesDefinesCtrl()
                    .setEntryRequestFlag(MessagesDefinesCtrl.TypeFlags.TRUE);
        } else {
            GetterControls.getInstance().getBeanMessagesDefinesCtrl()
                    .setEntryRequestFlag(MessagesDefinesCtrl.TypeFlags.FALSE);
        }
    }

    private void workRegistrationRequestMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        boolean requestDB = false;
        JvDefinesMessages.TypeErrorRegistration typeError = JvDefinesMessages.TypeErrorRegistration.NoError;
        boolean checkLogin =  GetterControls.getInstance()
                .getBeanDbCtrl().checkQueryToDB(DbCtrl.TypeExecutionCheck.Login,
                        (String) map.get(JvDefinesMessages.TypeData.Login));
        boolean checkEmail =  GetterControls.getInstance()
                .getBeanDbCtrl().checkQueryToDB(DbCtrl.TypeExecutionCheck.Email,
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
            requestDB = GetterControls.getInstance()
                    .getBeanEmailCtrl().startVerifyRegEmail((String) map.get(JvDefinesMessages.TypeData.Email));
            typeError = JvDefinesMessages.TypeErrorRegistration.EmailSending;
        }
        GetterControls.getInstance().getBeanSendMessagesCtrl()
                .sendMessage(JvDefinesMessages.TypeMessage.RegistrationReply, requestDB, typeError);
    }

    private void workRegistrationReplyMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        if ((Boolean) map.get(JvDefinesMessages.TypeData.BoolReply)) {
            GetterControls.getInstance().getBeanMessagesDefinesCtrl()
                    .setRegistrationRequestFlag(MessagesDefinesCtrl.TypeFlags.TRUE);
        } else {
            GetterControls.getInstance().getBeanMessagesDefinesCtrl()
                    .setRegistrationRequestFlag(MessagesDefinesCtrl.TypeFlags.FALSE);
        }
        GetterControls.getInstance().getBeanMessagesDefinesCtrl()
                .setErrorRegistrationFlag((JvDefinesMessages.TypeErrorRegistration) map.get(JvDefinesMessages.TypeData.ErrorReg));
    }

    private void workVerifyRegistrationEmailRequestMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        boolean checkCode = GetterControls.getInstance()
                .getBeanDbCtrl().checkQueryToDB(DbCtrl.TypeExecutionCheck.VerifyRegistrationEmail,
                        (String) map.get(JvDefinesMessages.TypeData.Email),
                        (String) map.get(JvDefinesMessages.TypeData.VerifyCode));
        if (checkCode) {
            String login = (String) map.get(JvDefinesMessages.TypeData.Login);
            String email = (String) map.get(JvDefinesMessages.TypeData.Email);
            String password = (String) map.get(JvDefinesMessages.TypeData.Password);

            String hashPassword = JvGetterCryptography.getInstance()
                    .getBeanHashCryptography().getHash(password);
            UUID uuidUser = UUID.randomUUID();

            boolean requestDB = GetterControls.getInstance()
                    .getBeanDbCtrl().insertQueryToDB(DbCtrl.TypeExecutionInsert.RegisterForm,
                            login,
                            email,
                            hashPassword,
                            uuidUser.toString());
            JvDefinesMessages.TypeErrorRegistration typeError = JvDefinesMessages.TypeErrorRegistration.NoError;
            if (!requestDB) {
                boolean checkLogin = GetterControls.getInstance()
                        .getBeanDbCtrl().checkQueryToDB(DbCtrl.TypeExecutionCheck.Login,
                                (String) map.get(JvDefinesMessages.TypeData.Login));
                boolean checkEmail = GetterControls.getInstance()
                        .getBeanDbCtrl().checkQueryToDB(DbCtrl.TypeExecutionCheck.Email,
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
            GetterControls.getInstance().getBeanSendMessagesCtrl()
                    .sendMessage(JvDefinesMessages.TypeMessage.VerifyRegistrationEmailReply, requestDB, typeError);
        } else {
            GetterControls.getInstance().getBeanSendMessagesCtrl()
                    .sendMessage(JvDefinesMessages.TypeMessage.VerifyRegistrationEmailReply, false, JvDefinesMessages.TypeErrorRegistration.Code);
        }
    }

    private void workVerifyRegistrationEmailReplyMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        if ((Boolean) map.get(JvDefinesMessages.TypeData.BoolReply)) {
            GetterControls.getInstance().getBeanMessagesDefinesCtrl().
                    setVerifyRegistrationEmailRequestFlag(MessagesDefinesCtrl.TypeFlags.TRUE);
        } else {
            GetterControls.getInstance().getBeanMessagesDefinesCtrl().
                    setVerifyRegistrationEmailRequestFlag(MessagesDefinesCtrl.TypeFlags.FALSE);
        }
        GetterControls.getInstance().getBeanMessagesDefinesCtrl()
                .setErrorVerifyRegEmailFlag((JvDefinesMessages.TypeErrorRegistration) map.get(JvDefinesMessages.TypeData.ErrorReg));
    }

    private void workResetPasswordRequestMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        String email = (String) map.get(JvDefinesMessages.TypeData.Email);
        boolean checkEmail = GetterControls.getInstance()
                .getBeanDbCtrl().checkQueryToDB(DbCtrl.TypeExecutionCheck.Email,
                        email);
        boolean reply = false;
        if (checkEmail) {
            reply = GetterControls.getInstance()
                    .getBeanEmailCtrl().startVerifyFamousEmail(email);
        }
        GetterControls.getInstance().getBeanSendMessagesCtrl()
                .sendMessage(JvDefinesMessages.TypeMessage.ResetPasswordReply, reply);
    }

    private void workResetPasswordReplyMessage( HashMap<JvDefinesMessages.TypeData, ?> map) {
        if ((Boolean) map.get(JvDefinesMessages.TypeData.BoolReply)) {
            GetterControls.getInstance().getBeanMessagesDefinesCtrl().
                    setResetPasswordRequestFlag(MessagesDefinesCtrl.TypeFlags.TRUE);
        } else {
            GetterControls.getInstance().getBeanMessagesDefinesCtrl()
                    .setResetPasswordRequestFlag(MessagesDefinesCtrl.TypeFlags.FALSE);
        }
    }

    private void workVerifyFamousEmailRequestMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        boolean requestDB = GetterControls.getInstance()
                .getBeanDbCtrl().checkQueryToDB(DbCtrl.TypeExecutionCheck.VerifyFamousEmailCode,
                        (String) map.get(JvDefinesMessages.TypeData.Email),
                        (String) map.get(JvDefinesMessages.TypeData.VerifyCode));
        GetterControls.getInstance().getBeanSendMessagesCtrl()
                .sendMessage(JvDefinesMessages.TypeMessage.VerifyFamousEmailReply, requestDB);
    }

    private void workVerifyFamousEmailReplyMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        if ((Boolean) map.get(JvDefinesMessages.TypeData.BoolReply)) {
            GetterControls.getInstance().getBeanMessagesDefinesCtrl().
                    setVerifyFamousEmailRequestFlag(MessagesDefinesCtrl.TypeFlags.TRUE);
        } else {
            GetterControls.getInstance().getBeanMessagesDefinesCtrl().
                    setVerifyFamousEmailRequestFlag(MessagesDefinesCtrl.TypeFlags.FALSE);
        }
    }

    private void workChangePasswordRequestMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        String email = (String) map.get(JvDefinesMessages.TypeData.Email);
        String password = (String) map.get(JvDefinesMessages.TypeData.Password);

        String hashPassword = JvGetterCryptography.getInstance()
                .getBeanHashCryptography().getHash(password);

        boolean requestDB = GetterControls.getInstance()
                .getBeanDbCtrl().insertQueryToDB(DbCtrl.TypeExecutionInsert.ChangePassword,
                        email,
                        hashPassword);
        GetterControls.getInstance().getBeanSendMessagesCtrl()
                .sendMessage(JvDefinesMessages.TypeMessage.ChangePasswordReply, requestDB);
    }

    private void workChangePasswordReplyMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        if ((Boolean) map.get(JvDefinesMessages.TypeData.BoolReply)) {
            GetterControls.getInstance().getBeanMessagesDefinesCtrl().
                    setChangePasswordRequest(MessagesDefinesCtrl.TypeFlags.TRUE);
        } else {
            GetterControls.getInstance().getBeanMessagesDefinesCtrl()
                    .setChangePasswordRequest(MessagesDefinesCtrl.TypeFlags.FALSE);
        }
    }

    private void workChatsLoadRequestMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        String uuidUserStr = map.get(JvDefinesMessages.TypeData.UuidUser).toString();
        List<Map<JvDbGlobalDefines.LineKeys, String>> requestDB = GetterControls.getInstance()
                .getBeanDbCtrl().getMultipleInfoFromDb(DbCtrl.TypeExecutionGetMultiple.ChatsLoad, uuidUserStr);
        GetterControls.getInstance().getBeanSendMessagesCtrl().
                sendMessage(JvDefinesMessages.TypeMessage.ChatsLoadReply, requestDB);
    }

    private void workChatsLoadReplyMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        Object objectFromMap = map.get(JvDefinesMessages.TypeData.ChatsInfoList);
        List<Map<JvDefinesMessages.TypeData, Object>> chatsInfo =
                JvGetterTools.getInstance().getBeanStructTools()
                    .objectInListMaps(objectFromMap, JvDefinesMessages.TypeData.class, Object.class);

        GetterControls.getInstance().getBeanChatsCtrl().createChatsObjects(chatsInfo);
        GetterControls.getInstance().getBeanMessagesDefinesCtrl()
                .setChatsLoadReplyFlag(MessagesDefinesCtrl.TypeFlags.TRUE);
    }

    private void workCheckOnlineUserRequestMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        @SuppressWarnings("unused")
        String ip = (String) map.get(JvDefinesMessages.TypeData.IP);
        UUID uuidUser = JvGetterSettings.getInstance().getBeanUsersInfoSettings().getUuid();

        if (uuidUser == null) {
            JvLog.write(JvLog.TypeLog.Warn, "Here uuidUser is not set.");
            return;
        }

        GetterControls.getInstance().getBeanSendMessagesCtrl()
                .sendMessage(JvDefinesMessages.TypeMessage.CheckOnlineUserReply, uuidUser);
    }

    private void workCheckOnlineUserReplyMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        UUID uuidUser = (UUID) map.get(JvDefinesMessages.TypeData.UuidUser);
        GetterControls.getInstance().getBeanOnlineServersCtrl().addUsersOnline(uuidUser, runnableCtrlFrom);
    }

    private void workLoadUsersOnlineStatusRequestMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        Object objectList = map.get(JvDefinesMessages.TypeData.UuidsUsersList);
        List<UUID> uuidsUsers = JvGetterTools.getInstance().getBeanStructTools().checkedCastList(objectList, UUID.class);
        Map<UUID, JvMainChatsGlobalDefines.TypeStatusOnline> statusesUsers =
                GetterControls.getInstance().getBeanOnlineServersCtrl().getStatusesUsers(uuidsUsers);
        Map<UUID, String> lastOnlineTimeUsers =
                GetterControls.getInstance().getBeanOnlineServersCtrl().getLastOnlineTimeUsers(uuidsUsers);
        GetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(
                JvDefinesMessages.TypeMessage.LoadUsersOnlineStatusReply, statusesUsers, lastOnlineTimeUsers);
    }

    private void workLoadUsersOnlineStatusReplyMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        Object objectMapStatusesUsers = map.get(JvDefinesMessages.TypeData.UsersOnlineInfoList);
        Object objectMapLastOnlineTimeUsers = map.get(JvDefinesMessages.TypeData.Timestamp);

        Map<UUID, JvMainChatsGlobalDefines.TypeStatusOnline> mapStatusesUsers =
                JvGetterTools.getInstance().getBeanStructTools().objectInMap(objectMapStatusesUsers, UUID.class,
                        JvMainChatsGlobalDefines.TypeStatusOnline.class);
        Map<UUID, String> mapLastOnlineTimeUsers =
                JvGetterTools.getInstance().getBeanStructTools().objectInMap(objectMapLastOnlineTimeUsers, UUID.class,
                        String.class);

        GetterControls.getInstance().getBeanChatsCtrl().setOnlineStatusesUsers(mapStatusesUsers);
        GetterControls.getInstance().getBeanChatsCtrl().setLastOnlineTimeUsersByStrings(mapLastOnlineTimeUsers);

        GetterControls.getInstance().getBeanMessagesDefinesCtrl()
                .setLoadUsersOnlineReplyFlag(MessagesDefinesCtrl.TypeFlags.TRUE);
    }

    private void workTextMessageSendUserToServerMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        UUID uuidUserSender = (UUID) map.get(JvDefinesMessages.TypeData.UuidUserSender);
        UUID uuidUserReceiver = (UUID) map.get(JvDefinesMessages.TypeData.UuidUserReceiver);
        UUID uuidMessage = (UUID) map.get(JvDefinesMessages.TypeData.UuidMessage);
        String text = (String) map.get(JvDefinesMessages.TypeData.TextMessage);
        String timestampStr = (String) map.get(JvDefinesMessages.TypeData.Timestamp);

        JvMainChatsGlobalDefines.TypeStatusMessage status = JvMainChatsGlobalDefines.TypeStatusMessage.Delivered;
        String statusString = status.toString();
        Map<UUID, JvMainChatsGlobalDefines.TypeStatusMessage> mapStatusMessages = new HashMap<>();
        mapStatusMessages.put(uuidMessage, status);
        int normaliseTimestampCount = 3;
        LocalDateTime timestamp = JvGetterTools.getInstance().getBeanFormatTools()
                .stringToLocalDateTime(timestampStr, normaliseTimestampCount);

        // записываем в первую очередь в БД
        GetterControls.getInstance().getBeanDbCtrl().insertQueryToDB(DbCtrl.TypeExecutionInsert.ChatMessagesSentMessage,
                uuidUserSender.toString(), uuidUserReceiver.toString(), uuidMessage.toString(), statusString, text, timestampStr);
        // отправляем статус "доставлено"
        GetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(
                JvDefinesMessages.TypeMessage.TextMessagesChangingStatusFromServer, mapStatusMessages);
        // отправляем пользователю, если он в сети
        GetterControls.getInstance().getBeanMessagesDialogCtrl().redirectMessageToOnlineUser(
                uuidUserSender, uuidUserReceiver, uuidMessage, status, text, timestamp);
        // отправляем квиток о доставке
        GetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(
                JvDefinesMessages.TypeMessage.TextMessageSendUserToServerVerification, true );
    }

    private void workTextMessageSendUserToServerVerificationMessage( HashMap<JvDefinesMessages.TypeData, ?> map) {
        if ((Boolean) map.get(JvDefinesMessages.TypeData.BoolReply)) {
            GetterControls.getInstance().getBeanMessagesDefinesCtrl()
                    .setTextMessageSendUserToServerFlag(MessagesDefinesCtrl.TypeFlags.TRUE);
        } else {
            GetterControls.getInstance().getBeanMessagesDefinesCtrl()
                    .setTextMessageSendUserToServerFlag(MessagesDefinesCtrl.TypeFlags.FALSE);
        }
    }

    private void workTextMessagesChangingStatusFromServerMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        Object statusesMap = map.get(JvDefinesMessages.TypeData.StatusMessagesMap);
        Map<UUID, JvMainChatsGlobalDefines.TypeStatusMessage> mapStatusesMessages = JvGetterTools.getInstance()
                .getBeanStructTools().objectInMap(statusesMap, UUID.class, JvMainChatsGlobalDefines.TypeStatusMessage.class);

        GetterControls.getInstance().getBeanMessagesDialogCtrl().setDirtyStatusToMessage(mapStatusesMessages);

        GetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(
                JvDefinesMessages.TypeMessage.TextMessagesChangingStatusFromServerVerification, true );
    }

    private void workTextMessagesChangingStatusFromServerVerificationMessage( HashMap<JvDefinesMessages.TypeData, ?> map) {
        if ((Boolean) map.get(JvDefinesMessages.TypeData.BoolReply)) {
            JvLog.write(JvLog.TypeLog.Info, "Received a message delivery receipt with a status without errors.");
        } else {
            JvLog.write(JvLog.TypeLog.Info, "A message delivery receipt has arrived with an error status.");
        }
    }

    private void workTextMessagesChangingStatusFromUserMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        Object statusesMap = map.get(JvDefinesMessages.TypeData.StatusMessagesMap);
        Map<UUID, JvMainChatsGlobalDefines.TypeStatusMessage> mapStatusesMessages = JvGetterTools.getInstance()
                .getBeanStructTools().objectInMap(statusesMap, UUID.class, JvMainChatsGlobalDefines.TypeStatusMessage.class);

        for (UUID uuidMessage : mapStatusesMessages.keySet()) {
            String statusByUuid = String.valueOf(mapStatusesMessages.get(uuidMessage).getValue());
            GetterControls.getInstance().getBeanDbCtrl().insertQueryToDB(DbCtrl.TypeExecutionInsert.ChatsMessageStatusChange,
                    uuidMessage.toString(), statusByUuid);
        }

        GetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(
                JvDefinesMessages.TypeMessage.TextMessagesChangingStatusFromUserVerification, true );
    }

    private void workTextMessagesChangingStatusFromUserVerificationMessage( HashMap<JvDefinesMessages.TypeData, ?> map) {
        if ((Boolean) map.get(JvDefinesMessages.TypeData.BoolReply)) {
            JvLog.write(JvLog.TypeLog.Info, "Received a message delivery receipt with a status without errors.");
        } else {
            JvLog.write(JvLog.TypeLog.Info, "A message delivery receipt has arrived with an error status.");
        }
    }

    private void workTextMessageRedirectServerToUserMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        UUID uuidUserSender = (UUID) map.get(JvDefinesMessages.TypeData.UuidUserSender);
        UUID uuidUserReceiver = (UUID) map.get(JvDefinesMessages.TypeData.UuidUserReceiver);
        UUID uuidMessage = (UUID) map.get(JvDefinesMessages.TypeData.UuidMessage);
        String text = (String) map.get(JvDefinesMessages.TypeData.TextMessage);
        String timestampStr = (String) map.get(JvDefinesMessages.TypeData.Timestamp);

        JvMainChatsGlobalDefines.TypeStatusMessage status = JvMainChatsGlobalDefines.TypeStatusMessage.Delivered;
        int normaliseTimestampCount = 3;
        LocalDateTime timestamp = JvGetterTools.getInstance().getBeanFormatTools()
                .stringToLocalDateTime(timestampStr, normaliseTimestampCount);

        GetterControls.getInstance().getBeanMessagesDialogCtrl().addRedirectMessageToModel(
                uuidUserSender, uuidUserReceiver, uuidMessage, status, text, timestamp);
        GetterControls.getInstance().getBeanMessagesDefinesCtrl()
                .setTextMessageRedirectServerToUserFlag(MessagesDefinesCtrl.TypeFlags.TRUE);

        GetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(
                JvDefinesMessages.TypeMessage.TextMessageRedirectServerToUserVerification, true );
    }

    private void workTextMessageRedirectServerToUserVerificationMessage( HashMap<JvDefinesMessages.TypeData, ?> map) {
        if ((Boolean) map.get(JvDefinesMessages.TypeData.BoolReply)) {
            GetterControls.getInstance().getBeanMessagesDefinesCtrl()
                    .setTextMessageRedirectServerToUserFlag(MessagesDefinesCtrl.TypeFlags.TRUE);
        } else {
            GetterControls.getInstance().getBeanMessagesDefinesCtrl()
                    .setTextMessageRedirectServerToUserFlag(MessagesDefinesCtrl.TypeFlags.FALSE);
        }
    }

    private void workMessagesLoadRequestMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        UUID uuidChat = (UUID) map.get(JvDefinesMessages.TypeData.UuidChat);
        int quantityMessages = (Integer) map.get(JvDefinesMessages.TypeData.QuantityMessages);

        List<Map<JvDbGlobalDefines.LineKeys, String>> requestDB = GetterControls.getInstance()
                .getBeanDbCtrl().getMultipleInfoFromDb(DbCtrl.TypeExecutionGetMultiple.MessagesLoad,
                        uuidChat.toString(), String.valueOf(quantityMessages));
        GetterControls.getInstance().getBeanSendMessagesCtrl().
                sendMessage(JvDefinesMessages.TypeMessage.MessagesLoadReply, requestDB);
    }

    private void workMessagesLoadReplyMessage(HashMap<JvDefinesMessages.TypeData, ?> map) {
        Object objectFromMap = map.get(JvDefinesMessages.TypeData.MessagesInfoList);
        List<Map<JvDefinesMessages.TypeData, Object>> msgInfo =
                JvGetterTools.getInstance().getBeanStructTools()
                        .objectInListMaps(objectFromMap, JvDefinesMessages.TypeData.class, Object.class);

        GetterControls.getInstance().getBeanMessagesDialogCtrl().createMessagesObjects(msgInfo);
        GetterControls.getInstance().getBeanMessagesDefinesCtrl()
                .setTextMessagesLoadReplyFlag(MessagesDefinesCtrl.TypeFlags.TRUE);
    }
}