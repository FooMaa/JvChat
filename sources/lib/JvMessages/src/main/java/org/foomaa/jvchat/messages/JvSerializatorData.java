package org.foomaa.jvchat.messages;

import com.google.protobuf.InvalidProtocolBufferException;
import java.util.HashMap;

public class JvSerializatorData {

    public enum TypeMessage {
        EntryRequest(0),
        EntryReply(1),
        RegistrationRequest(2),
        RegistrationReply(3),
        CodeMsgEmailRequest(4),
        CodeMsgEmailReply(5),
        VerifyFamousEmailRequest(6),
        VerifyFamousEmailReply(7),
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
        RegCode,
        ErrorReg,
        BoolReply,
        VerifyFamousCode,
        TypeEmailCodeMsg
    }

    public enum TypeErrorRegistration {
        Login(ClientServerSerializeProtocol_pb.RegistrationReply.Error.Login_VALUE),
        Email(ClientServerSerializeProtocol_pb.RegistrationReply.Error.Email_VALUE),
        LoginAndEmail(ClientServerSerializeProtocol_pb.RegistrationReply.Error.LoginAndEmail_VALUE),
        Code(ClientServerSerializeProtocol_pb.RegistrationReply.Error.Code_VALUE),
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

    public enum TypeEmailCodeMsg {
        Registration(ClientServerSerializeProtocol_pb.CodeMsgEmailRequest.
                TypeMsg.Registration_VALUE),
        ResetPassword(ClientServerSerializeProtocol_pb.CodeMsgEmailRequest.
                TypeMsg.ResetPassword_VALUE);

        private final int value;

        TypeEmailCodeMsg(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public boolean compare(int i) {
            return value == i;
        }

        public static TypeEmailCodeMsg getTypeEmailCodeMsg(int value)
        {
            TypeEmailCodeMsg[] typesCodeEmail = TypeEmailCodeMsg.values();
            for (TypeEmailCodeMsg type : typesCodeEmail) {
                if (type.compare(value))
                    return type;
            }
            return null;
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
                if (parameters.length == 4) {
                    Object login = parameters[0];
                    Object email = parameters[1];
                    Object password = parameters[2];
                    Object code = parameters[3];
                    return createRegistrationRequestMessage(type,
                            (String) login, (String) email, (String) password, (String) code);
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
                    return createRegistrationReplyMessage(type,
                            (Boolean) reply,
                            (TypeErrorRegistration) error);
                } else {
                    return new byte[0];
                }
            }
            case CodeMsgEmailRequest -> {
                if (parameters.length == 2) {
                    Object typeEmailCode = parameters[0];
                    Object email = parameters[1];
                    return createCodeMsgEmailRequestMessage(type,
                            (TypeEmailCodeMsg) typeEmailCode,
                            (String) email);
                } else {
                    return new byte[0];
                }
            }
            case CodeMsgEmailReply -> {
                if (parameters.length == 2) {
                    Object typeEmailCode = parameters[0];
                    Object reply = parameters[1];
                    return createCodeMsgEmailReplyMessage(type,
                            (TypeEmailCodeMsg) typeEmailCode,
                            (Boolean) reply);
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
        }
        return new byte[0];
    }

    public static HashMap<TypeData, ?> deserializeData(TypeMessage type, byte[] data) {
        return switch (type) {
            case EntryRequest -> takeEntryRequestMessage(data);
            case RegistrationRequest -> takeRegistrationRequestMessage(data);
            case EntryReply -> takeEntryReplyMessage(data);
            case RegistrationReply -> takeRegistrationReplyMessage(data);
            case CodeMsgEmailRequest -> takeCodeMsgEmailRequestMessage(data);
            case CodeMsgEmailReply -> takeCodeMsgEmailReplyMessage(data);
            case VerifyFamousEmailRequest -> takeVerifyFamousEmailRequestMessage(data);
            case VerifyFamousEmailReply -> takeVerifyFamousEmailReplyMessage(data);
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

    private static byte[] createRegistrationRequestMessage(TypeMessage type, String login, String email, String password, String code) {
        ClientServerSerializeProtocol_pb.RegistrationRequest msgRegRequest = ClientServerSerializeProtocol_pb.RegistrationRequest.newBuilder()
                .setLogin(login)
                .setEmail(email)
                .setPassword(password)
                .setCode(code)
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

    private static byte[] createCodeMsgEmailRequestMessage(TypeMessage type, TypeEmailCodeMsg typeMsg, String email) {
        ClientServerSerializeProtocol_pb.CodeMsgEmailRequest msgResetRequest = ClientServerSerializeProtocol_pb.CodeMsgEmailRequest.newBuilder()
                .setTypeMsg(ClientServerSerializeProtocol_pb.CodeMsgEmailRequest.TypeMsg.forNumber(typeMsg.getValue()))
                .setEmail(email)
                .build();
        ClientServerSerializeProtocol_pb.General resMsg = ClientServerSerializeProtocol_pb.General.newBuilder()
                .setType(type.getValue())
                .setCodeMsgEmailRequest(msgResetRequest)
                .build();
        return resMsg.toByteArray();
    }

    private static byte[] createCodeMsgEmailReplyMessage(TypeMessage type, TypeEmailCodeMsg typeMsg, boolean reply) {
        ClientServerSerializeProtocol_pb.CodeMsgEmailReply msgResetReply = ClientServerSerializeProtocol_pb.CodeMsgEmailReply.newBuilder()
                .setTypeMsg(ClientServerSerializeProtocol_pb.CodeMsgEmailReply.TypeMsg.forNumber(typeMsg.getValue()))
                .setReply(reply)
                .build();
        ClientServerSerializeProtocol_pb.General resMsg = ClientServerSerializeProtocol_pb.General.newBuilder()
                .setType(type.getValue())
                .setCodeMsgEmailReply(msgResetReply)
                .build();
        return resMsg.toByteArray();
    }

    private static byte[] createVerifyFamousEmailRequestMessage(TypeMessage type, String email, String code) {
        ClientServerSerializeProtocol_pb.VerifyFamousEmailRequest msgVerifyFamousEmailRequest = ClientServerSerializeProtocol_pb.VerifyFamousEmailRequest.newBuilder()
                .setEmail(email)
                .setCode(code)
                .build();
        ClientServerSerializeProtocol_pb.General resMsg = ClientServerSerializeProtocol_pb.General.newBuilder()
                .setType(type.getValue())
                .setVerifyFamousEmailRequest(msgVerifyFamousEmailRequest)
                .build();
        return resMsg.toByteArray();
    }

    private static byte[] createVerifyFamousEmailReplyMessage(TypeMessage type, boolean reply) {
        ClientServerSerializeProtocol_pb.VerifyFamousEmailReply msgVerifyFamousEmailReply = ClientServerSerializeProtocol_pb.VerifyFamousEmailReply.newBuilder()
                .setReply(reply)
                .build();
        ClientServerSerializeProtocol_pb.General resMsg = ClientServerSerializeProtocol_pb.General.newBuilder()
                .setType(type.getValue())
                .setVerifyFamousEmailReply(msgVerifyFamousEmailReply)
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

    private static HashMap<TypeData, String> takeRegistrationRequestMessage(byte[] data) {
        HashMap<TypeData, String> result = new HashMap<>();
        try {
            result.put(TypeData.Login, ClientServerSerializeProtocol_pb.General.parseFrom(data).
                    getRegistrationRequest().getLogin());
            result.put(TypeData.Email, ClientServerSerializeProtocol_pb.General.parseFrom(data).
                    getRegistrationRequest().getEmail());
            result.put(TypeData.Password, ClientServerSerializeProtocol_pb.General.parseFrom(data).
                    getRegistrationRequest().getPassword());
            result.put(TypeData.RegCode, ClientServerSerializeProtocol_pb.General.parseFrom(data).
                    getRegistrationRequest().getPassword());
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

    private static HashMap<TypeData, Object> takeCodeMsgEmailRequestMessage(byte[] data) {
        HashMap<TypeData, Object> result = new HashMap<>();
        try {
            result.put(TypeData.TypeEmailCodeMsg, TypeEmailCodeMsg.getTypeEmailCodeMsg(
                    ClientServerSerializeProtocol_pb.General.parseFrom(data).
                    getCodeMsgEmailRequest().getTypeMsg().getNumber()));
            result.put(TypeData.Email, ClientServerSerializeProtocol_pb.General.parseFrom(data).
                    getCodeMsgEmailRequest().getEmail());
        } catch (InvalidProtocolBufferException exception) {
            System.out.println("Error in protobuf deserialised data");
        }
        return result;
    }

    private static HashMap<TypeData, Object> takeCodeMsgEmailReplyMessage(byte[] data) {
        HashMap<TypeData, Object> result = new HashMap<>();
        try {
            result.put(TypeData.TypeEmailCodeMsg, TypeEmailCodeMsg.getTypeEmailCodeMsg(
                    ClientServerSerializeProtocol_pb.General.parseFrom(data).
                            getCodeMsgEmailReply().getTypeMsg().getNumber()));
            result.put(TypeData.BoolReply, ClientServerSerializeProtocol_pb.General.parseFrom(data)
                    .getCodeMsgEmailReply().getReply());
        } catch (InvalidProtocolBufferException exception) {
            System.out.println("Error in protobuf deserialised data");
        }
        return result;
    }

    private static HashMap<TypeData, String> takeVerifyFamousEmailRequestMessage(byte[] data) {
        HashMap<TypeData, String> result = new HashMap<>();
        try {
            result.put(TypeData.Email, ClientServerSerializeProtocol_pb.General.parseFrom(data).
                    getVerifyFamousEmailRequest().getEmail());
            result.put(TypeData.VerifyFamousCode, ClientServerSerializeProtocol_pb.General.parseFrom(data).
                    getVerifyFamousEmailRequest().getCode());
        } catch (InvalidProtocolBufferException exception) {
            System.out.println("Error in protobuf deserialised data");
        }
        return result;
    }

    private static HashMap<TypeData, Boolean> takeVerifyFamousEmailReplyMessage(byte[] data) {
        HashMap<TypeData, Boolean> result = new HashMap<>();
        try {
            result.put(TypeData.BoolReply, ClientServerSerializeProtocol_pb.General.parseFrom(data)
                    .getVerifyFamousEmailReply().getReply());
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