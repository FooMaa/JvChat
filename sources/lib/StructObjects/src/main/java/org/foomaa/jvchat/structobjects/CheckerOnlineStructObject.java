package org.foomaa.jvchat.structobjects;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Scope("prototype")
@Profile("servers")
@Getter
public class CheckerOnlineStructObject extends BaseStructObject {
    private UserStructObject user;
    private SocketRunnableCtrlStructObject socketRunnableCtrlStructObject;
    private boolean isSending;
    private LocalDateTime dateTimeSending;
    private LocalDateTime dateTimeUpdating;

    CheckerOnlineStructObject() {
        user = null;
        isSending = false;
        dateTimeSending = null;
        dateTimeUpdating = null;
        socketRunnableCtrlStructObject = null;

        commitProperties();
    }

    public void setUser(UserStructObject newUser) {
        if (user != newUser) {
            user = newUser;
            commitProperties();
        }
    }

    public void setIsSending(boolean newIsSending) {
        if (isSending != newIsSending) {
            isSending = newIsSending;
            commitProperties();
        }
    }

    public void setDateTimeSending(LocalDateTime newDateTimeSending) {
        if (!Objects.equals(dateTimeSending, newDateTimeSending)) {
            dateTimeSending = newDateTimeSending;
            commitProperties();
        }
    }

    public void setDateTimeUpdating(LocalDateTime newDateTimeUpdating) {
        if (dateTimeUpdating != newDateTimeUpdating) {
            dateTimeUpdating = newDateTimeUpdating;
            commitProperties();
        }
    }

    public void setSocketRunnableCtrlStructObject(
            SocketRunnableCtrlStructObject newRunnableCtrlStructObject) {
        if (socketRunnableCtrlStructObject != newRunnableCtrlStructObject) {
            socketRunnableCtrlStructObject = newRunnableCtrlStructObject;
            commitProperties();
        }
    }
}
