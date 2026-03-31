package org.foomaa.jvchat.messages;

import java.util.*;

import com.google.protobuf.InvalidProtocolBufferException;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.globaldefines.MainChatsGlobalDefines;

@Slf4j
public class DeserializatorDataMessages {
    @Builder
    DeserializatorDataMessages() {
    }

    public HashMap<DefinesMessages.TypeData, ?> deserializeData(DefinesMessages.TypeMessage type, byte[] data) {
        return switch (type) {
            case EntryRequest -> takeEntryRequestMessage(data);
            case EntryReply -> takeEntryReplyMessage(data);
            case RegistrationRequest -> takeRegistrationRequestMessage(data);
            case RegistrationReply -> takeRegistrationReplyMessage(data);
            case VerifyRegistrationEmailRequest -> takeVerifyRegistrationEmailRequestMessage(data);
            case VerifyRegistrationEmailReply -> takeVerifyRegistrationEmailReplyMessage(data);
            case ResetPasswordRequest -> takeResetPasswordRequestMessage(data);
            case ResetPasswordReply -> takeResetPasswordReplyMessage(data);
            case VerifyFamousEmailRequest -> takeVerifyFamousEmailRequestMessage(data);
            case VerifyFamousEmailReply -> takeVerifyFamousEmailReplyMessage(data);
            case ChangePasswordRequest -> takeChangePasswordRequestMessage(data);
            case ChangePasswordReply -> takeChangePasswordReplyMessage(data);
            case ChatsLoadRequest -> takeChatsLoadRequestMessage(data);
            case ChatsLoadReply -> takeChatsLoadReplyMessage(data);
            case CheckOnlineUserRequest -> takeCheckOnlineUserRequestMessage(data);
            case CheckOnlineUserReply -> takeCheckOnlineUserReplyMessage(data);
            case LoadUsersOnlineStatusRequest -> takeLoadUsersOnlineStatusRequestMessage(data);
            case LoadUsersOnlineStatusReply -> takeLoadUsersOnlineStatusReplyMessage(data);
            case TextMessageSendUserToServer -> takeTextMessageSendUserToServerMessage(data);
            case TextMessageSendUserToServerVerification -> takeTextMessageSendUserToServerVerificationMessage(data);
            case TextMessagesChangingStatusFromServer -> takeTextMessagesChangingStatusFromServerMessage(data);
            case TextMessagesChangingStatusFromServerVerification -> takeTextMessagesChangingStatusFromServerVerificationMessage(
                    data);
            case TextMessagesChangingStatusFromUser -> takeTextMessagesChangingStatusFromUserMessage(data);
            case TextMessagesChangingStatusFromUserVerification -> takeTextMessagesChangingStatusFromUserVerificationMessage(
                    data);
            case TextMessageRedirectServerToUser -> takeTextMessageRedirectServerToUserMessage(data);
            case TextMessageRedirectServerToUserVerification -> takeTextMessageRedirectServerToUserVerificationMessage(
                    data);
            case MessagesLoadRequest -> takeMessagesLoadRequestMessage(data);
            case MessagesLoadReply -> takeMessagesLoadReplyMessage(data);
        };
    }

    public DefinesMessages.TypeMessage getTypeMessage(byte[] data) {
        DefinesMessages.TypeMessage type = null;
        try {
            int numberType = ClientServerSerializeProtocolMessage_pb.General.parseFrom(data).getType();
            type = DefinesMessages.TypeMessage.getTypeMsg(numberType);
        } catch (InvalidProtocolBufferException exception) {
            log.error("Error in protobuf deserialised type.");
        }
        return type;
    }

    private HashMap<DefinesMessages.TypeData, String> takeEntryRequestMessage(byte[] data) {
        HashMap<DefinesMessages.TypeData, String> result = new HashMap<>();
        try {
            result.put(DefinesMessages.TypeData.Login,
                    ClientServerSerializeProtocolMessage_pb.General.parseFrom(data).getEntryRequest().getLogin());
            result.put(DefinesMessages.TypeData.Password,
                    ClientServerSerializeProtocolMessage_pb.General.parseFrom(data).getEntryRequest().getPassword());
        } catch (InvalidProtocolBufferException exception) {
            log.error("Error in protobuf deserialised data.");
        }
        return result;
    }

    private HashMap<DefinesMessages.TypeData, Object> takeEntryReplyMessage(byte[] data) {
        HashMap<DefinesMessages.TypeData, Object> result = new HashMap<>();
        try {
            result.put(DefinesMessages.TypeData.BoolReply,
                    ClientServerSerializeProtocolMessage_pb.General.parseFrom(data).getEntryReply().getReply());
            result.put(DefinesMessages.TypeData.UuidUser, UUID.fromString(
                    ClientServerSerializeProtocolMessage_pb.General.parseFrom(data).getEntryReply().getUuidUser()));
        } catch (InvalidProtocolBufferException exception) {
            log.error("Error in protobuf deserialised data.");
        }
        return result;
    }

    private HashMap<DefinesMessages.TypeData, String> takeRegistrationRequestMessage(byte[] data) {
        HashMap<DefinesMessages.TypeData, String> result = new HashMap<>();
        try {
            result.put(DefinesMessages.TypeData.Login, ClientServerSerializeProtocolMessage_pb.General.parseFrom(
                    data).getRegistrationRequest().getLogin());
            result.put(DefinesMessages.TypeData.Email, ClientServerSerializeProtocolMessage_pb.General.parseFrom(
                    data).getRegistrationRequest().getEmail());
            result.put(DefinesMessages.TypeData.Password, ClientServerSerializeProtocolMessage_pb.General.parseFrom(
                    data).getRegistrationRequest().getPassword());
        } catch (InvalidProtocolBufferException exception) {
            log.error("Error in protobuf deserialised data.");
        }
        return result;
    }

    private HashMap<DefinesMessages.TypeData, Object> takeRegistrationReplyMessage(byte[] data) {
        HashMap<DefinesMessages.TypeData, Object> result = new HashMap<>();
        try {
            result.put(DefinesMessages.TypeData.BoolReply,
                    ClientServerSerializeProtocolMessage_pb.General.parseFrom(data).getRegistrationReply().getReply());
            result.put(DefinesMessages.TypeData.ErrorReg,
                    DefinesMessages.TypeErrorRegistration.getTypeError(
                            ClientServerSerializeProtocolMessage_pb.General.parseFrom(
                                    data).getRegistrationReply().getError().getNumber()));
        } catch (InvalidProtocolBufferException exception) {
            log.error("Error in protobuf deserialised data.");
        }
        return result;
    }

    private HashMap<DefinesMessages.TypeData, String> takeVerifyRegistrationEmailRequestMessage(byte[] data) {
        HashMap<DefinesMessages.TypeData, String> result = new HashMap<>();
        try {
            result.put(DefinesMessages.TypeData.Login, ClientServerSerializeProtocolMessage_pb.General.parseFrom(
                    data).getVerifyRegistrationEmailRequest().getLogin());
            result.put(DefinesMessages.TypeData.Email, ClientServerSerializeProtocolMessage_pb.General.parseFrom(
                    data).getVerifyRegistrationEmailRequest().getEmail());
            result.put(DefinesMessages.TypeData.Password, ClientServerSerializeProtocolMessage_pb.General.parseFrom(
                    data).getVerifyRegistrationEmailRequest().getPassword());
            result.put(DefinesMessages.TypeData.VerifyCode, ClientServerSerializeProtocolMessage_pb.General.parseFrom(
                    data).getVerifyRegistrationEmailRequest().getCode());
        } catch (InvalidProtocolBufferException exception) {
            log.error("Error in protobuf deserialised data.");
        }
        return result;
    }

    private HashMap<DefinesMessages.TypeData, Object> takeVerifyRegistrationEmailReplyMessage(byte[] data) {
        HashMap<DefinesMessages.TypeData, Object> result = new HashMap<>();
        try {
            result.put(DefinesMessages.TypeData.BoolReply, ClientServerSerializeProtocolMessage_pb.General.parseFrom(
                    data).getVerifyRegistrationEmailReply().getReply());
            result.put(DefinesMessages.TypeData.ErrorReg,
                    DefinesMessages.TypeErrorRegistration.getTypeError(
                            ClientServerSerializeProtocolMessage_pb.General.parseFrom(
                                    data).getVerifyRegistrationEmailReply().getError().getNumber()));
        } catch (InvalidProtocolBufferException exception) {
            log.error("Error in protobuf deserialised data.");
        }
        return result;
    }

    private HashMap<DefinesMessages.TypeData, String> takeResetPasswordRequestMessage(byte[] data) {
        HashMap<DefinesMessages.TypeData, String> result = new HashMap<>();
        try {
            result.put(DefinesMessages.TypeData.Email, ClientServerSerializeProtocolMessage_pb.General.parseFrom(
                    data).getResetPasswordRequest().getEmail());
        } catch (InvalidProtocolBufferException exception) {
            log.error("Error in protobuf deserialised data.");
        }
        return result;
    }

    private HashMap<DefinesMessages.TypeData, Boolean> takeResetPasswordReplyMessage(byte[] data) {
        HashMap<DefinesMessages.TypeData, Boolean> result = new HashMap<>();
        try {
            result.put(DefinesMessages.TypeData.BoolReply,
                    ClientServerSerializeProtocolMessage_pb.General.parseFrom(data).getResetPasswordReply().getReply());
        } catch (InvalidProtocolBufferException exception) {
            log.error("Error in protobuf deserialised data.");
        }
        return result;
    }

    private HashMap<DefinesMessages.TypeData, String> takeVerifyFamousEmailRequestMessage(byte[] data) {
        HashMap<DefinesMessages.TypeData, String> result = new HashMap<>();
        try {
            result.put(DefinesMessages.TypeData.Email, ClientServerSerializeProtocolMessage_pb.General.parseFrom(
                    data).getVerifyFamousEmailRequest().getEmail());
            result.put(DefinesMessages.TypeData.VerifyCode, ClientServerSerializeProtocolMessage_pb.General.parseFrom(
                    data).getVerifyFamousEmailRequest().getCode());
        } catch (InvalidProtocolBufferException exception) {
            log.error("Error in protobuf deserialised data.");
        }
        return result;
    }

    private HashMap<DefinesMessages.TypeData, Boolean> takeVerifyFamousEmailReplyMessage(byte[] data) {
        HashMap<DefinesMessages.TypeData, Boolean> result = new HashMap<>();
        try {
            result.put(DefinesMessages.TypeData.BoolReply, ClientServerSerializeProtocolMessage_pb.General.parseFrom(
                    data).getVerifyFamousEmailReply().getReply());
        } catch (InvalidProtocolBufferException exception) {
            log.error("Error in protobuf deserialised data.");
        }
        return result;
    }

    private HashMap<DefinesMessages.TypeData, String> takeChangePasswordRequestMessage(byte[] data) {
        HashMap<DefinesMessages.TypeData, String> result = new HashMap<>();
        try {
            result.put(DefinesMessages.TypeData.Email, ClientServerSerializeProtocolMessage_pb.General.parseFrom(
                    data).getChangePasswordRequest().getEmail());
            result.put(DefinesMessages.TypeData.Password, ClientServerSerializeProtocolMessage_pb.General.parseFrom(
                    data).getChangePasswordRequest().getPassword());
        } catch (InvalidProtocolBufferException exception) {
            log.error("Error in protobuf deserialised data.");
        }
        return result;
    }

    private HashMap<DefinesMessages.TypeData, Boolean> takeChangePasswordReplyMessage(byte[] data) {
        HashMap<DefinesMessages.TypeData, Boolean> result = new HashMap<>();
        try {
            result.put(DefinesMessages.TypeData.BoolReply, ClientServerSerializeProtocolMessage_pb.General.parseFrom(
                    data).getChangePasswordReply().getReply());
        } catch (InvalidProtocolBufferException exception) {
            log.error("Error in protobuf deserialised data.");
        }
        return result;
    }

    private HashMap<DefinesMessages.TypeData, UUID> takeChatsLoadRequestMessage(byte[] data) {
        HashMap<DefinesMessages.TypeData, UUID> result = new HashMap<>();
        try {
            String uuidUserStr = ClientServerSerializeProtocolMessage_pb.General.parseFrom(
                    data).getChatsLoadRequest().getUuidUser();
            UUID uuidUser = UUID.fromString(uuidUserStr);

            result.put(DefinesMessages.TypeData.UuidUser, uuidUser);
        } catch (InvalidProtocolBufferException exception) {
            log.error("Error in protobuf deserialised data.");
        }
        return result;
    }

    private HashMap<DefinesMessages.TypeData, List<Map<DefinesMessages.TypeData, Object>>> takeChatsLoadReplyMessage(
            byte[] data) {
        HashMap<DefinesMessages.TypeData, List<Map<DefinesMessages.TypeData, Object>>> result = new HashMap<>();
        try {
            ClientServerSerializeProtocolMessage_pb.ChatsLoadReply chatsLoadReplyMsg = ClientServerSerializeProtocolMessage_pb.General.parseFrom(
                    data).getChatsLoadReply();

            List<Map<DefinesMessages.TypeData, Object>> listMainData = new ArrayList<>();
            for (int i = 0; i < chatsLoadReplyMsg.getChatsInfoCount(); i++) {
                String login = chatsLoadReplyMsg.getChatsInfo(i).getLogin();
                UUID uuidUser = UUID.fromString(chatsLoadReplyMsg.getChatsInfo(i).getUuidUser());
                String lastMessageText = chatsLoadReplyMsg.getChatsInfo(i).getLastMessageText();
                UUID uuidChat = UUID.fromString(chatsLoadReplyMsg.getChatsInfo(i).getUuidChat());
                UUID uuidMessage = UUID.fromString(chatsLoadReplyMsg.getChatsInfo(i).getUuidMessage());
                Boolean isLoginSentLastMessage = chatsLoadReplyMsg.getChatsInfo(i).getIsLoginSentLastMessage();
                MainChatsGlobalDefines.TypeStatusMessage statusMessage = MainChatsGlobalDefines.TypeStatusMessage.getTypeStatusMessage(
                        chatsLoadReplyMsg.getChatsInfo(i).getStatusMessage().getNumber());
                String dateTimeLastMessage = chatsLoadReplyMsg.getChatsInfo(i).getDateTimeLastMessage();

                Map<DefinesMessages.TypeData, Object> newMap = new HashMap<>();

                newMap.put(DefinesMessages.TypeData.Login, login);
                newMap.put(DefinesMessages.TypeData.UuidUser, uuidUser);
                newMap.put(DefinesMessages.TypeData.TextMessage, lastMessageText);
                newMap.put(DefinesMessages.TypeData.UuidChat, uuidChat);
                newMap.put(DefinesMessages.TypeData.UuidMessage, uuidMessage);
                newMap.put(DefinesMessages.TypeData.IsLoginSentLastMessage, isLoginSentLastMessage);
                newMap.put(DefinesMessages.TypeData.StatusMessage, statusMessage);
                newMap.put(DefinesMessages.TypeData.Timestamp, dateTimeLastMessage);

                listMainData.add(newMap);
            }

            result.put(DefinesMessages.TypeData.ChatsInfoList, listMainData);
        } catch (InvalidProtocolBufferException exception) {
            log.error("Error in protobuf deserialised data.");
        }
        return result;
    }

    private HashMap<DefinesMessages.TypeData, String> takeCheckOnlineUserRequestMessage(byte[] data) {
        HashMap<DefinesMessages.TypeData, String> result = new HashMap<>();
        try {
            result.put(DefinesMessages.TypeData.IP,
                    ClientServerSerializeProtocolMessage_pb.General.parseFrom(data).getCheckOnlineRequest().getIp());
        } catch (InvalidProtocolBufferException exception) {
            log.error("Error in protobuf deserialised data.");
        }
        return result;
    }

    private HashMap<DefinesMessages.TypeData, UUID> takeCheckOnlineUserReplyMessage(byte[] data) {
        HashMap<DefinesMessages.TypeData, UUID> result = new HashMap<>();
        try {
            result.put(DefinesMessages.TypeData.UuidUser,
                    UUID.fromString(ClientServerSerializeProtocolMessage_pb.General.parseFrom(
                            data).getCheckOnlineReply().getUuidUser()));
        } catch (InvalidProtocolBufferException exception) {
            log.error("Error in protobuf deserialised data.");
        }
        return result;
    }

    private HashMap<DefinesMessages.TypeData, List<UUID>> takeLoadUsersOnlineStatusRequestMessage(byte[] data) {
        HashMap<DefinesMessages.TypeData, List<UUID>> result = new HashMap<>();
        try {
            ClientServerSerializeProtocolMessage_pb.LoadUsersOnlineStatusRequest loadUsersOnlineStatusRequestMsg = ClientServerSerializeProtocolMessage_pb.General.parseFrom(
                    data).getLoadUsersOnlineStatusRequest();

            List<UUID> listMainData = new ArrayList<>();
            for (int i = 0; i < loadUsersOnlineStatusRequestMsg.getUuidsUsersCount(); i++) {
                UUID uuidUser = UUID.fromString(loadUsersOnlineStatusRequestMsg.getUuidsUsers(i));
                listMainData.add(uuidUser);
            }

            result.put(DefinesMessages.TypeData.UuidsUsersList, listMainData);
        } catch (InvalidProtocolBufferException exception) {
            log.error("Error in protobuf deserialised data.");
        }
        return result;
    }

    private HashMap<DefinesMessages.TypeData, Map<UUID, ?>> takeLoadUsersOnlineStatusReplyMessage(byte[] data) {
        HashMap<DefinesMessages.TypeData, Map<UUID, ?>> result = new HashMap<>();
        try {
            ClientServerSerializeProtocolMessage_pb.LoadUsersOnlineStatusReply loadUsersOnlineStatusReply = ClientServerSerializeProtocolMessage_pb.General.parseFrom(
                    data).getLoadUsersOnlineStatusReply();

            Map<String, ClientServerSerializeProtocolMessage_pb.LoadUsersOnlineStatusReply.StatusOnline> mapStatusesUsers = loadUsersOnlineStatusReply.getMapStatusOnlineMap();
            Map<String, String> mapLastOnlineTimeUsers = loadUsersOnlineStatusReply.getMapLastOnlineTimeMap();

            Map<UUID, MainChatsGlobalDefines.TypeStatusOnline> newMapStatusesUsers = new HashMap<>();
            for (String key : mapStatusesUsers.keySet()) {
                int integerStatus = mapStatusesUsers.get(key).getNumber();
                MainChatsGlobalDefines.TypeStatusOnline statusMsg = MainChatsGlobalDefines.TypeStatusOnline.getTypeStatusOnline(
                        integerStatus);

                newMapStatusesUsers.put(UUID.fromString(key), statusMsg);
            }

            Map<UUID, String> newMapLastOnlineTimes = new HashMap<>();
            for (String key : mapLastOnlineTimeUsers.keySet()) {
                newMapLastOnlineTimes.put(UUID.fromString(key), mapLastOnlineTimeUsers.get(key));
            }

            result.put(DefinesMessages.TypeData.UsersOnlineInfoList, newMapStatusesUsers);
            result.put(DefinesMessages.TypeData.Timestamp, newMapLastOnlineTimes);
        } catch (InvalidProtocolBufferException exception) {
            log.error("Error in protobuf deserialised data.");
        }
        return result;
    }

    private HashMap<DefinesMessages.TypeData, Object> takeTextMessageSendUserToServerMessage(byte[] data) {
        HashMap<DefinesMessages.TypeData, Object> result = new HashMap<>();
        try {
            ClientServerSerializeProtocolMessage_pb.TextMessageSendUserToServer msgData = ClientServerSerializeProtocolMessage_pb.General.parseFrom(
                    data).getTextMessageSendUserToServer();
            ClientServerSerializeProtocolMessage_pb.TextMessageInfo messageInfo = msgData.getTextMessageInfo();

            result.put(DefinesMessages.TypeData.UuidUserSender, UUID.fromString(messageInfo.getUuidUserSender()));
            result.put(DefinesMessages.TypeData.UuidUserReceiver, UUID.fromString(messageInfo.getUuidUserReceiver()));
            result.put(DefinesMessages.TypeData.UuidMessage, UUID.fromString(messageInfo.getUuidMessage()));
            result.put(DefinesMessages.TypeData.TextMessage, messageInfo.getText());
            result.put(DefinesMessages.TypeData.Timestamp, messageInfo.getTimestamp());
        } catch (InvalidProtocolBufferException exception) {
            log.error("Error in protobuf deserialised data.");
        }
        return result;
    }

    private HashMap<DefinesMessages.TypeData, Object> takeTextMessageSendUserToServerVerificationMessage(byte[] data) {
        HashMap<DefinesMessages.TypeData, Object> result = new HashMap<>();
        try {
            result.put(DefinesMessages.TypeData.BoolReply, ClientServerSerializeProtocolMessage_pb.General.parseFrom(
                    data).getTextMessageSendUserToServerVerification().getReply());
        } catch (InvalidProtocolBufferException exception) {
            log.error("Error in protobuf deserialised data.");
        }
        return result;
    }

    private HashMap<DefinesMessages.TypeData, Object> takeTextMessagesChangingStatusFromServerMessage(byte[] data) {
        HashMap<DefinesMessages.TypeData, Object> result = new HashMap<>();
        try {
            ClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromServer msgData = ClientServerSerializeProtocolMessage_pb.General.parseFrom(
                    data).getTextMessagesChangingStatusFromServer();

            Map<String, ClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromServer.StatusMessage> mapStatusesMessages = msgData.getMapStatusMessagesMap();
            Map<UUID, MainChatsGlobalDefines.TypeStatusMessage> newMapStatusesMessages = new HashMap<>();

            for (String key : mapStatusesMessages.keySet()) {
                ClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromServer.StatusMessage statusTmp = mapStatusesMessages.get(
                        key);
                MainChatsGlobalDefines.TypeStatusMessage statusMsg = MainChatsGlobalDefines.TypeStatusMessage.getTypeStatusMessage(
                        statusTmp.getNumber());
                newMapStatusesMessages.put(UUID.fromString(key), statusMsg);
            }

            result.put(DefinesMessages.TypeData.StatusMessagesMap, newMapStatusesMessages);
        } catch (InvalidProtocolBufferException exception) {
            log.error("Error in protobuf deserialised data.");
        }
        return result;
    }

    private HashMap<DefinesMessages.TypeData, Object> takeTextMessagesChangingStatusFromServerVerificationMessage(
            byte[] data) {
        HashMap<DefinesMessages.TypeData, Object> result = new HashMap<>();
        try {
            result.put(DefinesMessages.TypeData.BoolReply, ClientServerSerializeProtocolMessage_pb.General.parseFrom(
                    data).getTextMessagesChangingStatusFromServerVerification().getReply());
        } catch (InvalidProtocolBufferException exception) {
            log.error("Error in protobuf deserialised data.");
        }
        return result;
    }

    private HashMap<DefinesMessages.TypeData, Object> takeTextMessagesChangingStatusFromUserMessage(byte[] data) {
        HashMap<DefinesMessages.TypeData, Object> result = new HashMap<>();
        try {
            ClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromUser msgData = ClientServerSerializeProtocolMessage_pb.General.parseFrom(
                    data).getTextMessagesChangingStatusFromUser();

            Map<String, ClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromUser.StatusMessage> mapStatusesMessages = msgData.getMapStatusMessagesMap();
            Map<UUID, MainChatsGlobalDefines.TypeStatusMessage> newMapStatusesMessages = new HashMap<>();

            for (String key : mapStatusesMessages.keySet()) {
                ClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromUser.StatusMessage statusTmp = mapStatusesMessages.get(
                        key);
                MainChatsGlobalDefines.TypeStatusMessage statusMsg = MainChatsGlobalDefines.TypeStatusMessage.getTypeStatusMessage(
                        statusTmp.getNumber());
                newMapStatusesMessages.put(UUID.fromString(key), statusMsg);
            }

            result.put(DefinesMessages.TypeData.StatusMessagesMap, newMapStatusesMessages);
        } catch (InvalidProtocolBufferException exception) {
            log.error("Error in protobuf deserialised data.");
        }
        return result;
    }

    private HashMap<DefinesMessages.TypeData, Object> takeTextMessagesChangingStatusFromUserVerificationMessage(
            byte[] data) {
        HashMap<DefinesMessages.TypeData, Object> result = new HashMap<>();
        try {
            result.put(DefinesMessages.TypeData.BoolReply, ClientServerSerializeProtocolMessage_pb.General.parseFrom(
                    data).getTextMessagesChangingStatusFromUserVerification().getReply());
        } catch (InvalidProtocolBufferException exception) {
            log.error("Error in protobuf deserialised data.");
        }
        return result;
    }

    private HashMap<DefinesMessages.TypeData, Object> takeTextMessageRedirectServerToUserMessage(byte[] data) {
        HashMap<DefinesMessages.TypeData, Object> result = new HashMap<>();
        try {
            ClientServerSerializeProtocolMessage_pb.TextMessageSendUserToServer msgData = ClientServerSerializeProtocolMessage_pb.General.parseFrom(
                    data).getTextMessageSendUserToServer();
            ClientServerSerializeProtocolMessage_pb.TextMessageInfo messageInfo = msgData.getTextMessageInfo();

            result.put(DefinesMessages.TypeData.UuidUserSender, UUID.fromString(messageInfo.getUuidUserSender()));
            result.put(DefinesMessages.TypeData.UuidUserReceiver, UUID.fromString(messageInfo.getUuidUserReceiver()));
            result.put(DefinesMessages.TypeData.UuidMessage, UUID.fromString(messageInfo.getUuidMessage()));
            result.put(DefinesMessages.TypeData.TextMessage, messageInfo.getText());
            result.put(DefinesMessages.TypeData.Timestamp, messageInfo.getTimestamp());
        } catch (InvalidProtocolBufferException exception) {
            log.error("Error in protobuf deserialised data.");
        }
        return result;
    }

    private HashMap<DefinesMessages.TypeData, Object> takeTextMessageRedirectServerToUserVerificationMessage(
            byte[] data) {
        HashMap<DefinesMessages.TypeData, Object> result = new HashMap<>();
        try {
            result.put(DefinesMessages.TypeData.BoolReply, ClientServerSerializeProtocolMessage_pb.General.parseFrom(
                    data).getTextMessageRedirectServerToUserVerification().getReply());
        } catch (InvalidProtocolBufferException exception) {
            log.error("Error in protobuf deserialised data.");
        }
        return result;
    }

    private HashMap<DefinesMessages.TypeData, Object> takeMessagesLoadRequestMessage(byte[] data) {
        HashMap<DefinesMessages.TypeData, Object> result = new HashMap<>();
        try {
            ClientServerSerializeProtocolMessage_pb.MessagesLoadRequest msgData = ClientServerSerializeProtocolMessage_pb.General.parseFrom(
                    data).getMessagesLoadRequest();

            result.put(DefinesMessages.TypeData.UuidChat, UUID.fromString(msgData.getUuidChat()));
            result.put(DefinesMessages.TypeData.QuantityMessages, msgData.getQuantityMessages());
        } catch (InvalidProtocolBufferException exception) {
            log.error("Error in protobuf deserialised data.");
        }
        return result;
    }

    private HashMap<DefinesMessages.TypeData, List<Map<DefinesMessages.TypeData, Object>>> takeMessagesLoadReplyMessage(
            byte[] data) {
        HashMap<DefinesMessages.TypeData, List<Map<DefinesMessages.TypeData, Object>>> result = new HashMap<>();
        try {
            ClientServerSerializeProtocolMessage_pb.MessagesLoadReply msgLoadReplyMsg = ClientServerSerializeProtocolMessage_pb.General.parseFrom(
                    data).getMessagesLoadReply();

            List<Map<DefinesMessages.TypeData, Object>> listMainData = new ArrayList<>();
            for (int i = 0; i < msgLoadReplyMsg.getTextMessageInfoCount(); i++) {
                UUID uuidUserSender = UUID.fromString(msgLoadReplyMsg.getTextMessageInfo(i).getUuidUserSender());
                UUID uuidUserReceiver = UUID.fromString(msgLoadReplyMsg.getTextMessageInfo(i).getUuidUserReceiver());
                UUID uuidMessage = UUID.fromString(msgLoadReplyMsg.getTextMessageInfo(i).getUuidMessage());
                int statusMessage = msgLoadReplyMsg.getTextMessageInfo(i).getStatusMessage();
                String text = msgLoadReplyMsg.getTextMessageInfo(i).getText();
                String timestamp = msgLoadReplyMsg.getTextMessageInfo(i).getTimestamp();

                Map<DefinesMessages.TypeData, Object> newMap = new HashMap<>();

                newMap.put(DefinesMessages.TypeData.UuidUserSender, uuidUserSender);
                newMap.put(DefinesMessages.TypeData.UuidUserReceiver, uuidUserReceiver);
                newMap.put(DefinesMessages.TypeData.UuidMessage, uuidMessage);
                newMap.put(DefinesMessages.TypeData.StatusMessage, statusMessage);
                newMap.put(DefinesMessages.TypeData.TextMessage, text);
                newMap.put(DefinesMessages.TypeData.Timestamp, timestamp);

                listMainData.add(newMap);
            }

            result.put(DefinesMessages.TypeData.MessagesInfoList, listMainData);
        } catch (InvalidProtocolBufferException exception) {
            log.error("Error in protobuf deserialised data.");
        }
        return result;
    }
}
