package org.foomaa.jvchat.structobjects;

import java.time.LocalDateTime;
import java.util.Objects;


public class JvCheckerOnlineStructObject extends JvBaseStructObject {
    private JvUserStructObject user;
    private boolean isSending;
    private LocalDateTime dateTimeSending;
    private LocalDateTime dateTimeUpdating;

    JvCheckerOnlineStructObject() {
        user = null;
        isSending = false;
        dateTimeSending = null;
        dateTimeUpdating = null;

        commitProperties();
    }

    public void setUser(JvUserStructObject newUser) {
        if (user != newUser) {
            user = newUser;
            commitProperties();
        }
    }

    public void setSending(boolean newIsSending) {
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

    public JvUserStructObject getUser() {
        return user;
    }

    public boolean getIsSending() {
        return isSending;
    }

    public LocalDateTime getDateTimeSending() {
        return dateTimeSending;
    }

    public LocalDateTime getDateTimeUpdating() {
        return dateTimeUpdating;
    }
}
