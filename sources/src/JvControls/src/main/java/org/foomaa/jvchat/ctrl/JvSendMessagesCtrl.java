package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.globaldefines.JvDbGlobalDefines;
import org.foomaa.jvchat.messages.JvGetterMessages;
import org.foomaa.jvchat.messages.JvDefinesMessages;
import org.foomaa.jvchat.tools.JvGetterTools;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;


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
            case NecessityServerRequest -> {
                if (parameters.length == 1) {
                    Object typeNecessityObj = parameters[0];
                    int valueTypeNecessity = Integer.parseInt((String) typeNecessityObj);
                    JvDefinesMessages.TypeNecessityServer typeNecessity =
                            Objects.requireNonNull(JvDefinesMessages.TypeNecessityServer.getTypeNecessityServer(valueTypeNecessity));
                    byte[] bodyMessage = createBodyNecessityServerRequestMessage(type, typeNecessity);
                    sendReadyMessageNetwork(bodyMessage);
                }
            }
            case NecessityServerReply -> {
                if (parameters.length == 2) {
                    Object typeNecessityObj = parameters[0];
                    int valueTypeNecessity = Integer.parseInt((String) typeNecessityObj);
                    JvDefinesMessages.TypeNecessityServer typeNecessity =
                            Objects.requireNonNull(JvDefinesMessages.TypeNecessityServer.getTypeNecessityServer(valueTypeNecessity));
                    Object parameter = parameters[1];
                    byte[] bodyMessage = createBodyNecessityServerReplyMessage(type, typeNecessity, parameter);
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
                    byte[] bodyMessage = createBodyChatsLoadReplyMessage(type,
                            chatsInfo);
                    sendReadyMessageNetwork(bodyMessage);
                }
            }
        }
    }

    private void sendReadyMessageNetwork(byte[] bodyMessage) {
        JvGetterControls.getInstance()
                .getBeanNetworkCtrl().sendMessage(bodyMessage);
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

    private byte[] createBodyNecessityServerRequestMessage(JvDefinesMessages.TypeMessage type, JvDefinesMessages.TypeNecessityServer typeNecessity) {
        return JvGetterMessages.getInstance().getBeanSerializatorDataMessages().serialiseData(type, typeNecessity);
    }

    private byte[] createBodyNecessityServerReplyMessage(JvDefinesMessages.TypeMessage type, JvDefinesMessages.TypeNecessityServer typeNecessity, Object parameter) {
        return JvGetterMessages.getInstance().getBeanSerializatorDataMessages().serialiseData(type, typeNecessity, parameter);
    }

    private byte[] createBodyChatsLoadRequestMessage(JvDefinesMessages.TypeMessage type, String sender) {
        return JvGetterMessages.getInstance().getBeanSerializatorDataMessages().serialiseData(type, sender);
    }

    private byte[] createBodyChatsLoadReplyMessage(JvDefinesMessages.TypeMessage type, List<Map<JvDbGlobalDefines.LineKeys, String>> reply) {
        return JvGetterMessages.getInstance().getBeanSerializatorDataMessages().serialiseData(type, reply);
    }
}