package org.foomaa.jvchat.structobjects;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import org.foomaa.jvchat.globaldefines.MainChatsGlobalDefines;

@Component
public class UserStructObjectFactory {
    private final ObjectProvider<UserStructObject> userStructObjectObjectProvider;

    UserStructObjectFactory(ObjectProvider<UserStructObject> userStructObjectObjectProvider) {
        this.userStructObjectObjectProvider = userStructObjectObjectProvider;
    }

    public UserStructObject create() {
        return userStructObjectObjectProvider.getObject();
    }

    public UserStructObject create(String login, MainChatsGlobalDefines.TypeStatusOnline statusOnline,
            LocalDateTime timestampLastOnline, UUID uuid) {
        UserStructObject structObject = userStructObjectObjectProvider.getObject();

        structObject.setLogin(login);
        structObject.setStatusOnline(statusOnline);
        structObject.setTimestampLastOnline(timestampLastOnline);
        structObject.setUuid(uuid);

        return structObject;
    }

    public UserStructObject create(String login, UUID uuid) {
        UserStructObject structObject = userStructObjectObjectProvider.getObject();

        structObject.setLogin(login);
        structObject.setUuid(uuid);

        return structObject;
    }

    public UserStructObject create(UUID uuid) {
        UserStructObject structObject = userStructObjectObjectProvider.getObject();

        structObject.setUuid(uuid);

        return structObject;
    }
}
