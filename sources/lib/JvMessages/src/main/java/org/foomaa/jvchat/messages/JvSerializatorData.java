package org.foomaa.jvchat.messages;

import com.google.protobuf.InvalidProtocolBufferException;

import java.util.HashMap;

public class JvSerializatorData {

    public enum TypeMessage {
        EntryRequest(0),
        EntryReply(1),
        RegistrationRequest(2),
        RegistrationReply(3),
        ResetPasswordRequest(4),
        ResetPasswordReply(5),
        VerifyEmailRequest(6),
        VerifyEmailReply(7),
        ChangePasswordRequest(8),
        ChangePasswordReply(9);

        private final int value;

        TypeMessage(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public boolean compare(int i) {
            return value == i;
        }

        public static TypeMessage getTypeMsg(int value)
        {
            TypeMessage[] errors = TypeMessage.values();
            for (TypeMessage error : errors) {
                if (error.compare(value))
                    return error;
            }
            return null;
        }
    }

    public enum TypeData {
        Login,
        Email,
        Password,
        ErrorReg,
        BoolReply,
        VerifyCode,
    }

    public enum TypeErrorRegistration {
        Login(ClientServerSerializeProtocol_pb.RegistrationReply.Error.Login_VALUE),
        Email(ClientServerSerializeProtocol_pb.RegistrationReply.Error.Email_VALUE),
        LoginAndEmail(ClientServerSerializeProtocol_pb.RegistrationReply.Error.LoginAndEmail_VALUE),
        NoError (ClientServerSerializeProtocol_pb.RegistrationReply.Error.NoError_VALUE);

        private final int value;

        TypeErrorRegistration(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public boolean compare(int i) {
            return value == i;
        }

        public static TypeErrorRegistration getTypeError(int value)
        {
            TypeErrorRegistration[] errors = TypeErrorRegistration.values();
            for (TypeErrorRegistration error : errors) {
                if (error.compare(value))
                    return error;
            }
            return TypeErrorRegistration.NoError;
        }
    }

    public static byte[] serialiseData(TypeMessage type, Object... parameters) {
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
            case EntryReply -> {
                if (parameters.length == 1) {
                    Object reply = parameters[0];
                    return createEntryReplyMessage(type, (Boolean) reply);
                } else {
                    return new byte[0];
                }
            }
            case RegistrationReply -> {
                if (parameters.length == 2) {
                    Object reply = parameters[0];
                    Object error = parameters[1];
                    return createRegistrationReplyMessage(type, (Boolean) reply, (TypeErrorRegistration) error);
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
            case VerifyEmailRequest -> {
                if (parameters.length == 2) {
                    Object email = parameters[0];
                    Object password = parameters[1];
                    return createVerifyEmailRequestMessage(type, (String) email, (String) password);
                } else {
                    return new byte[0];
                }
            }
            case VerifyEmailReply -> {
                if (parameters.length == 1) {
                    Object reply = parameters[0];
                    return createVerifyEmailReplyMessage(type, (Boolean) reply);
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
        }
        return new byte[0];
    }

    public static HashMap<TypeData, ?> deserializeData(TypeMessage type, byte[] data) {
        return switch (type) {
            case EntryRequest -> takeEntryRequestMessage(data);
            case RegistrationRequest -> takeRegistrationRequestMessage(data);
            case EntryReply -> takeEntryReplyMessage(data);
            case RegistrationReply -> takeRegistrationReplyMessage(data);
            case ResetPasswordRequest -> takeResetPasswordRequestMessage(data);
            case ResetPasswordReply -> takeResetPasswordReplyMessage(data);
            case VerifyEmailRequest -> takeVerifyEmailRequestMessage(data);
            case VerifyEmailReply -> takeVerifyEmailReplyMessage(data);
            case ChangePasswordRequest -> takeChangePasswordRequestMessage(data);
            case ChangePasswordReply -> takeChangePasswordReplyMessage(data);
        };
    }

    public static TypeMessage getTypeMessage(byte[] data) {
        TypeMessage type = null;
        try {
            int numberType = ClientServerSerializeProtocol_pb.General.parseFrom(data).getType();
            type = TypeMessage.getTypeMsg(numberType);
        } catch (InvalidProtocolBufferException exception) {
            System.out.println("Error in protobuf deserialised type");
        }
        return type;
    }

    private static byte[] createEntryRequestMessage(TypeMessage type, String login, String password) {
        ClientServerSerializeProtocol_pb.EntryRequest msgEntryRequest = ClientServerSerializeProtocol_pb.EntryRequest.newBuilder()
                .setLogin(login)
                .setPassword(password)
                .build();
        ClientServerSerializeProtocol_pb.General resMsg = ClientServerSerializeProtocol_pb.General.newBuilder()
                .setType(type.getValue())
                .setEntryRequest(msgEntryRequest)
                .build();
        return resMsg.toByteArray();
    }

    private static byte[] createEntryReplyMessage(TypeMessage type, boolean reply) {
        ClientServerSerializeProtocol_pb.EntryReply msgEntryReply = ClientServerSerializeProtocol_pb.EntryReply.newBuilder()
                .setReply(reply)
                .build();
        ClientServerSerializeProtocol_pb.General resMsg = ClientServerSerializeProtocol_pb.General.newBuilder()
                .setType(type.getValue())
                .setEntryReply(msgEntryReply)
                .build();
        return resMsg.toByteArray();
    }

    private static byte[] createRegistrationRequestMessage(TypeMessage type, String login, String email, String password) {
        ClientServerSerializeProtocol_pb.RegistrationRequest msgRegRequest = ClientServerSerializeProtocol_pb.RegistrationRequest.newBuilder()
                .setLogin(login)
                .setEmail(email)
                .setPassword(password)
                .build();
        ClientServerSerializeProtocol_pb.General resMsg = ClientServerSerializeProtocol_pb.General.newBuilder()
                .setType(type.getValue())
                .setRegistrationRequest(msgRegRequest)
                .build();
        return resMsg.toByteArray();
    }

    private static byte[] createRegistrationReplyMessage(TypeMessage type, boolean reply, TypeErrorRegistration error) {
        ClientServerSerializeProtocol_pb.RegistrationReply msgRegReply = ClientServerSerializeProtocol_pb.RegistrationReply.newBuilder()
                .setReply(reply)
                .setError(ClientServerSerializeProtocol_pb.RegistrationReply.Error.forNumber(error.getValue()))
                .build();
        ClientServerSerializeProtocol_pb.General resMsg = ClientServerSerializeProtocol_pb.General.newBuilder()
                .setType(type.getValue())
                .setRegistrationReply(msgRegReply)
                .build();
        return resMsg.toByteArray();
    }

    private static byte[] createResetPasswordRequestMessage(TypeMessage type, String email) {
        ClientServerSerializeProtocol_pb.ResetPasswordRequest msgResetRequest = ClientServerSerializeProtocol_pb.ResetPasswordRequest.newBuilder()
                .setEmail(email)
                .build();
        ClientServerSerializeProtocol_pb.General resMsg = ClientServerSerializeProtocol_pb.General.newBuilder()
                .setType(type.getValue())
                .setResetPasswordRequest(msgResetRequest)
                .build();
        return resMsg.toByteArray();
    }

    private static byte[] createResetPasswordReplyMessage(TypeMessage type, boolean reply) {
        ClientServerSerializeProtocol_pb.ResetPasswordReply msgResetReply = ClientServerSerializeProtocol_pb.ResetPasswordReply.newBuilder()
                .setReply(reply)
                .build();
        ClientServerSerializeProtocol_pb.General resMsg = ClientServerSerializeProtocol_pb.General.newBuilder()
                .setType(type.getValue())
                .setResetPasswordReply(msgResetReply)
                .build();
        return resMsg.toByteArray();
    }

    private static byte[] createVerifyEmailRequestMessage(TypeMessage type, String email, String code) {
        ClientServerSerializeProtocol_pb.VerifyEmailRequest msgVerifyEmailRequest = ClientServerSerializeProtocol_pb.VerifyEmailRequest.newBuilder()
                .setEmail(email)
                .setCode(code)
                .build();
        ClientServerSerializeProtocol_pb.General resMsg = ClientServerSerializeProtocol_pb.General.newBuilder()
                .setType(type.getValue())
                .setVerifyEmailRequest(msgVerifyEmailRequest)
                .build();
        return resMsg.toByteArray();
    }

    private static byte[] createVerifyEmailReplyMessage(TypeMessage type, boolean reply) {
        ClientServerSerializeProtocol_pb.VerifyEmailReply msgVerifyEmailReply = ClientServerSerializeProtocol_pb.VerifyEmailReply.newBuilder()
                .setReply(reply)
                .build();
        ClientServerSerializeProtocol_pb.General resMsg = ClientServerSerializeProtocol_pb.General.newBuilder()
                .setType(type.getValue())
                .setVerifyEmailReply(msgVerifyEmailReply)
                .build();
        return resMsg.toByteArray();
    }

    private static byte[] createChangePasswordRequestMessage(TypeMessage type, String email, String password) {
        ClientServerSerializeProtocol_pb.ChangePasswordRequest msgChangePasswordRequest = ClientServerSerializeProtocol_pb.ChangePasswordRequest.newBuilder()
                .setEmail(email)
                .setPassword(password)
                .build();
        ClientServerSerializeProtocol_pb.General resMsg = ClientServerSerializeProtocol_pb.General.newBuilder()
                .setType(type.getValue())
                .setChangePasswordRequest(msgChangePasswordRequest)
                .build();
        return resMsg.toByteArray();
    }

    private static byte[] createChangePasswordReplyMessage(TypeMessage type, boolean reply) {
        ClientServerSerializeProtocol_pb.ChangePasswordReply msgChangePasswordReply = ClientServerSerializeProtocol_pb.ChangePasswordReply.newBuilder()
                .setReply(reply)
                .build();
        ClientServerSerializeProtocol_pb.General resMsg = ClientServerSerializeProtocol_pb.General.newBuilder()
                .setType(type.getValue())
                .setChangePasswordReply(msgChangePasswordReply)
                .build();
        return resMsg.toByteArray();
    }

    private static HashMap<TypeData, String> takeEntryRequestMessage(byte[] data) {
        HashMap<TypeData, String> result = new HashMap<>();
        try {
            result.put(TypeData.Login, ClientServerSerializeProtocol_pb.General.parseFrom(data)
                    .getEntryRequest().getLogin());
            result.put(TypeData.Password, ClientServerSerializeProtocol_pb.General.parseFrom(data)
                    .getEntryRequest().getPassword());
        } catch (InvalidProtocolBufferException exception) {
            System.out.println("Error in protobuf deserialised data");
        }
        return result;
    }

    private static HashMap<TypeData, String> takeRegistrationRequestMessage(byte[] data) {
        HashMap<TypeData, String> result = new HashMap<>();
        try {
            result.put(TypeData.Login, ClientServerSerializeProtocol_pb.General.parseFrom(data).
                    getRegistrationRequest().getLogin());
            result.put(TypeData.Email, ClientServerSerializeProtocol_pb.General.parseFrom(data).
                    getRegistrationRequest().getEmail());
            result.put(TypeData.Password, ClientServerSerializeProtocol_pb.General.parseFrom(data).
                    getRegistrationRequest().getPassword());
        } catch (InvalidProtocolBufferException exception) {
            System.out.println("Error in protobuf deserialised data");
        }
        return result;
    }

    private static HashMap<TypeData, Boolean> takeEntryReplyMessage(byte[] data) {
        HashMap<TypeData, Boolean> result = new HashMap<>();
        try {
            result.put(TypeData.BoolReply, ClientServerSerializeProtocol_pb.General.parseFrom(data)
                    .getEntryReply().getReply());
        } catch (InvalidProtocolBufferException exception) {
            System.out.println("Error in protobuf deserialised data");
        }
        return result;
    }

    private static HashMap<TypeData, Object> takeRegistrationReplyMessage(byte[] data) {
        HashMap<TypeData, Object> result = new HashMap<>();
        try {
            result.put(TypeData.BoolReply, ClientServerSerializeProtocol_pb.General.parseFrom(data)
                    .getRegistrationReply().getReply());
            result.put(TypeData.ErrorReg, TypeErrorRegistration.getTypeError(
                    ClientServerSerializeProtocol_pb.General.parseFrom(data).
                            getRegistrationReply().getError().getNumber()));
        } catch (InvalidProtocolBufferException exception) {
            System.out.println("Error in protobuf deserialised data");
        }
        return result;
    }

    private static HashMap<TypeData, String> takeResetPasswordRequestMessage(byte[] data) {
        HashMap<TypeData, String> result = new HashMap<>();
        try {
            result.put(TypeData.Email, ClientServerSerializeProtocol_pb.General.parseFrom(data).
                    getResetPasswordRequest().getEmail());
        } catch (InvalidProtocolBufferException exception) {
            System.out.println("Error in protobuf deserialised data");
        }
        return result;
    }

    private static HashMap<TypeData, Boolean> takeResetPasswordReplyMessage(byte[] data) {
        HashMap<TypeData, Boolean> result = new HashMap<>();
        try {
            result.put(TypeData.BoolReply, ClientServerSerializeProtocol_pb.General.parseFrom(data)
                    .getResetPasswordReply().getReply());
        } catch (InvalidProtocolBufferException exception) {
            System.out.println("Error in protobuf deserialised data");
        }
        return result;
    }

    private static HashMap<TypeData, String> takeVerifyEmailRequestMessage(byte[] data) {
        HashMap<TypeData, String> result = new HashMap<>();
        try {
            result.put(TypeData.Email, ClientServerSerializeProtocol_pb.General.parseFrom(data).
                    getVerifyEmailRequest().getEmail());
            result.put(TypeData.VerifyCode, ClientServerSerializeProtocol_pb.General.parseFrom(data).
                    getVerifyEmailRequest().getCode());
        } catch (InvalidProtocolBufferException exception) {
            System.out.println("Error in protobuf deserialised data");
        }
        return result;
    }

    private static HashMap<TypeData, Boolean> takeVerifyEmailReplyMessage(byte[] data) {
        HashMap<TypeData, Boolean> result = new HashMap<>();
        try {
            result.put(TypeData.BoolReply, ClientServerSerializeProtocol_pb.General.parseFrom(data)
                    .getVerifyEmailReply().getReply());
        } catch (InvalidProtocolBufferException exception) {
            System.out.println("Error in protobuf deserialised data");
        }
        return result;
    }

    private static HashMap<TypeData, String> takeChangePasswordRequestMessage(byte[] data) {
        HashMap<TypeData, String> result = new HashMap<>();
        try {
            result.put(TypeData.Email, ClientServerSerializeProtocol_pb.General.parseFrom(data).
                    getChangePasswordRequest().getEmail());
            result.put(TypeData.Password, ClientServerSerializeProtocol_pb.General.parseFrom(data).
                    getChangePasswordRequest().getPassword());
        } catch (InvalidProtocolBufferException exception) {
            System.out.println("Error in protobuf deserialised data");
        }
        return result;
    }

    private static HashMap<TypeData, Boolean> takeChangePasswordReplyMessage(byte[] data) {
        HashMap<TypeData, Boolean> result = new HashMap<>();
        try {
            result.put(TypeData.BoolReply, ClientServerSerializeProtocol_pb.General.parseFrom(data)
                    .getChangePasswordReply().getReply());
        } catch (InvalidProtocolBufferException exception) {
            System.out.println("Error in protobuf deserialised data");
        }
        return result;
    }
}
