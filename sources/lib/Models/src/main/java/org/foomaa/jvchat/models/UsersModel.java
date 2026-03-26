package org.foomaa.jvchat.models;

import java.util.UUID;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.structobjects.*;

@Component
@Lazy
@Slf4j
public class UsersModel extends BaseModel {
    private final UserStructObjectFactory userStructObjectFactory;

    UsersModel(UserStructObjectFactory userStructObjectFactory, RootStructObjectFactory rootStructObjectFactory,
            RootObjectsModel rootObjectsModel) {
        super(rootObjectsModel, rootStructObjectFactory);

        this.userStructObjectFactory = userStructObjectFactory;
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
        for (BaseStructObject baseStructObject : getRootObject().getChildren()) {
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
            UserStructObject userChat = userStructObjectFactory.create(uuidUser);
            addItem(userChat, getRootObject());
            return userChat;
        }

        return userStructObject;
    }
}
