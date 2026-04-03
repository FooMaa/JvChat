package org.foomaa.jvchat.globaldefines;

import lombok.Builder;
import lombok.Getter;

public class MainChatsGlobalDefines {
    @Builder
    MainChatsGlobalDefines() {}

    @Getter
    public enum TypeStatusMessage {
        Error(0),
        Sent(1),
        Delivered(2),
        Read(3);

        private final int value;

        TypeStatusMessage(int newValue) {
            value = newValue;
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
            return TypeStatusMessage.Error;
        }
    }

    @Getter
    public enum TypeStatusOnline {
        Error(0),
        Offline(1),
        Online(2);

        private final int value;

        TypeStatusOnline(int newValue) {
            value = newValue;
        }

        public static TypeStatusOnline getTypeStatusOnline(int value) {
            TypeStatusOnline[] statusKeys = TypeStatusOnline.values();
            for (TypeStatusOnline statusKey : statusKeys) {
                if (statusKey.getValue() == value) {
                    return statusKey;
                }
            }
            return TypeStatusOnline.Error;
        }
    }
}
