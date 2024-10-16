package org.foomaa.jvchat.globaldefines;

public class JvMainChatsGlobalDefines {
    private static JvMainChatsGlobalDefines instance;

    private JvMainChatsGlobalDefines() {}

    public static JvMainChatsGlobalDefines getInstance() {
        if (instance == null) {
            instance = new JvMainChatsGlobalDefines();
        }
        return instance;
    }

    public enum TypeStatusMessage {
        Error(0),
        Sent(1),
        Delivered(2),
        Read(3);

        private final int value;

        TypeStatusMessage(int newValue) {
            value = newValue;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        public static TypeStatusMessage getTypeStatusMessage(int value) {
            TypeStatusMessage[] statusKeys = TypeStatusMessage.values();
            for (TypeStatusMessage statusKey : statusKeys) {
                if (statusKey.getValue() == value) {
                    return statusKey;
                }
            }
            return null;
        }
    }

    public enum TypeStatusOnline {
        Error(0),
        Offline(1),
        Online(2);

        private final int value;

        TypeStatusOnline(int newValue) {
            value = newValue;
        }

        public int getValue() {
            return value;
        }

        public static TypeStatusOnline getTypeStatusOnline(int value) {
            TypeStatusOnline[] statusKeys = TypeStatusOnline.values();
            for (TypeStatusOnline statusKey : statusKeys) {
                if (statusKey.getValue() == value) {
                    return statusKey;
                }
            }
            return null;
        }
    }
}
