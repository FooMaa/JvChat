package org.foomaa.jvchat.structobjects;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.beans.factory.ObjectProvider;

import lombok.Builder;

public class CheckerOnlineStructObjectFactory {
    private final ObjectProvider<CheckerOnlineStructObject> checkerOnlineObjectProvider;

    @Builder
    CheckerOnlineStructObjectFactory(ObjectProvider<CheckerOnlineStructObject> checkerOnlineObjectProvider) {
        this.checkerOnlineObjectProvider =
                Objects.requireNonNull(checkerOnlineObjectProvider, "checkerOnlineObjectProvider is mandatory");
    }

    public CheckerOnlineStructObject create() {
        return checkerOnlineObjectProvider.getObject();
    }

    public CheckerOnlineStructObject create(
            UserStructObject user,
            boolean isSending,
            LocalDateTime dateTimeSending,
            LocalDateTime dateTimeUpdating,
            SocketRunnableCtrlStructObject runnableCtrlStructObject) {
        CheckerOnlineStructObject connectionEventStructObject = checkerOnlineObjectProvider.getObject();

        connectionEventStructObject.setUser(user);
        connectionEventStructObject.setIsSending(isSending);
        connectionEventStructObject.setDateTimeSending(dateTimeSending);
        connectionEventStructObject.setDateTimeUpdating(dateTimeUpdating);
        connectionEventStructObject.setSocketRunnableCtrlStructObject(runnableCtrlStructObject);

        return connectionEventStructObject;
    }

    public CheckerOnlineStructObject create(UserStructObject user, LocalDateTime dateTimeUpdating) {
        CheckerOnlineStructObject connectionEventStructObject = checkerOnlineObjectProvider.getObject();

        connectionEventStructObject.setUser(user);
        connectionEventStructObject.setDateTimeUpdating(dateTimeUpdating);

        return connectionEventStructObject;
    }

    public CheckerOnlineStructObject create(
            boolean isSending,
            LocalDateTime dateTimeSending,
            LocalDateTime dateTimeUpdating,
            SocketRunnableCtrlStructObject runnableCtrlStructObject) {
        CheckerOnlineStructObject connectionEventStructObject = checkerOnlineObjectProvider.getObject();

        connectionEventStructObject.setIsSending(isSending);
        connectionEventStructObject.setDateTimeSending(dateTimeSending);
        connectionEventStructObject.setDateTimeUpdating(dateTimeUpdating);
        connectionEventStructObject.setSocketRunnableCtrlStructObject(runnableCtrlStructObject);

        return connectionEventStructObject;
    }
}
