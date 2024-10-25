package org.foomaa.jvchat.structobjects;

import org.foomaa.jvchat.globaldefines.JvMainChatsGlobalDefines;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class JvUserStructObject extends JvBaseStructObject {
    private String login;
    private JvMainChatsGlobalDefines.TypeStatusOnline statusOnline;
    private final UUID uuid;
    private LocalDateTime timestampLastOnline;

    JvUserStructObject() {
        login = null;
        statusOnline = null;
        uuid = UUID.randomUUID();

        commitProperties();
    }

    public void setLogin(String newLogin) {
        if (!Objects.equals(login, newLogin)) {
            login = newLogin;
            commitProperties();
        }
    }

    public void setStatusOnline(JvMainChatsGlobalDefines.TypeStatusOnline newStatusOnline) {
        if (statusOnline != newStatusOnline) {
            statusOnline = newStatusOnline;
             commitProperties();
        }
    }

    public void setTimestampLastOnline(LocalDateTime newTimestampLastOnline) {
        if (!Objects.equals(timestampLastOnline, newTimestampLastOnline)) {
            timestampLastOnline = newTimestampLastOnline;
            commitProperties();
        }
    }

    public String getLogin() {
        return login;
    }

    public JvMainChatsGlobalDefines.TypeStatusOnline getStatusOnline() {
        return statusOnline;
    }

    public LocalDateTime getTimestampLastOnline() {
        return timestampLastOnline;
    }

    public UUID getUuid() {
        return uuid;
    }
}
