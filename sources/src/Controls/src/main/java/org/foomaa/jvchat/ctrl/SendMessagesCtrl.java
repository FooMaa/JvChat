package org.foomaa.jvchat.ctrl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.foomaa.jvchat.globaldefines.DbGlobalDefines;
import org.foomaa.jvchat.globaldefines.MainChatsGlobalDefines;
import org.foomaa.jvchat.messages.DefinesMessages;
import org.foomaa.jvchat.messages.SerializatorDataMessages;
import org.foomaa.jvchat.settings.ServersInfoSettings;
import org.foomaa.jvchat.tools.StructTools;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class SendMessagesCtrl {
    private final SerializatorDataMessages serializatorDataMessages;
    private final MessagesDefinesCtrl messagesDefinesCtrl;
    private final ObjectProvider<ServersInfoSettings> serversInfoSettingsObjectProvider;
    private final StructTools structTools;
    private final NetworkCtrl networkCtrl;

    SendMessagesCtrl(SerializatorDataMessages serializatorDataMessages,
                     MessagesDefinesCtrl messagesDefinesCtrl,
                     ObjectProvider<ServersInfoSettings> serversInfoSettingsObjectProvider,
                     StructTools structTools,
                     @Lazy NetworkCtrl networkCtrl) {
        this.serializatorDataMessages = serializatorDataMessages;
        this.messagesDefinesCtrl = messagesDefinesCtrl;
        this.serversInfoSettingsObjectProvider = serversInfoSettingsObjectProvider;
        this.structTools = structTools;
        this.networkCtrl = networkCtrl;
    }

    public final void sendMessage(DefinesMessages.TypeMessage type, Object... parameters) {
        switch (type) {
            case EntryRequest -> {
                if (parameters.length == 2) {
                    Object login = parameters[0];
                    Object password = parameters[1];
                    byte[] bodyMessage = createBodyEntryRequestMessage(type,
                            (String) login,
                            (String) password);
                    sendReadyMessageNetwork(bodyMessage);
                    messagesDefinesCtrl.setEntryRequestFlag(MessagesDefinesCtrl.TypeFlags.DEFAULT);
                }
            }
            case EntryReply -> {
                if (parameters.length == 2) {
                    Object reply = parameters[0];
                    Object uuidUser = parameters[1];
                    byte[] bodyMessage = createBodyEntryReplyMessage(type,
                            (Boolean) reply, (UUID) uuidUser);
                    sendReadyMessageNetwork(bodyMessage);
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
                    messagesDefinesCtrl.setRegistrationRequestFlag(MessagesDefinesCtrl.TypeFlags.DEFAULT);
                }
            }
            case RegistrationReply -> {
                if (parameters.length == 2) {
                    Object reply = parameters[0];
                    Object error = parameters[1];
                    byte[] bodyMessage = createBodyRegistrationReplyMessage(type,
                            (Boolean) reply, (DefinesMessages.TypeErrorRegistration) error);
                    sendReadyMessageNetwork(bodyMessage);
                }
            }
            case VerifyRegistrationEmailRequest -> {
                if (parameters.length == 4) {
                    Object login = parameters[0];
                    Object email = parameters[1];
                    Object password = parameters[2];
                    Object code = parameters[3];
                    byte[] bodyMessage = createBodyVerifyRegistrationEmailRequestMessage(type,
                            (String) login,
                            (String) email,
                            (String) password,
                            (String) code);
                    sendReadyMessageNetwork(bodyMessage);
                    messagesDefinesCtrl.setVerifyRegistrationEmailRequestFlag(MessagesDefinesCtrl.TypeFlags.DEFAULT);
                }
            }
            case VerifyRegistrationEmailReply -> {
                if (parameters.length == 2) {
                    Object reply = parameters[0];
                    Object error = parameters[1];
                    byte[] bodyMessage = createBodyVerifyRegistrationEmailReplyMessage(type,
                            (Boolean) reply, (DefinesMessages.TypeErrorRegistration) error);
                    sendReadyMessageNetwork(bodyMessage);
                }
            }
            case ResetPasswordRequest -> {
                if (parameters.length == 1) {
                    Object email = parameters[0];
                    byte[] bodyMessage = createBodyResetPasswordRequestMessage(type,
                            (String) email);
                    sendReadyMessageNetwork(bodyMessage);
                    messagesDefinesCtrl.setResetPasswordRequestFlag(MessagesDefinesCtrl.TypeFlags.DEFAULT);
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
            case VerifyFamousEmailRequest -> {
                if (parameters.length == 2) {
                    Object email = parameters[0];
                    Object code = parameters[1];
                    byte[] bodyMessage = createBodyVerifyFamousEmailRequestMessage(type,
                            (String) email, (String) code);
                    sendReadyMessageNetwork(bodyMessage);
                    messagesDefinesCtrl.setVerifyFamousEmailRequestFlag(MessagesDefinesCtrl.TypeFlags.DEFAULT);
                }
            }
            case VerifyFamousEmailReply -> {
                if (parameters.length == 1) {
                    Object reply = parameters[0];
                    byte[] bodyMessage = createBodyVerifyFamousEmailReplyMessage(type,
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
                    messagesDefinesCtrl.setChangePasswordRequest(MessagesDefinesCtrl.TypeFlags.DEFAULT);
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
            case ChatsLoadRequest -> {
                if (parameters.length == 1) {
                    Object uuidUser = parameters[0];
                    byte[] bodyMessage = createBodyChatsLoadRequestMessage(type,
                            (UUID) uuidUser);
                    sendReadyMessageNetwork(bodyMessage);
                    messagesDefinesCtrl.setChatsLoadReplyFlag(MessagesDefinesCtrl.TypeFlags.DEFAULT);
                }
            }
            case ChatsLoadReply -> {
                if (parameters.length == 1) {
                    Object chatsInfoObj = parameters[0];
                    List<Map<DbGlobalDefines.LineKeys, String>> chatsInfo =
                            structTools.objectInListMaps(chatsInfoObj, DbGlobalDefines.LineKeys.class, String.class);
                    byte[] bodyMessageChatsLoadReply = createBodyChatsLoadReplyMessage(type, chatsInfo);
                    sendReadyMessageNetwork(bodyMessageChatsLoadReply);

                    ServersInfoSettings serversInfoSettings = serversInfoSettingsObjectProvider.getIfAvailable();
                    if (serversInfoSettings == null) {
                        log.error("serversInfoSettings is null");
                        return;
                    }

                    sendMessage(DefinesMessages.TypeMessage.CheckOnlineUserRequest, serversInfoSettings.getIp());
                }
            }
            case CheckOnlineUserRequest -> {
                if (parameters.length == 2) {
                    Object ip = parameters[0];
                    Object runnableCtrl = parameters[1];
                    byte[] bodyMessage = createBodyCheckOnlineUserRequestMessage(type, (String) ip);
                    sendReadyMessageNetwork(bodyMessage, (Runnable) runnableCtrl);
                }
            }
            case CheckOnlineUserReply -> {
                if (parameters.length == 1) {
                    Object uuidUser = parameters[0];
                    byte[] bodyMessage = createBodyCheckOnlineUserReplyMessage(type, (UUID) uuidUser);
                    sendReadyMessageNetwork(bodyMessage);
                }
            }
            case LoadUsersOnlineStatusRequest -> {
                if (parameters.length == 1) {
                    Object uuidsObject = parameters[0];
                    List<UUID> uuidsList = structTools.checkedCastList(uuidsObject, UUID.class);
                    byte[] bodyMessage = createBodyLoadUsersOnlineStatusRequestMessage(type, uuidsList);
                    sendReadyMessageNetwork(bodyMessage);
                    messagesDefinesCtrl.setLoadUsersOnlineReplyFlag(MessagesDefinesCtrl.TypeFlags.DEFAULT);
                }
            }
            case LoadUsersOnlineStatusReply -> {
                if (parameters.length == 2) {
                    Object statusesUsersObj = parameters[0];
                    Object lastOnlineTimeUsersObj = parameters[1];
                    Map<UUID, MainChatsGlobalDefines.TypeStatusOnline> statusesUsersMap =
                            structTools.objectInMap(statusesUsersObj, UUID.class, MainChatsGlobalDefines.TypeStatusOnline.class);
                    Map<UUID, String> lastOnlineTimeUsers = structTools.objectInMap(lastOnlineTimeUsersObj, UUID.class, String.class);
                    byte[] bodyMessage = createBodyLoadUsersOnlineStatusReplyMessage(type, statusesUsersMap, lastOnlineTimeUsers);
                    sendReadyMessageNetwork(bodyMessage);
                }
            }
            case TextMessageSendUserToServer -> {
                if (parameters.length == 5) {
                    Object uuidUserSender = parameters[0];
                    Object uuidUserReceiver = parameters[1];
                    Object uuidMessage = parameters[2];
                    Object text = parameters[3];
                    Object timestamp = parameters[4];
                    byte[] bodyMessage = createBodyTextMessageSendUserToServerMessage(type, (UUID) uuidUserSender,
                            (UUID) uuidUserReceiver, (UUID) uuidMessage, (String) text, (String) timestamp);
                    sendReadyMessageNetwork(bodyMessage);
                }
            }
            case TextMessageSendUserToServerVerification -> {
                if (parameters.length == 1) {
                    Object reply = parameters[0];
                    byte[] bodyMessage = createBodyTextMessageSendUserToServerVerificationMessage(type, (Boolean) reply);
                    sendReadyMessageNetwork(bodyMessage);
                    messagesDefinesCtrl.setTextMessageSendUserToServerFlag(MessagesDefinesCtrl.TypeFlags.DEFAULT);
                }
            }
            case TextMessagesChangingStatusFromServer -> {
                if (parameters.length == 1) {
                    Object mapUuidStatus = parameters[0];
                    Map<UUID, MainChatsGlobalDefines.TypeStatusMessage> mapStatusesMessages =
                            structTools.objectInMap(mapUuidStatus, UUID.class, MainChatsGlobalDefines.TypeStatusMessage.class);
                    byte[] bodyMessage = createBodyTextMessagesChangingStatusFromServerMessage(type, mapStatusesMessages);
                    sendReadyMessageNetwork(bodyMessage);
                }
            }
            case TextMessagesChangingStatusFromServerVerification -> {
                if (parameters.length == 1) {
                    Object reply = parameters[0];
                    byte[] bodyMessage = createBodyTextMessagesChangingStatusFromServerVerificationMessage(type, (Boolean) reply);
                    sendReadyMessageNetwork(bodyMessage);
                }
            }
            case TextMessagesChangingStatusFromUser -> {
                if (parameters.length == 1) {
                    Object mapUuidStatus = parameters[0];
                    Map<UUID, MainChatsGlobalDefines.TypeStatusMessage> mapStatusesMessages =
                            structTools.objectInMap(mapUuidStatus, UUID.class, MainChatsGlobalDefines.TypeStatusMessage.class);
                    byte[] bodyMessage = createBodyTextMessagesChangingStatusFromUserMessage(type, mapStatusesMessages);
                    sendReadyMessageNetwork(bodyMessage);
                }
            }
            case TextMessagesChangingStatusFromUserVerification -> {
                if (parameters.length == 1) {
                    Object reply = parameters[0];
                    byte[] bodyMessage = createBodyTextMessagesChangingStatusFromUserVerificationMessage(type, (Boolean) reply);
                    sendReadyMessageNetwork(bodyMessage);
                }
            }
            case TextMessageRedirectServerToUser -> {
                if (parameters.length == 6) {
                    Object uuidUserSender = parameters[0];
                    Object uuidUserReceiver = parameters[1];
                    Object uuidMessage = parameters[2];
                    Object text = parameters[3];
                    Object timestamp = parameters[4];
                    Object runnableCtrl = parameters[5];
                    byte[] bodyMessage = createBodyTextMessageRedirectServerToUserMessage(type, (UUID) uuidUserSender,
                            (UUID) uuidUserReceiver, (UUID) uuidMessage, (String) text, (String) timestamp);
                    sendReadyMessageNetwork(bodyMessage, (Runnable) runnableCtrl);
                }
            }
            case TextMessageRedirectServerToUserVerification -> {
                if (parameters.length == 1) {
                    Object reply = parameters[0];
                    byte[] bodyMessage = createBodyTextMessageRedirectServerToUserVerificationMessage(type, (Boolean) reply);
                    sendReadyMessageNetwork(bodyMessage);
                }
            }
            case MessagesLoadRequest -> {
                if (parameters.length == 2) {
                    Object uuidChat = parameters[0];
                    Object quantityMessages = parameters[1];
                    byte[] bodyMessage = createMessagesLoadRequestMessage(type, (UUID) uuidChat, (Integer) quantityMessages);
                    sendReadyMessageNetwork(bodyMessage);
                    messagesDefinesCtrl.setTextMessagesLoadReplyFlag(MessagesDefinesCtrl.TypeFlags.DEFAULT);
                }
            }
            case MessagesLoadReply -> {
                if (parameters.length == 1) {
                    Object msgInfoObj = parameters[0];
                    List<Map<DbGlobalDefines.LineKeys, String>> msgInfo =
                            structTools.objectInListMaps(msgInfoObj, DbGlobalDefines.LineKeys.class, String.class);
                    byte[] bodyMessageChatsLoadReply = createBodyMessagesLoadReplyMessage(type, msgInfo);
                    sendReadyMessageNetwork(bodyMessageChatsLoadReply);
                }
            }
        }
    }

    private void sendReadyMessageNetwork(byte[] bodyMessage) {
        networkCtrl.sendMessage(bodyMessage);
    }

    private void sendReadyMessageNetwork(byte[] bodyMessage, Runnable runnableCtrl) {
        networkCtrl.sendMessageByRunnableCtrl(bodyMessage, runnableCtrl);
    }

    private byte[] createBodyEntryRequestMessage(DefinesMessages.TypeMessage type, String login, String password) {
        return serializatorDataMessages.serialiseData(type, login, password);
    }

    private byte[] createBodyEntryReplyMessage(DefinesMessages.TypeMessage type, Boolean reply, UUID uuidUser) {
        return serializatorDataMessages.serialiseData(type, reply, uuidUser);
    }

    private byte[] createBodyRegistrationRequestMessage(DefinesMessages.TypeMessage type, String login, String email, String password) {
        return serializatorDataMessages.serialiseData(type, login, email, password);
    }

    private byte[] createBodyRegistrationReplyMessage(DefinesMessages.TypeMessage type, Boolean reply, DefinesMessages.TypeErrorRegistration error) {
        return serializatorDataMessages.serialiseData(type, reply, error);
    }

    private byte[] createBodyVerifyRegistrationEmailRequestMessage(DefinesMessages.TypeMessage type, String login, String email, String password, String code) {
        return serializatorDataMessages.serialiseData(type, login, email, password, code);
    }

    private byte[] createBodyVerifyRegistrationEmailReplyMessage(DefinesMessages.TypeMessage type, Boolean reply, DefinesMessages.TypeErrorRegistration error) {
        return serializatorDataMessages.serialiseData(type, reply, error);
    }

    private byte[] createBodyResetPasswordRequestMessage(DefinesMessages.TypeMessage type, String email) {
        return serializatorDataMessages.serialiseData(type, email);
    }

    private byte[] createBodyResetPasswordReplyMessage(DefinesMessages.TypeMessage type, Boolean reply) {
        return serializatorDataMessages.serialiseData(type, reply);
    }

    private byte[] createBodyVerifyFamousEmailRequestMessage(DefinesMessages.TypeMessage type, String email, String code) {
        return serializatorDataMessages.serialiseData(type, email, code);
    }

    private byte[] createBodyVerifyFamousEmailReplyMessage(DefinesMessages.TypeMessage type, Boolean reply) {
        return serializatorDataMessages.serialiseData(type, reply);
    }

    private byte[] createBodyChangePasswordRequestMessage(DefinesMessages.TypeMessage type, String email, String password) {
        return serializatorDataMessages.serialiseData(type, email, password);
    }

    private byte[] createBodyChangePasswordReplyMessage(DefinesMessages.TypeMessage type, Boolean reply) {
        return serializatorDataMessages.serialiseData(type, reply);
    }

    private byte[] createBodyChatsLoadRequestMessage(DefinesMessages.TypeMessage type, UUID uuidUser) {
        return serializatorDataMessages.serialiseData(type, uuidUser);
    }

    private byte[] createBodyChatsLoadReplyMessage(DefinesMessages.TypeMessage type, List<Map<DbGlobalDefines.LineKeys, String>> reply) {
        return serializatorDataMessages.serialiseData(type, reply);
    }

    private byte[] createBodyCheckOnlineUserRequestMessage(DefinesMessages.TypeMessage type, String ip) {
        return serializatorDataMessages.serialiseData(type, ip);
    }

    private byte[] createBodyCheckOnlineUserReplyMessage(DefinesMessages.TypeMessage type, UUID login) {
        return serializatorDataMessages.serialiseData(type, login);
    }

    private byte[] createBodyLoadUsersOnlineStatusRequestMessage(DefinesMessages.TypeMessage type, List<UUID> uuids) {
        return serializatorDataMessages.serialiseData(type, uuids);
    }

    private byte[] createBodyLoadUsersOnlineStatusReplyMessage(DefinesMessages.TypeMessage type,
                                                               Map<UUID, MainChatsGlobalDefines.TypeStatusOnline> statusesUsers,
                                                               Map<UUID, String> lastOnlineTimeUsers) {
        return serializatorDataMessages.serialiseData(type, statusesUsers, lastOnlineTimeUsers);
    }

    private byte[] createBodyTextMessageSendUserToServerMessage(DefinesMessages.TypeMessage type,
                                                                UUID uuidUserSender, UUID uuidUserReceiver, UUID uuidMessage,
                                                                String text, String timestamp) {
        return serializatorDataMessages.serialiseData(
                type, uuidUserSender, uuidUserReceiver, uuidMessage, text, timestamp);
    }

    private byte[] createBodyTextMessageSendUserToServerVerificationMessage(DefinesMessages.TypeMessage type, boolean reply) {
        return serializatorDataMessages.serialiseData(type, reply);
    }

    private byte[] createBodyTextMessagesChangingStatusFromServerMessage(DefinesMessages.TypeMessage type,
                                                                         Map<UUID, MainChatsGlobalDefines.TypeStatusMessage> mapStatusMessages) {
        return serializatorDataMessages.serialiseData(type, mapStatusMessages);
    }

    private byte[] createBodyTextMessagesChangingStatusFromServerVerificationMessage(DefinesMessages.TypeMessage type, boolean reply) {
        return serializatorDataMessages.serialiseData(type, reply);
    }

    private byte[] createBodyTextMessagesChangingStatusFromUserMessage(DefinesMessages.TypeMessage type,
                                                                         Map<UUID, MainChatsGlobalDefines.TypeStatusMessage> mapStatusMessages) {
        return serializatorDataMessages.serialiseData(type, mapStatusMessages);
    }

    private byte[] createBodyTextMessagesChangingStatusFromUserVerificationMessage(DefinesMessages.TypeMessage type, boolean reply) {
        return serializatorDataMessages.serialiseData(type, reply);
    }

    private byte[] createBodyTextMessageRedirectServerToUserMessage(DefinesMessages.TypeMessage type,
                                                                    UUID uuidUserSender, UUID uuidUserReceiver, UUID uuidMessage,
                                                                    String text, String timestamp) {
        return serializatorDataMessages.serialiseData(
                type, uuidUserSender, uuidUserReceiver, uuidMessage, text, timestamp);
    }

    private byte[] createBodyTextMessageRedirectServerToUserVerificationMessage(DefinesMessages.TypeMessage type, boolean reply) {
        return serializatorDataMessages.serialiseData(type, reply);
    }

    private byte[] createMessagesLoadRequestMessage(DefinesMessages.TypeMessage type, UUID uuidChat, int quantityMessages) {
        return serializatorDataMessages.serialiseData(type, uuidChat, quantityMessages);
    }

    private byte[] createBodyMessagesLoadReplyMessage(DefinesMessages.TypeMessage type, List<Map<DbGlobalDefines.LineKeys, String>> reply) {
        return serializatorDataMessages.serialiseData(type, reply);
    }
}