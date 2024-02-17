package org.foomaa.jvchat.messages;

import com.google.protobuf.InvalidProtocolBufferException;

import java.util.HashMap;

public class JvSerializatorData {
    public enum TypeMessage {
        EntryRequest(0),
        EntryReply(1),
        RegistrationRequest(2),
        RegistrationReply(3);

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
        Password,
        BoolReply
    }

    public static <TYPE> byte[] serialiseData(TypeMessage type, TYPE ... parameters) {
        switch (type) {
            case EntryRequest:
                if (parameters.length == 2) {
                    TYPE login = parameters[0];
                    TYPE password = parameters[1];
                    return createEntryRequestMessage(type,
                            (String) login, (String) password);
                } else {
                    return new byte[0];
                }
            case RegistrationRequest:
                if (parameters.length == 2) {
                    TYPE login = parameters[0];
                    TYPE password = parameters[1];
                    return createRegistrationRequestMessage(type,
                            (String) login, (String) password);
                } else {
                    return new byte[0];
                }
            case EntryReply:
                if (parameters.length == 1) {
                    TYPE reply = parameters[0];
                    return createEntryReplyMessage(type, (Boolean) reply);
                } else {
                    return new byte[0];
                }
        }
        return new byte[0];
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
        Auth_pb.EntryReplyProto msgReplyRequest = Auth_pb.EntryReplyProto.newBuilder()
                .setReply(reply)
                .build();
        Auth_pb.GeneralAuthProto resMsg = Auth_pb.GeneralAuthProto.newBuilder()
                .setType(type.getValue())
                .setEntryReply(msgReplyRequest)
                .build();
        return resMsg.toByteArray();
    }

    private static byte[] createRegistrationRequestMessage(TypeMessage type, String login, String password) {
        Auth_pb.RegistrationRequestProto msgRegRequest = Auth_pb.RegistrationRequestProto.newBuilder()
                .setLogin(login)
                .setPassword(password)
                .build();
        Auth_pb.GeneralAuthProto resMsg = Auth_pb.GeneralAuthProto.newBuilder()
                .setType(type.getValue())
                .setRegistrationRequest(msgRegRequest)
                .build();
        return resMsg.toByteArray();
    }

    public static HashMap<TypeData, String> takeEntryRequestMessage(byte[] data) {
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

    public static HashMap<TypeData, String> takeRegistrationRequestMessage(byte[] data) {
        HashMap<TypeData, String> result = new HashMap<>();
        try {
            result.put(TypeData.Login, Auth_pb.GeneralAuthProto.parseFrom(data).
                    getRegistrationRequest().getLogin());
            result.put(TypeData.Password, Auth_pb.GeneralAuthProto.parseFrom(data).
                    getRegistrationRequest().getPassword());
        } catch (InvalidProtocolBufferException exception) {
            System.out.println("Error in protobuf deserialised data");
        }
        return result;
    }

    public static HashMap<TypeData, Boolean> takeEntryReplyMessage(byte[] data) {
        HashMap<TypeData, Boolean> result = new HashMap<>();
        try {
            result.put(TypeData.Login, Auth_pb.GeneralAuthProto.parseFrom(data)
                    .getEntryReply().getReply());
        } catch (InvalidProtocolBufferException exception) {
            System.out.println("Error in protobuf deserialised data");
        }
        return result;
    }
}
