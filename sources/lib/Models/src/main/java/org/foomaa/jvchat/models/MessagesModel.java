package org.foomaa.jvchat.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import lombok.Builder;

import org.foomaa.jvchat.globaldefines.MainChatsGlobalDefines;
import org.foomaa.jvchat.structobjects.*;

public class MessagesModel extends BaseModel {
    // DI ↓
    private final MessageStructObjectFactory messageStructObjectFactory;

    @Builder
    MessagesModel(
            RootObjectsModel rootObjectsModel,
            MessageStructObjectFactory messageStructObjectFactory,
            RootStructObjectFactory rootStructObjectFactory) {
        super(
                Objects.requireNonNull(rootObjectsModel, "rootObjectsModel is mandatory"),
                Objects.requireNonNull(rootStructObjectFactory, "rootStructObjectFactory is mandatory"));

        this.messageStructObjectFactory =
                Objects.requireNonNull(messageStructObjectFactory, "messageStructObjectFactory is mandatory");
    }

    public MessageStructObject createNewMessage(
            UUID uuidUserSender,
            UUID uuidUserReceiver,
            UUID uuidMessage,
            MainChatsGlobalDefines.TypeStatusMessage statusMessage,
            String text,
            LocalDateTime timestamp) {
        MessageStructObject messageObj = messageStructObjectFactory.create(
                uuidUserSender, uuidUserReceiver, statusMessage, text, timestamp, uuidMessage);

        addItem(messageObj, getRootObject());

        return messageObj;
    }

    public List<MessageStructObject> getAllMessages() {
        List<MessageStructObject> resultList = new ArrayList<>();

        for (BaseStructObject baseStructObject : getRootObject().getChildren()) {
            MessageStructObject messageStructObject = (MessageStructObject) baseStructObject;
            if (messageStructObject != null) {
                resultList.add(messageStructObject);
            }
        }

        return resultList;
    }

    public List<MessageStructObject> getSortedMessagesObjects() {
        List<MessageStructObject> list = getAllMessages();

        list.sort((msgObj1, msgObj2) -> {
            LocalDateTime msgObj2Date = msgObj2.getTimestamp();
            LocalDateTime msgObj1Date = msgObj1.getTimestamp();
            return msgObj1Date.compareTo(msgObj2Date);
        });

        return list;
    }

    public void addMessageStructObject(MessageStructObject messageStructObject) {
        addItem(messageStructObject, getRootObject());
    }
}
