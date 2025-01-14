package org.foomaa.jvchat.messages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.foomaa.jvchat.globaldefines.JvDbGlobalDefines;
import org.foomaa.jvchat.globaldefines.JvMainChatsGlobalDefines;
import org.foomaa.jvchat.tools.JvGetterTools;


public class JvSerializatorDataMessages {
    JvSerializatorDataMessages() {}

    public byte[] serialiseData(JvDefinesMessages.TypeMessage type, Object... parameters) {
        switch (type) {
            case EntryRequest -> {
                if (parameters.length == 2) {
                    Object login = parameters[0];
                    Object password = parameters[1];
                    return createEntryRequestMessage(type,
                            (String) login, (String) password);
                }
            }
            case EntryReply -> {
                if (parameters.length == 2) {
                    Object reply = parameters[0];
                    Object uuidUser = parameters[1];
                    return createEntryReplyMessage(type, (Boolean) reply, (UUID) uuidUser);
                }
            }
            case RegistrationRequest -> {
                if (parameters.length == 3) {
                    Object login = parameters[0];
                    Object email = parameters[1];
                    Object password = parameters[2];
                    return createRegistrationRequestMessage(type,
                            (String) login, (String) email, (String) password);
                }
            }
            case RegistrationReply -> {
                if (parameters.length == 2) {
                    Object reply = parameters[0];
                    Object error = parameters[1];
                    return createRegistrationReplyMessage(type, (Boolean) reply,
                            (JvDefinesMessages.TypeErrorRegistration) error);
                }
            }
            case VerifyRegistrationEmailRequest -> {
                if (parameters.length == 4) {
                    Object login = parameters[0];
                    Object email = parameters[1];
                    Object password = parameters[2];
                    Object code = parameters[3];
                    return createVerifyRegistrationEmailRequestMessage(type,
                            (String) login, (String) email, (String) password, (String) code);
                }
            }
            case VerifyRegistrationEmailReply -> {
                if (parameters.length == 2) {
                    Object reply = parameters[0];
                    Object error = parameters[1];
                    return createVerifyRegistrationEmailReplyMessage(type, (Boolean) reply,
                            (JvDefinesMessages.TypeErrorRegistration) error);
                }
            }
            case ResetPasswordRequest -> {
                if (parameters.length == 1) {
                    Object email = parameters[0];
                    return createResetPasswordRequestMessage(type, (String) email);
                }
            }
            case ResetPasswordReply -> {
                if (parameters.length == 1) {
                    Object reply = parameters[0];
                    return createResetPasswordReplyMessage(type, (Boolean) reply);
                }
            }
            case VerifyFamousEmailRequest -> {
                if (parameters.length == 2) {
                    Object email = parameters[0];
                    Object password = parameters[1];
                    return createVerifyFamousEmailRequestMessage(type, (String) email, (String) password);
                }
            }
            case VerifyFamousEmailReply -> {
                if (parameters.length == 1) {
                    Object reply = parameters[0];
                    return createVerifyFamousEmailReplyMessage(type, (Boolean) reply);
                }
            }
            case ChangePasswordRequest -> {
                if (parameters.length == 2) {
                    Object email = parameters[0];
                    Object code = parameters[1];
                    return createChangePasswordRequestMessage(type, (String) email, (String) code);
                }
            }
            case ChangePasswordReply -> {
                if (parameters.length == 1) {
                    Object reply = parameters[0];
                    return createChangePasswordReplyMessage(type, (Boolean) reply);
                }
            }
            case ChatsLoadRequest -> {
                if (parameters.length == 1) {
                    Object uuidUser = parameters[0];
                    return createChatsLoadRequestMessage(type, (UUID) uuidUser);
                }
            }
            case ChatsLoadReply -> {
                if (parameters.length == 1) {
                    Object chatsInfoObj = parameters[0];
                    List<Map<JvDbGlobalDefines.LineKeys, String>> chatsInfo =
                            JvGetterTools.getInstance().getBeanStructTools()
                                    .objectInListMaps(chatsInfoObj, JvDbGlobalDefines.LineKeys.class, String.class);
                    return createChatsLoadReplyMessage(type, chatsInfo);
                }
            }
            case CheckOnlineUserRequest -> {
                if (parameters.length == 1) {
                    Object ip = parameters[0];
                    return createCheckOnlineUserRequestMessage(type, (String) ip);
                }
            }
            case CheckOnlineUserReply -> {
                if (parameters.length == 1) {
                    Object login = parameters[0];
                    return createCheckOnlineUserReplyMessage(type, (String) login);
                }
            }
            case LoadUsersOnlineStatusRequest -> {
                if (parameters.length == 1) {
                    Object loginsObject = parameters[0];
                    List<String> loginsList = JvGetterTools.getInstance()
                            .getBeanStructTools().checkedCastList(loginsObject, String.class);
                    return createLoadUsersOnlineStatusRequestMessage(type, loginsList);
                }
            }
            case LoadUsersOnlineStatusReply -> {
                if (parameters.length == 2) {
                    Object statusesUsersObj = parameters[0];
                    Object lastOnlineTimeUsersObj = parameters[1];
                    Map<String, JvMainChatsGlobalDefines.TypeStatusOnline> statusesUsersMap = JvGetterTools.getInstance()
                            .getBeanStructTools().objectInMap(statusesUsersObj, String.class, JvMainChatsGlobalDefines.TypeStatusOnline.class);
                    Map<String, String> lastOnlineTimeUsers = JvGetterTools.getInstance()
                            .getBeanStructTools().objectInMap(lastOnlineTimeUsersObj, String.class, String.class);
                    return createLoadUsersOnlineStatusReplyMessage(type, statusesUsersMap, lastOnlineTimeUsers);
                }
            }
            case TextMessageSendUserToServer -> {
                if (parameters.length == 5) {
                    Object loginSender = parameters[0];
                    Object loginReceiver = parameters[1];
                    Object uuid = parameters[2];
                    Object text = parameters[3];
                    Object timestamp = parameters[4];
                    return createTextMessageSendUserToServerMessage(type, (String) loginSender,
                            (String) loginReceiver, (String) uuid, (String) text, (String) timestamp);
                }
            }
            case TextMessageSendUserToServerVerification -> {
                if (parameters.length == 1) {
                    Object reply = parameters[0];
                    return createTextMessageSendUserToServerVerificationMessage(type, (Boolean) reply);
                }
            }
            case TextMessagesChangingStatusFromServer -> {
                if (parameters.length == 1) {
                    Object mapUuidStatus = parameters[0];
                    Map<UUID, JvMainChatsGlobalDefines.TypeStatusMessage> mapStatusesMessages = JvGetterTools.getInstance()
                            .getBeanStructTools().objectInMap(mapUuidStatus, UUID.class, JvMainChatsGlobalDefines.TypeStatusMessage.class);
                    return createTextMessageChangingStatusFromServerMessage(type, mapStatusesMessages);
                }
            }
            case TextMessagesChangingStatusFromServerVerification -> {
                if (parameters.length == 1) {
                    Object reply = parameters[0];
                    return createTextMessagesChangingStatusFromServerVerificationMessage(type, (Boolean) reply);
                }
            }
            case TextMessagesChangingStatusFromUser -> {
                if (parameters.length == 1) {
                    Object mapUuidStatus = parameters[0];
                    Map<UUID, JvMainChatsGlobalDefines.TypeStatusMessage> mapStatusesMessages = JvGetterTools.getInstance()
                            .getBeanStructTools().objectInMap(mapUuidStatus, UUID.class, JvMainChatsGlobalDefines.TypeStatusMessage.class);
                    return createTextMessageChangingStatusFromUserMessage(type, mapStatusesMessages);
                }
            }
            case TextMessagesChangingStatusFromUserVerification -> {
                if (parameters.length == 1) {
                    Object reply = parameters[0];
                    return createTextMessagesChangingStatusFromUserVerificationMessage(type, (Boolean) reply);
                }
            }
            case TextMessageRedirectServerToUser -> {
                if (parameters.length == 5) {
                    Object loginSender = parameters[0];
                    Object loginReceiver = parameters[1];
                    Object uuid = parameters[2];
                    Object text = parameters[3];
                    Object timestamp = parameters[4];
                    return createTextMessageRedirectServerToUserMessage(type, (String) loginSender,
                            (String) loginReceiver, (String) uuid, (String) text, (String) timestamp);
                }
            }
            case TextMessageRedirectServerToUserVerification -> {
                if (parameters.length == 1) {
                    Object reply = parameters[0];
                    return createTextMessageRedirectServerToUserVerificationMessage(type, (Boolean) reply);
                }
            }
            case MessagesLoadRequest -> {
                if (parameters.length == 3) {
                    Object loginRequesting = parameters[0];
                    Object loginDialog = parameters[1];
                    Object quantityMessages = parameters[2];
                    return createMessagesLoadRequestMessage(type, (String) loginRequesting, (String) loginDialog, (Integer) quantityMessages);
                }
            }
            case MessagesLoadReply -> {
                if (parameters.length == 1) {
                    Object messagesInfoObj = parameters[0];
                    List<Map<JvDbGlobalDefines.LineKeys, String>> messagesInfo =
                            JvGetterTools.getInstance().getBeanStructTools()
                                    .objectInListMaps(messagesInfoObj, JvDbGlobalDefines.LineKeys.class, String.class);
                    return createMessagesLoadReplyMessage(type, messagesInfo);
                }
            }
        }
        return new byte[0];
    }

    private byte[] createEntryRequestMessage(JvDefinesMessages.TypeMessage type, String login, String password) {
        JvClientServerSerializeProtocolMessage_pb.EntryRequest msgEntryRequest =
                JvClientServerSerializeProtocolMessage_pb.EntryRequest.newBuilder()
                .setLogin(login)
                .setPassword(password)
                .build();
        JvClientServerSerializeProtocolMessage_pb.General resMsg =
                JvClientServerSerializeProtocolMessage_pb.General.newBuilder()
                .setType(type.getValue())
                .setEntryRequest(msgEntryRequest)
                .build();
        return resMsg.toByteArray();
    }

    private byte[] createEntryReplyMessage(JvDefinesMessages.TypeMessage type, boolean reply, UUID uuidUser) {
        JvClientServerSerializeProtocolMessage_pb.EntryReply msgEntryReply =
                JvClientServerSerializeProtocolMessage_pb.EntryReply.newBuilder()
                .setReply(reply)
                .setUuidUser(uuidUser.toString())
                .build();
        JvClientServerSerializeProtocolMessage_pb.General resMsg =
                JvClientServerSerializeProtocolMessage_pb.General.newBuilder()
                .setType(type.getValue())
                .setEntryReply(msgEntryReply)
                .build();
        return resMsg.toByteArray();
    }

    private byte[] createRegistrationRequestMessage(JvDefinesMessages.TypeMessage type, String login, String email, String password) {
        JvClientServerSerializeProtocolMessage_pb.RegistrationRequest msgRegRequest =
                JvClientServerSerializeProtocolMessage_pb.RegistrationRequest.newBuilder()
                .setLogin(login)
                .setEmail(email)
                .setPassword(password)
                .build();
        JvClientServerSerializeProtocolMessage_pb.General resMsg =
                JvClientServerSerializeProtocolMessage_pb.General.newBuilder()
                .setType(type.getValue())
                .setRegistrationRequest(msgRegRequest)
                .build();
        return resMsg.toByteArray();
    }

    private byte[] createRegistrationReplyMessage(JvDefinesMessages.TypeMessage type, boolean reply, JvDefinesMessages.TypeErrorRegistration error) {
        JvClientServerSerializeProtocolMessage_pb.RegistrationReply msgRegReply =
                JvClientServerSerializeProtocolMessage_pb.RegistrationReply.newBuilder()
                .setReply(reply)
                .setError(JvClientServerSerializeProtocolMessage_pb.RegistrationReply.Error.forNumber(error.getValue()))
                .build();
        JvClientServerSerializeProtocolMessage_pb.General resMsg =
                JvClientServerSerializeProtocolMessage_pb.General.newBuilder()
                .setType(type.getValue())
                .setRegistrationReply(msgRegReply)
                .build();
        return resMsg.toByteArray();
    }

    private byte[] createVerifyRegistrationEmailRequestMessage(JvDefinesMessages.TypeMessage type, String login, String email, String password, String code) {
        JvClientServerSerializeProtocolMessage_pb.VerifyRegistrationEmailRequest msgVerifyRegRequest =
                JvClientServerSerializeProtocolMessage_pb.VerifyRegistrationEmailRequest.newBuilder()
                .setLogin(login)
                .setEmail(email)
                .setPassword(password)
                .setCode(code)
                .build();
        JvClientServerSerializeProtocolMessage_pb.General resMsg =
                JvClientServerSerializeProtocolMessage_pb.General.newBuilder()
                .setType(type.getValue())
                .setVerifyRegistrationEmailRequest(msgVerifyRegRequest)
                .build();
        return resMsg.toByteArray();
    }

    private byte[] createVerifyRegistrationEmailReplyMessage(JvDefinesMessages.TypeMessage type, boolean reply, JvDefinesMessages.TypeErrorRegistration error) {
        JvClientServerSerializeProtocolMessage_pb.VerifyRegistrationEmailReply msgVerifyRegReply =
                JvClientServerSerializeProtocolMessage_pb.VerifyRegistrationEmailReply.newBuilder()
                .setReply(reply)
                .setError(JvClientServerSerializeProtocolMessage_pb.VerifyRegistrationEmailReply.Error.forNumber(error.getValue()))
                .build();
        JvClientServerSerializeProtocolMessage_pb.General resMsg =
                JvClientServerSerializeProtocolMessage_pb.General.newBuilder()
                .setType(type.getValue())
                .setVerifyRegistrationEmailReply(msgVerifyRegReply)
                .build();
        return resMsg.toByteArray();
    }

    private byte[] createResetPasswordRequestMessage(JvDefinesMessages.TypeMessage type, String email) {
        JvClientServerSerializeProtocolMessage_pb.ResetPasswordRequest msgResetRequest =
                JvClientServerSerializeProtocolMessage_pb.ResetPasswordRequest.newBuilder()
                .setEmail(email)
                .build();
        JvClientServerSerializeProtocolMessage_pb.General resMsg =
                JvClientServerSerializeProtocolMessage_pb.General.newBuilder()
                .setType(type.getValue())
                .setResetPasswordRequest(msgResetRequest)
                .build();
        return resMsg.toByteArray();
    }

    private byte[] createResetPasswordReplyMessage(JvDefinesMessages.TypeMessage type, boolean reply) {
        JvClientServerSerializeProtocolMessage_pb.ResetPasswordReply msgResetReply =
                JvClientServerSerializeProtocolMessage_pb.ResetPasswordReply.newBuilder()
                .setReply(reply)
                .build();
        JvClientServerSerializeProtocolMessage_pb.General resMsg =
                JvClientServerSerializeProtocolMessage_pb.General.newBuilder()
                .setType(type.getValue())
                .setResetPasswordReply(msgResetReply)
                .build();
        return resMsg.toByteArray();
    }

    private byte[] createVerifyFamousEmailRequestMessage(JvDefinesMessages.TypeMessage type, String email, String code) {
        JvClientServerSerializeProtocolMessage_pb.VerifyFamousEmailRequest msgVerifyEmailRequest =
                JvClientServerSerializeProtocolMessage_pb.VerifyFamousEmailRequest.newBuilder()
                .setEmail(email)
                .setCode(code)
                .build();
        JvClientServerSerializeProtocolMessage_pb.General resMsg =
                JvClientServerSerializeProtocolMessage_pb.General.newBuilder()
                .setType(type.getValue())
                .setVerifyFamousEmailRequest(msgVerifyEmailRequest)
                .build();
        return resMsg.toByteArray();
    }

    private byte[] createVerifyFamousEmailReplyMessage(JvDefinesMessages.TypeMessage type, boolean reply) {
        JvClientServerSerializeProtocolMessage_pb.VerifyFamousEmailReply msgVerifyEmailReply =
                JvClientServerSerializeProtocolMessage_pb.VerifyFamousEmailReply.newBuilder()
                .setReply(reply)
                .build();
        JvClientServerSerializeProtocolMessage_pb.General resMsg =
                JvClientServerSerializeProtocolMessage_pb.General.newBuilder()
                .setType(type.getValue())
                .setVerifyFamousEmailReply(msgVerifyEmailReply)
                .build();
        return resMsg.toByteArray();
    }

    private byte[] createChangePasswordRequestMessage(JvDefinesMessages.TypeMessage type, String email, String password) {
        JvClientServerSerializeProtocolMessage_pb.ChangePasswordRequest msgChangePasswordRequest =
                JvClientServerSerializeProtocolMessage_pb.ChangePasswordRequest.newBuilder()
                .setEmail(email)
                .setPassword(password)
                .build();
        JvClientServerSerializeProtocolMessage_pb.General resMsg =
                JvClientServerSerializeProtocolMessage_pb.General.newBuilder()
                .setType(type.getValue())
                .setChangePasswordRequest(msgChangePasswordRequest)
                .build();
        return resMsg.toByteArray();
    }

    private byte[] createChangePasswordReplyMessage(JvDefinesMessages.TypeMessage type, boolean reply) {
        JvClientServerSerializeProtocolMessage_pb.ChangePasswordReply msgChangePasswordReply =
                JvClientServerSerializeProtocolMessage_pb.ChangePasswordReply.newBuilder()
                .setReply(reply)
                .build();
        JvClientServerSerializeProtocolMessage_pb.General resMsg =
                JvClientServerSerializeProtocolMessage_pb.General.newBuilder()
                .setType(type.getValue())
                .setChangePasswordReply(msgChangePasswordReply)
                .build();
        return resMsg.toByteArray();
    }

    private byte[] createChatsLoadRequestMessage(JvDefinesMessages.TypeMessage type, UUID uuidUser) {
        JvClientServerSerializeProtocolMessage_pb.ChatsLoadRequest msgChatsLoadRequest =
                JvClientServerSerializeProtocolMessage_pb.ChatsLoadRequest.newBuilder()
                        .setUuidUser(uuidUser.toString())
                        .build();
        JvClientServerSerializeProtocolMessage_pb.General resMsg =
                JvClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setChatsLoadRequest(msgChatsLoadRequest)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createChatsLoadReplyMessage(JvDefinesMessages.TypeMessage type,
                                               List<Map<JvDbGlobalDefines.LineKeys, String>> chatsInfo) {
        JvClientServerSerializeProtocolMessage_pb.ChatsLoadReply.Builder builder =
                JvClientServerSerializeProtocolMessage_pb.ChatsLoadReply.newBuilder();

        for (Map<JvDbGlobalDefines.LineKeys, String> map : chatsInfo) {
            JvClientServerSerializeProtocolMessage_pb.ChatsInfo chatsInfoPart = JvClientServerSerializeProtocolMessage_pb.ChatsInfo
                    .newBuilder()
                    .setLogin(map.get(JvDbGlobalDefines.LineKeys.Login))
                    .setLastMessageText(map.get(JvDbGlobalDefines.LineKeys.LastMessageText))
                    .setUuidChat(map.get(JvDbGlobalDefines.LineKeys.UuidChat))
                    .setUuidMessage(map.get(JvDbGlobalDefines.LineKeys.UuidMessage))
                    .setIsLoginSentLastMessage(Boolean.parseBoolean(map.get(JvDbGlobalDefines.LineKeys.IsLoginSentLastMessage)))
                    .setStatusMessage(JvClientServerSerializeProtocolMessage_pb.ChatsInfo.TypeStatusMessage
                            .forNumber(Integer.parseInt(map.get(JvDbGlobalDefines.LineKeys.StatusMessage))))
                    .setDateTimeLastMessage(map.get(JvDbGlobalDefines.LineKeys.DateTimeLastMessage))
                    .build();
            builder.addChatsInfo(chatsInfoPart);
        }

        JvClientServerSerializeProtocolMessage_pb.ChatsLoadReply msgChatsLoadReply = builder.build();
        JvClientServerSerializeProtocolMessage_pb.General resMsg =
                JvClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setChatsLoadReply(msgChatsLoadReply)
                        .build();

        return resMsg.toByteArray();
    }

    private byte[] createCheckOnlineUserRequestMessage(JvDefinesMessages.TypeMessage type, String ip) {
        JvClientServerSerializeProtocolMessage_pb.CheckOnlineUserRequest msgCheckOnlineRequest =
                JvClientServerSerializeProtocolMessage_pb.CheckOnlineUserRequest.newBuilder()
                        .setIp(ip)
                        .build();
        JvClientServerSerializeProtocolMessage_pb.General resMsg =
                JvClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setCheckOnlineRequest(msgCheckOnlineRequest)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createCheckOnlineUserReplyMessage(JvDefinesMessages.TypeMessage type, String login) {
        JvClientServerSerializeProtocolMessage_pb.CheckOnlineUserReply msgCheckOnlineReplyBuilder =
                JvClientServerSerializeProtocolMessage_pb.CheckOnlineUserReply.newBuilder()
                        .setLogin(login)
                        .build();
        JvClientServerSerializeProtocolMessage_pb.General resMsg =
                JvClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setCheckOnlineReply(msgCheckOnlineReplyBuilder)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createLoadUsersOnlineStatusRequestMessage(JvDefinesMessages.TypeMessage type, List<String> logins) {
        JvClientServerSerializeProtocolMessage_pb.LoadUsersOnlineStatusRequest.Builder builder =
                JvClientServerSerializeProtocolMessage_pb.LoadUsersOnlineStatusRequest.newBuilder();

        for (String login : logins) {
            builder.addLogins(login);
        }

        JvClientServerSerializeProtocolMessage_pb.LoadUsersOnlineStatusRequest msgLoadUsersOnlineStatusRequest = builder.build();

        JvClientServerSerializeProtocolMessage_pb.General resMsg =
                JvClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setLoadUsersOnlineStatusRequest(msgLoadUsersOnlineStatusRequest)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createLoadUsersOnlineStatusReplyMessage(JvDefinesMessages.TypeMessage type,
                                                           Map<String, JvMainChatsGlobalDefines.TypeStatusOnline> statusesUsers,
                                                           Map<String, String> lastOnlineTimeUsers) {
        Map<String, JvClientServerSerializeProtocolMessage_pb.LoadUsersOnlineStatusReply.StatusOnline> newMapStatusesUsers = new HashMap<>();

        for (String key : statusesUsers.keySet()) {
            int integerStatus =  statusesUsers.get(key).getValue();
            JvClientServerSerializeProtocolMessage_pb.LoadUsersOnlineStatusReply.StatusOnline statusMsg =
                    JvClientServerSerializeProtocolMessage_pb.LoadUsersOnlineStatusReply.StatusOnline.forNumber(integerStatus);
            newMapStatusesUsers.put(key, statusMsg);
        }

        JvClientServerSerializeProtocolMessage_pb.LoadUsersOnlineStatusReply msgLoadUsersOnlineStatusReply =
                JvClientServerSerializeProtocolMessage_pb.LoadUsersOnlineStatusReply
                        .newBuilder()
                        .putAllMapStatusOnline(newMapStatusesUsers)
                        .putAllMapLastOnlineTime(lastOnlineTimeUsers)
                        .build();

        JvClientServerSerializeProtocolMessage_pb.General resMsg =
                JvClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setLoadUsersOnlineStatusReply(msgLoadUsersOnlineStatusReply)
                        .build();

        return resMsg.toByteArray();
    }

    private byte[] createTextMessageSendUserToServerMessage(JvDefinesMessages.TypeMessage type,
                                                            String loginSender, String loginReceiver,
                                                            String uuid, String text, String timestamp) {
        JvClientServerSerializeProtocolMessage_pb.MessageInfo messageInfo =
                JvClientServerSerializeProtocolMessage_pb.MessageInfo.newBuilder()
                        .setLoginSender(loginSender)
                        .setLoginReceiver(loginReceiver)
                        .setUuid(uuid)
                        .setText(text)
                        .setTimestamp(timestamp)
                        .build();
        JvClientServerSerializeProtocolMessage_pb.TextMessageSendUserToServer msgTextMessageSendUserToServer =
                JvClientServerSerializeProtocolMessage_pb.TextMessageSendUserToServer.newBuilder()
                        .setMessageInfo(messageInfo)
                        .build();
        JvClientServerSerializeProtocolMessage_pb.General resMsg =
                JvClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setTextMessageSendUserToServer(msgTextMessageSendUserToServer)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createTextMessageSendUserToServerVerificationMessage(JvDefinesMessages.TypeMessage type, boolean reply) {
        JvClientServerSerializeProtocolMessage_pb.TextMessageSendUserToServerVerification msgVerify =
                JvClientServerSerializeProtocolMessage_pb.TextMessageSendUserToServerVerification.newBuilder()
                        .setReply(reply)
                        .build();
        JvClientServerSerializeProtocolMessage_pb.General resMsg =
                JvClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setTextMessageSendUserToServerVerification(msgVerify)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createTextMessageChangingStatusFromServerMessage(JvDefinesMessages.TypeMessage type,
                                                                    Map<UUID, JvMainChatsGlobalDefines.TypeStatusMessage> mapStatusMessages) {
        Map<String, JvClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromServer.StatusMessage> resultMap =
                new HashMap<>();
        for (UUID uuid : mapStatusMessages.keySet()) {
            int statusInt = mapStatusMessages.get(uuid).getValue();
            JvClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromServer.StatusMessage statusMsg =
                    JvClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromServer.StatusMessage.forNumber(statusInt);
            resultMap.put(uuid.toString(), statusMsg);
        }

        JvClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromServer msgTextMessagesChangingStatusFromServer =
                JvClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromServer.newBuilder()
                        .putAllMapStatusMessages(resultMap)
                        .build();
        JvClientServerSerializeProtocolMessage_pb.General resMsg =
                JvClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setTextMessagesChangingStatusFromServer(msgTextMessagesChangingStatusFromServer)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createTextMessagesChangingStatusFromServerVerificationMessage(JvDefinesMessages.TypeMessage type, boolean reply) {
        JvClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromServerVerification msgVerify =
                JvClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromServerVerification.newBuilder()
                        .setReply(reply)
                        .build();
        JvClientServerSerializeProtocolMessage_pb.General resMsg =
                JvClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setTextMessagesChangingStatusFromServerVerification(msgVerify)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createTextMessageChangingStatusFromUserMessage(JvDefinesMessages.TypeMessage type,
                                                                    Map<UUID, JvMainChatsGlobalDefines.TypeStatusMessage> mapStatusMessages) {
        Map<String, JvClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromUser.StatusMessage> resultMap =
                new HashMap<>();
        for (UUID uuid : mapStatusMessages.keySet()) {
            int statusInt = mapStatusMessages.get(uuid).getValue();
            JvClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromUser.StatusMessage statusMsg =
                    JvClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromUser.StatusMessage.forNumber(statusInt);
            resultMap.put(uuid.toString(), statusMsg);
        }

        JvClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromUser msgTextMessagesChangingStatusFromUser =
                JvClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromUser.newBuilder()
                        .putAllMapStatusMessages(resultMap)
                        .build();
        JvClientServerSerializeProtocolMessage_pb.General resMsg =
                JvClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setTextMessagesChangingStatusFromUser(msgTextMessagesChangingStatusFromUser)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createTextMessagesChangingStatusFromUserVerificationMessage(JvDefinesMessages.TypeMessage type, boolean reply) {
        JvClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromUserVerification msgVerify =
                JvClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromUserVerification.newBuilder()
                        .setReply(reply)
                        .build();
        JvClientServerSerializeProtocolMessage_pb.General resMsg =
                JvClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setTextMessagesChangingStatusFromUserVerification(msgVerify)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createTextMessageRedirectServerToUserMessage(JvDefinesMessages.TypeMessage type,
                                                            String loginSender, String loginReceiver,
                                                            String uuid, String text, String timestamp) {
        JvClientServerSerializeProtocolMessage_pb.MessageInfo messageInfo =
                JvClientServerSerializeProtocolMessage_pb.MessageInfo.newBuilder()
                        .setLoginSender(loginSender)
                        .setLoginReceiver(loginReceiver)
                        .setUuid(uuid)
                        .setText(text)
                        .setTimestamp(timestamp)
                        .build();
        JvClientServerSerializeProtocolMessage_pb.TextMessageSendUserToServer msgTextMessageSendUserToServer =
                JvClientServerSerializeProtocolMessage_pb.TextMessageSendUserToServer.newBuilder()
                        .setMessageInfo(messageInfo)
                        .build();
        JvClientServerSerializeProtocolMessage_pb.General resMsg =
                JvClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setTextMessageSendUserToServer(msgTextMessageSendUserToServer)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createTextMessageRedirectServerToUserVerificationMessage(JvDefinesMessages.TypeMessage type, boolean reply) {
        JvClientServerSerializeProtocolMessage_pb.TextMessageRedirectServerToUserVerification msgVerify =
                JvClientServerSerializeProtocolMessage_pb.TextMessageRedirectServerToUserVerification.newBuilder()
                        .setReply(reply)
                        .build();
        JvClientServerSerializeProtocolMessage_pb.General resMsg =
                JvClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setTextMessageRedirectServerToUserVerification(msgVerify)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createMessagesLoadRequestMessage(JvDefinesMessages.TypeMessage type, String loginRequesting,
                                                    String loginDialog, int quantityMessages) {
        JvClientServerSerializeProtocolMessage_pb.MessagesLoadRequest loadMessagesRequest =
                JvClientServerSerializeProtocolMessage_pb.MessagesLoadRequest.newBuilder()
                        .setLoginRequesting(loginRequesting)
                        .setLoginDialog(loginDialog)
                        .setQuantityMessages(quantityMessages)
                        .build();
        JvClientServerSerializeProtocolMessage_pb.General resMsg =
                JvClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setMessagesLoadRequest(loadMessagesRequest)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createMessagesLoadReplyMessage(JvDefinesMessages.TypeMessage type,
                                               List<Map<JvDbGlobalDefines.LineKeys, String>> chatsInfo) {
        JvClientServerSerializeProtocolMessage_pb.MessagesLoadReply.Builder builder =
                JvClientServerSerializeProtocolMessage_pb.MessagesLoadReply.newBuilder();

        for (Map<JvDbGlobalDefines.LineKeys, String> map : chatsInfo) {
            Map<String, String> newMapStr = new HashMap<>();

            for (JvDbGlobalDefines.LineKeys key : map.keySet()) {
                newMapStr.put(key.getValue(), map.get(key));
            }

            JvClientServerSerializeProtocolMessage_pb.MessagesInfoMap msgInfoMap = JvClientServerSerializeProtocolMessage_pb.MessagesInfoMap
                    .newBuilder()
                    .putAllMapInfo(newMapStr)
                    .build();
            builder.addMessagesInfoMap(msgInfoMap);
        }

        JvClientServerSerializeProtocolMessage_pb.MessagesLoadReply msgMessagesLoadReply = builder.build();
        JvClientServerSerializeProtocolMessage_pb.General resMsg =
                JvClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setMessagesLoadReply(msgMessagesLoadReply)
                        .build();

        return resMsg.toByteArray();
    }
}