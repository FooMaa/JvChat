package org.foomaa.jvchat.models;

import java.time.LocalDateTime;
import java.util.*;
import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.globaldefines.MainChatsGlobalDefines;
import org.foomaa.jvchat.settings.GetterSettings;
import org.foomaa.jvchat.structobjects.*;


@Slf4j
public class ChatsModel extends BaseModel {
    private UUID currentActiveChatUuid;

    ChatsModel() {
        setRootObject(GetterStructObjects.getInstance()
                .getBeanRootStructObject(getNameModel()));
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
        UserStructObject userChat = GetterStructObjects.getInstance().getBeanUserStructObject();
        userChat.setLogin(login);
        userChat.setUuid(uuidUser);
        GetterModels.getInstance().getBeanUsersModel().addCreatedUser(userChat);

        UUID uuidSender = isLoginSentLastMessage ? uuidUser : GetterSettings.getInstance().getBeanUsersInfoSettings().getUuid();
        UUID uuidReceiver = isLoginSentLastMessage ? GetterSettings.getInstance().getBeanUsersInfoSettings().getUuid() : uuidUser;

        MessageStructObject lastMessage = GetterStructObjects.getInstance().getBeanMessageStructObject();
        lastMessage.setUuidUserSender(uuidSender);
        lastMessage.setUuidUserReceiver(uuidReceiver);
        lastMessage.setText(lastMessageText);
        lastMessage.setStatusMessage(statusMessage);
        lastMessage.setUuid(uuidLastMessage);
        lastMessage.setTimestamp(timestampLastMessage);

        ChatStructObject chat = GetterStructObjects.getInstance().getBeanChatStructObject();
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
