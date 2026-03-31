package org.foomaa.jvchat.structobjects;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.ObjectProvider;

import lombok.Builder;

import org.foomaa.jvchat.globaldefines.MainChatsGlobalDefines;

public class UserStructObjectFactory {
    private final ObjectProvider<UserStructObject> userStructObjectObjectProvider;

    @Builder
    UserStructObjectFactory(ObjectProvider<UserStructObject> userStructObjectObjectProvider) {
        this.userStructObjectObjectProvider = Objects.requireNonNull(userStructObjectObjectProvider,
                "userStructObjectObjectProvider is mandatory");
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
