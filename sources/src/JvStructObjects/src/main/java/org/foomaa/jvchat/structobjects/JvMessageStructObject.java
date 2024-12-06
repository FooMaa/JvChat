package org.foomaa.jvchat.structobjects;

import java.time.LocalDateTime;
import java.util.Objects;

import org.foomaa.jvchat.globaldefines.JvMainChatsGlobalDefines;


public class JvMessageStructObject extends JvBaseStructObject {
    private String loginSender;
    private String loginReceiver;
    private JvMainChatsGlobalDefines.TypeStatusMessage statusMessage;
    private String text;
    private LocalDateTime timestamp;

    JvMessageStructObject() {
        loginSender = null;
        loginReceiver = null;
        statusMessage = null;
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

    public void setText(String newText) {
        if (!Objects.equals(text, newText)) {
            text = newText;
            commitProperties();
        }
    }

    public void setTimestamp(LocalDateTime newTimestamp) {
        if (!Objects.equals(timestamp, newTimestamp)) {
            timestamp = newTimestamp;
            commitProperties();
        }
    }

    public JvMainChatsGlobalDefines.TypeStatusMessage getStatusMessage() {
        return statusMessage;
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
