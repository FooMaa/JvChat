package org.foomaa.jvchat.structobjects;

import org.foomaa.jvchat.globaldefines.JvMainChatsGlobalDefines;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;


public class JvMessageStructObject extends JvBaseStructObject {
    private String loginSender;
    private String loginReceiver;
    private JvMainChatsGlobalDefines.TypeStatusMessage statusMessage;
    private UUID uuid;
    private String text;
    private LocalDateTime timestamp;

    JvMessageStructObject() {
        loginSender = null;
        loginReceiver = null;
        statusMessage = null;
        uuid = null;
        text = null;
        timestamp = null;

        commitProperties();
    }

    public String getLoginSender() {
        return loginSender;
    }

    public void setLoginSender(String newLoginSender) {
        if (!Objects.equals(loginSender, newLoginSender)) {
            loginSender = newLoginSender;
            commitProperties();
        }
    }

    public void setLoginReceiver(String newLoginReceiver) {
        if (!Objects.equals(loginReceiver, newLoginReceiver)) {
            loginReceiver = newLoginReceiver;
            commitProperties();
        }
    }

    public void setStatusMessage(JvMainChatsGlobalDefines.TypeStatusMessage newStatusMessage) {
        if (statusMessage != newStatusMessage) {
            statusMessage = newStatusMessage;
            commitProperties();
        }
    }

    public void setUuid(UUID newUuid) {
        if (!uuid.equals(newUuid)) {
            uuid = newUuid;
            commitProperties();
        }
    }

    public void setText(String newText) {
        if (!Objects.equals(text, newText)) {
            text = newText;
            commitProperties();
        }
    }

    public void setTimestamp(LocalDateTime newTimestamp) {
        if (timestamp != newTimestamp) {
            timestamp = newTimestamp;
            commitProperties();
        }
    }

    public JvMainChatsGlobalDefines.TypeStatusMessage getStatusMessage() {
        return statusMessage;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getLoginReceiver() {
        return loginReceiver;
    }
}
