package org.foomaa.jvchat.models;

import org.foomaa.jvchat.globaldefines.JvMainChatsGlobalDefines;
import org.foomaa.jvchat.structobjects.JvGetterStructObjects;
import org.foomaa.jvchat.structobjects.JvMessageStructObject;

import java.time.LocalDateTime;
import java.util.UUID;


public class JvMessagesModel extends JvBaseModel {
    private static JvMessagesModel instance;

    private JvMessagesModel() {
        setRootObject(JvGetterStructObjects.getInstance()
                .getBeanRootStructObject(getNameModel()));
    }

    static JvMessagesModel getInstance() {
        if (instance == null) {
            instance = new JvMessagesModel();
        }
        return instance;
    }

    public void createNewMessage(String loginSender,
                                 String loginReceiver,
                                 String text,
                                 JvMainChatsGlobalDefines.TypeStatusMessage statusMessage,
                                 UUID uuid,
                                 LocalDateTime timestamp) {
        JvMessageStructObject messageObj = JvGetterStructObjects.getInstance().getBeanMessageStructObject();

        messageObj.setLoginSender(loginSender);
        messageObj.setLoginReceiver(loginReceiver);
        messageObj.setText(text);
        messageObj.setStatusMessage(statusMessage);
        messageObj.setUuid(uuid);
        messageObj.setTimestamp(timestamp);

        addItem(messageObj, getRootObject());
    }
}