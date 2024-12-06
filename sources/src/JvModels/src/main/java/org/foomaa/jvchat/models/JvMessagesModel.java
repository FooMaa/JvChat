package org.foomaa.jvchat.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.foomaa.jvchat.globaldefines.JvMainChatsGlobalDefines;
import org.foomaa.jvchat.structobjects.JvBaseStructObject;
import org.foomaa.jvchat.structobjects.JvGetterStructObjects;
import org.foomaa.jvchat.structobjects.JvMessageStructObject;


public class JvMessagesModel extends JvBaseModel {
    JvMessagesModel() {
        setRootObject(JvGetterStructObjects.getInstance()
                .getBeanRootStructObject(getNameModel()));
    }

    public JvMessageStructObject createNewMessage(String loginSender,
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

        return messageObj;
    }

    public List<JvMessageStructObject> getAllMessages() {
        List<JvMessageStructObject> resultList = new ArrayList<>();

        for (JvBaseStructObject baseStructObject : getRootObject().getChildren()) {
            JvMessageStructObject messageStructObject = (JvMessageStructObject) baseStructObject;
            if (messageStructObject != null) {
                resultList.add(messageStructObject);
            }
        }

        return resultList;
    }

    public List<JvMessageStructObject> getSortedMessagesObjects() {
        List<JvMessageStructObject> list = getAllMessages();

        list.sort((msgObj1, msgObj2) -> {
            LocalDateTime msgObj2Date = msgObj2.getTimestamp();
            LocalDateTime msgObj1Date = msgObj1.getTimestamp();
            return msgObj1Date.compareTo(msgObj2Date);
        });

        return list;
    }

    public void addMessageStructObject(JvMessageStructObject messageStructObject) {
        addItem(messageStructObject, getRootObject());
    }
}