package org.foomaa.jvchat.models;

import java.util.Objects;
import java.util.UUID;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.structobjects.*;

@Slf4j
public class UsersModel extends BaseModel {
    // DI ↓
    private final UserStructObjectFactory userStructObjectFactory;

    @Builder
    UsersModel(
            RootObjectsModel rootObjectsModel,
            UserStructObjectFactory userStructObjectFactory,
            RootStructObjectFactory rootStructObjectFactory) {
        super(
                Objects.requireNonNull(rootObjectsModel, "rootObjectsModel is mandatory"),
                Objects.requireNonNull(rootStructObjectFactory, "rootStructObjectFactory is mandatory"));

        this.userStructObjectFactory =
                Objects.requireNonNull(userStructObjectFactory, "userStructObjectFactory is mandatory");
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
