package org.foomaa.jvchat.models;

import org.foomaa.jvchat.logger.Log;
import org.foomaa.jvchat.structobjects.BaseStructObject;
import org.foomaa.jvchat.structobjects.GetterStructObjects;
import org.foomaa.jvchat.structobjects.UserStructObject;

import java.util.UUID;


public class UsersModel extends BaseModel {
    UsersModel() {
        setRootObject(GetterStructObjects.getInstance()
                .getBeanRootStructObject(getNameModel()));
    }

    public void addCreatedUser(UserStructObject userStructObject) {
        if (isUuidUserAdded(userStructObject.getUuid())) {
            addItem(userStructObject, getRootObject());
        }
    }

    private boolean isUuidUserAdded(UUID uuidUser) {
        return findUserStructObjectByUuidUser(uuidUser) != null;
    }

    private UserStructObject findUserStructObjectByUuidUser(UUID uuidUser) {
        for (BaseStructObject baseStructObject: getRootObject().getChildren()) {
            UserStructObject userStructObject = (UserStructObject) baseStructObject;
            if (userStructObject != null && userStructObject.getUuid().equals(uuidUser)) {
                return userStructObject;
            }
        }

        return null;
    }

    public UserStructObject findCreateUserStructObjectByUuidUser(UUID uuidUser) {
        UserStructObject userStructObject = findUserStructObjectByUuidUser(uuidUser);

        if (userStructObject == null) {
            Log.write(Log.TypeLog.Warn, "There is no userStructObject with uuid created here, creating...");
            UserStructObject userChat = GetterStructObjects.getInstance().getBeanUserStructObject();
            userChat.setUuid(uuidUser);
            addItem(userChat, getRootObject());
            return userChat;
        }

        return userStructObject;
    }
}
