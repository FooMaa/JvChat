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
        VerifyResetPasswordRequest(6),
        VerifyResetPasswordReply(7);

        private final int value;

        TypeMessage(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
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
        Login(0),
        EMAIL(1),
        LoginAndEmail(2),
        NoError (9999);

        private final int value;

        TypeErrorRegistration(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public static <TYPEPARAM> byte[] serialiseData(TypeMessage type, TYPEPARAM... parameters) {
        switch (type) {
            case EntryRequest:
                if (parameters.length == 2) {
                    TYPEPARAM login = parameters[0];
                    TYPEPARAM password = parameters[1];
                    return createEntryRequestMessage(type,
                            (String) login, (String) password);
                } else {
                    return new byte[0];
                }
            case RegistrationRequest:
                if (parameters.length == 3) {
                    TYPEPARAM login = parameters[0];
                    TYPEPARAM email = parameters[1];
                    TYPEPARAM password = parameters[2];
                    return createRegistrationRequestMessage(type,
                            (String) login, (String) email, (String) password);
                } else {
                    return new byte[0];
                }
            case EntryReply:
                if (parameters.length == 1) {
                    TYPEPARAM reply = parameters[0];
                    return createEntryReplyMessage(type, (Boolean) reply);
                } else {
                    return new byte[0];
                }
            case RegistrationReply:
                if (parameters.length == 2) {
                    TYPEPARAM reply = parameters[0];
                    TYPEPARAM error = parameters[1];
                    return createRegistrationReplyMessage(type, (Boolean) reply, (TypeErrorRegistration) error);
                } else {
                    return new byte[0];
                }
            case ResetPasswordRequest:
                if (parameters.length == 1) {
                    TYPEPARAM email = parameters[0];
                    return createResetPasswordRequestMessage(type, (String) email);
                } else {
                    return new byte[0];
                }
            case ResetPasswordReply:
                if (parameters.length == 1) {
                    TYPEPARAM reply = parameters[0];
                    return createResetPasswordReplyMessage(type, (Boolean) reply);
                } else {
                    return new byte[0];
                }
            case VerifyResetPasswordRequest:
                if (parameters.length == 1) {
                    TYPEPARAM code = parameters[0];
                    return createVerifyResetPasswordRequestMessage(type, (String) code);
                } else {
                    return new byte[0];
                }
            case VerifyResetPasswordReply:
                if (parameters.length == 1) {
                    TYPEPARAM reply = parameters[0];
                    return createVerifyResetPasswordReplyMessage(type, (Boolean) reply);
                } else {
                    return new byte[0];
                }
        }
        return new byte[0];
    }

    public static HashMap<TypeData, ?> deserializeData(TypeMessage type, byte[] data) {
        switch (type) {
            case EntryRequest:
               return takeEntryRequestMessage(data);
            case RegistrationRequest:
                return takeRegistrationRequestMessage(data);
            case EntryReply:
                return takeEntryReplyMessage(data);
            case RegistrationReply:
                return takeRegistrationReplyMessage(data);
            case ResetPasswordRequest:
                return takeResetPasswordRequestMessage(data);
            case ResetPasswordReply:
                return takeResetPasswordReplyMessage(data);
            case VerifyResetPasswordRequest:
                return takeVerifyPasswordRequestMessage(data);
            case VerifyResetPasswordReply:
                return takeVerifyResetPasswordReplyMessage(data);
        }
        return new HashMap<>();
     }

     public static HashMap<TypeData, TypeErrorRegistration> getErrorRegistration(byte[] data) {
         HashMap<TypeData, TypeErrorRegistration> result = new HashMap<>();
         try {
             result.put(TypeData.ErrorReg, TypeErrorRegistration.values()[
                     Auth_pb.GeneralAuthProto.parseFrom(data).getRegistrationReply().getError().getNumber()]);
         } catch (InvalidProtocolBufferException exception) {
             System.out.println("Error in protobuf deserialised data");
         }
         return result;
     }

    public static TypeMessage getTypeMessage(byte[] data) {
        TypeMessage type = null;
        try {
            int numberType = Auth_pb.GeneralAuthProto.parseFrom(data).getType();
            type = TypeMessage.values()[numberType];
        } catch (InvalidProtocolBufferException exception) {
            System.out.println("Error in protobuf deserialised type");
        }
        return type;
    }

    private static byte[] createEntryRequestMessage(TypeMessage type, String login, String password) {
        Auth_pb.EntryRequestProto msgEntryRequest = Auth_pb.EntryRequestProto.newBuilder()
                .setLogin(login)
                .setPassword(password)
                .build();
        Auth_pb.GeneralAuthProto resMsg = Auth_pb.GeneralAuthProto.newBuilder()
                .setType(type.getValue())
                .setEntryRequest(msgEntryRequest)
                .build();
        return resMsg.toByteArray();
    }

    private static byte[] createEntryReplyMessage(TypeMessage type, boolean reply) {
        Auth_pb.EntryReplyProto msgEntryReply = Auth_pb.EntryReplyProto.newBuilder()
                .setReply(reply)
                .build();
        Auth_pb.GeneralAuthProto resMsg = Auth_pb.GeneralAuthProto.newBuilder()
                .setType(type.getValue())
                .setEntryReply(msgEntryReply)
                .build();
        return resMsg.toByteArray();
    }

    private static byte[] createRegistrationRequestMessage(TypeMessage type, String login, String email, String password) {
        Auth_pb.RegistrationRequestProto msgRegRequest = Auth_pb.RegistrationRequestProto.newBuilder()
                .setLogin(login)
                .setEmail(email)
                .setPassword(password)
                .build();
        Auth_pb.GeneralAuthProto resMsg = Auth_pb.GeneralAuthProto.newBuilder()
                .setType(type.getValue())
                .setRegistrationRequest(msgRegRequest)
                .build();
        return resMsg.toByteArray();
    }

    private static byte[] createRegistrationReplyMessage(TypeMessage type, boolean reply, TypeErrorRegistration error) {
        Auth_pb.RegistrationReplyProto msgRegReply = Auth_pb.RegistrationReplyProto.newBuilder()
                .setReply(reply)
                .setError(Auth_pb.RegistrationReplyProto.Error.valueOf(error.getValue()))
                .build();
        Auth_pb.GeneralAuthProto resMsg = Auth_pb.GeneralAuthProto.newBuilder()
                .setType(type.getValue())
                .setRegistrationReply(msgRegReply)
                .build();
        return resMsg.toByteArray();
    }

    private static byte[] createResetPasswordRequestMessage(TypeMessage type, String email) {
        Auth_pb.ResetPasswordRequestProto msgResetRequest = Auth_pb.ResetPasswordRequestProto.newBuilder()
                .setEmail(email)
                .build();
        Auth_pb.GeneralAuthProto resMsg = Auth_pb.GeneralAuthProto.newBuilder()
                .setType(type.getValue())
                .setResetPasswordRequest(msgResetRequest)
                .build();
        return resMsg.toByteArray();
    }

    private static byte[] createResetPasswordReplyMessage(TypeMessage type, boolean reply) {
        Auth_pb.ResetPasswordReplyProto msgResetReply = Auth_pb.ResetPasswordReplyProto.newBuilder()
                .setReply(reply)
                .build();
        Auth_pb.GeneralAuthProto resMsg = Auth_pb.GeneralAuthProto.newBuilder()
                .setType(type.getValue())
                .setResetPasswordReply(msgResetReply)
                .build();
        return resMsg.toByteArray();
    }

    private static byte[] createVerifyResetPasswordRequestMessage(TypeMessage type, String code) {
        Auth_pb.VerifyResetPasswordRequestProto msgVerifyResetPasswordRequest = Auth_pb.VerifyResetPasswordRequestProto.newBuilder()
                .setCode(code)
                .build();
        Auth_pb.GeneralAuthProto resMsg = Auth_pb.GeneralAuthProto.newBuilder()
                .setType(type.getValue())
                .setVerifyResetPasswordRequest(msgVerifyResetPasswordRequest)
                .build();
        return resMsg.toByteArray();
    }

    private static byte[] createVerifyResetPasswordReplyMessage(TypeMessage type, boolean reply) {
        Auth_pb.VerifyResetPasswordReplyProto msgVerifyResetPasswordReply = Auth_pb.VerifyResetPasswordReplyProto.newBuilder()
                .setReply(reply)
                .build();
        Auth_pb.GeneralAuthProto resMsg = Auth_pb.GeneralAuthProto.newBuilder()
                .setType(type.getValue())
                .setVerifyResetPasswordReply(msgVerifyResetPasswordReply)
                .build();
        return resMsg.toByteArray();
    }

    private static HashMap<TypeData, String> takeEntryRequestMessage(byte[] data) {
        HashMap<TypeData, String> result = new HashMap<>();
        try {
            result.put(TypeData.Login, Auth_pb.GeneralAuthProto.parseFrom(data)
                    .getEntryRequest().getLogin());
            result.put(TypeData.Password, Auth_pb.GeneralAuthProto.parseFrom(data)
                    .getEntryRequest().getPassword());
        } catch (InvalidProtocolBufferException exception) {
            System.out.println("Error in protobuf deserialised data");
        }
        return result;
    }

    private static HashMap<TypeData, String> takeRegistrationRequestMessage(byte[] data) {
        HashMap<TypeData, String> result = new HashMap<>();
        try {
            result.put(TypeData.Login, Auth_pb.GeneralAuthProto.parseFrom(data).
                    getRegistrationRequest().getLogin());
            result.put(TypeData.Email, Auth_pb.GeneralAuthProto.parseFrom(data).
                    getRegistrationRequest().getEmail());
            result.put(TypeData.Password, Auth_pb.GeneralAuthProto.parseFrom(data).
                    getRegistrationRequest().getPassword());
        } catch (InvalidProtocolBufferException exception) {
            System.out.println("Error in protobuf deserialised data");
        }
        return result;
    }

    private static HashMap<TypeData, Boolean> takeEntryReplyMessage(byte[] data) {
        HashMap<TypeData, Boolean> result = new HashMap<>();
        try {
            result.put(TypeData.BoolReply, Auth_pb.GeneralAuthProto.parseFrom(data)
                    .getEntryReply().getReply());
        } catch (InvalidProtocolBufferException exception) {
            System.out.println("Error in protobuf deserialised data");
        }
        return result;
    }

    private static HashMap<TypeData, Boolean> takeRegistrationReplyMessage(byte[] data) {
        HashMap<TypeData, Boolean> result = new HashMap<>();
        try {
            result.put(TypeData.BoolReply, Auth_pb.GeneralAuthProto.parseFrom(data)
                    .getRegistrationReply().getReply());
        } catch (InvalidProtocolBufferException exception) {
            System.out.println("Error in protobuf deserialised data");
        }
        return result;
    }

    private static HashMap<TypeData, String> takeResetPasswordRequestMessage(byte[] data) {
        HashMap<TypeData, String> result = new HashMap<>();
        try {
            result.put(TypeData.Email, Auth_pb.GeneralAuthProto.parseFrom(data).
                    getResetPasswordRequest().getEmail());
        } catch (InvalidProtocolBufferException exception) {
            System.out.println("Error in protobuf deserialised data");
        }
        return result;
    }

    private static HashMap<TypeData, Boolean> takeResetPasswordReplyMessage(byte[] data) {
        HashMap<TypeData, Boolean> result = new HashMap<>();
        try {
            result.put(TypeData.BoolReply, Auth_pb.GeneralAuthProto.parseFrom(data)
                    .getResetPasswordReply().getReply());
        } catch (InvalidProtocolBufferException exception) {
            System.out.println("Error in protobuf deserialised data");
        }
        return result;
    }

    private static HashMap<TypeData, String> takeVerifyPasswordRequestMessage(byte[] data) {
        HashMap<TypeData, String> result = new HashMap<>();
        try {
            result.put(TypeData.VerifyCode, Auth_pb.GeneralAuthProto.parseFrom(data).
                    getVerifyResetPasswordRequest().getCode());
        } catch (InvalidProtocolBufferException exception) {
            System.out.println("Error in protobuf deserialised data");
        }
        return result;
    }

    private static HashMap<TypeData, Boolean> takeVerifyResetPasswordReplyMessage(byte[] data) {
        HashMap<TypeData, Boolean> result = new HashMap<>();
        try {
            result.put(TypeData.BoolReply, Auth_pb.GeneralAuthProto.parseFrom(data)
                    .getVerifyResetPasswordReply().getReply());
        } catch (InvalidProtocolBufferException exception) {
            System.out.println("Error in protobuf deserialised data");
        }
        return result;
    }
}
