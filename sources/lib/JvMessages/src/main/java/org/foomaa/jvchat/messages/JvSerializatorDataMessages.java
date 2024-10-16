package org.foomaa.jvchat.messages;

import org.foomaa.jvchat.globaldefines.JvDbGlobalDefines;
import org.foomaa.jvchat.globaldefines.JvMainChatsGlobalDefines;
import org.foomaa.jvchat.tools.JvGetterTools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JvSerializatorDataMessages {
    private static JvSerializatorDataMessages instance;

    private JvSerializatorDataMessages() {}

    static JvSerializatorDataMessages getInstance() {
        if (instance == null) {
            instance = new JvSerializatorDataMessages();
        }
        return instance;
    }

    public byte[] serialiseData(JvDefinesMessages.TypeMessage type, Object... parameters) {
        switch (type) {
            case EntryRequest -> {
                if (parameters.length == 2) {
                    Object login = parameters[0];
                    Object password = parameters[1];
                    return createEntryRequestMessage(type,
                            (String) login, (String) password);
                } else {
                    return new byte[0];
                }
            }
            case EntryReply -> {
                if (parameters.length == 1) {
                    Object reply = parameters[0];
                    return createEntryReplyMessage(type, (Boolean) reply);
                } else {
                    return new byte[0];
                }
            }
            case RegistrationRequest -> {
                if (parameters.length == 3) {
                    Object login = parameters[0];
                    Object email = parameters[1];
                    Object password = parameters[2];
                    return createRegistrationRequestMessage(type,
                            (String) login, (String) email, (String) password);
                } else {
                    return new byte[0];
                }
            }
            case RegistrationReply -> {
                if (parameters.length == 2) {
                    Object reply = parameters[0];
                    Object error = parameters[1];
                    return createRegistrationReplyMessage(type, (Boolean) reply,
                            (JvDefinesMessages.TypeErrorRegistration) error);
                } else {
                    return new byte[0];
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
                } else {
                    return new byte[0];
                }
            }
            case VerifyRegistrationEmailReply -> {
                if (parameters.length == 2) {
                    Object reply = parameters[0];
                    Object error = parameters[1];
                    return createVerifyRegistrationEmailReplyMessage(type, (Boolean) reply,
                            (JvDefinesMessages.TypeErrorRegistration) error);
                } else {
                    return new byte[0];
                }
            }
            case ResetPasswordRequest -> {
                if (parameters.length == 1) {
                    Object email = parameters[0];
                    return createResetPasswordRequestMessage(type, (String) email);
                } else {
                    return new byte[0];
                }
            }
            case ResetPasswordReply -> {
                if (parameters.length == 1) {
                    Object reply = parameters[0];
                    return createResetPasswordReplyMessage(type, (Boolean) reply);
                } else {
                    return new byte[0];
                }
            }
            case VerifyFamousEmailRequest -> {
                if (parameters.length == 2) {
                    Object email = parameters[0];
                    Object password = parameters[1];
                    return createVerifyFamousEmailRequestMessage(type, (String) email, (String) password);
                } else {
                    return new byte[0];
                }
            }
            case VerifyFamousEmailReply -> {
                if (parameters.length == 1) {
                    Object reply = parameters[0];
                    return createVerifyFamousEmailReplyMessage(type, (Boolean) reply);
                } else {
                    return new byte[0];
                }
            }
            case ChangePasswordRequest -> {
                if (parameters.length == 2) {
                    Object email = parameters[0];
                    Object code = parameters[1];
                    return createChangePasswordRequestMessage(type, (String) email, (String) code);
                } else {
                    return new byte[0];
                }
            }
            case ChangePasswordReply -> {
                if (parameters.length == 1) {
                    Object reply = parameters[0];
                    return createChangePasswordReplyMessage(type, (Boolean) reply);
                } else {
                    return new byte[0];
                }
            }
            case ChatsLoadRequest -> {
                if (parameters.length == 1) {
                    Object sender = parameters[0];
                    return createChatsLoadRequestMessage(type, (String) sender);
                } else {
                    return new byte[0];
                }
            }
            case ChatsLoadReply -> {
                if (parameters.length == 1) {
                    Object chatsInfoObj = parameters[0];
                    List<Map<JvDbGlobalDefines.LineKeys, String>> chatsInfo =
                            JvGetterTools.getInstance().getBeanStructTools()
                                    .objectInListMaps(chatsInfoObj, JvDbGlobalDefines.LineKeys.class, String.class);
                    return createChatsLoadReplyMessage(type, chatsInfo);
                } else {
                    return new byte[0];
                }
            }
            case CheckOnlineUserRequest -> {
                if (parameters.length == 1) {
                    Object ip = parameters[0];
                    return createCheckOnlineUserRequestMessage(type, (String) ip);
                } else {
                    return new byte[0];
                }
            }
            case CheckOnlineUserReply -> {
                if (parameters.length == 1) {
                    Object login = parameters[0];
                    return createCheckOnlineUserReplyMessage(type, (String) login);
                } else {
                    return new byte[0];
                }
            }
            case LoadUsersOnlineStatusRequest -> {
                if (parameters.length == 1) {
                    Object loginsObject = parameters[0];
                    List<String> loginsList = JvGetterTools.getInstance()
                            .getBeanStructTools().checkedCastList(loginsObject, String.class);
                    return createLoadUsersOnlineStatusRequestMessage(type, loginsList);
                } else {
                    return new byte[0];
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
                } else {
                    return new byte[0];
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
                } else {
                    return new byte[0];
                }
            }
            case TextMessageChangingStatusFromServer -> {
                if (parameters.length == 4) {
                    Object loginSender = parameters[0];
                    Object loginReceiver = parameters[1];
                    Object uuid = parameters[2];
                    Object status = parameters[3];
                    JvMainChatsGlobalDefines.TypeStatusMessage statusMsg =
                            JvMainChatsGlobalDefines.TypeStatusMessage.getTypeStatusMessage((Integer) status);
                    return createTextMessageChangingStatusFromServerMessage(type, (String) loginSender,
                            (String) loginReceiver, (String) uuid,
                            statusMsg);
                } else {
                    return new byte[0];
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

    private byte[] createEntryReplyMessage(JvDefinesMessages.TypeMessage type, boolean reply) {
        JvClientServerSerializeProtocolMessage_pb.EntryReply msgEntryReply =
                JvClientServerSerializeProtocolMessage_pb.EntryReply.newBuilder()
                .setReply(reply)
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

    private byte[] createChatsLoadRequestMessage(JvDefinesMessages.TypeMessage type, String login) {
        JvClientServerSerializeProtocolMessage_pb.ChatsLoadRequest msgChatsLoadRequest =
                JvClientServerSerializeProtocolMessage_pb.ChatsLoadRequest.newBuilder()
                        .setSender(login)
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
            Map<String, String> newMapStr = new HashMap<>();

            for (JvDbGlobalDefines.LineKeys key : map.keySet()) {
                newMapStr.put(key.getValue(), map.get(key));
            }

            JvClientServerSerializeProtocolMessage_pb.ChatsInfoMap chatsInfoMap = JvClientServerSerializeProtocolMessage_pb.ChatsInfoMap
                    .newBuilder()
                    .putAllMapInfo(newMapStr)
                    .build();
            builder.addChatsInfoMap(chatsInfoMap);
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
        JvClientServerSerializeProtocolMessage_pb.TextMessageSendUserToServer msgTextMessageSendUserToServer =
                JvClientServerSerializeProtocolMessage_pb.TextMessageSendUserToServer.newBuilder()
                        .setLoginSender(loginSender)
                        .setLoginReceiver(loginReceiver)
                        .setUuid(uuid)
                        .setText(text)
                        .setTimestamp(timestamp)
                        .build();
        JvClientServerSerializeProtocolMessage_pb.General resMsg =
                JvClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setTextMessageSendUserToServer(msgTextMessageSendUserToServer)
                        .build();
        return resMsg.toByteArray();
    }

    private byte[] createTextMessageChangingStatusFromServerMessage(JvDefinesMessages.TypeMessage type,
                                                            String loginSender, String loginReceiver, String uuid,
                                                           JvMainChatsGlobalDefines.TypeStatusMessage status) {
        JvClientServerSerializeProtocolMessage_pb.TextMessageChangingStatusFromServer msgTextMessageChangingStatusFromServer =
                JvClientServerSerializeProtocolMessage_pb.TextMessageChangingStatusFromServer.newBuilder()
                        .setLoginSender(loginSender)
                        .setLoginReceiver(loginReceiver)
                        .setUuid(uuid)
                        .setStatus(status.getValue())
                        .build();
        JvClientServerSerializeProtocolMessage_pb.General resMsg =
                JvClientServerSerializeProtocolMessage_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setTextMessageChangingStatusFromServer(msgTextMessageChangingStatusFromServer)
                        .build();
        return resMsg.toByteArray();
    }


}