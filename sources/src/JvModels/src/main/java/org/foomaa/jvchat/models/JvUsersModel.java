package org.foomaa.jvchat.models;

import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.structobjects.JvBaseStructObject;
import org.foomaa.jvchat.structobjects.JvGetterStructObjects;
import org.foomaa.jvchat.structobjects.JvUserStructObject;

import java.util.Objects;


public class JvUsersModel extends JvBaseModel {
    JvUsersModel() {
        setRootObject(JvGetterStructObjects.getInstance()
                .getBeanRootStructObject(getNameModel()));
    }

    public void addCreatedUser(JvUserStructObject userStructObject) {
        if (isLoginAdded(userStructObject.getLogin())) {
            addItem(userStructObject, getRootObject());
        }
    }

    private boolean isLoginAdded(String login) {
        return findUserStructObjectByLogin(login) != null;
    }

    private JvUserStructObject findUserStructObjectByLogin(String login) {
        for (JvBaseStructObject baseStructObject: getRootObject().getChildren()) {
            JvUserStructObject userStructObject = (JvUserStructObject) baseStructObject;
            if (userStructObject != null &&
                    Objects.equals(userStructObject.getLogin(), login)) {
                return userStructObject;
            }
        }

        return null;
    }

    public JvUserStructObject findCreateUserStructObjectByLogin(String login) {
        JvUserStructObject userStructObject = findUserStructObjectByLogin(login);

        if (userStructObject == null) {
            JvLog.write(JvLog.TypeLog.Warn, "Здесь не создан userStructObject c логином, создаю...");
            JvUserStructObject userChat = JvGetterStructObjects.getInstance().getBeanUserStructObject();
            userChat.setLogin(login);
            addItem(userChat, getRootObject());
            return userChat;
        }

        return userStructObject;
    }
}
