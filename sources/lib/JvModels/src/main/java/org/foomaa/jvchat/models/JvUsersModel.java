package org.foomaa.jvchat.models;

import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.structobjects.JvBaseStructObject;
import org.foomaa.jvchat.structobjects.JvGetterStructObjects;
import org.foomaa.jvchat.structobjects.JvUserStructObject;

import java.util.UUID;


public class JvUsersModel extends JvBaseModel {
    JvUsersModel() {
        setRootObject(JvGetterStructObjects.getInstance()
                .getBeanRootStructObject(getNameModel()));
    }

    public void addCreatedUser(JvUserStructObject userStructObject) {
        if (isUuidUserAdded(userStructObject.getUuid())) {
            addItem(userStructObject, getRootObject());
        }
    }

    private boolean isUuidUserAdded(UUID uuidUser) {
        return findUserStructObjectByUuidUser(uuidUser) != null;
    }

    private JvUserStructObject findUserStructObjectByUuidUser(UUID uuidUser) {
        for (JvBaseStructObject baseStructObject: getRootObject().getChildren()) {
            JvUserStructObject userStructObject = (JvUserStructObject) baseStructObject;
            if (userStructObject != null && userStructObject.getUuid().equals(uuidUser)) {
                return userStructObject;
            }
        }

        return null;
    }

    public JvUserStructObject findCreateUserStructObjectByUuidUser(UUID uuidUser) {
        JvUserStructObject userStructObject = findUserStructObjectByUuidUser(uuidUser);

        if (userStructObject == null) {
            JvLog.write(JvLog.TypeLog.Warn, "There is no userStructObject with uuid created here, creating...");
            JvUserStructObject userChat = JvGetterStructObjects.getInstance().getBeanUserStructObject();
            userChat.setUuid(uuidUser);
            addItem(userChat, getRootObject());
            return userChat;
        }

        return userStructObject;
    }
}
