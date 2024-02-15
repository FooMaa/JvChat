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
                    return createEntryMessage(type, login, password);
                } else {
                    return new byte[0];
                }
            case RegistrationRequest:
                if (parameters.length == 2) {
                    String login = parameters[0];
                    String password = parameters[1];
                    return createRegistrationMessage(type, login, password);
                } else {
                    return new byte[0];
                }
        }
        return new byte[0];
    }

    public static byte[] deserialiseData(byte[] data) throws InvalidProtocolBufferException {
        final  Auth_pb.EntryRequestProto copiedAlbumProtos = Auth_pb.EntryRequestProto.parseFrom(data);
        return data;
    }

    private static byte[] createEntryMessage(TypeMessage type, String login, String password) {
        Auth_pb.EntryRequestProto msg = Auth_pb.EntryRequestProto.newBuilder()
                .setType(type.getValue())
                .setLogin(login)
                .setPassword(password)
                .build();
        return msg.toByteArray();
    }

    private static byte[] createRegistrationMessage(TypeMessage type, String login, String password) {
        Auth_pb.RegistrationRequestProto msg = Auth_pb.RegistrationRequestProto.newBuilder()
                .setType(type.getValue())
                .setLogin(login)
                .setPassword(password)
                .build();
        return msg.toByteArray();
    }
}
