package org.foomaa.jvchat.globaldefines;

import java.util.Objects;


public class JvDbGlobalDefines {
    JvDbGlobalDefines() {}

    public enum LineKeys {
        Sender("sender"),
        Receiver("receiver"),
        Login("login"),
        LastMessageText("last_message_text"),
        UuidUser("uuid_user"),
        UuidChat("uuid_chat"),
        UuidMessage("uuid_message"),
        UuidSender("uuid_sender"),
        UuidReceiver("uuid_receiver"),
        IsLoginSentLastMessage("is_login_sent_last_message"),
        DateTimeLastMessage("datetime_last_message"),
        StatusMessage("status_message"),
        StatusOnline("status_online"),
        LastOnlineTime("last_online_time"),
        TextMessage("text_message"),
        DateTimeMessage("datetime_message");

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