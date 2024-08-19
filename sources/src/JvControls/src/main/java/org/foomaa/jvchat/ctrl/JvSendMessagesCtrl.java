package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.messages.JvGetterMessages;
import org.foomaa.jvchat.messages.JvMessagesDefines;

import java.util.ArrayList;
import java.util.List;

public class JvSendMessagesCtrl {
    private static JvSendMessagesCtrl instance;

    private JvSendMessagesCtrl() {}

    static JvSendMessagesCtrl getInstance() {
        if (instance == null) {
            instance = new JvSendMessagesCtrl();
        }
        return instance;
    }

    public final void sendMessage(JvMessagesDefines.TypeMessage type, Object... parameters) {
        switch (type) {
            case EntryRequest -> {
                if (parameters.length == 2) {
                    Object login = parameters[0];
                    Object password = parameters[1];
                    byte[] bodyMessage = createBodyEntryRequestMessage(type,
                            (String) login,
                            (String) password);
                    sendReadyMessageNetwork(bodyMessage);
                    JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().
                            setEntryRequestFlag(JvMessagesDefinesCtrl.TypeFlags.DEFAULT);
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
                    JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().
                            setRegistrationRequestFlag(JvMessagesDefinesCtrl.TypeFlags.DEFAULT);
                }
            }
            case RegistrationReply -> {
                if (parameters.length == 2) {
                    Object reply = parameters[0];
                    Object error = parameters[1];
                    byte[] bodyMessage = createBodyRegistrationReplyMessage(type,
                            (Boolean) reply, (JvMessagesDefines.TypeErrorRegistration) error);
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
                    JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().
                            setVerifyRegistrationEmailRequestFlag(JvMessagesDefinesCtrl.TypeFlags.DEFAULT);
                }
            }
            case VerifyRegistrationEmailReply -> {
                if (parameters.length == 2) {
                    Object reply = parameters[0];
                    Object error = parameters[1];
                    byte[] bodyMessage = createBodyVerifyRegistrationEmailReplyMessage(type,
                            (Boolean) reply, (JvMessagesDefines.TypeErrorRegistration) error);
                    sendReadyMessageNetwork(bodyMessage);
                }
            }
            case ResetPasswordRequest -> {
                if (parameters.length == 1) {
                    Object email = parameters[0];
                    byte[] bodyMessage = createBodyResetPasswordRequestMessage(type,
                            (String) email);
                    sendReadyMessageNetwork(bodyMessage);
                    JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().
                            setResetPasswordRequestFlag(JvMessagesDefinesCtrl.TypeFlags.DEFAULT);
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
                    JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().
                            setChangePasswordRequest(JvMessagesDefinesCtrl.TypeFlags.DEFAULT);
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
                }
            }
            case ChatsLoadReply -> {
                if (parameters.length == 1) {
                    Object receiversObj = parameters[0];
                    List<String> receivers = new ArrayList<>();
                    if (receiversObj instanceof List<?> receiversList) {
                        for (Object obj : receiversList) {
                            receivers.add((String) obj);
                        }
                    }
                    byte[] bodyMessage = createBodyChatsLoadReplyMessage(type,
                            receivers);
                    sendReadyMessageNetwork(bodyMessage);
                }
            }
        }
    }

    private void sendReadyMessageNetwork(byte[] bodyMessage) {
        JvGetterControls.getInstance()
                .getBeanNetworkCtrl().sendMessage(bodyMessage);
    }

    private byte[] createBodyEntryRequestMessage(JvMessagesDefines.TypeMessage type, String login, String password) {
        return JvGetterMessages.getInstance().getBeanMessagesSerializatorData().serialiseData(type, login, password);
    }

    private byte[] createBodyEntryReplyMessage(JvMessagesDefines.TypeMessage type, Boolean reply) {
        return JvGetterMessages.getInstance().getBeanMessagesSerializatorData().serialiseData(type, reply);
    }

    private byte[] createBodyRegistrationRequestMessage(JvMessagesDefines.TypeMessage type, String login, String email, String password) {
        return JvGetterMessages.getInstance().getBeanMessagesSerializatorData().serialiseData(type, login, email, password);
    }

    private byte[] createBodyRegistrationReplyMessage(JvMessagesDefines.TypeMessage type, Boolean reply, JvMessagesDefines.TypeErrorRegistration error) {
        return JvGetterMessages.getInstance().getBeanMessagesSerializatorData().serialiseData(type, reply, error);
    }

    private byte[] createBodyVerifyRegistrationEmailRequestMessage(JvMessagesDefines.TypeMessage type, String login, String email, String password, String code) {
        return JvGetterMessages.getInstance().getBeanMessagesSerializatorData().serialiseData(type, login, email, password, code);
    }

    private byte[] createBodyVerifyRegistrationEmailReplyMessage(JvMessagesDefines.TypeMessage type, Boolean reply, JvMessagesDefines.TypeErrorRegistration error) {
        return JvGetterMessages.getInstance().getBeanMessagesSerializatorData().serialiseData(type, reply, error);
    }

    private byte[] createBodyResetPasswordRequestMessage(JvMessagesDefines.TypeMessage type, String email) {
        return JvGetterMessages.getInstance().getBeanMessagesSerializatorData().serialiseData(type, email);
    }

    private byte[] createBodyResetPasswordReplyMessage(JvMessagesDefines.TypeMessage type, Boolean reply) {
        return JvGetterMessages.getInstance().getBeanMessagesSerializatorData().serialiseData(type, reply);
    }

    private byte[] createBodyVerifyFamousEmailRequestMessage(JvMessagesDefines.TypeMessage type, String email, String code) {
        return JvGetterMessages.getInstance().getBeanMessagesSerializatorData().serialiseData(type, email, code);
    }

    private byte[] createBodyVerifyFamousEmailReplyMessage(JvMessagesDefines.TypeMessage type, Boolean reply) {
        return JvGetterMessages.getInstance().getBeanMessagesSerializatorData().serialiseData(type, reply);
    }

    private byte[] createBodyChangePasswordRequestMessage(JvMessagesDefines.TypeMessage type, String email, String password) {
        return JvGetterMessages.getInstance().getBeanMessagesSerializatorData().serialiseData(type, email, password);
    }

    private byte[] createBodyChangePasswordReplyMessage(JvMessagesDefines.TypeMessage type, Boolean reply) {
        return JvGetterMessages.getInstance().getBeanMessagesSerializatorData().serialiseData(type, reply);
    }

    private byte[] createBodyChatsLoadRequestMessage(JvMessagesDefines.TypeMessage type, String sender) {
        return JvGetterMessages.getInstance().getBeanMessagesSerializatorData().serialiseData(type, sender);
    }

    private byte[] createBodyChatsLoadReplyMessage(JvMessagesDefines.TypeMessage type, List<String> reply) {
        return JvGetterMessages.getInstance().getBeanMessagesSerializatorData().serialiseData(type, reply);
    }
}