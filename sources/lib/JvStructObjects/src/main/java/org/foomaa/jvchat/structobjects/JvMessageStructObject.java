package org.foomaa.jvchat.structobjects;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.foomaa.jvchat.globaldefines.JvMainChatsGlobalDefines;


public class JvMessageStructObject extends JvBaseStructObject {
    private UUID uuidUserSender;
    private UUID uuidUserReceiver;
    private JvMainChatsGlobalDefines.TypeStatusMessage statusMessage;
    private String text;
    private LocalDateTime timestamp;

    JvMessageStructObject() {
        uuidUserSender = null;
        uuidUserReceiver = null;
        statusMessage = null;
        text = null;
        timestamp = null;

        commitProperties();
    }

    public void setUuidUserSender(UUID newUuidUserSender) {
        if (uuidUserSender == null || uuidUserSender.equals(newUuidUserSender)) {
            uuidUserSender = newUuidUserSender;
            commitProperties();
        }
    }

    public void setUuidUserReceiver(UUID newUuidUserReceiver) {
        if (uuidUserReceiver == null || uuidUserSender.equals(newUuidUserReceiver)) {
            uuidUserReceiver = newUuidUserReceiver;
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

    public UUID getUuidUserReceiver() {
        return uuidUserReceiver;
    }

    public UUID getUuidUserSender() {
        return uuidUserSender;
    }
}
