package org.foomaa.jvchat.globaldefines;

import java.util.Objects;


public class JvDbGlobalDefines {
    private static JvDbGlobalDefines instance;

    private JvDbGlobalDefines() {}

    public static JvDbGlobalDefines getInstance() {
        if (instance == null) {
            instance = new JvDbGlobalDefines();
        }
        return instance;
    }

    public enum LineKeys {
        Sender("sender"),
        Receiver("receiver"),
        LastMessage("last_message"),
        DateTimeMessage("datetime_last_message"),
        StatusMessage("status_message"),
        Status("status"),
        LastOnlineTime("last_online_time");

        private final String value;

        LineKeys(String newValue) {
            value = newValue;
        }

        public String getValue() {
            return value;
        }

        public static LineKeys getTypeLineKey(String value) {
            LineKeys[] lineKeys = LineKeys.values();
            for (LineKeys lineKey : lineKeys) {
                if (Objects.equals(lineKey.getValue(), value)) {
                    return lineKey;
                }
            }
            return null;
        }
    }
}