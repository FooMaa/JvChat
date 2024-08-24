package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.globaldefines.JvDbGlobalDefines;
import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.settings.JvGetterSettings;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class JvChatsCtrl {
    private static JvChatsCtrl instance;
    private List<Map<JvDbGlobalDefines.LineKeys, String>> chatsInfo;

    public enum TypeStatusMessage {
        Error(-1),
        Sent(0),
        Delivered(1),
        Read(2);

        private final int value;

        TypeStatusMessage(int newValue) {
            value = newValue;
        }

        public int getValue() {
            return value;
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

    private JvChatsCtrl() {}

    static JvChatsCtrl getInstance() {
        if (instance == null) {
            instance = new JvChatsCtrl();
        }
        return instance;
    }

    public void setChatsInfo(List<Map<JvDbGlobalDefines.LineKeys, String>> newChatsInfo) {
        if (chatsInfo != newChatsInfo) {
            chatsInfo = newChatsInfo;
        }
        System.out.println("######################################");
        System.out.println(chatsInfo);
        System.out.println(getTimestampLastMessage("sgubr"));
    }

    public List<Map<JvDbGlobalDefines.LineKeys, String>> getChatsInfo() {
        return chatsInfo;
    }

    public List<String> getLoginsChats() {
        if (chatsInfo.isEmpty()) {
            JvLog.write(JvLog.TypeLog.Warn, "chatsInfo пуст здесь");
            return null;
        }

        String currentUserLogin = JvGetterSettings.getInstance().getBeanUserInfoSettings().getLogin();
        List<String> listLogins = new ArrayList<>();

        for (Map<JvDbGlobalDefines.LineKeys, String> map : chatsInfo) {
            String sender = map.get(JvDbGlobalDefines.LineKeys.Sender);
            String receiver = map.get(JvDbGlobalDefines.LineKeys.Receiver);

            if (Objects.equals(sender, currentUserLogin)) {
                listLogins.add(receiver);
            } else if (Objects.equals(receiver, currentUserLogin)) {
                listLogins.add(sender);
            }
        }

        return listLogins;
    }

    public String getLastMessage(String login) {
        if (chatsInfo.isEmpty()) {
            JvLog.write(JvLog.TypeLog.Error, "chatsInfo пуст здесь");
            return null;
        }

        String lastMessage = "";

        for (Map<JvDbGlobalDefines.LineKeys, String> map : chatsInfo) {
            String sender = map.get(JvDbGlobalDefines.LineKeys.Sender);
            String receiver = map.get(JvDbGlobalDefines.LineKeys.Receiver);

            if (Objects.equals(sender, login) || Objects.equals(receiver, login)) {
                lastMessage = map.get(JvDbGlobalDefines.LineKeys.Message);
                break;
            }
        }

        return lastMessage;
    }

    public TypeStatusMessage getStatusLastMessage(String login) {
        if (chatsInfo.isEmpty()) {
            JvLog.write(JvLog.TypeLog.Error, "chatsInfo пуст здесь");
            return null;
        }

        String statusMessageString = "";
        int statusMessageInteger = -1;

        for (Map<JvDbGlobalDefines.LineKeys, String> map : chatsInfo) {
            String sender = map.get(JvDbGlobalDefines.LineKeys.Sender);
            String receiver = map.get(JvDbGlobalDefines.LineKeys.Receiver);

            if (Objects.equals(sender, login) || Objects.equals(receiver, login)) {
                statusMessageString = map.get(JvDbGlobalDefines.LineKeys.Status);
                break;
            }
        }

        try {
            statusMessageInteger = Integer.parseInt(statusMessageString);
        } catch (NumberFormatException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Статус сообщения невозможно определить, из-за невозможности приведения его к типу int");
        }

        return TypeStatusMessage.getTypeStatusMessage(statusMessageInteger);
    }

    public String getLastMessageSender(String login) {
        if (chatsInfo.isEmpty()) {
            JvLog.write(JvLog.TypeLog.Error, "chatsInfo пуст здесь");
            return null;
        }

        String lastMessageSender = "";

        for (Map<JvDbGlobalDefines.LineKeys, String> map : chatsInfo) {
            String sender = map.get(JvDbGlobalDefines.LineKeys.Sender);
            String receiver = map.get(JvDbGlobalDefines.LineKeys.Receiver);

            if (Objects.equals(sender, login) || Objects.equals(receiver, login)) {
                lastMessageSender = map.get(JvDbGlobalDefines.LineKeys.Sender);
                break;
            }
        }

        return lastMessageSender;
    }

    public LocalDateTime getTimestampLastMessage(String login) {
        if (chatsInfo.isEmpty()) {
            JvLog.write(JvLog.TypeLog.Error, "chatsInfo пуст здесь");
            return null;
        }

        LocalDateTime timestamp = null;
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        for (Map<JvDbGlobalDefines.LineKeys, String> map : chatsInfo) {
            String sender = map.get(JvDbGlobalDefines.LineKeys.Sender);
            String receiver = map.get(JvDbGlobalDefines.LineKeys.Receiver);

            if (Objects.equals(sender, login) || Objects.equals(receiver, login)) {
                String timestampFromMap = map.get(JvDbGlobalDefines.LineKeys.DateTime);
                int normalizeCount = 3;
                String timestampString = normalizeMillisecond(timestampFromMap, normalizeCount);

                if (timestampString == null) {
                    JvLog.write(JvLog.TypeLog.Error, "Не получилось нормально преобразовать дату и время к нужному формату");
                    return null;
                }

                timestamp = LocalDateTime.parse(timestampString, formatter);
                break;
            }
        }

        return timestamp;
    }

    private String normalizeMillisecond(String timestamp, int normalizeCount) {
        String resultTimestamp;
        String[] parts = timestamp.split("\\.");

        if (parts.length == 2) {
            StringBuilder milliseconds = new StringBuilder(new StringBuilder(parts[1]));

            if (milliseconds.length() < normalizeCount) {
                while (milliseconds.length() < normalizeCount) {
                    milliseconds.append("0");
                }
            } else if (milliseconds.length() > normalizeCount) {
                milliseconds = new StringBuilder(milliseconds.substring(0, normalizeCount));
            }

            resultTimestamp = parts[0] + "." + milliseconds;
        } else {
            JvLog.write(JvLog.TypeLog.Error, "Не получилось нормально преобразовать дату и время к нужному формату");
            return null;
        }

        return resultTimestamp;
    }

    public String getTimeHMLastMessage(String login) {
        LocalDateTime timestamp = getTimestampLastMessage(login);

        int hourInt = timestamp.getHour();
        int minInt = timestamp.getMinute();
        int dayInt = timestamp.getDayOfMonth();
        int monthInt = timestamp.getMonthValue();
        int yearInt = timestamp.getYear();

        String hour = hourInt < 10 ? "0" + hourInt : String.valueOf(hourInt);
        String min = minInt < 10 ? "0" + minInt : String.valueOf(minInt);
        String day = dayInt < 10 ? "0" + dayInt : String.valueOf(dayInt);
        String month = monthInt < 10 ? "0" + monthInt : String.valueOf(monthInt);
        String year = String.valueOf(yearInt);

        return String.format("%s:%s %s.%s.%s", hour, min, day, month, year);
    }
}