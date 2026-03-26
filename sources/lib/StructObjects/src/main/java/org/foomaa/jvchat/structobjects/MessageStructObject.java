package org.foomaa.jvchat.structobjects;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Getter;

import org.foomaa.jvchat.globaldefines.MainChatsGlobalDefines;

@Component
@Scope("prototype")
@Getter
public class MessageStructObject extends BaseStructObject {
    private UUID uuidUserSender;
    private UUID uuidUserReceiver;
    private MainChatsGlobalDefines.TypeStatusMessage statusMessage;
    private String text;
    private LocalDateTime timestamp;

    MessageStructObject() {
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

    public void setStatusMessage(MainChatsGlobalDefines.TypeStatusMessage newStatusMessage) {
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
}
