package org.foomaa.jvchat.globaldefines;

import java.util.Objects;

public class JvDbGlobalDefines {
    private static JvDbGlobalDefines instance;

    public enum LineKeys {
        Sender("Sender"),
        Receiver("Receiver"),
        Message("Message"),
        Datetime("Datetime");

        private final String value;

        LineKeys(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static LineKeys getTypeLineKey(String value)
        {
            LineKeys[] lineKeys = LineKeys.values();
            for (LineKeys lineKey : lineKeys) {
                if (Objects.equals(lineKey.getValue(), value)) {
                    return lineKey;
                }
            }
            return null;
        }
    }

    private JvDbGlobalDefines() {}

    public static JvDbGlobalDefines getInstance() {
        if (instance == null) {
            instance = new JvDbGlobalDefines();
        }
        return instance;
    }
}
