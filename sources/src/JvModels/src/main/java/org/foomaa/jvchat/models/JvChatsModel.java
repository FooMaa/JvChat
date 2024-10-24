package org.foomaa.jvchat.models;

import org.foomaa.jvchat.globaldefines.JvMainChatsGlobalDefines;
import org.foomaa.jvchat.structobjects.JvChatStructObject;
import org.foomaa.jvchat.structobjects.JvGetterStructObjects;
import org.foomaa.jvchat.structobjects.JvMessageStructObject;
import org.foomaa.jvchat.structobjects.JvUserStructObject;

import java.time.LocalDateTime;
import java.util.UUID;

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

//        userChat.setLogin();
//        userChat.setStatusOnline();
//        userChat.setTimestampLastOnline();

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
    }
}
