package org.foomaa.jvchat.messages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import org.foomaa.jvchat.globaldefines.DbGlobalDefines;
import org.foomaa.jvchat.globaldefines.MainChatsGlobalDefines;
import org.foomaa.jvchat.tools.StructTools;

@Component
public class SerializatorDataMessages {
    // DI ↓
    private final StructTools structTools;

    SerializatorDataMessages(StructTools structTools) {
        this.structTools = structTools;
    }

    public byte[] serialiseData(DefinesMessages.TypeMessage type, Object... parameters) {
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
                            (DefinesMessages.TypeErrorRegistration) error);
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
                            (DefinesMessages.TypeErrorRegistration) error);
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
                    List<Map<DbGlobalDefines.LineKeys, String>> chatsInfo =
                            structTools.objectInListMaps(chatsInfoObj, DbGlobalDefines.LineKeys.class, String.class);
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
                    Object uuidUser = parameters[0];
                    return createCheckOnlineUserReplyMessage(type, (UUID) uuidUser);
                }
            }
            case LoadUsersOnlineStatusRequest -> {
                if (parameters.length == 1) {
                    Object uuidsObject = parameters[0];
                    List<UUID> uuidsUsers = structTools.checkedCastList(uuidsObject, UUID.class);
                    return createLoadUsersOnlineStatusRequestMessage(type, uuidsUsers);
                }
            }
            case LoadUsersOnlineStatusReply -> {
                if (parameters.length == 2) {
                    Object statusesUsersObj = parameters[0];
                    Object lastOnlineTimeUsersObj = parameters[1];
                    Map<UUID, MainChatsGlobalDefines.TypeStatusOnline> statusesUsersMap =
                            structTools.objectInMap(statusesUsersObj, UUID.class, MainChatsGlobalDefines.TypeStatusOnline.class);
                    Map<UUID, String> lastOnlineTimeUsers =
                            structTools.objectInMap(lastOnlineTimeUsersObj, UUID.class, String.class);
                    return createLoadUsersOnlineStatusReplyMessage(type, statusesUsersMap, lastOnlineTimeUsers);
                }
            }
            case TextMessageSendUserToServer -> {
                if (parameters.length == 5) {
                    Object uuidUserSender = parameters[0];
                    Object uuidUserReceiver = parameters[1];
                    Object uuidMessage = parameters[2];
                    Object text = parameters[3];
                    Object timestamp = parameters[4];
                    return createTextMessageSendUserToServerMessage(type, (UUID) uuidUserSender,
                            (UUID) uuidUserReceiver, (UUID) uuidMessage, (String) text, (String) timestamp);
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
                    Map<UUID, MainChatsGlobalDefines.TypeStatusMessage> mapStatusesMessages =
                            structTools.objectInMap(mapUuidStatus, UUID.class, MainChatsGlobalDefines.TypeStatusMessage.class);
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
                    Map<UUID, MainChatsGlobalDefines.TypeStatusMessage> mapStatusesMessages =
                            structTools.objectInMap(mapUuidStatus, UUID.class, MainChatsGlobalDefines.TypeStatusMessage.class);
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
                    Object uuidUserSender = parameters[0];
                    Object uuidUserReceiver = parameters[1];
                    Object uuidMessage = parameters[2];
                    Object text = parameters[3];
                    Object timestamp = parameters[4];
                    return createTextMessageRedirectServerToUserMessage(type, (UUID) uuidUserSender,
                            (UUID) uuidUserReceiver, (UUID) uuidMessage, (String) text, (String) timestamp);
                }
            }
            case TextMessageRedirectServerToUserVerification -> {
                if (parameters.length == 1) {
                    Object reply = parameters[0];
                    return createTextMessageRedirectServerToUserVerificationMessage(type, (Boolean) reply);
                }
            }
            case MessagesLoadRequest -> {
                if (parameters.length == 2) {
                    Object uuidChat = parameters[0];
                    Object quantityMessages = parameters[1];
                    return createMessagesLoadRequestMessage(type, (UUID) uuidChat, (Integer) quantityMessages);
                }
            }
            case MessagesLoadReply -> {
                if (parameters.length == 1) {
                    Object messagesInfoObj = parameters[0];
                    List<Map<DbGlobalDefines.LineKeys, String>> messagesInfo =
                            structTools.objectInListMaps(messagesInfoObj, DbGlobalDefines.LineKeys.class, String.class);
                    return createMessagesLoadReplyMessage(type, messagesInfo);
                }
            }
        }
        return new byte[0];
    }

    private byte[] createEntryRequestMessage(DefinesMessages.TypeMessage type, String login, String password) {
        ClientServerSerializeProtocolMessage_pb.EntryRequest msgEntryRequest =
                ClientServerSerializeProtocolMessage_pb.EntryRequest.newBuilder()
                        .setLogin(login)
                        .setPassword(password)
                        .build();
        ClientServerSerializeProtocolMessage_pb.General resMsg =
                ClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setEntryRequest(msgEntryRequest)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createEntryReplyMessage(DefinesMessages.TypeMessage type, boolean reply, UUID uuidUser) {
        ClientServerSerializeProtocolMessage_pb.EntryReply msgEntryReply =
                ClientServerSerializeProtocolMessage_pb.EntryReply.newBuilder()
                        .setReply(reply)
                        .setUuidUser(uuidUser.toString())
                        .build();
        ClientServerSerializeProtocolMessage_pb.General resMsg =
                ClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setEntryReply(msgEntryReply)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createRegistrationRequestMessage(DefinesMessages.TypeMessage type, String login, String email, String password) {
        ClientServerSerializeProtocolMessage_pb.RegistrationRequest msgRegRequest =
                ClientServerSerializeProtocolMessage_pb.RegistrationRequest.newBuilder()
                        .setLogin(login)
                        .setEmail(email)
                        .setPassword(password)
                        .build();
        ClientServerSerializeProtocolMessage_pb.General resMsg =
                ClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setRegistrationRequest(msgRegRequest)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createRegistrationReplyMessage(DefinesMessages.TypeMessage type, boolean reply, DefinesMessages.TypeErrorRegistration error) {
        ClientServerSerializeProtocolMessage_pb.RegistrationReply msgRegReply =
                ClientServerSerializeProtocolMessage_pb.RegistrationReply.newBuilder()
                        .setReply(reply)
                        .setError(ClientServerSerializeProtocolMessage_pb.RegistrationReply.Error.forNumber(error.getValue()))
                        .build();
        ClientServerSerializeProtocolMessage_pb.General resMsg =
                ClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setRegistrationReply(msgRegReply)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createVerifyRegistrationEmailRequestMessage(DefinesMessages.TypeMessage type, String login, String email, String password, String code) {
        ClientServerSerializeProtocolMessage_pb.VerifyRegistrationEmailRequest msgVerifyRegRequest =
                ClientServerSerializeProtocolMessage_pb.VerifyRegistrationEmailRequest.newBuilder()
                        .setLogin(login)
                        .setEmail(email)
                        .setPassword(password)
                        .setCode(code)
                        .build();
        ClientServerSerializeProtocolMessage_pb.General resMsg =
                ClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setVerifyRegistrationEmailRequest(msgVerifyRegRequest)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createVerifyRegistrationEmailReplyMessage(DefinesMessages.TypeMessage type, boolean reply, DefinesMessages.TypeErrorRegistration error) {
        ClientServerSerializeProtocolMessage_pb.VerifyRegistrationEmailReply msgVerifyRegReply =
                ClientServerSerializeProtocolMessage_pb.VerifyRegistrationEmailReply.newBuilder()
                        .setReply(reply)
                        .setError(ClientServerSerializeProtocolMessage_pb.VerifyRegistrationEmailReply.Error.forNumber(error.getValue()))
                        .build();
        ClientServerSerializeProtocolMessage_pb.General resMsg =
                ClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setVerifyRegistrationEmailReply(msgVerifyRegReply)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createResetPasswordRequestMessage(DefinesMessages.TypeMessage type, String email) {
        ClientServerSerializeProtocolMessage_pb.ResetPasswordRequest msgResetRequest =
                ClientServerSerializeProtocolMessage_pb.ResetPasswordRequest.newBuilder()
                        .setEmail(email)
                        .build();
        ClientServerSerializeProtocolMessage_pb.General resMsg =
                ClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setResetPasswordRequest(msgResetRequest)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createResetPasswordReplyMessage(DefinesMessages.TypeMessage type, boolean reply) {
        ClientServerSerializeProtocolMessage_pb.ResetPasswordReply msgResetReply =
                ClientServerSerializeProtocolMessage_pb.ResetPasswordReply.newBuilder()
                        .setReply(reply)
                        .build();
        ClientServerSerializeProtocolMessage_pb.General resMsg =
                ClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setResetPasswordReply(msgResetReply)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createVerifyFamousEmailRequestMessage(DefinesMessages.TypeMessage type, String email, String code) {
        ClientServerSerializeProtocolMessage_pb.VerifyFamousEmailRequest msgVerifyEmailRequest =
                ClientServerSerializeProtocolMessage_pb.VerifyFamousEmailRequest.newBuilder()
                        .setEmail(email)
                        .setCode(code)
                        .build();
        ClientServerSerializeProtocolMessage_pb.General resMsg =
                ClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setVerifyFamousEmailRequest(msgVerifyEmailRequest)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createVerifyFamousEmailReplyMessage(DefinesMessages.TypeMessage type, boolean reply) {
        ClientServerSerializeProtocolMessage_pb.VerifyFamousEmailReply msgVerifyEmailReply =
                ClientServerSerializeProtocolMessage_pb.VerifyFamousEmailReply.newBuilder()
                        .setReply(reply)
                        .build();
        ClientServerSerializeProtocolMessage_pb.General resMsg =
                ClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setVerifyFamousEmailReply(msgVerifyEmailReply)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createChangePasswordRequestMessage(DefinesMessages.TypeMessage type, String email, String password) {
        ClientServerSerializeProtocolMessage_pb.ChangePasswordRequest msgChangePasswordRequest =
                ClientServerSerializeProtocolMessage_pb.ChangePasswordRequest.newBuilder()
                        .setEmail(email)
                        .setPassword(password)
                        .build();
        ClientServerSerializeProtocolMessage_pb.General resMsg =
                ClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setChangePasswordRequest(msgChangePasswordRequest)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createChangePasswordReplyMessage(DefinesMessages.TypeMessage type, boolean reply) {
        ClientServerSerializeProtocolMessage_pb.ChangePasswordReply msgChangePasswordReply =
                ClientServerSerializeProtocolMessage_pb.ChangePasswordReply.newBuilder()
                        .setReply(reply)
                        .build();
        ClientServerSerializeProtocolMessage_pb.General resMsg =
                ClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setChangePasswordReply(msgChangePasswordReply)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createChatsLoadRequestMessage(DefinesMessages.TypeMessage type, UUID uuidUser) {
        ClientServerSerializeProtocolMessage_pb.ChatsLoadRequest msgChatsLoadRequest =
                ClientServerSerializeProtocolMessage_pb.ChatsLoadRequest.newBuilder()
                        .setUuidUser(uuidUser.toString())
                        .build();
        ClientServerSerializeProtocolMessage_pb.General resMsg =
                ClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setChatsLoadRequest(msgChatsLoadRequest)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createChatsLoadReplyMessage(DefinesMessages.TypeMessage type,
                                               List<Map<DbGlobalDefines.LineKeys, String>> chatsInfo) {
        ClientServerSerializeProtocolMessage_pb.ChatsLoadReply.Builder builder =
                ClientServerSerializeProtocolMessage_pb.ChatsLoadReply.newBuilder();

        for (Map<DbGlobalDefines.LineKeys, String> map : chatsInfo) {
            ClientServerSerializeProtocolMessage_pb.ChatsInfo chatsInfoPart = ClientServerSerializeProtocolMessage_pb.ChatsInfo
                    .newBuilder()
                    .setLogin(map.get(DbGlobalDefines.LineKeys.Login))
                    .setLastMessageText(map.get(DbGlobalDefines.LineKeys.TextMessage))
                    .setUuidChat(map.get(DbGlobalDefines.LineKeys.UuidChat))
                    .setUuidUser(map.get(DbGlobalDefines.LineKeys.UuidUser))
                    .setUuidMessage(map.get(DbGlobalDefines.LineKeys.UuidMessage))
                    .setIsLoginSentLastMessage(Boolean.parseBoolean(map.get(DbGlobalDefines.LineKeys.IsLoginSentLastMessage)))
                    .setStatusMessage(ClientServerSerializeProtocolMessage_pb.ChatsInfo.TypeStatusMessage
                            .forNumber(Integer.parseInt(map.get(DbGlobalDefines.LineKeys.StatusMessage))))
                    .setDateTimeLastMessage(map.get(DbGlobalDefines.LineKeys.DateTimeMessage))
                    .build();
            builder.addChatsInfo(chatsInfoPart);
        }

        ClientServerSerializeProtocolMessage_pb.ChatsLoadReply msgChatsLoadReply = builder.build();
        ClientServerSerializeProtocolMessage_pb.General resMsg =
                ClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setChatsLoadReply(msgChatsLoadReply)
                        .build();

        return resMsg.toByteArray();
    }

    private byte[] createCheckOnlineUserRequestMessage(DefinesMessages.TypeMessage type, String ip) {
        ClientServerSerializeProtocolMessage_pb.CheckOnlineUserRequest msgCheckOnlineRequest =
                ClientServerSerializeProtocolMessage_pb.CheckOnlineUserRequest.newBuilder()
                        .setIp(ip)
                        .build();
        ClientServerSerializeProtocolMessage_pb.General resMsg =
                ClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setCheckOnlineRequest(msgCheckOnlineRequest)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createCheckOnlineUserReplyMessage(DefinesMessages.TypeMessage type, UUID uuidUser) {
        ClientServerSerializeProtocolMessage_pb.CheckOnlineUserReply msgCheckOnlineReplyBuilder =
                ClientServerSerializeProtocolMessage_pb.CheckOnlineUserReply.newBuilder()
                        .setUuidUser(uuidUser.toString())
                        .build();
        ClientServerSerializeProtocolMessage_pb.General resMsg =
                ClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setCheckOnlineReply(msgCheckOnlineReplyBuilder)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createLoadUsersOnlineStatusRequestMessage(DefinesMessages.TypeMessage type, List<UUID> uuidsUsers) {
        ClientServerSerializeProtocolMessage_pb.LoadUsersOnlineStatusRequest.Builder builder =
                ClientServerSerializeProtocolMessage_pb.LoadUsersOnlineStatusRequest.newBuilder();

        for (UUID uuidUser : uuidsUsers) {
            builder.addUuidsUsers(uuidUser.toString());
        }

        ClientServerSerializeProtocolMessage_pb.LoadUsersOnlineStatusRequest msgLoadUsersOnlineStatusRequest = builder.build();

        ClientServerSerializeProtocolMessage_pb.General resMsg =
                ClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setLoadUsersOnlineStatusRequest(msgLoadUsersOnlineStatusRequest)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createLoadUsersOnlineStatusReplyMessage(DefinesMessages.TypeMessage type,
                                                           Map<UUID, MainChatsGlobalDefines.TypeStatusOnline> statusesUsers,
                                                           Map<UUID, String> lastOnlineTimeUsers) {
        Map<String, ClientServerSerializeProtocolMessage_pb.LoadUsersOnlineStatusReply.StatusOnline> newMapStatusesUsers = new HashMap<>();

        for (UUID key : statusesUsers.keySet()) {
            int integerStatus = statusesUsers.get(key).getValue();
            ClientServerSerializeProtocolMessage_pb.LoadUsersOnlineStatusReply.StatusOnline statusMsg =
                    ClientServerSerializeProtocolMessage_pb.LoadUsersOnlineStatusReply.StatusOnline.forNumber(integerStatus);
            newMapStatusesUsers.put(key.toString(), statusMsg);
        }

        Map<String, String> newMapLastOnlineTimes = new HashMap<>();

        for (UUID key : lastOnlineTimeUsers.keySet()) {
            newMapLastOnlineTimes.put(key.toString(), lastOnlineTimeUsers.get(key));
        }

        ClientServerSerializeProtocolMessage_pb.LoadUsersOnlineStatusReply msgLoadUsersOnlineStatusReply =
                ClientServerSerializeProtocolMessage_pb.LoadUsersOnlineStatusReply
                        .newBuilder()
                        .putAllMapStatusOnline(newMapStatusesUsers)
                        .putAllMapLastOnlineTime(newMapLastOnlineTimes)
                        .build();

        ClientServerSerializeProtocolMessage_pb.General resMsg =
                ClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setLoadUsersOnlineStatusReply(msgLoadUsersOnlineStatusReply)
                        .build();

        return resMsg.toByteArray();
    }

    private byte[] createTextMessageSendUserToServerMessage(DefinesMessages.TypeMessage type,
                                                            UUID uuidUserSender, UUID uuidUserReceiver,
                                                            UUID uuidMessage, String text, String timestamp) {
        ClientServerSerializeProtocolMessage_pb.TextMessageInfo messageInfo =
                ClientServerSerializeProtocolMessage_pb.TextMessageInfo.newBuilder()
                        .setUuidUserSender(uuidUserSender.toString())
                        .setUuidUserReceiver(uuidUserReceiver.toString())
                        .setUuidMessage(uuidMessage.toString())
                        .setText(text)
                        .setTimestamp(timestamp)
                        .build();
        ClientServerSerializeProtocolMessage_pb.TextMessageSendUserToServer msgTextMessageSendUserToServer =
                ClientServerSerializeProtocolMessage_pb.TextMessageSendUserToServer.newBuilder()
                        .setTextMessageInfo(messageInfo)
                        .build();
        ClientServerSerializeProtocolMessage_pb.General resMsg =
                ClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setTextMessageSendUserToServer(msgTextMessageSendUserToServer)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createTextMessageSendUserToServerVerificationMessage(DefinesMessages.TypeMessage type, boolean reply) {
        ClientServerSerializeProtocolMessage_pb.TextMessageSendUserToServerVerification msgVerify =
                ClientServerSerializeProtocolMessage_pb.TextMessageSendUserToServerVerification.newBuilder()
                        .setReply(reply)
                        .build();
        ClientServerSerializeProtocolMessage_pb.General resMsg =
                ClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setTextMessageSendUserToServerVerification(msgVerify)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createTextMessageChangingStatusFromServerMessage(DefinesMessages.TypeMessage type,
                                                                    Map<UUID, MainChatsGlobalDefines.TypeStatusMessage> mapStatusMessages) {
        Map<String, ClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromServer.StatusMessage> resultMap =
                new HashMap<>();
        for (UUID uuid : mapStatusMessages.keySet()) {
            int statusInt = mapStatusMessages.get(uuid).getValue();
            ClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromServer.StatusMessage statusMsg =
                    ClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromServer.StatusMessage.forNumber(statusInt);
            resultMap.put(uuid.toString(), statusMsg);
        }

        ClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromServer msgTextMessagesChangingStatusFromServer =
                ClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromServer.newBuilder()
                        .putAllMapStatusMessages(resultMap)
                        .build();
        ClientServerSerializeProtocolMessage_pb.General resMsg =
                ClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setTextMessagesChangingStatusFromServer(msgTextMessagesChangingStatusFromServer)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createTextMessagesChangingStatusFromServerVerificationMessage(DefinesMessages.TypeMessage type, boolean reply) {
        ClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromServerVerification msgVerify =
                ClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromServerVerification.newBuilder()
                        .setReply(reply)
                        .build();
        ClientServerSerializeProtocolMessage_pb.General resMsg =
                ClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setTextMessagesChangingStatusFromServerVerification(msgVerify)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createTextMessageChangingStatusFromUserMessage(DefinesMessages.TypeMessage type,
                                                                  Map<UUID, MainChatsGlobalDefines.TypeStatusMessage> mapStatusMessages) {
        Map<String, ClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromUser.StatusMessage> resultMap =
                new HashMap<>();
        for (UUID uuid : mapStatusMessages.keySet()) {
            int statusInt = mapStatusMessages.get(uuid).getValue();
            ClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromUser.StatusMessage statusMsg =
                    ClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromUser.StatusMessage.forNumber(statusInt);
            resultMap.put(uuid.toString(), statusMsg);
        }

        ClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromUser msgTextMessagesChangingStatusFromUser =
                ClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromUser.newBuilder()
                        .putAllMapStatusMessages(resultMap)
                        .build();
        ClientServerSerializeProtocolMessage_pb.General resMsg =
                ClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setTextMessagesChangingStatusFromUser(msgTextMessagesChangingStatusFromUser)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createTextMessagesChangingStatusFromUserVerificationMessage(DefinesMessages.TypeMessage type, boolean reply) {
        ClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromUserVerification msgVerify =
                ClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromUserVerification.newBuilder()
                        .setReply(reply)
                        .build();
        ClientServerSerializeProtocolMessage_pb.General resMsg =
                ClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setTextMessagesChangingStatusFromUserVerification(msgVerify)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createTextMessageRedirectServerToUserMessage(DefinesMessages.TypeMessage type,
                                                                UUID uuidUserSender, UUID uuidUserReceiver,
                                                                UUID uuidMessage, String text, String timestamp) {
        ClientServerSerializeProtocolMessage_pb.TextMessageInfo messageInfo =
                ClientServerSerializeProtocolMessage_pb.TextMessageInfo.newBuilder()
                        .setUuidUserSender(uuidUserSender.toString())
                        .setUuidUserReceiver(uuidUserReceiver.toString())
                        .setUuidMessage(uuidMessage.toString())
                        .setText(text)
                        .setTimestamp(timestamp)
                        .build();
        ClientServerSerializeProtocolMessage_pb.TextMessageSendUserToServer msgTextMessageSendUserToServer =
                ClientServerSerializeProtocolMessage_pb.TextMessageSendUserToServer.newBuilder()
                        .setTextMessageInfo(messageInfo)
                        .build();
        ClientServerSerializeProtocolMessage_pb.General resMsg =
                ClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setTextMessageSendUserToServer(msgTextMessageSendUserToServer)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createTextMessageRedirectServerToUserVerificationMessage(DefinesMessages.TypeMessage type, boolean reply) {
        ClientServerSerializeProtocolMessage_pb.TextMessageRedirectServerToUserVerification msgVerify =
                ClientServerSerializeProtocolMessage_pb.TextMessageRedirectServerToUserVerification.newBuilder()
                        .setReply(reply)
                        .build();
        ClientServerSerializeProtocolMessage_pb.General resMsg =
                ClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setTextMessageRedirectServerToUserVerification(msgVerify)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createMessagesLoadRequestMessage(DefinesMessages.TypeMessage type,
                                                    UUID uuidChat, int quantityMessages) {
        ClientServerSerializeProtocolMessage_pb.MessagesLoadRequest loadMessagesRequest =
                ClientServerSerializeProtocolMessage_pb.MessagesLoadRequest.newBuilder()
                        .setUuidChat(uuidChat.toString())
                        .setQuantityMessages(quantityMessages)
                        .build();
        ClientServerSerializeProtocolMessage_pb.General resMsg =
                ClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setMessagesLoadRequest(loadMessagesRequest)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createMessagesLoadReplyMessage(DefinesMessages.TypeMessage type,
                                                  List<Map<DbGlobalDefines.LineKeys, String>> msgInfo) {
        ClientServerSerializeProtocolMessage_pb.MessagesLoadReply.Builder builder =
                ClientServerSerializeProtocolMessage_pb.MessagesLoadReply.newBuilder();

        for (Map<DbGlobalDefines.LineKeys, String> map : msgInfo) {
            ClientServerSerializeProtocolMessage_pb.TextMessageInfo msgInfoMap = ClientServerSerializeProtocolMessage_pb.TextMessageInfo
                    .newBuilder()
                    .setUuidUserSender(map.get(DbGlobalDefines.LineKeys.UuidSender))
                    .setUuidUserReceiver(map.get(DbGlobalDefines.LineKeys.UuidReceiver))
                    .setUuidMessage(map.get(DbGlobalDefines.LineKeys.UuidMessage))
                    .setStatusMessage(Integer.parseInt(map.get(DbGlobalDefines.LineKeys.StatusMessage)))
                    .setText(map.get(DbGlobalDefines.LineKeys.TextMessage))
                    .setTimestamp(map.get(DbGlobalDefines.LineKeys.DateTimeMessage))
                    .build();
            builder.addTextMessageInfo(msgInfoMap);
        }

        ClientServerSerializeProtocolMessage_pb.MessagesLoadReply msgMessagesLoadReply = builder.build();
        ClientServerSerializeProtocolMessage_pb.General resMsg =
                ClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setMessagesLoadReply(msgMessagesLoadReply)
                        .build();

        return resMsg.toByteArray();
    }
}