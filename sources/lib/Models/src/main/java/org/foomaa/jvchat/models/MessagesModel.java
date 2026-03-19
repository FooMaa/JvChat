package org.foomaa.jvchat.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.foomaa.jvchat.globaldefines.MainChatsGlobalDefines;
import org.foomaa.jvchat.structobjects.BaseStructObject;
import org.foomaa.jvchat.structobjects.MessageStructObject;
import org.foomaa.jvchat.structobjects.RootStructObject;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


@Component
@Lazy
public class MessagesModel extends BaseModel {
    private final ObjectProvider<MessageStructObject> messageStructObjectObjectProvider;

    MessagesModel(ObjectProvider<MessageStructObject> messageStructObjectObjectProvider,
                  ObjectProvider<RootStructObject> rootStructObjectObjectProvider,
                  RootObjectsModel rootObjectsModel) {
        super(rootObjectsModel, rootStructObjectObjectProvider);

        this.messageStructObjectObjectProvider = messageStructObjectObjectProvider;
    }

    public MessageStructObject createNewMessage(UUID uuidUserSender,
                                                UUID uuidUserReceiver,
                                                UUID uuidMessage,
                                                MainChatsGlobalDefines.TypeStatusMessage statusMessage,
                                                String text,
                                                LocalDateTime timestamp) {
        MessageStructObject messageObj = messageStructObjectObjectProvider.getObject();

        messageObj.setUuidUserSender(uuidUserSender);
        messageObj.setUuidUserReceiver(uuidUserReceiver);
        messageObj.setText(text);
        messageObj.setStatusMessage(statusMessage);
        messageObj.setUuid(uuidMessage);
        messageObj.setTimestamp(timestamp);

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