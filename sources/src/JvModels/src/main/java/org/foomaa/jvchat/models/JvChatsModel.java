package org.foomaa.jvchat.models;

import java.time.LocalDateTime;
import java.util.*;

import org.foomaa.jvchat.globaldefines.JvMainChatsGlobalDefines;
import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.settings.JvGetterSettings;
import org.foomaa.jvchat.structobjects.*;


public class JvChatsModel extends JvBaseModel {
    private UUID currentActiveChatUuid;

    JvChatsModel() {
        setRootObject(JvGetterStructObjects.getInstance()
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
                              String lastMessageText,
                              UUID uuidChat,
                              UUID uuidLastMessage,
                              Boolean isLoginSentLastMessage,
                              JvMainChatsGlobalDefines.TypeStatusMessage statusMessage,
                              LocalDateTime timestampLastMessage) {
        JvUserStructObject userChat = JvGetterStructObjects.getInstance().getBeanUserStructObject();
        userChat.setLogin(login);
        JvGetterModels.getInstance().getBeanUsersModel().addCreatedUser(userChat);

        String sender = isLoginSentLastMessage ? login : JvGetterSettings.getInstance().getBeanUsersInfoSettings().getLogin();
        String receiver = isLoginSentLastMessage ? JvGetterSettings.getInstance().getBeanUsersInfoSettings().getLogin() : login;

        JvMessageStructObject lastMessage = JvGetterStructObjects.getInstance().getBeanMessageStructObject();
        lastMessage.setLoginSender(sender);
        lastMessage.setLoginReceiver(receiver);
        lastMessage.setText(lastMessageText);
        lastMessage.setStatusMessage(statusMessage);
        lastMessage.setUuid(uuidLastMessage);
        lastMessage.setTimestamp(timestampLastMessage);

        JvChatStructObject chat = JvGetterStructObjects.getInstance().getBeanChatStructObject();
        chat.setUserChat(userChat);
        chat.setLastMessage(lastMessage);
        chat.setUuid(uuidChat);

        addItem(chat, getRootObject());
    }

    public void setOnlineStatusToUser(String login, JvMainChatsGlobalDefines.TypeStatusOnline statusOnline) {
        JvChatStructObject chat = findByLogin(login);
        if (chat == null) {
            JvLog.write(JvLog.TypeLog.Error, "Сюда попал объект chat, который null");
            return;
        }
        chat.getUserChat().setStatusOnline(statusOnline);
    }

    public void setTimestampLastOnlineToUser(String login, LocalDateTime timestamp) {
        JvChatStructObject chat = findByLogin(login);
        if (chat == null) {
            JvLog.write(JvLog.TypeLog.Error, "Сюда попал объект chat, который null");
            return;
        }
        chat.getUserChat().setTimestampLastOnline(timestamp);
    }

    private JvChatStructObject findByLogin(String login) {
        for (JvBaseStructObject baseStructObject : getRootObject().getChildren()) {
            JvChatStructObject chatStructObject = (JvChatStructObject) baseStructObject;
            if (chatStructObject == null) {
                JvLog.write(JvLog.TypeLog.Error, "Сюда попал объект chatStructObject, который null");
                continue;
            }
            String loginObj = chatStructObject.getUserChat().getLogin();
            if (Objects.equals(loginObj, login)) {
                return chatStructObject;
            }
        }

        return null;
    }

    public List<JvChatStructObject> getAllChatsObjects() {
        List<JvChatStructObject> resultList = new ArrayList<>();

        for (JvBaseStructObject baseStructObject : getRootObject().getChildren()) {
            JvChatStructObject chatStructObject = (JvChatStructObject) baseStructObject;
            if (chatStructObject == null) {
                JvLog.write(JvLog.TypeLog.Error, "Сюда попал объект chatStructObject, который null");
                continue;
            }
            resultList.add(chatStructObject);
        }

        return resultList;
    }

    public List<JvUserStructObject> getAllUsersObjects() {
        List<JvChatStructObject> chatsList = getAllChatsObjects();
        List<JvUserStructObject> resultList = new ArrayList<>();

        for (JvChatStructObject chatObject : chatsList) {
            if (chatObject == null) {
                JvLog.write(JvLog.TypeLog.Error, "Сюда попал объект chatStructObject, который null");
                continue;
            }
            resultList.add(chatObject.getUserChat());
        }

        return resultList;
    }

    public List<JvChatStructObject> getSortedChatsObjects() {
        List<JvChatStructObject> list = getAllChatsObjects();

        list.sort((chatObj1, chatObj2) -> {
            LocalDateTime chatObj2Date = chatObj2.getLastMessage().getTimestamp();
            LocalDateTime chatObj1Date = chatObj1.getLastMessage().getTimestamp();
            return chatObj2Date.compareTo(chatObj1Date);
        });

        return list;
    }
}
