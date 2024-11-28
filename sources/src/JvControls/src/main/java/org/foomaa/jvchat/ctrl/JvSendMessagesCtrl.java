package org.foomaa.jvchat.ctrl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.foomaa.jvchat.globaldefines.JvDbGlobalDefines;
import org.foomaa.jvchat.globaldefines.JvMainChatsGlobalDefines;
import org.foomaa.jvchat.messages.JvGetterMessages;
import org.foomaa.jvchat.messages.JvDefinesMessages;
import org.foomaa.jvchat.settings.JvGetterSettings;
import org.foomaa.jvchat.tools.JvGetterTools;


public class JvSendMessagesCtrl {
    private static JvSendMessagesCtrl instance;

    private JvSendMessagesCtrl() {}

    static JvSendMessagesCtrl getInstance() {
        if (instance == null) {
            instance = new JvSendMessagesCtrl();
        }
        return instance;
    }

    public final void sendMessage(JvDefinesMessages.TypeMessage type, Object... parameters) {
        switch (type) {
            case EntryRequest -> {
                if (parameters.length == 2) {
                    Object login = parameters[0];
                    Object password = parameters[1];
                    byte[] bodyMessage = createBodyEntryRequestMessage(type,
                            (String) login,
                            (String) password);
                    sendReadyMessageNetwork(bodyMessage);
                    JvGetterControls.getInstance().getBeanMessagesDefinesCtrl()
                            .setEntryRequestFlag(JvMessagesDefinesCtrl.TypeFlags.DEFAULT);
                }
            }
            case EntryReply -> {
                if (parameters.length == 1) {
                    Object reply = parameters[0];
                    byte[] bodyMessage = createBodyEntryReplyMessage(type,
                            (Boolean) reply);
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
                    JvGetterControls.getInstance().getBeanMessagesDefinesCtrl()
                            .setRegistrationRequestFlag(JvMessagesDefinesCtrl.TypeFlags.DEFAULT);
                }
            }
            case RegistrationReply -> {
                if (parameters.length == 2) {
                    Object reply = parameters[0];
                    Object error = parameters[1];
                    byte[] bodyMessage = createBodyRegistrationReplyMessage(type,
                            (Boolean) reply, (JvDefinesMessages.TypeErrorRegistration) error);
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
                    JvGetterControls.getInstance().getBeanMessagesDefinesCtrl()
                            .setVerifyRegistrationEmailRequestFlag(JvMessagesDefinesCtrl.TypeFlags.DEFAULT);
                }
            }
            case VerifyRegistrationEmailReply -> {
                if (parameters.length == 2) {
                    Object reply = parameters[0];
                    Object error = parameters[1];
                    byte[] bodyMessage = createBodyVerifyRegistrationEmailReplyMessage(type,
                            (Boolean) reply, (JvDefinesMessages.TypeErrorRegistration) error);
                    sendReadyMessageNetwork(bodyMessage);
                }
            }
            case ResetPasswordRequest -> {
                if (parameters.length == 1) {
                    Object email = parameters[0];
                    byte[] bodyMessage = createBodyResetPasswordRequestMessage(type,
                            (String) email);
                    sendReadyMessageNetwork(bodyMessage);
                    JvGetterControls.getInstance().getBeanMessagesDefinesCtrl()
                            .setResetPasswordRequestFlag(JvMessagesDefinesCtrl.TypeFlags.DEFAULT);
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
                    JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().
                            setVerifyFamousEmailRequestFlag(JvMessagesDefinesCtrl.TypeFlags.DEFAULT);
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
                    JvGetterControls.getInstance().getBeanMessagesDefinesCtrl()
                            .setChangePasswordRequest(JvMessagesDefinesCtrl.TypeFlags.DEFAULT);
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
                    Object sender = parameters[0];
                    byte[] bodyMessage = createBodyChatsLoadRequestMessage(type,
                            (String) sender);
                    sendReadyMessageNetwork(bodyMessage);
                    JvGetterControls.getInstance().getBeanMessagesDefinesCtrl()
                            .setChatsLoadReplyFlag(JvMessagesDefinesCtrl.TypeFlags.DEFAULT);
                }
            }
            case ChatsLoadReply -> {
                if (parameters.length == 1) {
                    Object chatsInfoObj = parameters[0];
                    List<Map<JvDbGlobalDefines.LineKeys, String>> chatsInfo =
                            JvGetterTools.getInstance().getBeanStructTools()
                            .objectInListMaps(chatsInfoObj, JvDbGlobalDefines.LineKeys.class, String.class);
                    byte[] bodyMessageChatsLoadReply = createBodyChatsLoadReplyMessage(type, chatsInfo);
                    sendReadyMessageNetwork(bodyMessageChatsLoadReply);
                    sendMessage(JvDefinesMessages.TypeMessage.CheckOnlineUserRequest,
                            JvGetterSettings.getInstance().getBeanServersInfoSettings().getIp());
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
                    Object login = parameters[0];
                    byte[] bodyMessage = createBodyCheckOnlineUserReplyMessage(type, (String) login);
                    sendReadyMessageNetwork(bodyMessage);
                }
            }
            case LoadUsersOnlineStatusRequest -> {
                if (parameters.length == 1) {
                    Object loginsObject = parameters[0];
                    List<String> loginsList = JvGetterTools.getInstance()
                            .getBeanStructTools().checkedCastList(loginsObject, String.class);
                    byte[] bodyMessage = createBodyLoadUsersOnlineStatusRequestMessage(type, loginsList);
                    sendReadyMessageNetwork(bodyMessage);
                    JvGetterControls.getInstance().getBeanMessagesDefinesCtrl()
                            .setLoadUsersOnlineReplyFlag(JvMessagesDefinesCtrl.TypeFlags.DEFAULT);
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
                    byte[] bodyMessage = createBodyLoadUsersOnlineStatusReplyMessage(type, statusesUsersMap, lastOnlineTimeUsers);
                    sendReadyMessageNetwork(bodyMessage);
                }
            }
            case TextMessageSendUserToServer -> {
                if (parameters.length == 5) {
                    Object loginSender = parameters[0];
                    Object loginReceiver = parameters[1];
                    Object uuid = parameters[2];
                    Object text = parameters[3];
                    Object timestamp = parameters[4];
                    byte[] bodyMessage = createBodyTextMessageSendUserToServerMessage(type, (String) loginSender,
                            (String) loginReceiver, (String) uuid, (String) text, (String) timestamp);
                    sendReadyMessageNetwork(bodyMessage);
                }
            }
            case TextMessagesChangingStatusFromServer -> {
                if (parameters.length == 3) {
                    Object loginSender = parameters[0];
                    Object loginReceiver = parameters[1];
                    Object mapUuidStatus = parameters[2];
                    Map<UUID, JvMainChatsGlobalDefines.TypeStatusMessage> mapStatusesMessages = JvGetterTools.getInstance()
                            .getBeanStructTools().objectInMap(mapUuidStatus, UUID.class, JvMainChatsGlobalDefines.TypeStatusMessage.class);
                    byte[] bodyMessage = createBodyTextMessagesChangingStatusFromServerMessage(
                            type, (String) loginSender, (String) loginReceiver, mapStatusesMessages);
                    sendReadyMessageNetwork(bodyMessage);
                }
            }
            case TextMessageRedirectServerToUser -> {
                if (parameters.length == 6) {
                    Object loginSender = parameters[0];
                    Object loginReceiver = parameters[1];
                    Object uuid = parameters[2];
                    Object text = parameters[3];
                    Object timestamp = parameters[4];
                    Object runnableCtrl = parameters[5];
                    byte[] bodyMessage = createBodyTextMessageRedirectServerToUserMessage(type, (String) loginSender,
                            (String) loginReceiver, (String) uuid, (String) text, (String) timestamp);
                    sendReadyMessageNetwork(bodyMessage, (Runnable) runnableCtrl);
                }
            }
            case MessagesLoadRequest -> {
                if (parameters.length == 3) {
                    Object loginRequesting = parameters[0];
                    Object loginDialog = parameters[1];
                    Object quantityMessages = parameters[2];
                    byte[] bodyMessage = createMessagesLoadRequestMessage(type, (String) loginRequesting, (String) loginDialog, (Integer) quantityMessages);
                    sendReadyMessageNetwork(bodyMessage);
                    JvGetterControls.getInstance().getBeanMessagesDefinesCtrl()
                            .setMessagesLoadReplyFlag(JvMessagesDefinesCtrl.TypeFlags.DEFAULT);
                }
            }
            case MessagesLoadReply -> {
                if (parameters.length == 1) {
                    Object msgInfoObj = parameters[0];
                    List<Map<JvDbGlobalDefines.LineKeys, String>> msgInfo =
                            JvGetterTools.getInstance().getBeanStructTools()
                                    .objectInListMaps(msgInfoObj, JvDbGlobalDefines.LineKeys.class, String.class);
                    byte[] bodyMessageChatsLoadReply = createBodyMessagesLoadReplyMessage(type, msgInfo);
                    sendReadyMessageNetwork(bodyMessageChatsLoadReply);
                }
            }
        }
    }

    private void sendReadyMessageNetwork(byte[] bodyMessage) {
        JvGetterControls.getInstance().getBeanNetworkCtrl().sendMessage(bodyMessage);
    }

    private void sendReadyMessageNetwork(byte[] bodyMessage, Runnable runnableCtrl) {
        JvGetterControls.getInstance().getBeanNetworkCtrl().sendMessageByRunnableCtrl(bodyMessage, runnableCtrl);
    }

    private byte[] createBodyEntryRequestMessage(JvDefinesMessages.TypeMessage type, String login, String password) {
        return JvGetterMessages.getInstance().getBeanSerializatorDataMessages().serialiseData(type, login, password);
    }

    private byte[] createBodyEntryReplyMessage(JvDefinesMessages.TypeMessage type, Boolean reply) {
        return JvGetterMessages.getInstance().getBeanSerializatorDataMessages().serialiseData(type, reply);
    }

    private byte[] createBodyRegistrationRequestMessage(JvDefinesMessages.TypeMessage type, String login, String email, String password) {
        return JvGetterMessages.getInstance().getBeanSerializatorDataMessages().serialiseData(type, login, email, password);
    }

    private byte[] createBodyRegistrationReplyMessage(JvDefinesMessages.TypeMessage type, Boolean reply, JvDefinesMessages.TypeErrorRegistration error) {
        return JvGetterMessages.getInstance().getBeanSerializatorDataMessages().serialiseData(type, reply, error);
    }

    private byte[] createBodyVerifyRegistrationEmailRequestMessage(JvDefinesMessages.TypeMessage type, String login, String email, String password, String code) {
        return JvGetterMessages.getInstance().getBeanSerializatorDataMessages().serialiseData(type, login, email, password, code);
    }

    private byte[] createBodyVerifyRegistrationEmailReplyMessage(JvDefinesMessages.TypeMessage type, Boolean reply, JvDefinesMessages.TypeErrorRegistration error) {
        return JvGetterMessages.getInstance().getBeanSerializatorDataMessages().serialiseData(type, reply, error);
    }

    private byte[] createBodyResetPasswordRequestMessage(JvDefinesMessages.TypeMessage type, String email) {
        return JvGetterMessages.getInstance().getBeanSerializatorDataMessages().serialiseData(type, email);
    }

    private byte[] createBodyResetPasswordReplyMessage(JvDefinesMessages.TypeMessage type, Boolean reply) {
        return JvGetterMessages.getInstance().getBeanSerializatorDataMessages().serialiseData(type, reply);
    }

    private byte[] createBodyVerifyFamousEmailRequestMessage(JvDefinesMessages.TypeMessage type, String email, String code) {
        return JvGetterMessages.getInstance().getBeanSerializatorDataMessages().serialiseData(type, email, code);
    }

    private byte[] createBodyVerifyFamousEmailReplyMessage(JvDefinesMessages.TypeMessage type, Boolean reply) {
        return JvGetterMessages.getInstance().getBeanSerializatorDataMessages().serialiseData(type, reply);
    }

    private byte[] createBodyChangePasswordRequestMessage(JvDefinesMessages.TypeMessage type, String email, String password) {
        return JvGetterMessages.getInstance().getBeanSerializatorDataMessages().serialiseData(type, email, password);
    }

    private byte[] createBodyChangePasswordReplyMessage(JvDefinesMessages.TypeMessage type, Boolean reply) {
        return JvGetterMessages.getInstance().getBeanSerializatorDataMessages().serialiseData(type, reply);
    }

    private byte[] createBodyChatsLoadRequestMessage(JvDefinesMessages.TypeMessage type, String sender) {
        return JvGetterMessages.getInstance().getBeanSerializatorDataMessages().serialiseData(type, sender);
    }

    private byte[] createBodyChatsLoadReplyMessage(JvDefinesMessages.TypeMessage type, List<Map<JvDbGlobalDefines.LineKeys, String>> reply) {
        return JvGetterMessages.getInstance().getBeanSerializatorDataMessages().serialiseData(type, reply);
    }

    private byte[] createBodyCheckOnlineUserRequestMessage(JvDefinesMessages.TypeMessage type, String ip) {
        return JvGetterMessages.getInstance().getBeanSerializatorDataMessages().serialiseData(type, ip);
    }

    private byte[] createBodyCheckOnlineUserReplyMessage(JvDefinesMessages.TypeMessage type, String login) {
        return JvGetterMessages.getInstance().getBeanSerializatorDataMessages().serialiseData(type, login);
    }

    private byte[] createBodyLoadUsersOnlineStatusRequestMessage(JvDefinesMessages.TypeMessage type, List<String> logins) {
        return JvGetterMessages.getInstance().getBeanSerializatorDataMessages().serialiseData(type, logins);
    }

    private byte[] createBodyLoadUsersOnlineStatusReplyMessage(JvDefinesMessages.TypeMessage type,
                                                               Map<String, JvMainChatsGlobalDefines.TypeStatusOnline> statusesUsers,
                                                               Map<String, String> lastOnlineTimeUsers) {
        return JvGetterMessages.getInstance().getBeanSerializatorDataMessages().serialiseData(type, statusesUsers, lastOnlineTimeUsers);
    }

    private byte[] createBodyTextMessageSendUserToServerMessage(JvDefinesMessages.TypeMessage type,
                                                            String loginSender, String loginReceiver,
                                                            String uuid, String text, String timestamp) {
        return JvGetterMessages.getInstance().getBeanSerializatorDataMessages().serialiseData(
                type, loginSender, loginReceiver, uuid, text, timestamp);
    }

    private byte[] createBodyTextMessagesChangingStatusFromServerMessage(JvDefinesMessages.TypeMessage type,
                                                                         String loginSender, String loginReceiver,
                                                                         Map<UUID, JvMainChatsGlobalDefines.TypeStatusMessage> mapStatusMessages) {
        return JvGetterMessages.getInstance().getBeanSerializatorDataMessages().serialiseData(type, loginSender, loginReceiver, mapStatusMessages);
    }

    private byte[] createBodyTextMessageRedirectServerToUserMessage(JvDefinesMessages.TypeMessage type,
                                                                String loginSender, String loginReceiver,
                                                                String uuid, String text, String timestamp) {
        return JvGetterMessages.getInstance().getBeanSerializatorDataMessages().serialiseData(
                type, loginSender, loginReceiver, uuid, text, timestamp);
    }

    private byte[] createMessagesLoadRequestMessage(JvDefinesMessages.TypeMessage type, String loginRequesting,
                                                    String loginDialog, int quantityMessages) {
        return JvGetterMessages.getInstance().getBeanSerializatorDataMessages().serialiseData(type, loginRequesting, loginDialog, quantityMessages);
    }

    private byte[] createBodyMessagesLoadReplyMessage(JvDefinesMessages.TypeMessage type, List<Map<JvDbGlobalDefines.LineKeys, String>> reply) {
        return JvGetterMessages.getInstance().getBeanSerializatorDataMessages().serialiseData(type, reply);
    }
}