package org.foomaa.jvchat.models;

import lombok.extern.slf4j.Slf4j;
import org.foomaa.jvchat.structobjects.BaseStructObject;
import org.foomaa.jvchat.structobjects.RootStructObject;
import org.foomaa.jvchat.structobjects.UserStructObject;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
@Lazy
@Slf4j
public class UsersModel extends BaseModel {
    private final ObjectProvider<UserStructObject> userStructObjectObjectProvider;

    UsersModel(ObjectProvider<UserStructObject> userStructObjectObjectProvider,
               ObjectProvider<RootStructObject> rootStructObjectObjectProvider,
               RootObjectsModel rootObjectsModel) {
        super(rootObjectsModel, rootStructObjectObjectProvider);

        this.userStructObjectObjectProvider = userStructObjectObjectProvider;
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
            log.warn("There is no userStructObject with uuid created here, creating...");
            UserStructObject userChat = userStructObjectObjectProvider.getObject();
            userChat.setUuid(uuidUser);
            addItem(userChat, getRootObject());
            return userChat;
        }

        return userStructObject;
    }
}
