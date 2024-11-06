package org.foomaa.jvchat.models;

import org.foomaa.jvchat.globaldefines.JvMainChatsGlobalDefines;
import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.settings.JvGetterSettings;
import org.foomaa.jvchat.structobjects.*;

import java.time.LocalDateTime;
import java.util.*;


public class JvChatsModel extends JvBaseModel {
    private static JvChatsModel instance;

    private JvChatsModel() {
        setRootObject(JvGetterStructObjects.getInstance()
                .getBeanRootStructObject(getNameModel()));
    }

    static JvChatsModel getInstance() {
        if (instance == null) {
            instance = new JvChatsModel();
        }
        return instance;
    }

    public void createNewChat(String lastMessageLoginSender,
                              String lastMessageLoginReceiver,
                              String lastMessageText ,
                              UUID uuidLastMessage ,
                              JvMainChatsGlobalDefines.TypeStatusMessage statusMessage ,
                              LocalDateTime timestampLastMessage) {
        JvUserStructObject userChat = JvGetterStructObjects.getInstance().getBeanUserStructObject();
        String currentUserLogin = JvGetterSettings.getInstance().getBeanUsersInfoSettings().getLogin();
        if (Objects.equals(lastMessageLoginSender, currentUserLogin)) {
            userChat.setLogin(lastMessageLoginReceiver);
        } else if (Objects.equals(lastMessageLoginReceiver, currentUserLogin)) {
            userChat.setLogin(lastMessageLoginSender);
        }
        JvGetterModels.getInstance().getBeanUsersModel().addCreatedUser(userChat);

        JvMessageStructObject lastMessage = JvGetterStructObjects.getInstance().getBeanMessageStructObject();
        lastMessage.setLoginSender(lastMessageLoginSender);
        lastMessage.setLoginReceiver(lastMessageLoginReceiver);
        lastMessage.setText(lastMessageText);
        lastMessage.setStatusMessage(statusMessage);
        lastMessage.setUuid(uuidLastMessage);
        lastMessage.setTimestamp(timestampLastMessage);

        JvChatStructObject chat = JvGetterStructObjects.getInstance().getBeanChatStructObject();
        chat.setUserChat(userChat);
        chat.setLastMessage(lastMessage);

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

    @Deprecated
    @SuppressWarnings("unused")
    public List<JvMessageStructObject> getAllLastMessagesObjects() {
        List<JvChatStructObject> chatsList = getAllChatsObjects();
        List<JvMessageStructObject> resultList = new ArrayList<>();

        for (JvChatStructObject chatObject : chatsList) {
            if (chatObject == null) {
                JvLog.write(JvLog.TypeLog.Error, "Сюда попал объект chatStructObject, который null");
                continue;
            }
            resultList.add(chatObject.getLastMessage());
        }

        return resultList;
    }
}
