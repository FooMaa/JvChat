package org.foomaa.jvchat.ctrl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.globaldefines.MainChatsGlobalDefines;
import org.foomaa.jvchat.messages.DefinesMessages;
import org.foomaa.jvchat.models.ChatsModel;
import org.foomaa.jvchat.structobjects.ChatStructObject;
import org.foomaa.jvchat.structobjects.MessageStructObject;
import org.foomaa.jvchat.structobjects.UserStructObject;
import org.foomaa.jvchat.tools.FormatTools;

@Component
@Profile("users")
@Slf4j
public class ChatsCtrl {
    private final ChatsModel chatsModel;
    private final FormatTools formatTools;

    ChatsCtrl(ChatsModel chatsModel, FormatTools formatTools) {
        this.chatsModel = chatsModel;
        this.formatTools = formatTools;
    }

    public void createChatsObjects(List<Map<DefinesMessages.TypeData, Object>> chatsInfo) {
        chatsModel.clearModel();

        int normalizeTimestampCount = 3;
        for (Map<DefinesMessages.TypeData, Object> chat : chatsInfo) {
            String login = (String) chat.get(DefinesMessages.TypeData.Login);
            UUID uuidUser = (UUID) chat.get(DefinesMessages.TypeData.UuidUser);
            String lastMessageText = (String) chat.get(DefinesMessages.TypeData.TextMessage);
            UUID uuidChat = (UUID) chat.get(DefinesMessages.TypeData.UuidChat);
            UUID uuidLastMessage = (UUID) chat.get(DefinesMessages.TypeData.UuidMessage);
            Boolean isLoginSentLastMessage = (Boolean) chat.get(DefinesMessages.TypeData.IsLoginSentLastMessage);
            MainChatsGlobalDefines.TypeStatusMessage statusMessage = (MainChatsGlobalDefines.TypeStatusMessage) chat
                    .get(DefinesMessages.TypeData.StatusMessage);
            LocalDateTime timestampLastMessage = formatTools.stringToLocalDateTime(
                    (String) chat.get(DefinesMessages.TypeData.Timestamp), normalizeTimestampCount);

            if (timestampLastMessage == null) {
                log.warn("It was not possible to normalize the date and time to the required format.");
            }

            chatsModel.createNewChat(login, uuidUser, lastMessageText, uuidChat, uuidLastMessage,
                    isLoginSentLastMessage, statusMessage, timestampLastMessage);
        }
    }

    public void setOnlineStatusesUsers(Map<UUID, MainChatsGlobalDefines.TypeStatusOnline> onlineStatusesUsers) {
        for (UUID uuidUser : onlineStatusesUsers.keySet()) {
            chatsModel.setOnlineStatusToUser(uuidUser, onlineStatusesUsers.get(uuidUser));
        }
    }

    public void setLastOnlineTimeUsersByStrings(Map<UUID, String> lastOnlineTimeUsers) {
        int normalizeTimestampCount = 3;
        for (UUID uuidUser : lastOnlineTimeUsers.keySet()) {
            LocalDateTime timestamp = formatTools.stringToLocalDateTime(lastOnlineTimeUsers.get(uuidUser),
                    normalizeTimestampCount);
            chatsModel.setTimestampLastOnlineToUser(uuidUser, timestamp);
        }
    }

    public String getTimeFormattedLastOnline(LocalDateTime lastOnlineDateTime) {
        if (lastOnlineDateTime == null) {
            log.warn("Here lastOnlineDateTime turned out to be null (Maybe for those who are online).");
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
        List<UserStructObject> listUsers = chatsModel.getAllUsersObjects();
        List<UUID> resultList = new ArrayList<>();

        for (UserStructObject user : listUsers) {
            resultList.add(user.getUuid());
        }

        return resultList;
    }

    public List<ChatStructObject> getChatsObjects() {
        return chatsModel.getSortedChatsObjects();
    }

    public UserStructObject getUserObjectsByUuidUser(UUID uuidUser) {
        List<UserStructObject> usersList = chatsModel.getAllUsersObjects();

        for (UserStructObject user : usersList) {
            if (user.getUuid().equals(uuidUser)) {
                return user;
            }
        }
        return null;
    }

    public String getTimeFormattedLastMessage(LocalDateTime timestamp) {
        if (timestamp == null) {
            log.error("Here the timestamp turned out to be null.");
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

    public void changeLastMessage(MessageStructObject message) {
        List<ChatStructObject> chatsList = chatsModel.getAllChatsObjects();

        for (ChatStructObject chat : chatsList) {
            MessageStructObject lastMessageObj = chat.getLastMessage();
            UUID uuidUserSender = lastMessageObj.getUuidUserSender();
            UUID uuidUserReceiver = lastMessageObj.getUuidUserReceiver();
            if ((uuidUserSender.equals(message.getUuidUserSender())
                    && uuidUserReceiver.equals(message.getUuidUserReceiver()))
                    || (uuidUserSender.equals(message.getUuidUserReceiver())
                            && uuidUserReceiver.equals(message.getUuidUserReceiver()))) {
                chat.setLastMessage(message);
                return;
            }
        }
    }

    public MessageStructObject getMessageObjectByUuidChat(UUID uuidChat) {
        List<ChatStructObject> chatsList = chatsModel.getAllChatsObjects();

        for (ChatStructObject chat : chatsList) {
            if (chat.getUuid().equals(uuidChat)) {
                return chat.getLastMessage();
            }
        }

        return null;
    }
}
