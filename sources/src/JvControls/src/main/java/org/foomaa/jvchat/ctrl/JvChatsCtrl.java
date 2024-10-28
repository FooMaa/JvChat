package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.globaldefines.JvDbGlobalDefines;
import org.foomaa.jvchat.globaldefines.JvMainChatsGlobalDefines;
import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.models.JvChatsModel;
import org.foomaa.jvchat.models.JvGetterModels;
import org.foomaa.jvchat.structobjects.JvChatStructObject;
import org.foomaa.jvchat.structobjects.JvMessageStructObject;
import org.foomaa.jvchat.structobjects.JvUserStructObject;
import org.foomaa.jvchat.tools.JvGetterTools;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class JvChatsCtrl {
    private static JvChatsCtrl instance;
    private final JvChatsModel chatsModel;

    private JvChatsCtrl() {
        chatsModel = JvGetterModels.getInstance().getBeanChatsModel();
    }

    static JvChatsCtrl getInstance() {
        if (instance == null) {
            instance = new JvChatsCtrl();
        }
        return instance;
    }

    public void createChatsObjects(List<Map<JvDbGlobalDefines.LineKeys, String>> chatsInfo) {
        for (Map<JvDbGlobalDefines.LineKeys, String> chat : chatsInfo) {
            String lastMessageLoginSender = chat.get(JvDbGlobalDefines.LineKeys.Sender);
            String lastMessageLoginReceiver = chat.get(JvDbGlobalDefines.LineKeys.Receiver);
            String lastMessageText = chat.get(JvDbGlobalDefines.LineKeys.LastMessage);
            UUID uuidLastMessage = UUID.fromString(chat.get(JvDbGlobalDefines.LineKeys.UuidMessage));
            JvMainChatsGlobalDefines.TypeStatusMessage statusMessage =
                    statusMessageStringToInt(chat.get(JvDbGlobalDefines.LineKeys.StatusMessage));
            LocalDateTime timestampLastMessage = timestampFromString(chat.get(JvDbGlobalDefines.LineKeys.DateTimeMessage));

            chatsModel.createNewChat(
                    lastMessageLoginSender,
                    lastMessageLoginReceiver,
                    lastMessageText,
                    uuidLastMessage,
                    statusMessage,
                    timestampLastMessage);
        }
    }

    private JvMainChatsGlobalDefines.TypeStatusMessage statusMessageStringToInt(String statusMessageStr) {
        int statusMessageInt;
        try{
            statusMessageInt = Integer.parseInt(statusMessageStr);
        }
        catch (NumberFormatException ex){
            JvLog.write(JvLog.TypeLog.Error, "Тут статус не преобразовался в int");
            return JvMainChatsGlobalDefines.TypeStatusMessage.Error;
        }

        return JvMainChatsGlobalDefines.TypeStatusMessage.getTypeStatusMessage(statusMessageInt);
    }

    private LocalDateTime timestampFromString(String timestampStart) {
        if (timestampStart == null || Objects.equals(timestampStart, "")) {
            JvLog.write(JvLog.TypeLog.Error, "Ошибка при попытке парсинга времени последнего онлайна");
            return null;
        }

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        int normalizeCount = 3;
        String timestampString = JvGetterTools.getInstance()
                .getBeanFormattedTools().normalizeMillisecond(timestampStart, normalizeCount);

        if (timestampString == null) {
            JvLog.write(JvLog.TypeLog.Error, "Не получилось нормализовать дату и время к нужному формату");
            return null;
        }

        return LocalDateTime.parse(timestampString, formatter);
    }

    public void setOnlineStatusesUsers(Map<String, JvMainChatsGlobalDefines.TypeStatusOnline> onlineStatusesUsers) {
        for (String login : onlineStatusesUsers.keySet()) {
            chatsModel.setOnlineStatusToUser(login, onlineStatusesUsers.get(login));
        }
    }

    public void setLastOnlineTimeUsersByStrings(Map<String, String> lastOnlineTimeUsers) {
        for (String login : lastOnlineTimeUsers.keySet()) {
            LocalDateTime timestamp = timestampFromString(lastOnlineTimeUsers.get(login));
            chatsModel.setTimestampLastOnlineToUser(login, timestamp);
        }
    }

    public String getTimeFormattedLastOnline(LocalDateTime lastOnlineDateTime) {
        if (lastOnlineDateTime == null) {
            JvLog.write(JvLog.TypeLog.Warn, "Тут lastOnlineDateTime оказался null. (Может быть у тех, кто в сети)");
            return "";
        }

        String result;
        DateTimeFormatter formatter;
        Duration duration = Duration.between(lastOnlineDateTime, LocalDateTime.now());

        if (duration.toDays() < 1) {
            formatter = DateTimeFormatter.ofPattern("HH:mm");
            result = "в " + lastOnlineDateTime.format(formatter);
        } else if (duration.toDays() == 1) {
            formatter = DateTimeFormatter.ofPattern("HH:mm");
            result = "вчера в " + lastOnlineDateTime.format(formatter);
        } else {
            formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");
            result = lastOnlineDateTime.format(formatter);
        }

        return result;
    }

    public List<String> getLoginsChats() {
        List<JvUserStructObject> listUsers = chatsModel.getAllUsersObjects();
        List<String> resultList = new ArrayList<>();

        for (JvUserStructObject user : listUsers) {
            resultList.add(user.getLogin());
        }

        return resultList;
    }

    public List<JvChatStructObject> getChatsObjects() {
        return chatsModel.getAllChatsObjects();
    }

    public JvUserStructObject getUserObjectsByLogin(String login) {
        List<JvUserStructObject> usersList = chatsModel.getAllUsersObjects();

        for (JvUserStructObject user : usersList) {
            if (Objects.equals(user.getLogin(), login)) {
                return user;
            }
        }
        return null;
    }

    public String getTimeFormattedLastMessage(LocalDateTime timestamp) {
        if (timestamp == null) {
            JvLog.write(JvLog.TypeLog.Error, "Здесь timestamp оказался null");
            return "";
        }

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

    public void changeLastMessage(String loginSender, String loginReceiver, JvMessageStructObject message) {
        List<JvChatStructObject> chatsList = chatsModel.getAllChatsObjects();

        for (JvChatStructObject chat : chatsList) {
            JvMessageStructObject lastMessageObj = chat.getLastMessage();
            String sender = lastMessageObj.getLoginSender();
            String receiver = lastMessageObj.getLoginReceiver();
            if ((Objects.equals(sender, loginSender) && Objects.equals(receiver, loginReceiver)) ||
                    (Objects.equals(sender, loginReceiver) && Objects.equals(receiver, loginSender))) {
                chat.setLastMessage(message);
                return;
            }
        }
    }

    public JvMessageStructObject getMessageObjectByLoginChat(String loginChat) {
        List<JvChatStructObject> chatsList = chatsModel.getAllChatsObjects();

        for (JvChatStructObject chat : chatsList) {
            if (Objects.equals(loginChat, chat.getUserChat().getLogin())) {
                return chat.getLastMessage();
            }
        }

        return null;
    }
}