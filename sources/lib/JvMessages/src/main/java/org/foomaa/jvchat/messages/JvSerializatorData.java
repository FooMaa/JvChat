package org.foomaa.jvchat.messages;

import java.util.Map;


public class JvSerializatorData {
    public enum TypeMessage {
        Entry(0),
        Registration(1);

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
            case Entry:
                if (parameters.length == 2) {
                    String login = parameters[0];
                    String password = parameters[1];
                    return entryMessage(type, login, password);
                } else {
                    return new byte[0];
                }
            case Registration:
                if (parameters.length == 2) {
                    String login = parameters[0];
                    String password = parameters[1];
                    return registrationMessage(type, login, password);
                } else {
                    return new byte[0];
                }
        }

        return new byte[0];
    }

    private static byte[] entryMessage(TypeMessage type, String login, String password) {
        Auth_pb.DbEntryProto msg = Auth_pb.DbEntryProto.newBuilder()
                .setType(type.getValue())
                .setLogin(login)
                .setPassword(password)
                .build();
        return msg.toByteArray();
    }

    private static byte[] registrationMessage(TypeMessage type, String login, String password) {
        Auth_pb.DbEntryProto msg = Auth_pb.DbEntryProto.newBuilder()
                .setType(type.getValue())
                .setLogin(login)
                .setPassword(password)
                .build();
        return msg.toByteArray();
    }
}
