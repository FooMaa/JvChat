package org.foomaa.jvchat.ctrl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.globaldefines.MainChatsGlobalDefines;
import org.foomaa.jvchat.messages.DefinesMessages;
import org.foomaa.jvchat.models.ChatsModel;
import org.foomaa.jvchat.models.MessagesModel;
import org.foomaa.jvchat.settings.UsersInfoSettings;
import org.foomaa.jvchat.structobjects.ChatStructObject;
import org.foomaa.jvchat.structobjects.MessageStructObject;
import org.foomaa.jvchat.structobjects.MessageStructObjectFactory;
import org.foomaa.jvchat.tools.FormatTools;

@Slf4j
public class MessagesDialogCtrl {
    private final MessagesModel messagesModel;
    private final ChatsModel chatsModel;
    private final FormatTools formatTools;
    private final SendMessagesCtrl sendMessagesCtrl;
    private final MessagesDefinesCtrl messagesDefinesCtrl;
    private final OnlineServersCtrl onlineServersCtrl;
    private final ChatsCtrl chatsCtrl;
    private final UsersInfoSettings usersInfoSettings;
    private final MessageStructObjectFactory messageStructObjectFactory;

    @Builder
    MessagesDialogCtrl(
            MessagesModel messagesModel,
            ChatsModel chatsModel,
            FormatTools formatTools,
            SendMessagesCtrl sendMessagesCtrl,
            MessagesDefinesCtrl messagesDefinesCtrl,
            MessageStructObjectFactory messageStructObjectFactory,
            OnlineServersCtrl onlineServersCtrl,
            ChatsCtrl chatsCtrl,
            UsersInfoSettings usersInfoSettings) {
        this.messagesModel = Objects.requireNonNull(messagesModel, "messagesModel is mandatory");
        this.chatsModel = Objects.requireNonNull(chatsModel, "chatsModel is mandatory");
        this.formatTools = Objects.requireNonNull(formatTools, "formatTools is mandatory");
        this.sendMessagesCtrl = Objects.requireNonNull(sendMessagesCtrl, "sendMessagesCtrl is mandatory");
        this.messagesDefinesCtrl = Objects.requireNonNull(messagesDefinesCtrl, "messagesDefinesCtrl is mandatory");
        this.messageStructObjectFactory =
                Objects.requireNonNull(messageStructObjectFactory, "messageStructObjectFactory is mandatory");

        this.onlineServersCtrl = onlineServersCtrl;
        this.chatsCtrl = chatsCtrl;
        this.usersInfoSettings = usersInfoSettings;
    }

    public void setCurrentActiveChatUuid(UUID newUuidChat) {
        chatsModel.setCurrentActiveChatUuid(newUuidChat);
    }

    public UUID getCurrentActiveChatUuid() {
        return chatsModel.getCurrentActiveChatUuid();
    }

    public ChatStructObject findChatByUuid(UUID targetUuid) {
        List<ChatStructObject> chatsList = chatsModel.getAllChatsObjects();

        for (ChatStructObject chat : chatsList) {
            UUID chatUuid = chat.getUuid();
            if (chatUuid.equals(targetUuid)) {
                return chat;
            }
        }
        return null;
    }

    public MessageStructObject createAndSendMessage(String text) {
        if (getCurrentActiveChatUuid() == null) {
            log.error("No selected dialog, cannot sending message");
            return null;
        }

        if (usersInfoSettings == null) {
            log.error("usersInfoSettings is null");
            return null;
        }

        UUID uuidSender = usersInfoSettings.getUuid();
        ChatStructObject chat = findChatByUuid(getCurrentActiveChatUuid());
        UUID uuidReceiver = chat.getUserChat().getUuid();
        UUID uuidMessage = UUID.randomUUID();
        LocalDateTime timestamp = LocalDateTime.now();
        MainChatsGlobalDefines.TypeStatusMessage status = MainChatsGlobalDefines.TypeStatusMessage.Sent;

        MessageStructObject messageStructObject =
                messagesModel.createNewMessage(uuidSender, uuidReceiver, uuidMessage, status, text, timestamp);

        sendNewMessage(messageStructObject);
        setLastMessageInChatCtrl(messageStructObject);

        return messageStructObject;
    }

    public void createMessagesObjects(List<Map<DefinesMessages.TypeData, Object>> msgInfo) {
        messagesModel.clearModel();
        int normalizeCountTimestamp = 3;

        for (Map<DefinesMessages.TypeData, Object> msg : msgInfo) {
            UUID uuidUserSender = (UUID) msg.get(DefinesMessages.TypeData.UuidUserSender);
            UUID uuidUserReceiver = (UUID) msg.get(DefinesMessages.TypeData.UuidUserReceiver);
            UUID uuidMessage = (UUID) msg.get(DefinesMessages.TypeData.UuidMessage);
            String text = (String) msg.get(DefinesMessages.TypeData.TextMessage);
            MainChatsGlobalDefines.TypeStatusMessage statusMessage =
                    MainChatsGlobalDefines.TypeStatusMessage.getTypeStatusMessage(
                            (Integer) msg.get(DefinesMessages.TypeData.StatusMessage));
            LocalDateTime timestampMessage = formatTools.stringToLocalDateTime(
                    (String) msg.get(DefinesMessages.TypeData.Timestamp), normalizeCountTimestamp);

            if (timestampMessage == null) {
                log.warn("It was not possible to normalize the date and time to the required format.");
            }

            messagesModel.createNewMessage(
                    uuidUserSender, uuidUserReceiver, uuidMessage, statusMessage, text, timestampMessage);
        }
    }

    private void setLastMessageInChatCtrl(MessageStructObject message) {
        if (chatsCtrl == null) {
            log.error("chatsCtrl is null");
            return;
        }

        chatsCtrl.changeLastMessage(message);
    }

    private void sendNewMessage(MessageStructObject message) {
        String timestampNewMessage = formatTools.localDateTimeToString(message.getTimestamp());

        sendMessagesCtrl.sendMessage(
                DefinesMessages.TypeMessage.TextMessageSendUserToServer,
                message.getUuidUserSender(),
                message.getUuidUserReceiver(),
                message.getUuid(),
                message.getText(),
                timestampNewMessage);
    }

    public void setDirtyStatusToMessage(Map<UUID, MainChatsGlobalDefines.TypeStatusMessage> mapStatusesMessages) {
        for (UUID uuid : mapStatusesMessages.keySet()) {
            MessageStructObject message = findMessage(uuid);
            if (message != null) {
                message.setStatusMessage(mapStatusesMessages.get(uuid));
            }
        }
        messagesDefinesCtrl.setTextMessagesChangingStatusFromServerFlag(MessagesDefinesCtrl.TypeFlags.TRUE);
    }

    public MessageStructObject findMessage(UUID uuid) {
        List<MessageStructObject> listMessages = messagesModel.getAllMessages();

        for (MessageStructObject message : listMessages) {
            if (message != null && message.getUuid().equals(uuid)) {
                return message;
            }
        }
        return null;
    }

    public boolean isCurrentUserSender(MessageStructObject messageStructObject) {
        if (usersInfoSettings == null) {
            log.error("usersInfoSettings is null");
            return false;
        }

        UUID currentUuid = usersInfoSettings.getUuid();
        return Objects.equals(currentUuid, messageStructObject.getUuidUserSender());
    }

    public void redirectMessageToOnlineUser(
            UUID uuidUserSender,
            UUID uuidUserReceiver,
            UUID uuidMessage,
            MainChatsGlobalDefines.TypeStatusMessage statusMessage,
            String text,
            LocalDateTime timestamp) {
        MessageStructObject messageStructObject =
                createMessageByData(uuidUserSender, uuidUserReceiver, uuidMessage, statusMessage, text, timestamp);

        if (onlineServersCtrl == null) {
            log.error("onlineServersCtrl is null");
            return;
        }

        boolean isUserOnline =
                onlineServersCtrl.isUuidUserInListCheckerOnline(messageStructObject.getUuidUserReceiver());
        if (!isUserOnline) {
            return;
        }

        Runnable runnableUserCtrl = onlineServersCtrl.getRunnableByUuidUser(messageStructObject.getUuidUserReceiver());
        if (runnableUserCtrl == null) {
            log.error("Here runnableUserCtrl turned out to be null.");
            return;
        }

        String timestampMessage = formatTools.localDateTimeToString(messageStructObject.getTimestamp());

        sendMessagesCtrl.sendMessage(
                DefinesMessages.TypeMessage.TextMessageRedirectServerToUser,
                messageStructObject.getUuidUserSender(),
                messageStructObject.getUuidUserReceiver(),
                messageStructObject.getUuid(),
                messageStructObject.getText(),
                timestampMessage,
                runnableUserCtrl);
    }

    private MessageStructObject createMessageByData(
            UUID uuidUserSender,
            UUID uuidUserReceiver,
            UUID uuidMessage,
            MainChatsGlobalDefines.TypeStatusMessage statusMessage,
            String text,
            LocalDateTime timestamp) {

        return messageStructObjectFactory.create(
                uuidUserSender, uuidUserReceiver, statusMessage, text, timestamp, uuidMessage);
    }

    public String getTimeFormattedMessage(LocalDateTime timestamp) {
        if (timestamp == null) {
            log.error("Here the timestamp turned out to be null.");
            return "";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return timestamp.format(formatter);
    }

    public void addRedirectMessageToModel(
            UUID uuidUserSender,
            UUID uuidUserReceiver,
            UUID uuidMessage,
            MainChatsGlobalDefines.TypeStatusMessage statusMessage,
            String text,
            LocalDateTime timestamp) {
        MessageStructObject messageStructObject =
                createMessageByData(uuidUserSender, uuidUserReceiver, uuidMessage, statusMessage, text, timestamp);
        messagesModel.addMessageStructObject(messageStructObject);
    }

    public List<MessageStructObject> getAllSortedMessages() {
        return messagesModel.getSortedMessagesObjects();
    }

    public UUID findUuidChatByUuidUser(UUID uuidSender) {
        List<ChatStructObject> chatsList = chatsModel.getAllChatsObjects();

        for (ChatStructObject chat : chatsList) {
            UUID userUuid = chat.getUserChat().getUuid();
            if (userUuid.equals(uuidSender)) {
                return chat.getUuid();
            }
        }
        return null;
    }
}
