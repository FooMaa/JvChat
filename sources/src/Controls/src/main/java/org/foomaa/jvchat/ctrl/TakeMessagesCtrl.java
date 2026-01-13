package org.foomaa.jvchat.ctrl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.cryptography.HashCryptography;
import org.foomaa.jvchat.globaldefines.DbGlobalDefines;
import org.foomaa.jvchat.globaldefines.MainChatsGlobalDefines;
import org.foomaa.jvchat.messages.GetterMessages;
import org.foomaa.jvchat.messages.DefinesMessages;
import org.foomaa.jvchat.settings.GetterSettings;
import org.foomaa.jvchat.tools.GetterTools;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class TakeMessagesCtrl {
    private Runnable runnableCtrlFrom;
    private final HashCryptography hashCryptography;

    TakeMessagesCtrl(HashCryptography hashCryptography) {
        this.hashCryptography = hashCryptography;
    }

    public void takeMessage(byte[] data) {
        DefinesMessages.TypeMessage type = GetterMessages.getInstance().getBeanDeserializatorDataMessages().getTypeMessage(data);

        switch (type) {
            case EntryRequest -> workEntryRequestMessage(getDeserializeMapData(type, data));
            case EntryReply -> workEntryReplyMessage(getDeserializeMapData(type, data));
            case RegistrationRequest -> workRegistrationRequestMessage(getDeserializeMapData(type, data));
            case RegistrationReply -> workRegistrationReplyMessage(getDeserializeMapData(type, data));
            case VerifyRegistrationEmailRequest ->
                    workVerifyRegistrationEmailRequestMessage(getDeserializeMapData(type, data));
            case VerifyRegistrationEmailReply ->
                    workVerifyRegistrationEmailReplyMessage(getDeserializeMapData(type, data));
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
            case LoadUsersOnlineStatusRequest ->
                    workLoadUsersOnlineStatusRequestMessage(getDeserializeMapData(type, data));
            case LoadUsersOnlineStatusReply -> workLoadUsersOnlineStatusReplyMessage(getDeserializeMapData(type, data));
            case TextMessageSendUserToServer ->
                    workTextMessageSendUserToServerMessage(getDeserializeMapData(type, data));
            case TextMessageSendUserToServerVerification ->
                    workTextMessageSendUserToServerVerificationMessage(getDeserializeMapData(type, data));
            case TextMessagesChangingStatusFromServer ->
                    workTextMessagesChangingStatusFromServerMessage(getDeserializeMapData(type, data));
            case TextMessagesChangingStatusFromServerVerification ->
                    workTextMessagesChangingStatusFromServerVerificationMessage(getDeserializeMapData(type, data));
            case TextMessagesChangingStatusFromUser ->
                    workTextMessagesChangingStatusFromUserMessage(getDeserializeMapData(type, data));
            case TextMessagesChangingStatusFromUserVerification ->
                    workTextMessagesChangingStatusFromUserVerificationMessage(getDeserializeMapData(type, data));
            case TextMessageRedirectServerToUser ->
                    workTextMessageRedirectServerToUserMessage(getDeserializeMapData(type, data));
            case TextMessageRedirectServerToUserVerification ->
                    workTextMessageRedirectServerToUserVerificationMessage(getDeserializeMapData(type, data));
            case MessagesLoadRequest -> workMessagesLoadRequestMessage(getDeserializeMapData(type, data));
            case MessagesLoadReply -> workMessagesLoadReplyMessage(getDeserializeMapData(type, data));
        }

        clearRunnableCtrlFromConnection();
    }

    private HashMap<DefinesMessages.TypeData, ?> getDeserializeMapData(DefinesMessages.TypeMessage type, byte[] data) {
        return GetterMessages.getInstance().getBeanDeserializatorDataMessages().deserializeData(type, data);
    }

    public void setRunnableCtrlFromConnection(Runnable runnable) {
        if (runnableCtrlFrom != runnable) {
            runnableCtrlFrom = runnable;
        }
    }

    private void clearRunnableCtrlFromConnection() {
        runnableCtrlFrom = null;
    }

    private void workEntryRequestMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        String login = (String) map.get(DefinesMessages.TypeData.Login);
        String password = (String) map.get(DefinesMessages.TypeData.Password);

        String hashPassword = hashCryptography.getHash(password);

        boolean requestDB = GetterControls.getInstance()
                .getBeanDbCtrl().checkQueryToDB(DbCtrl.TypeExecutionCheck.UserPassword,
                        login,
                        hashPassword);

        String uuidUserStr = GetterControls.getInstance()
                .getBeanDbCtrl().getSingleDataFromDb(DbCtrl.TypeExecutionGetSingle.UuidUserByLogin,
                        login);
        UUID uuidUser = UUID.fromString(uuidUserStr);

        GetterControls.getInstance().getBeanSendMessagesCtrl()
                .sendMessage(DefinesMessages.TypeMessage.EntryReply, requestDB, uuidUser);
    }

    private void workEntryReplyMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        UUID uuidUser = (UUID) map.get(DefinesMessages.TypeData.UuidUser);
        GetterSettings.getInstance().getBeanUsersInfoSettings().setUuid(uuidUser);

        if ((Boolean) map.get(DefinesMessages.TypeData.BoolReply)) {
            GetterControls.getInstance().getBeanMessagesDefinesCtrl()
                    .setEntryRequestFlag(MessagesDefinesCtrl.TypeFlags.TRUE);
        } else {
            GetterControls.getInstance().getBeanMessagesDefinesCtrl()
                    .setEntryRequestFlag(MessagesDefinesCtrl.TypeFlags.FALSE);
        }
    }

    private void workRegistrationRequestMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        boolean requestDB = false;
        DefinesMessages.TypeErrorRegistration typeError = DefinesMessages.TypeErrorRegistration.NoError;
        boolean checkLogin = GetterControls.getInstance()
                .getBeanDbCtrl().checkQueryToDB(DbCtrl.TypeExecutionCheck.Login,
                        (String) map.get(DefinesMessages.TypeData.Login));
        boolean checkEmail = GetterControls.getInstance()
                .getBeanDbCtrl().checkQueryToDB(DbCtrl.TypeExecutionCheck.Email,
                        (String) map.get(DefinesMessages.TypeData.Email));
        if (checkLogin) {
            typeError = DefinesMessages.TypeErrorRegistration.Login;
        }
        if (checkEmail) {
            typeError = DefinesMessages.TypeErrorRegistration.Email;
        }
        if (checkLogin && checkEmail) {
            typeError = DefinesMessages.TypeErrorRegistration.LoginAndEmail;
        }
        if (typeError == DefinesMessages.TypeErrorRegistration.NoError) {
            requestDB = GetterControls.getInstance()
                    .getBeanEmailCtrl().startVerifyRegEmail((String) map.get(DefinesMessages.TypeData.Email));
            typeError = DefinesMessages.TypeErrorRegistration.EmailSending;
        }
        GetterControls.getInstance().getBeanSendMessagesCtrl()
                .sendMessage(DefinesMessages.TypeMessage.RegistrationReply, requestDB, typeError);
    }

    private void workRegistrationReplyMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        if ((Boolean) map.get(DefinesMessages.TypeData.BoolReply)) {
            GetterControls.getInstance().getBeanMessagesDefinesCtrl()
                    .setRegistrationRequestFlag(MessagesDefinesCtrl.TypeFlags.TRUE);
        } else {
            GetterControls.getInstance().getBeanMessagesDefinesCtrl()
                    .setRegistrationRequestFlag(MessagesDefinesCtrl.TypeFlags.FALSE);
        }
        GetterControls.getInstance().getBeanMessagesDefinesCtrl()
                .setErrorRegistrationFlag((DefinesMessages.TypeErrorRegistration) map.get(DefinesMessages.TypeData.ErrorReg));
    }

    private void workVerifyRegistrationEmailRequestMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        boolean checkCode = GetterControls.getInstance()
                .getBeanDbCtrl().checkQueryToDB(DbCtrl.TypeExecutionCheck.VerifyRegistrationEmail,
                        (String) map.get(DefinesMessages.TypeData.Email),
                        (String) map.get(DefinesMessages.TypeData.VerifyCode));
        if (checkCode) {
            String login = (String) map.get(DefinesMessages.TypeData.Login);
            String email = (String) map.get(DefinesMessages.TypeData.Email);
            String password = (String) map.get(DefinesMessages.TypeData.Password);

            String hashPassword = hashCryptography.getHash(password);
            UUID uuidUser = UUID.randomUUID();

            boolean requestDB = GetterControls.getInstance()
                    .getBeanDbCtrl().insertQueryToDB(DbCtrl.TypeExecutionInsert.RegisterForm,
                            login,
                            email,
                            hashPassword,
                            uuidUser.toString());
            DefinesMessages.TypeErrorRegistration typeError = DefinesMessages.TypeErrorRegistration.NoError;
            if (!requestDB) {
                boolean checkLogin = GetterControls.getInstance()
                        .getBeanDbCtrl().checkQueryToDB(DbCtrl.TypeExecutionCheck.Login,
                                (String) map.get(DefinesMessages.TypeData.Login));
                boolean checkEmail = GetterControls.getInstance()
                        .getBeanDbCtrl().checkQueryToDB(DbCtrl.TypeExecutionCheck.Email,
                                (String) map.get(DefinesMessages.TypeData.Email));
                if (checkLogin) {
                    typeError = DefinesMessages.TypeErrorRegistration.Login;
                }
                if (checkEmail) {
                    typeError = DefinesMessages.TypeErrorRegistration.Email;
                }
                if (checkLogin && checkEmail) {
                    typeError = DefinesMessages.TypeErrorRegistration.LoginAndEmail;
                }
            }
            GetterControls.getInstance().getBeanSendMessagesCtrl()
                    .sendMessage(DefinesMessages.TypeMessage.VerifyRegistrationEmailReply, requestDB, typeError);
        } else {
            GetterControls.getInstance().getBeanSendMessagesCtrl()
                    .sendMessage(DefinesMessages.TypeMessage.VerifyRegistrationEmailReply, false, DefinesMessages.TypeErrorRegistration.Code);
        }
    }

    private void workVerifyRegistrationEmailReplyMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        if ((Boolean) map.get(DefinesMessages.TypeData.BoolReply)) {
            GetterControls.getInstance().getBeanMessagesDefinesCtrl().
                    setVerifyRegistrationEmailRequestFlag(MessagesDefinesCtrl.TypeFlags.TRUE);
        } else {
            GetterControls.getInstance().getBeanMessagesDefinesCtrl().
                    setVerifyRegistrationEmailRequestFlag(MessagesDefinesCtrl.TypeFlags.FALSE);
        }
        GetterControls.getInstance().getBeanMessagesDefinesCtrl()
                .setErrorVerifyRegEmailFlag((DefinesMessages.TypeErrorRegistration) map.get(DefinesMessages.TypeData.ErrorReg));
    }

    private void workResetPasswordRequestMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        String email = (String) map.get(DefinesMessages.TypeData.Email);
        boolean checkEmail = GetterControls.getInstance()
                .getBeanDbCtrl().checkQueryToDB(DbCtrl.TypeExecutionCheck.Email,
                        email);
        boolean reply = false;
        if (checkEmail) {
            reply = GetterControls.getInstance()
                    .getBeanEmailCtrl().startVerifyFamousEmail(email);
        }
        GetterControls.getInstance().getBeanSendMessagesCtrl()
                .sendMessage(DefinesMessages.TypeMessage.ResetPasswordReply, reply);
    }

    private void workResetPasswordReplyMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        if ((Boolean) map.get(DefinesMessages.TypeData.BoolReply)) {
            GetterControls.getInstance().getBeanMessagesDefinesCtrl().
                    setResetPasswordRequestFlag(MessagesDefinesCtrl.TypeFlags.TRUE);
        } else {
            GetterControls.getInstance().getBeanMessagesDefinesCtrl()
                    .setResetPasswordRequestFlag(MessagesDefinesCtrl.TypeFlags.FALSE);
        }
    }

    private void workVerifyFamousEmailRequestMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        boolean requestDB = GetterControls.getInstance()
                .getBeanDbCtrl().checkQueryToDB(DbCtrl.TypeExecutionCheck.VerifyFamousEmailCode,
                        (String) map.get(DefinesMessages.TypeData.Email),
                        (String) map.get(DefinesMessages.TypeData.VerifyCode));
        GetterControls.getInstance().getBeanSendMessagesCtrl()
                .sendMessage(DefinesMessages.TypeMessage.VerifyFamousEmailReply, requestDB);
    }

    private void workVerifyFamousEmailReplyMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        if ((Boolean) map.get(DefinesMessages.TypeData.BoolReply)) {
            GetterControls.getInstance().getBeanMessagesDefinesCtrl().
                    setVerifyFamousEmailRequestFlag(MessagesDefinesCtrl.TypeFlags.TRUE);
        } else {
            GetterControls.getInstance().getBeanMessagesDefinesCtrl().
                    setVerifyFamousEmailRequestFlag(MessagesDefinesCtrl.TypeFlags.FALSE);
        }
    }

    private void workChangePasswordRequestMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        String email = (String) map.get(DefinesMessages.TypeData.Email);
        String password = (String) map.get(DefinesMessages.TypeData.Password);

        String hashPassword = hashCryptography.getHash(password);

        boolean requestDB = GetterControls.getInstance()
                .getBeanDbCtrl().insertQueryToDB(DbCtrl.TypeExecutionInsert.ChangePassword,
                        email,
                        hashPassword);
        GetterControls.getInstance().getBeanSendMessagesCtrl()
                .sendMessage(DefinesMessages.TypeMessage.ChangePasswordReply, requestDB);
    }

    private void workChangePasswordReplyMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        if ((Boolean) map.get(DefinesMessages.TypeData.BoolReply)) {
            GetterControls.getInstance().getBeanMessagesDefinesCtrl().
                    setChangePasswordRequest(MessagesDefinesCtrl.TypeFlags.TRUE);
        } else {
            GetterControls.getInstance().getBeanMessagesDefinesCtrl()
                    .setChangePasswordRequest(MessagesDefinesCtrl.TypeFlags.FALSE);
        }
    }

    private void workChatsLoadRequestMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        String uuidUserStr = map.get(DefinesMessages.TypeData.UuidUser).toString();
        List<Map<DbGlobalDefines.LineKeys, String>> requestDB = GetterControls.getInstance()
                .getBeanDbCtrl().getMultipleInfoFromDb(DbCtrl.TypeExecutionGetMultiple.ChatsLoad, uuidUserStr);
        GetterControls.getInstance().getBeanSendMessagesCtrl().
                sendMessage(DefinesMessages.TypeMessage.ChatsLoadReply, requestDB);
    }

    private void workChatsLoadReplyMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        Object objectFromMap = map.get(DefinesMessages.TypeData.ChatsInfoList);
        List<Map<DefinesMessages.TypeData, Object>> chatsInfo =
                GetterTools.getInstance().getBeanStructTools()
                        .objectInListMaps(objectFromMap, DefinesMessages.TypeData.class, Object.class);

        GetterControls.getInstance().getBeanChatsCtrl().createChatsObjects(chatsInfo);
        GetterControls.getInstance().getBeanMessagesDefinesCtrl()
                .setChatsLoadReplyFlag(MessagesDefinesCtrl.TypeFlags.TRUE);
    }

    private void workCheckOnlineUserRequestMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        @SuppressWarnings("unused")
        String ip = (String) map.get(DefinesMessages.TypeData.IP);
        UUID uuidUser = GetterSettings.getInstance().getBeanUsersInfoSettings().getUuid();

        if (uuidUser == null) {
            log.warn("Here uuidUser is not set.");
            return;
        }

        GetterControls.getInstance().getBeanSendMessagesCtrl()
                .sendMessage(DefinesMessages.TypeMessage.CheckOnlineUserReply, uuidUser);
    }

    private void workCheckOnlineUserReplyMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        UUID uuidUser = (UUID) map.get(DefinesMessages.TypeData.UuidUser);
        GetterControls.getInstance().getBeanOnlineServersCtrl().addUsersOnline(uuidUser, runnableCtrlFrom);
    }

    private void workLoadUsersOnlineStatusRequestMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        Object objectList = map.get(DefinesMessages.TypeData.UuidsUsersList);
        List<UUID> uuidsUsers = GetterTools.getInstance().getBeanStructTools().checkedCastList(objectList, UUID.class);
        Map<UUID, MainChatsGlobalDefines.TypeStatusOnline> statusesUsers =
                GetterControls.getInstance().getBeanOnlineServersCtrl().getStatusesUsers(uuidsUsers);
        Map<UUID, String> lastOnlineTimeUsers =
                GetterControls.getInstance().getBeanOnlineServersCtrl().getLastOnlineTimeUsers(uuidsUsers);
        GetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(
                DefinesMessages.TypeMessage.LoadUsersOnlineStatusReply, statusesUsers, lastOnlineTimeUsers);
    }

    private void workLoadUsersOnlineStatusReplyMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        Object objectMapStatusesUsers = map.get(DefinesMessages.TypeData.UsersOnlineInfoList);
        Object objectMapLastOnlineTimeUsers = map.get(DefinesMessages.TypeData.Timestamp);

        Map<UUID, MainChatsGlobalDefines.TypeStatusOnline> mapStatusesUsers =
                GetterTools.getInstance().getBeanStructTools().objectInMap(objectMapStatusesUsers, UUID.class,
                        MainChatsGlobalDefines.TypeStatusOnline.class);
        Map<UUID, String> mapLastOnlineTimeUsers =
                GetterTools.getInstance().getBeanStructTools().objectInMap(objectMapLastOnlineTimeUsers, UUID.class,
                        String.class);

        GetterControls.getInstance().getBeanChatsCtrl().setOnlineStatusesUsers(mapStatusesUsers);
        GetterControls.getInstance().getBeanChatsCtrl().setLastOnlineTimeUsersByStrings(mapLastOnlineTimeUsers);

        GetterControls.getInstance().getBeanMessagesDefinesCtrl()
                .setLoadUsersOnlineReplyFlag(MessagesDefinesCtrl.TypeFlags.TRUE);
    }

    private void workTextMessageSendUserToServerMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        UUID uuidUserSender = (UUID) map.get(DefinesMessages.TypeData.UuidUserSender);
        UUID uuidUserReceiver = (UUID) map.get(DefinesMessages.TypeData.UuidUserReceiver);
        UUID uuidMessage = (UUID) map.get(DefinesMessages.TypeData.UuidMessage);
        String text = (String) map.get(DefinesMessages.TypeData.TextMessage);
        String timestampStr = (String) map.get(DefinesMessages.TypeData.Timestamp);

        MainChatsGlobalDefines.TypeStatusMessage status = MainChatsGlobalDefines.TypeStatusMessage.Delivered;
        String statusString = status.toString();
        Map<UUID, MainChatsGlobalDefines.TypeStatusMessage> mapStatusMessages = new HashMap<>();
        mapStatusMessages.put(uuidMessage, status);
        int normaliseTimestampCount = 3;
        LocalDateTime timestamp = GetterTools.getInstance().getBeanFormatTools()
                .stringToLocalDateTime(timestampStr, normaliseTimestampCount);

        // write to the database first
        GetterControls.getInstance().getBeanDbCtrl().insertQueryToDB(DbCtrl.TypeExecutionInsert.ChatMessagesSentMessage,
                uuidUserSender.toString(), uuidUserReceiver.toString(), uuidMessage.toString(), statusString, text, timestampStr);
        // send the status "delivered"
        GetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(
                DefinesMessages.TypeMessage.TextMessagesChangingStatusFromServer, mapStatusMessages);
        // send to the user if he is online
        GetterControls.getInstance().getBeanMessagesDialogCtrl().redirectMessageToOnlineUser(
                uuidUserSender, uuidUserReceiver, uuidMessage, status, text, timestamp);
        // send a delivery receipt
        GetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(
                DefinesMessages.TypeMessage.TextMessageSendUserToServerVerification, true);
    }

    private void workTextMessageSendUserToServerVerificationMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        if ((Boolean) map.get(DefinesMessages.TypeData.BoolReply)) {
            GetterControls.getInstance().getBeanMessagesDefinesCtrl()
                    .setTextMessageSendUserToServerFlag(MessagesDefinesCtrl.TypeFlags.TRUE);
        } else {
            GetterControls.getInstance().getBeanMessagesDefinesCtrl()
                    .setTextMessageSendUserToServerFlag(MessagesDefinesCtrl.TypeFlags.FALSE);
        }
    }

    private void workTextMessagesChangingStatusFromServerMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        Object statusesMap = map.get(DefinesMessages.TypeData.StatusMessagesMap);
        Map<UUID, MainChatsGlobalDefines.TypeStatusMessage> mapStatusesMessages = GetterTools.getInstance()
                .getBeanStructTools().objectInMap(statusesMap, UUID.class, MainChatsGlobalDefines.TypeStatusMessage.class);

        GetterControls.getInstance().getBeanMessagesDialogCtrl().setDirtyStatusToMessage(mapStatusesMessages);

        GetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(
                DefinesMessages.TypeMessage.TextMessagesChangingStatusFromServerVerification, true);
    }

    private void workTextMessagesChangingStatusFromServerVerificationMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        if ((Boolean) map.get(DefinesMessages.TypeData.BoolReply)) {
            log.info("Received a message delivery receipt with a status without errors.");
        } else {
            log.info("A message delivery receipt has arrived with an error status.");
        }
    }

    private void workTextMessagesChangingStatusFromUserMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        Object statusesMap = map.get(DefinesMessages.TypeData.StatusMessagesMap);
        Map<UUID, MainChatsGlobalDefines.TypeStatusMessage> mapStatusesMessages = GetterTools.getInstance()
                .getBeanStructTools().objectInMap(statusesMap, UUID.class, MainChatsGlobalDefines.TypeStatusMessage.class);

        for (UUID uuidMessage : mapStatusesMessages.keySet()) {
            String statusByUuid = String.valueOf(mapStatusesMessages.get(uuidMessage).getValue());
            GetterControls.getInstance().getBeanDbCtrl().insertQueryToDB(DbCtrl.TypeExecutionInsert.ChatsMessageStatusChange,
                    uuidMessage.toString(), statusByUuid);
        }

        GetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(
                DefinesMessages.TypeMessage.TextMessagesChangingStatusFromUserVerification, true);
    }

    private void workTextMessagesChangingStatusFromUserVerificationMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        if ((Boolean) map.get(DefinesMessages.TypeData.BoolReply)) {
            log.info("Received a message delivery receipt with a status without errors.");
        } else {
            log.info("A message delivery receipt has arrived with an error status.");
        }
    }

    private void workTextMessageRedirectServerToUserMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        UUID uuidUserSender = (UUID) map.get(DefinesMessages.TypeData.UuidUserSender);
        UUID uuidUserReceiver = (UUID) map.get(DefinesMessages.TypeData.UuidUserReceiver);
        UUID uuidMessage = (UUID) map.get(DefinesMessages.TypeData.UuidMessage);
        String text = (String) map.get(DefinesMessages.TypeData.TextMessage);
        String timestampStr = (String) map.get(DefinesMessages.TypeData.Timestamp);

        MainChatsGlobalDefines.TypeStatusMessage status = MainChatsGlobalDefines.TypeStatusMessage.Delivered;
        int normaliseTimestampCount = 3;
        LocalDateTime timestamp = GetterTools.getInstance().getBeanFormatTools()
                .stringToLocalDateTime(timestampStr, normaliseTimestampCount);

        GetterControls.getInstance().getBeanMessagesDialogCtrl().addRedirectMessageToModel(
                uuidUserSender, uuidUserReceiver, uuidMessage, status, text, timestamp);
        GetterControls.getInstance().getBeanMessagesDefinesCtrl()
                .setTextMessageRedirectServerToUserFlag(MessagesDefinesCtrl.TypeFlags.TRUE);

        GetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(
                DefinesMessages.TypeMessage.TextMessageRedirectServerToUserVerification, true);
    }

    private void workTextMessageRedirectServerToUserVerificationMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        if ((Boolean) map.get(DefinesMessages.TypeData.BoolReply)) {
            GetterControls.getInstance().getBeanMessagesDefinesCtrl()
                    .setTextMessageRedirectServerToUserFlag(MessagesDefinesCtrl.TypeFlags.TRUE);
        } else {
            GetterControls.getInstance().getBeanMessagesDefinesCtrl()
                    .setTextMessageRedirectServerToUserFlag(MessagesDefinesCtrl.TypeFlags.FALSE);
        }
    }

    private void workMessagesLoadRequestMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        UUID uuidChat = (UUID) map.get(DefinesMessages.TypeData.UuidChat);
        int quantityMessages = (Integer) map.get(DefinesMessages.TypeData.QuantityMessages);

        List<Map<DbGlobalDefines.LineKeys, String>> requestDB = GetterControls.getInstance()
                .getBeanDbCtrl().getMultipleInfoFromDb(DbCtrl.TypeExecutionGetMultiple.MessagesLoad,
                        uuidChat.toString(), String.valueOf(quantityMessages));
        GetterControls.getInstance().getBeanSendMessagesCtrl().
                sendMessage(DefinesMessages.TypeMessage.MessagesLoadReply, requestDB);
    }

    private void workMessagesLoadReplyMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        Object objectFromMap = map.get(DefinesMessages.TypeData.MessagesInfoList);
        List<Map<DefinesMessages.TypeData, Object>> msgInfo =
                GetterTools.getInstance().getBeanStructTools()
                        .objectInListMaps(objectFromMap, DefinesMessages.TypeData.class, Object.class);

        GetterControls.getInstance().getBeanMessagesDialogCtrl().createMessagesObjects(msgInfo);
        GetterControls.getInstance().getBeanMessagesDefinesCtrl()
                .setTextMessagesLoadReplyFlag(MessagesDefinesCtrl.TypeFlags.TRUE);
    }
}