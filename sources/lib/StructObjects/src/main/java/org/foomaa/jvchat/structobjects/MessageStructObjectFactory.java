package org.foomaa.jvchat.structobjects;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import org.foomaa.jvchat.globaldefines.MainChatsGlobalDefines;

@Component
public class MessageStructObjectFactory {
    private final ObjectProvider<MessageStructObject> messageStructObjectObjectProvider;

    MessageStructObjectFactory(
            ObjectProvider<MessageStructObject> messageStructObjectObjectProvider) {
        this.messageStructObjectObjectProvider = messageStructObjectObjectProvider;
    }

    public MessageStructObject create() {
        return messageStructObjectObjectProvider.getObject();
    }

    public MessageStructObject create(UUID uuidUserSender, UUID uuidUserReceiver,
            MainChatsGlobalDefines.TypeStatusMessage statusMessage, String text, LocalDateTime timestamp,
            UUID uuidMessage) {
        MessageStructObject messageStructObject = messageStructObjectObjectProvider.getObject();

        messageStructObject.setUuidUserSender(uuidUserSender);
        messageStructObject.setUuidUserReceiver(uuidUserReceiver);
        messageStructObject.setStatusMessage(statusMessage);
        messageStructObject.setText(text);
        messageStructObject.setTimestamp(timestamp);
        messageStructObject.setUuid(uuidMessage);

        return messageStructObject;
    }
}
