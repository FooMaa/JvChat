package org.foomaa.jvchat.structobjects;

import java.time.LocalDateTime;
import java.util.Objects;

import lombok.Builder;
import lombok.Getter;

import org.foomaa.jvchat.globaldefines.MainChatsGlobalDefines;

@Getter
public class UserStructObject extends BaseStructObject {
    private String login;
    private MainChatsGlobalDefines.TypeStatusOnline statusOnline;
    private LocalDateTime timestampLastOnline;

    @Builder
    UserStructObject() {
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

    public void setStatusOnline(MainChatsGlobalDefines.TypeStatusOnline newStatusOnline) {
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
}
