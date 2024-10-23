package org.foomaa.jvchat.models;

import org.foomaa.jvchat.globaldefines.JvMainChatsGlobalDefines;
import org.foomaa.jvchat.structobjects.JvGetterStructObjects;
import org.foomaa.jvchat.structobjects.JvMessageStructObject;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;


public class JvMessagesModel extends JvBaseModel {
    private static JvMessagesModel instance;

    private String currentActiveLoginUI;

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

    public String getCurrentActiveLoginUI() {
        return currentActiveLoginUI;
    }

    public void setCurrentActiveLoginUI(String newCurrentActiveLoginUI) {
        if (!Objects.equals(currentActiveLoginUI, newCurrentActiveLoginUI)) {
            currentActiveLoginUI = newCurrentActiveLoginUI;
        }
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
}