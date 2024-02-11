package org.foomaa.jvchat.messages;

public class JvSerializatorData {
    public enum TypeMessage {
        Entry,
        Registration
    }

    public static byte[] serialiseData(TypeMessage type, String ... parameters) {
        switch (type) {
            case Entry:
                if (parameters.length == 2) {
                    String login = parameters[0];
                    String password = parameters[1];
                    return entryMessage(login, password);
                } else {
                    return new byte[0];
                }
            case Registration:
                if (parameters.length == 2) {
                    String login = parameters[0];
                    String password = parameters[1];
                    return registrationMessage(login, password);
                } else {
                    return new byte[0];
                }
        }

        return new byte[0];
    }

    private static byte[] entryMessage(String login, String password) {
        Auth_pb.DbEntryProto msg = Auth_pb.DbEntryProto.newBuilder()
                .setLogin(login)
                .setPassword(password)
                .build();
        return msg.toByteArray();
    }

    private static byte[] registrationMessage(String login, String password) {
        Auth_pb.DbEntryProto msg = Auth_pb.DbEntryProto.newBuilder()
                .setLogin(login)
                .setPassword(password)
                .build();
        return msg.toByteArray();
    }
}
