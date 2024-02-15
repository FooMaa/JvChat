package org.foomaa.jvchat.messages;


import com.google.protobuf.InvalidProtocolBufferException;

public class JvSerializatorData {
    public enum TypeMessage {
        EntryRequest(0),
        EntryReply(1),
        RegistrationRequest(2),
        RegistrationReply(3);

        private final int value;
        private TypeMessage(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }

    public static byte[] serialiseData(TypeMessage type, String ... parameters) {
        switch (type) {
            case EntryRequest:
                if (parameters.length == 2) {
                    String login = parameters[0];
                    String password = parameters[1];
                    return createEntryRequestMessage(type, login, password);
                } else {
                    return new byte[0];
                }
            case RegistrationRequest:
                if (parameters.length == 2) {
                    String login = parameters[0];
                    String password = parameters[1];
                    return createRegistrationRequestMessage(type, login, password);
                } else {
                    return new byte[0];
                }
        }
        return new byte[0];
    }

    public static void deSerialiseData(byte[] data) {
        TypeMessage type = null;
        try {
            int numberType = Auth_pb.GeneralAuthProto.parseFrom(data).getType();
            type = TypeMessage.values()[numberType];
        } catch (InvalidProtocolBufferException exception) {
            System.out.println("Error in protobuf deserialised");
        }

        switch (type) {
            case EntryRequest:
                break;
            case RegistrationRequest:
                break;
        }
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

    private static void takeEntryRequestMessage(Auth_pb.EntryRequestProto entryRequest) {
        System.out.println("Deserialize: " + entryRequest.getLogin() + " " + entryRequest.getPassword());
    }
}
