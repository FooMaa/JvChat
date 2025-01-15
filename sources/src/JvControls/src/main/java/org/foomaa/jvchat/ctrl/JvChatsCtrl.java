package org.foomaa.jvchat.ctrl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.foomaa.jvchat.globaldefines.JvMainChatsGlobalDefines;
import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.messages.JvDefinesMessages;
import org.foomaa.jvchat.models.JvChatsModel;
import org.foomaa.jvchat.models.JvGetterModels;
import org.foomaa.jvchat.structobjects.JvChatStructObject;
import org.foomaa.jvchat.structobjects.JvMessageStructObject;
import org.foomaa.jvchat.structobjects.JvUserStructObject;
import org.foomaa.jvchat.tools.JvGetterTools;


public class JvChatsCtrl {
    private final JvChatsModel chatsModel;

    JvChatsCtrl() {
        chatsModel = JvGetterModels.getInstance().getBeanChatsModel();
    }

    public void createChatsObjects(List<Map<JvDefinesMessages.TypeData, Object>> chatsInfo) {
        chatsModel.clearModel();

        int normalizeTimestampCount = 3;
        for (Map<JvDefinesMessages.TypeData, Object> chat : chatsInfo) {
            String login = (String) chat.get(JvDefinesMessages.TypeData.Login);
            UUID uuidUser = (UUID) chat.get(JvDefinesMessages.TypeData.UuidUser);
            String lastMessageText = (String) chat.get(JvDefinesMessages.TypeData.LastMessageText);
            UUID uuidChat = (UUID) chat.get(JvDefinesMessages.TypeData.UuidChat);
            UUID uuidLastMessage = (UUID) chat.get(JvDefinesMessages.TypeData.UuidMessage);
            Boolean isLoginSentLastMessage = (Boolean) chat.get(JvDefinesMessages.TypeData.IsLoginSentLastMessage);
            JvMainChatsGlobalDefines.TypeStatusMessage statusMessage =
                    (JvMainChatsGlobalDefines.TypeStatusMessage) chat.get(JvDefinesMessages.TypeData.StatusMessage);
            LocalDateTime timestampLastMessage = JvGetterTools.getInstance().getBeanFormatTools()
                    .stringToLocalDateTime((String) chat.get(JvDefinesMessages.TypeData.DateTimeLastMessage), normalizeTimestampCount);

            if (timestampLastMessage == null) {
                JvLog.write(JvLog.TypeLog.Warn, "Не получилось нормализовать дату и время к нужному формату");
            }

            chatsModel.createNewChat(login, uuidUser, lastMessageText, uuidChat, uuidLastMessage,
                    isLoginSentLastMessage, statusMessage, timestampLastMessage);
        }
    }

    public void setOnlineStatusesUsers(Map<UUID, JvMainChatsGlobalDefines.TypeStatusOnline> onlineStatusesUsers) {
        for (UUID uuidUser : onlineStatusesUsers.keySet()) {
            chatsModel.setOnlineStatusToUser(uuidUser, onlineStatusesUsers.get(uuidUser));
        }
    }

    public void setLastOnlineTimeUsersByStrings(Map<UUID, String> lastOnlineTimeUsers) {
        int normalizeTimestampCount = 3;
        for (UUID uuidUser : lastOnlineTimeUsers.keySet()) {
            LocalDateTime timestamp = JvGetterTools.getInstance().getBeanFormatTools()
                    .stringToLocalDateTime(lastOnlineTimeUsers.get(uuidUser), normalizeTimestampCount);
            chatsModel.setTimestampLastOnlineToUser(uuidUser, timestamp);
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

    public List<UUID> getUuidsUsersChats() {
        List<JvUserStructObject> listUsers = chatsModel.getAllUsersObjects();
        List<UUID> resultList = new ArrayList<>();

        for (JvUserStructObject user : listUsers) {
            resultList.add(user.getUuid());
        }

        return resultList;
    }

    public List<JvChatStructObject> getChatsObjects() {
        return chatsModel.getSortedChatsObjects();
    }

    public JvUserStructObject getUserObjectsByUuidUser(UUID uuidUser) {
        List<JvUserStructObject> usersList = chatsModel.getAllUsersObjects();

        for (JvUserStructObject user : usersList) {
            if (user.getUuid().equals(uuidUser)) {
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

    public void changeLastMessage(JvMessageStructObject message) {
        List<JvChatStructObject> chatsList = chatsModel.getAllChatsObjects();

        for (JvChatStructObject chat : chatsList) {
            JvMessageStructObject lastMessageObj = chat.getLastMessage();
            String sender = lastMessageObj.getLoginSender();
            String receiver = lastMessageObj.getLoginReceiver();
            if ((Objects.equals(sender, message.getLoginSender()) && Objects.equals(receiver, message.getLoginReceiver())) ||
                    (Objects.equals(sender, message.getLoginReceiver()) && Objects.equals(receiver, message.getLoginReceiver()))) {
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