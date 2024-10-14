package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.globaldefines.JvDbGlobalDefines;
import org.foomaa.jvchat.globaldefines.JvMainChatsGlobalDefines;
import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.settings.JvGetterSettings;
import org.foomaa.jvchat.tools.JvGetterTools;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;


public class JvChatsCtrl {
    private static JvChatsCtrl instance;
    private List<Map<JvDbGlobalDefines.LineKeys, String>> chatsInfo;
    private Map<String, JvMainChatsGlobalDefines.TypeStatusOnline> onlineStatusesUsers;
    private Map<String, String> lastOnlineTimeUsers;

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
    }

    public void setOnlineStatusesUsers(Map<String, JvMainChatsGlobalDefines.TypeStatusOnline> newOnlineStatusesUsers) {
        if (onlineStatusesUsers != newOnlineStatusesUsers) {
            onlineStatusesUsers = newOnlineStatusesUsers;
        }
    }

    public void setLastOnlineTimeUsersByStrings(Map<String, String> newLastOnlineTimeUsers) {
        if (lastOnlineTimeUsers != newLastOnlineTimeUsers) {
            lastOnlineTimeUsers = newLastOnlineTimeUsers;
        }
    }

    public Map<String, JvMainChatsGlobalDefines.TypeStatusOnline> getOnlineStatusesUsers() {
        return onlineStatusesUsers;
    }

    public Map<String, String> getLastOnlineTimeUsersText() {
        Map<String, String> resultMap = new HashMap<>();

        for (String login : lastOnlineTimeUsers.keySet()) {
            String value = createTextLastOnlineStatusTime(lastOnlineTimeUsers.get(login));
            resultMap.put(login, value);
        }

        return resultMap;
    }

    public List<String> getLoginsChats() {
        if (chatsInfo.isEmpty()) {
            JvLog.write(JvLog.TypeLog.Warn, "chatsInfo пуст здесь");
            return null;
        }

        String currentUserLogin = JvGetterSettings.getInstance().getBeanUsersInfoSettings().getLogin();
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
                lastMessage = map.get(JvDbGlobalDefines.LineKeys.LastMessage);
                break;
            }
        }

        return lastMessage;
    }

    public JvMainChatsGlobalDefines.TypeStatusMessage getStatusLastMessage(String login) {
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
                statusMessageString = map.get(JvDbGlobalDefines.LineKeys.StatusMessage);
                break;
            }
        }

        try {
            statusMessageInteger = Integer.parseInt(statusMessageString);
        } catch (NumberFormatException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Статус сообщения невозможно определить, из-за невозможности приведения его к типу int");
        }

        return JvMainChatsGlobalDefines.TypeStatusMessage.getTypeStatusMessage(statusMessageInteger);
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
                String timestampFromMap = map.get(JvDbGlobalDefines.LineKeys.DateTimeMessage);
                int normalizeCount = 3;
                String timestampString = JvGetterTools.getInstance()
                        .getBeanFormattedTools().normalizeMillisecond(timestampFromMap, normalizeCount);

                if (timestampString == null) {
                    JvLog.write(JvLog.TypeLog.Error, "Не получилось нормализовать дату и время к нужному формату");
                    return null;
                }

                timestamp = LocalDateTime.parse(timestampString, formatter);
                break;
            }
        }

        return timestamp;
    }

    public String getTimeHMLastMessage(String login) {
        LocalDateTime timestamp = getTimestampLastMessage(login);

        Duration duration = Duration.between(timestamp, LocalDateTime.now());
        DateTimeFormatter formatter;
        String result;

        if (duration.toDays() < 1) {
            formatter = DateTimeFormatter.ofPattern("HH:mm");
            result = timestamp.format(formatter);
        } else if (duration.toDays() == 1) {
            formatter = DateTimeFormatter.ofPattern("HH:mm");
            result = "Вчера " + timestamp.format(formatter);
        } else {
            formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");
            result = timestamp.format(formatter);
        }

        return result;
    }

    private String createTextLastOnlineStatusTime(String lastOnlineDateTime) {
        LocalDateTime localDateTime = getTimestampLastOnline(lastOnlineDateTime);
        if (localDateTime == null) {
            JvLog.write(JvLog.TypeLog.Error, "Ошибка вычисления localDateTime");
            return "";
        }

        String result;
        DateTimeFormatter formatter;
        Duration duration = Duration.between(localDateTime, LocalDateTime.now());

        if (duration.toDays() < 1) {
            formatter = DateTimeFormatter.ofPattern("HH:mm");
            result = "в " + localDateTime.format(formatter);
        } else if (duration.toDays() == 1) {
            formatter = DateTimeFormatter.ofPattern("HH:mm");
            result = "вчера в " + localDateTime.format(formatter);
        } else {
            formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");
            result = localDateTime.format(formatter);
        }

        return result;
    }

    private LocalDateTime getTimestampLastOnline(String lastOnlineDateTime) {
        if (lastOnlineDateTime == null || Objects.equals(lastOnlineDateTime, "")) {
            JvLog.write(JvLog.TypeLog.Error, "Ошибка при попытке парсинга времени последнего онлайна");
            return null;
        }

        int normalizeCount = 3;
        String timestampString = JvGetterTools.getInstance()
                .getBeanFormattedTools().normalizeMillisecond(lastOnlineDateTime, normalizeCount);

        if (timestampString == null) {
            JvLog.write(JvLog.TypeLog.Error, "Не получилось нормализовать дату и время к нужному формату");
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime localDateTime;
        try {
            localDateTime = LocalDateTime.parse(timestampString, formatter);
        } catch (DateTimeParseException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Ошибка при попытке парсинга времени последнего онлайна");
            return null;
        }

        return localDateTime;
    }
}