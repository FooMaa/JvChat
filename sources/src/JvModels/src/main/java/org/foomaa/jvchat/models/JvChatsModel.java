package org.foomaa.jvchat.models;

import org.foomaa.jvchat.structobjects.JvChatStructObject;
import org.foomaa.jvchat.structobjects.JvGetterStructObjects;
import org.foomaa.jvchat.structobjects.JvUserStructObject;

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

    public void createNewChat() {
        JvUserStructObject userChat = JvGetterStructObjects.getInstance().getBeanUserStructObject();

//        userChat.setLogin();
//        userChat.setStatusOnline();
//        userChat.setTimestampLastOnline();

        JvChatStructObject chat = JvGetterStructObjects.getInstance().getBeanChatStructObject();

//        chat.setUserChat(userChat);
//        chat.setLastMessage();
    }
}
