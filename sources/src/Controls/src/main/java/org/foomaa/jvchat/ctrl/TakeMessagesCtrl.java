package org.foomaa.jvchat.ctrl;

import java.time.LocalDateTime;
import java.util.*;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.cryptography.HashCryptography;
import org.foomaa.jvchat.globaldefines.DbGlobalDefines;
import org.foomaa.jvchat.globaldefines.MainChatsGlobalDefines;
import org.foomaa.jvchat.messages.DefinesMessages;
import org.foomaa.jvchat.messages.DeserializatorDataMessages;
import org.foomaa.jvchat.settings.UsersInfoSettings;
import org.foomaa.jvchat.tools.FormatTools;
import org.foomaa.jvchat.tools.StructTools;

@Slf4j
public class TakeMessagesCtrl {
    private Runnable runnableCtrlFrom;
    private final HashCryptography hashCryptography;
    private final DeserializatorDataMessages deserializatorDataMessages;
    private final StructTools structTools;
    private final FormatTools formatTools;
    private final SendMessagesCtrl sendMessagesCtrl;
    private final MessagesDefinesCtrl messagesDefinesCtrl;
    private final MessagesDialogCtrl messagesDialogCtrl;
    private final OnlineServersCtrl onlineServersCtrl;
    private final UsersInfoSettings usersInfoSettings;
    private final ChatsCtrl chatsCtrl;
    private final EmailCtrl emailCtrl;
    private final DbCtrl dbCtrl;

    @Builder
    TakeMessagesCtrl(
            HashCryptography hashCryptography,
            DeserializatorDataMessages deserializatorDataMessages,
            StructTools structTools,
            FormatTools formatTools,
            SendMessagesCtrl sendMessagesCtrl,
            MessagesDefinesCtrl messagesDefinesCtrl,
            MessagesDialogCtrl messagesDialogCtrl,
            OnlineServersCtrl onlineServersCtrl,
            UsersInfoSettings usersInfoSettings,
            ChatsCtrl chatsCtrl,
            EmailCtrl emailCtrl,
            DbCtrl dbCtrl) {
        this.hashCryptography = Objects.requireNonNull(hashCryptography, "hashCryptography is mandatory");
        this.structTools = Objects.requireNonNull(structTools, "structTools is mandatory");
        this.formatTools = Objects.requireNonNull(formatTools, "formatTools is mandatory");
        this.sendMessagesCtrl = Objects.requireNonNull(sendMessagesCtrl, "sendMessagesCtrl is mandatory");
        this.messagesDefinesCtrl = Objects.requireNonNull(messagesDefinesCtrl, "messagesDefinesCtrl is mandatory");
        this.messagesDialogCtrl = Objects.requireNonNull(messagesDialogCtrl, "messagesDialogCtrl is mandatory");
        this.deserializatorDataMessages =
                Objects.requireNonNull(deserializatorDataMessages, "deserializatorDataMessages is mandatory");

        this.onlineServersCtrl = onlineServersCtrl;
        this.usersInfoSettings = usersInfoSettings;
        this.chatsCtrl = chatsCtrl;
        this.emailCtrl = emailCtrl;
        this.dbCtrl = dbCtrl;
    }

    public void takeMessage(byte[] data) {
        DefinesMessages.TypeMessage type = deserializatorDataMessages.getTypeMessage(data);

        switch (type) {
            case EntryRequest -> workEntryRequestMessage(getDeserializeMapData(type, data));
            case EntryReply -> workEntryReplyMessage(getDeserializeMapData(type, data));
            case RegistrationRequest -> workRegistrationRequestMessage(getDeserializeMapData(type, data));
            case RegistrationReply -> workRegistrationReplyMessage(getDeserializeMapData(type, data));
            case VerifyRegistrationEmailRequest -> workVerifyRegistrationEmailRequestMessage(
                    getDeserializeMapData(type, data));
            case VerifyRegistrationEmailReply -> workVerifyRegistrationEmailReplyMessage(
                    getDeserializeMapData(type, data));
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
            case LoadUsersOnlineStatusRequest -> workLoadUsersOnlineStatusRequestMessage(
                    getDeserializeMapData(type, data));
            case LoadUsersOnlineStatusReply -> workLoadUsersOnlineStatusReplyMessage(getDeserializeMapData(type, data));
            case TextMessageSendUserToServer -> workTextMessageSendUserToServerMessage(
                    getDeserializeMapData(type, data));
            case TextMessageSendUserToServerVerification -> workTextMessageSendUserToServerVerificationMessage(
                    getDeserializeMapData(type, data));
            case TextMessagesChangingStatusFromServer -> workTextMessagesChangingStatusFromServerMessage(
                    getDeserializeMapData(type, data));
            case TextMessagesChangingStatusFromServerVerification -> workTextMessagesChangingStatusFromServerVerificationMessage(
                    getDeserializeMapData(type, data));
            case TextMessagesChangingStatusFromUser -> workTextMessagesChangingStatusFromUserMessage(
                    getDeserializeMapData(type, data));
            case TextMessagesChangingStatusFromUserVerification -> workTextMessagesChangingStatusFromUserVerificationMessage(
                    getDeserializeMapData(type, data));
            case TextMessageRedirectServerToUser -> workTextMessageRedirectServerToUserMessage(
                    getDeserializeMapData(type, data));
            case TextMessageRedirectServerToUserVerification -> workTextMessageRedirectServerToUserVerificationMessage(
                    getDeserializeMapData(type, data));
            case MessagesLoadRequest -> workMessagesLoadRequestMessage(getDeserializeMapData(type, data));
            case MessagesLoadReply -> workMessagesLoadReplyMessage(getDeserializeMapData(type, data));
        }

        clearRunnableCtrlFromConnection();
    }

    private HashMap<DefinesMessages.TypeData, ?> getDeserializeMapData(DefinesMessages.TypeMessage type, byte[] data) {
        return deserializatorDataMessages.deserializeData(type, data);
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

        if (dbCtrl == null) {
            log.error("dbCtrl is null");
            return;
        }

        boolean requestDB = dbCtrl.checkQueryToDB(DbCtrl.TypeExecutionCheck.UserPassword, login, hashPassword);

        String uuidUserStr = dbCtrl.getSingleDataFromDb(DbCtrl.TypeExecutionGetSingle.UuidUserByLogin, login);
        UUID uuidUser = UUID.fromString(uuidUserStr);

        sendMessagesCtrl.sendMessage(DefinesMessages.TypeMessage.EntryReply, requestDB, uuidUser);
    }

    private void workEntryReplyMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        UUID uuidUser = (UUID) map.get(DefinesMessages.TypeData.UuidUser);

        if (usersInfoSettings == null) {
            log.error("usersInfoSettings is null");
            return;
        }

        usersInfoSettings.setUuid(uuidUser);

        if ((Boolean) map.get(DefinesMessages.TypeData.BoolReply)) {
            messagesDefinesCtrl.setEntryRequestFlag(MessagesDefinesCtrl.TypeFlags.TRUE);
        } else {
            messagesDefinesCtrl.setEntryRequestFlag(MessagesDefinesCtrl.TypeFlags.FALSE);
        }
    }

    private void workRegistrationRequestMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        boolean requestDB = false;
        DefinesMessages.TypeErrorRegistration typeError = DefinesMessages.TypeErrorRegistration.NoError;

        if (dbCtrl == null) {
            log.error("dbCtrl is null");
            return;
        }

        boolean checkLogin = dbCtrl.checkQueryToDB(
                DbCtrl.TypeExecutionCheck.Login, (String) map.get(DefinesMessages.TypeData.Login));
        boolean checkEmail = dbCtrl.checkQueryToDB(
                DbCtrl.TypeExecutionCheck.Email, (String) map.get(DefinesMessages.TypeData.Email));
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
            if (emailCtrl == null) {
                log.error("emailCtrl is null");
                return;
            }

            requestDB = emailCtrl.startVerifyRegEmail((String) map.get(DefinesMessages.TypeData.Email));
            typeError = DefinesMessages.TypeErrorRegistration.EmailSending;
        }
        sendMessagesCtrl.sendMessage(DefinesMessages.TypeMessage.RegistrationReply, requestDB, typeError);
    }

    private void workRegistrationReplyMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        if ((Boolean) map.get(DefinesMessages.TypeData.BoolReply)) {
            messagesDefinesCtrl.setRegistrationRequestFlag(MessagesDefinesCtrl.TypeFlags.TRUE);
        } else {
            messagesDefinesCtrl.setRegistrationRequestFlag(MessagesDefinesCtrl.TypeFlags.FALSE);
        }
        messagesDefinesCtrl.setErrorRegistrationFlag(
                (DefinesMessages.TypeErrorRegistration) map.get(DefinesMessages.TypeData.ErrorReg));
    }

    private void workVerifyRegistrationEmailRequestMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        if (dbCtrl == null) {
            log.error("dbCtrl is null");
            return;
        }

        boolean checkCode = dbCtrl.checkQueryToDB(
                DbCtrl.TypeExecutionCheck.VerifyRegistrationEmail,
                (String) map.get(DefinesMessages.TypeData.Email),
                (String) map.get(DefinesMessages.TypeData.VerifyCode));
        if (checkCode) {
            String login = (String) map.get(DefinesMessages.TypeData.Login);
            String email = (String) map.get(DefinesMessages.TypeData.Email);
            String password = (String) map.get(DefinesMessages.TypeData.Password);

            String hashPassword = hashCryptography.getHash(password);
            UUID uuidUser = UUID.randomUUID();

            boolean requestDB = dbCtrl.insertQueryToDB(
                    DbCtrl.TypeExecutionInsert.RegisterForm, login, email, hashPassword, uuidUser.toString());
            DefinesMessages.TypeErrorRegistration typeError = DefinesMessages.TypeErrorRegistration.NoError;
            if (!requestDB) {
                boolean checkLogin = dbCtrl.checkQueryToDB(
                        DbCtrl.TypeExecutionCheck.Login, (String) map.get(DefinesMessages.TypeData.Login));
                boolean checkEmail = dbCtrl.checkQueryToDB(
                        DbCtrl.TypeExecutionCheck.Email, (String) map.get(DefinesMessages.TypeData.Email));
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
            sendMessagesCtrl.sendMessage(
                    DefinesMessages.TypeMessage.VerifyRegistrationEmailReply, requestDB, typeError);
        } else {
            sendMessagesCtrl.sendMessage(
                    DefinesMessages.TypeMessage.VerifyRegistrationEmailReply,
                    false,
                    DefinesMessages.TypeErrorRegistration.Code);
        }
    }

    private void workVerifyRegistrationEmailReplyMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        if ((Boolean) map.get(DefinesMessages.TypeData.BoolReply)) {
            messagesDefinesCtrl.setVerifyRegistrationEmailRequestFlag(MessagesDefinesCtrl.TypeFlags.TRUE);
        } else {
            messagesDefinesCtrl.setVerifyRegistrationEmailRequestFlag(MessagesDefinesCtrl.TypeFlags.FALSE);
        }
        messagesDefinesCtrl.setErrorVerifyRegEmailFlag(
                (DefinesMessages.TypeErrorRegistration) map.get(DefinesMessages.TypeData.ErrorReg));
    }

    private void workResetPasswordRequestMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        String email = (String) map.get(DefinesMessages.TypeData.Email);

        if (dbCtrl == null) {
            log.error("dbCtrl is null");
            return;
        }

        boolean checkEmail = dbCtrl.checkQueryToDB(DbCtrl.TypeExecutionCheck.Email, email);
        boolean reply = false;
        if (checkEmail) {
            if (emailCtrl == null) {
                log.error("emailCtrl is null");
                return;
            }

            reply = emailCtrl.startVerifyFamousEmail(email);
        }
        sendMessagesCtrl.sendMessage(DefinesMessages.TypeMessage.ResetPasswordReply, reply);
    }

    private void workResetPasswordReplyMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        if ((Boolean) map.get(DefinesMessages.TypeData.BoolReply)) {
            messagesDefinesCtrl.setResetPasswordRequestFlag(MessagesDefinesCtrl.TypeFlags.TRUE);
        } else {
            messagesDefinesCtrl.setResetPasswordRequestFlag(MessagesDefinesCtrl.TypeFlags.FALSE);
        }
    }

    private void workVerifyFamousEmailRequestMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        if (dbCtrl == null) {
            log.error("dbCtrl is null");
            return;
        }

        boolean requestDB = dbCtrl.checkQueryToDB(
                DbCtrl.TypeExecutionCheck.VerifyFamousEmailCode,
                (String) map.get(DefinesMessages.TypeData.Email),
                (String) map.get(DefinesMessages.TypeData.VerifyCode));
        sendMessagesCtrl.sendMessage(DefinesMessages.TypeMessage.VerifyFamousEmailReply, requestDB);
    }

    private void workVerifyFamousEmailReplyMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        if ((Boolean) map.get(DefinesMessages.TypeData.BoolReply)) {
            messagesDefinesCtrl.setVerifyFamousEmailRequestFlag(MessagesDefinesCtrl.TypeFlags.TRUE);
        } else {
            messagesDefinesCtrl.setVerifyFamousEmailRequestFlag(MessagesDefinesCtrl.TypeFlags.FALSE);
        }
    }

    private void workChangePasswordRequestMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        String email = (String) map.get(DefinesMessages.TypeData.Email);
        String password = (String) map.get(DefinesMessages.TypeData.Password);

        String hashPassword = hashCryptography.getHash(password);

        if (dbCtrl == null) {
            log.error("dbCtrl is null");
            return;
        }

        boolean requestDB = dbCtrl.insertQueryToDB(DbCtrl.TypeExecutionInsert.ChangePassword, email, hashPassword);
        sendMessagesCtrl.sendMessage(DefinesMessages.TypeMessage.ChangePasswordReply, requestDB);
    }

    private void workChangePasswordReplyMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        if ((Boolean) map.get(DefinesMessages.TypeData.BoolReply)) {
            messagesDefinesCtrl.setChangePasswordRequest(MessagesDefinesCtrl.TypeFlags.TRUE);
        } else {
            messagesDefinesCtrl.setChangePasswordRequest(MessagesDefinesCtrl.TypeFlags.FALSE);
        }
    }

    private void workChatsLoadRequestMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        String uuidUserStr = map.get(DefinesMessages.TypeData.UuidUser).toString();

        if (dbCtrl == null) {
            log.error("dbCtrl is null");
            return;
        }

        List<Map<DbGlobalDefines.LineKeys, String>> requestDB =
                dbCtrl.getMultipleInfoFromDb(DbCtrl.TypeExecutionGetMultiple.ChatsLoad, uuidUserStr);
        sendMessagesCtrl.sendMessage(DefinesMessages.TypeMessage.ChatsLoadReply, requestDB);
    }

    private void workChatsLoadReplyMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        Object objectFromMap = map.get(DefinesMessages.TypeData.ChatsInfoList);
        List<Map<DefinesMessages.TypeData, Object>> chatsInfo =
                structTools.objectInListMaps(objectFromMap, DefinesMessages.TypeData.class, Object.class);

        if (chatsCtrl == null) {
            log.error("charsCtrl is null");
            return;
        }

        chatsCtrl.createChatsObjects(chatsInfo);
        messagesDefinesCtrl.setChatsLoadReplyFlag(MessagesDefinesCtrl.TypeFlags.TRUE);
    }

    private void workCheckOnlineUserRequestMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        @SuppressWarnings("unused")
        String ip = (String) map.get(DefinesMessages.TypeData.IP);

        if (usersInfoSettings == null) {
            log.error("usersInfoSettings is null");
            return;
        }

        UUID uuidUser = usersInfoSettings.getUuid();

        if (uuidUser == null) {
            log.warn("Here uuidUser is not set.");
            return;
        }

        sendMessagesCtrl.sendMessage(DefinesMessages.TypeMessage.CheckOnlineUserReply, uuidUser);
    }

    private void workCheckOnlineUserReplyMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        UUID uuidUser = (UUID) map.get(DefinesMessages.TypeData.UuidUser);

        if (onlineServersCtrl == null) {
            log.error("onlineServersCtrl is null");
            return;
        }

        onlineServersCtrl.addUsersOnline(uuidUser, runnableCtrlFrom);
    }

    private void workLoadUsersOnlineStatusRequestMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        Object objectList = map.get(DefinesMessages.TypeData.UuidsUsersList);
        List<UUID> uuidsUsers = structTools.checkedCastList(objectList, UUID.class);

        if (onlineServersCtrl == null) {
            log.error("onlineServersCtrl is null");
            return;
        }

        Map<UUID, MainChatsGlobalDefines.TypeStatusOnline> statusesUsers =
                onlineServersCtrl.getStatusesUsers(uuidsUsers);
        Map<UUID, String> lastOnlineTimeUsers = onlineServersCtrl.getLastOnlineTimeUsers(uuidsUsers);
        sendMessagesCtrl.sendMessage(
                DefinesMessages.TypeMessage.LoadUsersOnlineStatusReply, statusesUsers, lastOnlineTimeUsers);
    }

    private void workLoadUsersOnlineStatusReplyMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        Object objectMapStatusesUsers = map.get(DefinesMessages.TypeData.UsersOnlineInfoList);
        Object objectMapLastOnlineTimeUsers = map.get(DefinesMessages.TypeData.Timestamp);

        Map<UUID, MainChatsGlobalDefines.TypeStatusOnline> mapStatusesUsers = structTools.objectInMap(
                objectMapStatusesUsers, UUID.class, MainChatsGlobalDefines.TypeStatusOnline.class);
        Map<UUID, String> mapLastOnlineTimeUsers =
                structTools.objectInMap(objectMapLastOnlineTimeUsers, UUID.class, String.class);

        if (chatsCtrl == null) {
            log.error("charsCtrl is null");
            return;
        }

        chatsCtrl.setOnlineStatusesUsers(mapStatusesUsers);
        chatsCtrl.setLastOnlineTimeUsersByStrings(mapLastOnlineTimeUsers);

        messagesDefinesCtrl.setLoadUsersOnlineReplyFlag(MessagesDefinesCtrl.TypeFlags.TRUE);
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
        LocalDateTime timestamp = formatTools.stringToLocalDateTime(timestampStr, normaliseTimestampCount);

        // write to the database first
        if (dbCtrl == null) {
            log.error("dbCtrl is null");
            return;
        }

        dbCtrl.insertQueryToDB(
                DbCtrl.TypeExecutionInsert.ChatMessagesSentMessage,
                uuidUserSender.toString(),
                uuidUserReceiver.toString(),
                uuidMessage.toString(),
                statusString,
                text,
                timestampStr);
        // send the status "delivered"
        sendMessagesCtrl.sendMessage(
                DefinesMessages.TypeMessage.TextMessagesChangingStatusFromServer, mapStatusMessages);
        // send to the user if he is online
        messagesDialogCtrl.redirectMessageToOnlineUser(
                uuidUserSender, uuidUserReceiver, uuidMessage, status, text, timestamp);
        // send a delivery receipt
        sendMessagesCtrl.sendMessage(DefinesMessages.TypeMessage.TextMessageSendUserToServerVerification, true);
    }

    private void workTextMessageSendUserToServerVerificationMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        if ((Boolean) map.get(DefinesMessages.TypeData.BoolReply)) {
            messagesDefinesCtrl.setTextMessageSendUserToServerFlag(MessagesDefinesCtrl.TypeFlags.TRUE);
        } else {
            messagesDefinesCtrl.setTextMessageSendUserToServerFlag(MessagesDefinesCtrl.TypeFlags.FALSE);
        }
    }

    private void workTextMessagesChangingStatusFromServerMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        Object statusesMap = map.get(DefinesMessages.TypeData.StatusMessagesMap);
        Map<UUID, MainChatsGlobalDefines.TypeStatusMessage> mapStatusesMessages =
                structTools.objectInMap(statusesMap, UUID.class, MainChatsGlobalDefines.TypeStatusMessage.class);

        messagesDialogCtrl.setDirtyStatusToMessage(mapStatusesMessages);

        sendMessagesCtrl.sendMessage(
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
        Map<UUID, MainChatsGlobalDefines.TypeStatusMessage> mapStatusesMessages =
                structTools.objectInMap(statusesMap, UUID.class, MainChatsGlobalDefines.TypeStatusMessage.class);

        for (UUID uuidMessage : mapStatusesMessages.keySet()) {
            String statusByUuid =
                    String.valueOf(mapStatusesMessages.get(uuidMessage).getValue());

            if (dbCtrl == null) {
                log.error("dbCtrl is null");
                return;
            }

            dbCtrl.insertQueryToDB(
                    DbCtrl.TypeExecutionInsert.ChatsMessageStatusChange, uuidMessage.toString(), statusByUuid);
        }

        sendMessagesCtrl.sendMessage(DefinesMessages.TypeMessage.TextMessagesChangingStatusFromUserVerification, true);
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
        LocalDateTime timestamp = formatTools.stringToLocalDateTime(timestampStr, normaliseTimestampCount);

        messagesDialogCtrl.addRedirectMessageToModel(
                uuidUserSender, uuidUserReceiver, uuidMessage, status, text, timestamp);
        messagesDefinesCtrl.setTextMessageRedirectServerToUserFlag(MessagesDefinesCtrl.TypeFlags.TRUE);

        sendMessagesCtrl.sendMessage(DefinesMessages.TypeMessage.TextMessageRedirectServerToUserVerification, true);
    }

    private void workTextMessageRedirectServerToUserVerificationMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        if ((Boolean) map.get(DefinesMessages.TypeData.BoolReply)) {
            messagesDefinesCtrl.setTextMessageRedirectServerToUserFlag(MessagesDefinesCtrl.TypeFlags.TRUE);
        } else {
            messagesDefinesCtrl.setTextMessageRedirectServerToUserFlag(MessagesDefinesCtrl.TypeFlags.FALSE);
        }
    }

    private void workMessagesLoadRequestMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        UUID uuidChat = (UUID) map.get(DefinesMessages.TypeData.UuidChat);
        int quantityMessages = (Integer) map.get(DefinesMessages.TypeData.QuantityMessages);

        if (dbCtrl == null) {
            log.error("dbCtrl is null");
            return;
        }

        List<Map<DbGlobalDefines.LineKeys, String>> requestDB = dbCtrl.getMultipleInfoFromDb(
                DbCtrl.TypeExecutionGetMultiple.MessagesLoad, uuidChat.toString(), String.valueOf(quantityMessages));
        sendMessagesCtrl.sendMessage(DefinesMessages.TypeMessage.MessagesLoadReply, requestDB);
    }

    private void workMessagesLoadReplyMessage(HashMap<DefinesMessages.TypeData, ?> map) {
        Object objectFromMap = map.get(DefinesMessages.TypeData.MessagesInfoList);
        List<Map<DefinesMessages.TypeData, Object>> msgInfo =
                structTools.objectInListMaps(objectFromMap, DefinesMessages.TypeData.class, Object.class);

        messagesDialogCtrl.createMessagesObjects(msgInfo);
        messagesDefinesCtrl.setTextMessagesLoadReplyFlag(MessagesDefinesCtrl.TypeFlags.TRUE);
    }
}
