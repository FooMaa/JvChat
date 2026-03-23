package org.foomaa.jvchat.models;

import java.time.LocalDateTime;
import java.util.*;
import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.globaldefines.MainChatsGlobalDefines;
import org.foomaa.jvchat.settings.UsersInfoSettings;
import org.foomaa.jvchat.structobjects.*;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Lazy
@Slf4j
public class ChatsModel extends BaseModel {
    private final UsersInfoSettings usersInfoSettings;

    private UUID currentActiveChatUuid;
    private final UsersModel usersModel;
    private final ObjectProvider<MessageStructObject> messageStructObjectObjectProvider;
    private final ObjectProvider<ChatStructObject> chatStructObjectObjectProvider;
    private final ObjectProvider<UserStructObject> userStructObjectObjectProvider;

    ChatsModel(UsersInfoSettings usersInfoSettings,
               UsersModel usersModel,
               ObjectProvider<MessageStructObject> messageStructObjectObjectProvider,
               ObjectProvider<ChatStructObject> chatStructObjectObjectProvider,
               ObjectProvider<UserStructObject> userStructObjectObjectProvider,
               ObjectProvider<RootStructObject> rootStructObjectObjectProvider,
               RootObjectsModel rootObjectsModel) {
        super(rootObjectsModel, rootStructObjectObjectProvider);

        this.usersInfoSettings = usersInfoSettings;
        this.usersModel = usersModel;
        this.messageStructObjectObjectProvider = messageStructObjectObjectProvider;
        this.chatStructObjectObjectProvider = chatStructObjectObjectProvider;
        this.userStructObjectObjectProvider = userStructObjectObjectProvider;

        currentActiveChatUuid = null;
    }

    public UUID getCurrentActiveChatUuid() {
        return currentActiveChatUuid;
    }

    public void setCurrentActiveChatUuid(UUID newCurrentActiveChatUuid) {
        if (!Objects.equals(currentActiveChatUuid, newCurrentActiveChatUuid)) {
            currentActiveChatUuid = newCurrentActiveChatUuid;
        }
    }

    public void createNewChat(String login,
                              UUID uuidUser,
                              String lastMessageText,
                              UUID uuidChat,
                              UUID uuidLastMessage,
                              Boolean isLoginSentLastMessage,
                              MainChatsGlobalDefines.TypeStatusMessage statusMessage,
                              LocalDateTime timestampLastMessage) {
        UserStructObject userChat = userStructObjectObjectProvider.getObject();
        userChat.setLogin(login);
        userChat.setUuid(uuidUser);
        usersModel.addCreatedUser(userChat);

        UUID uuidSender = isLoginSentLastMessage ? uuidUser : usersInfoSettings.getUuid();
        UUID uuidReceiver = isLoginSentLastMessage ? usersInfoSettings.getUuid() : uuidUser;

        MessageStructObject lastMessage = messageStructObjectObjectProvider.getObject();
        lastMessage.setUuidUserSender(uuidSender);
        lastMessage.setUuidUserReceiver(uuidReceiver);
        lastMessage.setText(lastMessageText);
        lastMessage.setStatusMessage(statusMessage);
        lastMessage.setUuid(uuidLastMessage);
        lastMessage.setTimestamp(timestampLastMessage);

        ChatStructObject chat = chatStructObjectObjectProvider.getObject();
        chat.setUserChat(userChat);
        chat.setLastMessage(lastMessage);
        chat.setUuid(uuidChat);

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
