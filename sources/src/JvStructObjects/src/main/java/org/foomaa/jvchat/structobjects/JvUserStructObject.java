package org.foomaa.jvchat.structobjects;

import java.time.LocalDateTime;
import java.util.Objects;

import org.foomaa.jvchat.globaldefines.JvMainChatsGlobalDefines;


public class JvUserStructObject extends JvBaseStructObject {
    private String login;
    private JvMainChatsGlobalDefines.TypeStatusOnline statusOnline;
    private LocalDateTime timestampLastOnline;

    JvUserStructObject() {
        login = null;
        statusOnline = null;

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
}
