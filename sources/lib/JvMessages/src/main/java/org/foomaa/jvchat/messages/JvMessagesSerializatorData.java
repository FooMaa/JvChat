package org.foomaa.jvchat.messages;


public class JvMessagesSerializatorData {
    private static JvMessagesSerializatorData instance;

    private  JvMessagesSerializatorData() {}

    public static JvMessagesSerializatorData getInstance() {
        if (instance == null) {
            instance = new JvMessagesSerializatorData();
        }
        return instance;
    }

    public byte[] serialiseData(JvMessagesDefines.TypeMessage type, Object... parameters) {
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
                            (JvMessagesDefines.TypeErrorRegistration) error);
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
                            (JvMessagesDefines.TypeErrorRegistration) error);
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
        }
        return new byte[0];
    }

    private byte[] createEntryRequestMessage(JvMessagesDefines.TypeMessage type, String login, String password) {
        ClientServerSerializeProtocol_pb.EntryRequest msgEntryRequest =
                ClientServerSerializeProtocol_pb.EntryRequest.newBuilder()
                .setLogin(login)
                .setPassword(password)
                .build();
        ClientServerSerializeProtocol_pb.General resMsg =
                ClientServerSerializeProtocol_pb.General.newBuilder()
                .setType(type.getValue())
                .setEntryRequest(msgEntryRequest)
                .build();
        return resMsg.toByteArray();
    }

    private byte[] createEntryReplyMessage(JvMessagesDefines.TypeMessage type, boolean reply) {
        ClientServerSerializeProtocol_pb.EntryReply msgEntryReply =
                ClientServerSerializeProtocol_pb.EntryReply.newBuilder()
                .setReply(reply)
                .build();
        ClientServerSerializeProtocol_pb.General resMsg =
                ClientServerSerializeProtocol_pb.General.newBuilder()
                .setType(type.getValue())
                .setEntryReply(msgEntryReply)
                .build();
        return resMsg.toByteArray();
    }

    private byte[] createRegistrationRequestMessage(JvMessagesDefines.TypeMessage type, String login, String email, String password) {
        ClientServerSerializeProtocol_pb.RegistrationRequest msgRegRequest =
                ClientServerSerializeProtocol_pb.RegistrationRequest.newBuilder()
                .setLogin(login)
                .setEmail(email)
                .setPassword(password)
                .build();
        ClientServerSerializeProtocol_pb.General resMsg =
                ClientServerSerializeProtocol_pb.General.newBuilder()
                .setType(type.getValue())
                .setRegistrationRequest(msgRegRequest)
                .build();
        return resMsg.toByteArray();
    }

    private byte[] createRegistrationReplyMessage(JvMessagesDefines.TypeMessage type, boolean reply, JvMessagesDefines.TypeErrorRegistration error) {
        ClientServerSerializeProtocol_pb.RegistrationReply msgRegReply =
                ClientServerSerializeProtocol_pb.RegistrationReply.newBuilder()
                .setReply(reply)
                .setError(ClientServerSerializeProtocol_pb.RegistrationReply.Error.forNumber(error.getValue()))
                .build();
        ClientServerSerializeProtocol_pb.General resMsg =
                ClientServerSerializeProtocol_pb.General.newBuilder()
                .setType(type.getValue())
                .setRegistrationReply(msgRegReply)
                .build();
        return resMsg.toByteArray();
    }

    private byte[] createVerifyRegistrationEmailRequestMessage(JvMessagesDefines.TypeMessage type, String login, String email, String password, String code) {
        ClientServerSerializeProtocol_pb.VerifyRegistrationEmailRequest msgVerifyRegRequest =
                ClientServerSerializeProtocol_pb.VerifyRegistrationEmailRequest.newBuilder()
                .setLogin(login)
                .setEmail(email)
                .setPassword(password)
                .setCode(code)
                .build();
        ClientServerSerializeProtocol_pb.General resMsg =
                ClientServerSerializeProtocol_pb.General.newBuilder()
                .setType(type.getValue())
                .setVerifyRegistrationEmailRequest(msgVerifyRegRequest)
                .build();
        return resMsg.toByteArray();
    }

    private byte[] createVerifyRegistrationEmailReplyMessage(JvMessagesDefines.TypeMessage type, boolean reply, JvMessagesDefines.TypeErrorRegistration error) {
        ClientServerSerializeProtocol_pb.VerifyRegistrationEmailReply msgVerifyRegReply =
                ClientServerSerializeProtocol_pb.VerifyRegistrationEmailReply.newBuilder()
                .setReply(reply)
                .setError(ClientServerSerializeProtocol_pb.VerifyRegistrationEmailReply.Error.forNumber(error.getValue()))
                .build();
        ClientServerSerializeProtocol_pb.General resMsg =
                ClientServerSerializeProtocol_pb.General.newBuilder()
                .setType(type.getValue())
                .setVerifyRegistrationEmailReply(msgVerifyRegReply)
                .build();
        return resMsg.toByteArray();
    }

    private byte[] createResetPasswordRequestMessage(JvMessagesDefines.TypeMessage type, String email) {
        ClientServerSerializeProtocol_pb.ResetPasswordRequest msgResetRequest =
                ClientServerSerializeProtocol_pb.ResetPasswordRequest.newBuilder()
                .setEmail(email)
                .build();
        ClientServerSerializeProtocol_pb.General resMsg =
                ClientServerSerializeProtocol_pb.General.newBuilder()
                .setType(type.getValue())
                .setResetPasswordRequest(msgResetRequest)
                .build();
        return resMsg.toByteArray();
    }

    private byte[] createResetPasswordReplyMessage(JvMessagesDefines.TypeMessage type, boolean reply) {
        ClientServerSerializeProtocol_pb.ResetPasswordReply msgResetReply =
                ClientServerSerializeProtocol_pb.ResetPasswordReply.newBuilder()
                .setReply(reply)
                .build();
        ClientServerSerializeProtocol_pb.General resMsg =
                ClientServerSerializeProtocol_pb.General.newBuilder()
                .setType(type.getValue())
                .setResetPasswordReply(msgResetReply)
                .build();
        return resMsg.toByteArray();
    }

    private byte[] createVerifyFamousEmailRequestMessage(JvMessagesDefines.TypeMessage type, String email, String code) {
        ClientServerSerializeProtocol_pb.VerifyFamousEmailRequest msgVerifyEmailRequest =
                ClientServerSerializeProtocol_pb.VerifyFamousEmailRequest.newBuilder()
                .setEmail(email)
                .setCode(code)
                .build();
        ClientServerSerializeProtocol_pb.General resMsg =
                ClientServerSerializeProtocol_pb.General.newBuilder()
                .setType(type.getValue())
                .setVerifyFamousEmailRequest(msgVerifyEmailRequest)
                .build();
        return resMsg.toByteArray();
    }

    private byte[] createVerifyFamousEmailReplyMessage(JvMessagesDefines.TypeMessage type, boolean reply) {
        ClientServerSerializeProtocol_pb.VerifyFamousEmailReply msgVerifyEmailReply =
                ClientServerSerializeProtocol_pb.VerifyFamousEmailReply.newBuilder()
                .setReply(reply)
                .build();
        ClientServerSerializeProtocol_pb.General resMsg =
                ClientServerSerializeProtocol_pb.General.newBuilder()
                .setType(type.getValue())
                .setVerifyFamousEmailReply(msgVerifyEmailReply)
                .build();
        return resMsg.toByteArray();
    }

    private byte[] createChangePasswordRequestMessage(JvMessagesDefines.TypeMessage type, String email, String password) {
        ClientServerSerializeProtocol_pb.ChangePasswordRequest msgChangePasswordRequest =
                ClientServerSerializeProtocol_pb.ChangePasswordRequest.newBuilder()
                .setEmail(email)
                .setPassword(password)
                .build();
        ClientServerSerializeProtocol_pb.General resMsg =
                ClientServerSerializeProtocol_pb.General.newBuilder()
                .setType(type.getValue())
                .setChangePasswordRequest(msgChangePasswordRequest)
                .build();
        return resMsg.toByteArray();
    }

    private byte[] createChangePasswordReplyMessage(JvMessagesDefines.TypeMessage type, boolean reply) {
        ClientServerSerializeProtocol_pb.ChangePasswordReply msgChangePasswordReply =
                ClientServerSerializeProtocol_pb.ChangePasswordReply.newBuilder()
                .setReply(reply)
                .build();
        ClientServerSerializeProtocol_pb.General resMsg =
                ClientServerSerializeProtocol_pb.General.newBuilder()
                .setType(type.getValue())
                .setChangePasswordReply(msgChangePasswordReply)
                .build();
        return resMsg.toByteArray();
    }

    private byte[] createChatsLoadRequestMessage(JvMessagesDefines.TypeMessage type, String sender) {
        ClientServerSerializeProtocol_pb.ChatsLoadRequest msgChatsLoadRequest =
                ClientServerSerializeProtocol_pb.ChatsLoadRequest.newBuilder()
                        .setSender(sender)
                        .build();
        ClientServerSerializeProtocol_pb.General resMsg =
                ClientServerSerializeProtocol_pb.General.newBuilder()
                        .setType(type.getValue())
                        .setChatsLoadRequest(msgChatsLoadRequest)
                        .build();
        return resMsg.toByteArray();
    }
}