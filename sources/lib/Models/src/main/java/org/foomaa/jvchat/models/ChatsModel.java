package org.foomaa.jvchat.models;

import java.time.LocalDateTime;
import java.util.*;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.globaldefines.MainChatsGlobalDefines;
import org.foomaa.jvchat.settings.UsersInfoSettings;
import org.foomaa.jvchat.structobjects.*;

@Slf4j
public class ChatsModel extends BaseModel {
    @Getter
    private UUID currentActiveChatUuid;
    // DI ↓
    private final UsersInfoSettings usersInfoSettings;
    private final UsersModel usersModel;
    private final MessageStructObjectFactory messageStructObjectFactory;
    private final ChatStructObjectFactory chatStructObjectFactory;
    private final UserStructObjectFactory userStructObjectFactory;

    @Builder
    ChatsModel(
            UsersInfoSettings usersInfoSettings,
            UsersModel usersModel,
            MessageStructObjectFactory messageStructObjectFactory,
            ChatStructObjectFactory chatStructObjectFactory,
            UserStructObjectFactory userStructObjectFactory,
            RootStructObjectFactory rootStructObjectFactory,
            RootObjectsModel rootObjectsModel) {
        super(
                Objects.requireNonNull(rootObjectsModel, "rootObjectsModel is mandatory"),
                Objects.requireNonNull(rootStructObjectFactory, "rootStructObjectFactory is mandatory"));

        this.usersInfoSettings = Objects.requireNonNull(usersInfoSettings, "usersInfoSettings is mandatory");
        this.usersModel = Objects.requireNonNull(usersModel, "usersModel is mandatory");
        this.messageStructObjectFactory =
                Objects.requireNonNull(messageStructObjectFactory, "messageStructObjectFactory is mandatory");
        this.chatStructObjectFactory =
                Objects.requireNonNull(chatStructObjectFactory, "chatStructObjectFactory is mandatory");
        this.userStructObjectFactory =
                Objects.requireNonNull(userStructObjectFactory, "userStructObjectFactory is mandatory");

        currentActiveChatUuid = null;
    }

    public void setCurrentActiveChatUuid(UUID newCurrentActiveChatUuid) {
        if (!Objects.equals(currentActiveChatUuid, newCurrentActiveChatUuid)) {
            currentActiveChatUuid = newCurrentActiveChatUuid;
        }
    }

    public void createNewChat(
            String login,
            UUID uuidUser,
            String lastMessageText,
            UUID uuidChat,
            UUID uuidLastMessage,
            Boolean isLoginSentLastMessage,
            MainChatsGlobalDefines.TypeStatusMessage statusMessage,
            LocalDateTime timestampLastMessage) {
        UserStructObject userChat = userStructObjectFactory.create(login, uuidUser);
        usersModel.addCreatedUser(userChat);

        UUID uuidSender = isLoginSentLastMessage ? uuidUser : usersInfoSettings.getUuid();
        UUID uuidReceiver = isLoginSentLastMessage ? usersInfoSettings.getUuid() : uuidUser;

        MessageStructObject lastMessage = messageStructObjectFactory.create(
                uuidSender, uuidReceiver, statusMessage, lastMessageText, timestampLastMessage, uuidLastMessage);

        ChatStructObject chat = chatStructObjectFactory.create(userChat, lastMessage, uuidChat);

        addItem(chat, getRootObject());
    }

    public void setOnlineStatusToUser(UUID uuidUser, MainChatsGlobalDefines.TypeStatusOnline statusOnline) {
        ChatStructObject chat = findByUuidUser(uuidUser);
        if (chat == null) {
            log.error("This includes a chat object, which is null.");
            return;
        }
        chat.getUserChat().setStatusOnline(statusOnline);
    }

    public void setTimestampLastOnlineToUser(UUID uuidUser, LocalDateTime timestamp) {
        ChatStructObject chat = findByUuidUser(uuidUser);
        if (chat == null) {
            log.error("This includes a chat object, which is null.");
            return;
        }
        chat.getUserChat().setTimestampLastOnline(timestamp);
    }

    private ChatStructObject findByUuidUser(UUID uuidUser) {
        for (BaseStructObject baseStructObject : getRootObject().getChildren()) {
            ChatStructObject chatStructObject = (ChatStructObject) baseStructObject;
            if (chatStructObject == null) {
                log.error("This includes a chat object, which is null.");
                continue;
            }
            UUID uuidObj = chatStructObject.getUserChat().getUuid();
            if (uuidObj.equals(uuidUser)) {
                return chatStructObject;
            }
        }

        return null;
    }

    public List<ChatStructObject> getAllChatsObjects() {
        List<ChatStructObject> resultList = new ArrayList<>();

        for (BaseStructObject baseStructObject : getRootObject().getChildren()) {
            ChatStructObject chatStructObject = (ChatStructObject) baseStructObject;
            if (chatStructObject == null) {
                log.error("This includes the chatStructObject object, which is null.");
                continue;
            }
            resultList.add(chatStructObject);
        }

        return resultList;
    }

    public List<UserStructObject> getAllUsersObjects() {
        List<ChatStructObject> chatsList = getAllChatsObjects();
        List<UserStructObject> resultList = new ArrayList<>();

        for (ChatStructObject chatObject : chatsList) {
            if (chatObject == null) {
                log.error("This includes the chatStructObject object, which is null.");
                continue;
            }
            resultList.add(chatObject.getUserChat());
        }

        return resultList;
    }

    public List<ChatStructObject> getSortedChatsObjects() {
        List<ChatStructObject> list = getAllChatsObjects();

        list.sort((chatObj1, chatObj2) -> {
            LocalDateTime chatObj2Date = chatObj2.getLastMessage().getTimestamp();
            LocalDateTime chatObj1Date = chatObj1.getLastMessage().getTimestamp();
            return chatObj2Date.compareTo(chatObj1Date);
        });

        return list;
    }
}
