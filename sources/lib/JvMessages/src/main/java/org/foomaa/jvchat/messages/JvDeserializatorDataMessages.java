package org.foomaa.jvchat.messages;

import java.util.*;

import com.google.protobuf.InvalidProtocolBufferException;
import org.foomaa.jvchat.globaldefines.JvMainChatsGlobalDefines;
import org.foomaa.jvchat.logger.JvLog;


public class JvDeserializatorDataMessages {
    JvDeserializatorDataMessages() {}

    public HashMap<JvDefinesMessages.TypeData, ?> deserializeData(JvDefinesMessages.TypeMessage type, byte[] data) {
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
            case TextMessagesChangingStatusFromServerVerification -> takeTextMessagesChangingStatusFromServerVerificationMessage(data);
            case TextMessagesChangingStatusFromUser -> takeTextMessagesChangingStatusFromUserMessage(data);
            case TextMessagesChangingStatusFromUserVerification -> takeTextMessagesChangingStatusFromUserVerificationMessage(data);
            case TextMessageRedirectServerToUser -> takeTextMessageRedirectServerToUserMessage(data);
            case TextMessageRedirectServerToUserVerification -> takeTextMessageRedirectServerToUserVerificationMessage(data);
            case MessagesLoadRequest -> takeMessagesLoadRequestMessage(data);
            case MessagesLoadReply -> takeMessagesLoadReplyMessage(data);
        };
    }

    public JvDefinesMessages.TypeMessage getTypeMessage(byte[] data) {
        JvDefinesMessages.TypeMessage type = null;
        try {
            int numberType = JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data).getType();
            type = JvDefinesMessages.TypeMessage.getTypeMsg(numberType);
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised type");
        }
        return type;
    }

    private HashMap<JvDefinesMessages.TypeData, String> takeEntryRequestMessage(byte[] data) {
        HashMap<JvDefinesMessages.TypeData, String> result = new HashMap<>();
        try {
            result.put(JvDefinesMessages.TypeData.Login, JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data)
                    .getEntryRequest().getLogin());
            result.put(JvDefinesMessages.TypeData.Password, JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data)
                    .getEntryRequest().getPassword());
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvDefinesMessages.TypeData, Object> takeEntryReplyMessage(byte[] data) {
        HashMap<JvDefinesMessages.TypeData, Object> result = new HashMap<>();
        try {
            result.put(JvDefinesMessages.TypeData.BoolReply, JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data)
                    .getEntryReply().getReply());
            result.put(JvDefinesMessages.TypeData.UuidUser, UUID.fromString(JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data)
                    .getEntryReply().getUuidUser()));
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvDefinesMessages.TypeData, String> takeRegistrationRequestMessage(byte[] data) {
        HashMap<JvDefinesMessages.TypeData, String> result = new HashMap<>();
        try {
            result.put(JvDefinesMessages.TypeData.Login, JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data).
                    getRegistrationRequest().getLogin());
            result.put(JvDefinesMessages.TypeData.Email, JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data).
                    getRegistrationRequest().getEmail());
            result.put(JvDefinesMessages.TypeData.Password, JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data).
                    getRegistrationRequest().getPassword());
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvDefinesMessages.TypeData, Object> takeRegistrationReplyMessage(byte[] data) {
        HashMap<JvDefinesMessages.TypeData, Object> result = new HashMap<>();
        try {
            result.put(JvDefinesMessages.TypeData.BoolReply, JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data)
                    .getRegistrationReply().getReply());
            result.put(JvDefinesMessages.TypeData.ErrorReg, JvDefinesMessages.TypeErrorRegistration.getTypeError(
                    JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data).
                            getRegistrationReply().getError().getNumber()));
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvDefinesMessages.TypeData, String> takeVerifyRegistrationEmailRequestMessage(byte[] data) {
        HashMap<JvDefinesMessages.TypeData, String> result = new HashMap<>();
        try {
            result.put(JvDefinesMessages.TypeData.Login, JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data).
                    getVerifyRegistrationEmailRequest().getLogin());
            result.put(JvDefinesMessages.TypeData.Email, JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data).
                    getVerifyRegistrationEmailRequest().getEmail());
            result.put(JvDefinesMessages.TypeData.Password, JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data).
                    getVerifyRegistrationEmailRequest().getPassword());
            result.put(JvDefinesMessages.TypeData.VerifyCode, JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data).
                    getVerifyRegistrationEmailRequest().getCode());
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvDefinesMessages.TypeData, Object> takeVerifyRegistrationEmailReplyMessage(byte[] data) {
        HashMap<JvDefinesMessages.TypeData, Object> result = new HashMap<>();
        try {
            result.put(JvDefinesMessages.TypeData.BoolReply, JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data)
                    .getVerifyRegistrationEmailReply().getReply());
            result.put(JvDefinesMessages.TypeData.ErrorReg, JvDefinesMessages.TypeErrorRegistration.getTypeError(
                    JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data).
                            getVerifyRegistrationEmailReply().getError().getNumber()));
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvDefinesMessages.TypeData, String> takeResetPasswordRequestMessage(byte[] data) {
        HashMap<JvDefinesMessages.TypeData, String> result = new HashMap<>();
        try {
            result.put(JvDefinesMessages.TypeData.Email, JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data).
                    getResetPasswordRequest().getEmail());
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvDefinesMessages.TypeData, Boolean> takeResetPasswordReplyMessage(byte[] data) {
        HashMap<JvDefinesMessages.TypeData, Boolean> result = new HashMap<>();
        try {
            result.put(JvDefinesMessages.TypeData.BoolReply, JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data)
                    .getResetPasswordReply().getReply());
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvDefinesMessages.TypeData, String> takeVerifyFamousEmailRequestMessage(byte[] data) {
        HashMap<JvDefinesMessages.TypeData, String> result = new HashMap<>();
        try {
            result.put(JvDefinesMessages.TypeData.Email, JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data).
                    getVerifyFamousEmailRequest().getEmail());
            result.put(JvDefinesMessages.TypeData.VerifyCode, JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data).
                    getVerifyFamousEmailRequest().getCode());
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvDefinesMessages.TypeData, Boolean> takeVerifyFamousEmailReplyMessage(byte[] data) {
        HashMap<JvDefinesMessages.TypeData, Boolean> result = new HashMap<>();
        try {
            result.put(JvDefinesMessages.TypeData.BoolReply, JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data)
                    .getVerifyFamousEmailReply().getReply());
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvDefinesMessages.TypeData, String> takeChangePasswordRequestMessage(byte[] data) {
        HashMap<JvDefinesMessages.TypeData, String> result = new HashMap<>();
        try {
            result.put(JvDefinesMessages.TypeData.Email, JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data).
                    getChangePasswordRequest().getEmail());
            result.put(JvDefinesMessages.TypeData.Password, JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data).
                    getChangePasswordRequest().getPassword());
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvDefinesMessages.TypeData, Boolean> takeChangePasswordReplyMessage(byte[] data) {
        HashMap<JvDefinesMessages.TypeData, Boolean> result = new HashMap<>();
        try {
            result.put(JvDefinesMessages.TypeData.BoolReply, JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data)
                    .getChangePasswordReply().getReply());
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvDefinesMessages.TypeData, UUID> takeChatsLoadRequestMessage(byte[] data) {
        HashMap<JvDefinesMessages.TypeData, UUID> result = new HashMap<>();
        try {
            String uuidUserStr = JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data).
                    getChatsLoadRequest().getUuidUser();
            UUID uuidUser = UUID.fromString(uuidUserStr);

            result.put(JvDefinesMessages.TypeData.UuidUser, uuidUser);
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvDefinesMessages.TypeData, List<Map<JvDefinesMessages.TypeData, Object>>> takeChatsLoadReplyMessage(byte[] data) {
        HashMap<JvDefinesMessages.TypeData, List<Map<JvDefinesMessages.TypeData, Object>>> result = new HashMap<>();
        try {
            JvClientServerSerializeProtocolMessage_pb.ChatsLoadReply chatsLoadReplyMsg =
                    JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data).getChatsLoadReply();

            List<Map<JvDefinesMessages.TypeData, Object>> listMainData = new ArrayList<>();
            for (int i = 0; i < chatsLoadReplyMsg.getChatsInfoCount(); i++) {
                String login = chatsLoadReplyMsg.getChatsInfo(i).getLogin();
                UUID uuidUser = UUID.fromString(chatsLoadReplyMsg.getChatsInfo(i).getUuidUser());
                String lastMessageText = chatsLoadReplyMsg.getChatsInfo(i).getLastMessageText();
                UUID uuidChat = UUID.fromString(chatsLoadReplyMsg.getChatsInfo(i).getUuidChat());
                UUID uuidMessage = UUID.fromString(chatsLoadReplyMsg.getChatsInfo(i).getUuidMessage());
                Boolean isLoginSentLastMessage = chatsLoadReplyMsg.getChatsInfo(i).getIsLoginSentLastMessage();
                JvMainChatsGlobalDefines.TypeStatusMessage statusMessage =
                        JvMainChatsGlobalDefines.TypeStatusMessage.getTypeStatusMessage(
                                chatsLoadReplyMsg.getChatsInfo(i).getStatusMessage().getNumber());
                String dateTimeLastMessage = chatsLoadReplyMsg.getChatsInfo(i).getDateTimeLastMessage();

                Map<JvDefinesMessages.TypeData, Object> newMap = new HashMap<>();

                newMap.put(JvDefinesMessages.TypeData.Login, login);
                newMap.put(JvDefinesMessages.TypeData.UuidUser, uuidUser);
                newMap.put(JvDefinesMessages.TypeData.TextMessage, lastMessageText);
                newMap.put(JvDefinesMessages.TypeData.UuidChat, uuidChat);
                newMap.put(JvDefinesMessages.TypeData.UuidMessage, uuidMessage);
                newMap.put(JvDefinesMessages.TypeData.IsLoginSentLastMessage, isLoginSentLastMessage);
                newMap.put(JvDefinesMessages.TypeData.StatusMessage, statusMessage);
                newMap.put(JvDefinesMessages.TypeData.Timestamp, dateTimeLastMessage);

                listMainData.add(newMap);
            }

            result.put(JvDefinesMessages.TypeData.ChatsInfoList, listMainData);
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvDefinesMessages.TypeData, String> takeCheckOnlineUserRequestMessage(byte[] data) {
        HashMap<JvDefinesMessages.TypeData, String> result = new HashMap<>();
        try {
            result.put(JvDefinesMessages.TypeData.IP, JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data).
                    getCheckOnlineRequest().getIp());
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvDefinesMessages.TypeData, UUID> takeCheckOnlineUserReplyMessage(byte[] data) {
        HashMap<JvDefinesMessages.TypeData, UUID> result = new HashMap<>();
        try {
            result.put(JvDefinesMessages.TypeData.UuidUser, UUID.fromString(JvClientServerSerializeProtocolMessage_pb.General
                    .parseFrom(data).getCheckOnlineReply().getUuidUser()));
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvDefinesMessages.TypeData, List<UUID>> takeLoadUsersOnlineStatusRequestMessage(byte[] data) {
        HashMap<JvDefinesMessages.TypeData, List<UUID>> result = new HashMap<>();
        try {
            JvClientServerSerializeProtocolMessage_pb.LoadUsersOnlineStatusRequest loadUsersOnlineStatusRequestMsg =
                    JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data).getLoadUsersOnlineStatusRequest();

            List<UUID> listMainData = new ArrayList<>();
            for (int i = 0; i < loadUsersOnlineStatusRequestMsg.getUuidsUsersCount(); i++) {
                UUID uuidUser = UUID.fromString(loadUsersOnlineStatusRequestMsg.getUuidsUsers(i));
                listMainData.add(uuidUser);
            }

            result.put(JvDefinesMessages.TypeData.UuidsUsersList, listMainData);
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvDefinesMessages.TypeData, Map<UUID, ?>> takeLoadUsersOnlineStatusReplyMessage(byte[] data) {
        HashMap<JvDefinesMessages.TypeData, Map<UUID, ?>> result = new HashMap<>();
        try {
            JvClientServerSerializeProtocolMessage_pb.LoadUsersOnlineStatusReply loadUsersOnlineStatusReply =
                    JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data).getLoadUsersOnlineStatusReply();

            Map<String, JvClientServerSerializeProtocolMessage_pb.LoadUsersOnlineStatusReply.StatusOnline> mapStatusesUsers =
                    loadUsersOnlineStatusReply.getMapStatusOnlineMap();
            Map<String, String> mapLastOnlineTimeUsers = loadUsersOnlineStatusReply.getMapLastOnlineTimeMap();

            Map<UUID, JvMainChatsGlobalDefines.TypeStatusOnline> newMapStatusesUsers = new HashMap<>();
            for (String key : mapStatusesUsers.keySet()) {
                int integerStatus = mapStatusesUsers.get(key).getNumber();
                JvMainChatsGlobalDefines.TypeStatusOnline statusMsg =
                        JvMainChatsGlobalDefines.TypeStatusOnline.getTypeStatusOnline(integerStatus);

                newMapStatusesUsers.put(UUID.fromString(key), statusMsg);
            }

            Map<UUID, String> newMapLastOnlineTimes = new HashMap<>();
            for (String key : mapLastOnlineTimeUsers.keySet()) {
                newMapLastOnlineTimes.put(UUID.fromString(key), mapLastOnlineTimeUsers.get(key));
            }

            result.put(JvDefinesMessages.TypeData.UsersOnlineInfoList, newMapStatusesUsers);
            result.put(JvDefinesMessages.TypeData.Timestamp, newMapLastOnlineTimes);
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvDefinesMessages.TypeData, Object> takeTextMessageSendUserToServerMessage(byte[] data) {
        HashMap<JvDefinesMessages.TypeData, Object> result = new HashMap<>();
        try {
            JvClientServerSerializeProtocolMessage_pb.TextMessageSendUserToServer msgData =
                    JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data)
                            .getTextMessageSendUserToServer();
            JvClientServerSerializeProtocolMessage_pb.TextMessageInfo messageInfo = msgData.getTextMessageInfo();

            result.put(JvDefinesMessages.TypeData.UuidUserSender, UUID.fromString(messageInfo.getUuidUserSender()));
            result.put(JvDefinesMessages.TypeData.UuidUserReceiver, UUID.fromString(messageInfo.getUuidUserReceiver()));
            result.put(JvDefinesMessages.TypeData.UuidMessage, UUID.fromString(messageInfo.getUuidMessage()));
            result.put(JvDefinesMessages.TypeData.TextMessage, messageInfo.getText());
            result.put(JvDefinesMessages.TypeData.Timestamp, messageInfo.getTimestamp());
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvDefinesMessages.TypeData, Object> takeTextMessageSendUserToServerVerificationMessage(byte[] data) {
        HashMap<JvDefinesMessages.TypeData, Object> result = new HashMap<>();
        try {
            result.put(JvDefinesMessages.TypeData.BoolReply, JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data)
                    .getTextMessageSendUserToServerVerification().getReply());
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvDefinesMessages.TypeData, Object> takeTextMessagesChangingStatusFromServerMessage(byte[] data) {
        HashMap<JvDefinesMessages.TypeData, Object> result = new HashMap<>();
        try {
            JvClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromServer msgData =
                    JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data)
                            .getTextMessagesChangingStatusFromServer();

            Map<String, JvClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromServer.StatusMessage> mapStatusesMessages =
                    msgData.getMapStatusMessagesMap();
            Map<UUID, JvMainChatsGlobalDefines.TypeStatusMessage> newMapStatusesMessages = new HashMap<>();

            for (String key : mapStatusesMessages.keySet()) {
                JvClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromServer.StatusMessage statusTmp =
                        mapStatusesMessages.get(key);
                JvMainChatsGlobalDefines.TypeStatusMessage statusMsg =
                        JvMainChatsGlobalDefines.TypeStatusMessage.getTypeStatusMessage(statusTmp.getNumber());
                newMapStatusesMessages.put(UUID.fromString(key), statusMsg);
            }

            result.put(JvDefinesMessages.TypeData.MapStatusMessages, newMapStatusesMessages);
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvDefinesMessages.TypeData, Object> takeTextMessagesChangingStatusFromServerVerificationMessage(byte[] data) {
        HashMap<JvDefinesMessages.TypeData, Object> result = new HashMap<>();
        try {
            result.put(JvDefinesMessages.TypeData.BoolReply, JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data)
                    .getTextMessagesChangingStatusFromServerVerification().getReply());
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvDefinesMessages.TypeData, Object> takeTextMessagesChangingStatusFromUserMessage(byte[] data) {
        HashMap<JvDefinesMessages.TypeData, Object> result = new HashMap<>();
        try {
            JvClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromUser msgData =
                    JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data)
                            .getTextMessagesChangingStatusFromUser();

            Map<String, JvClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromUser.StatusMessage> mapStatusesMessages =
                    msgData.getMapStatusMessagesMap();
            Map<UUID, JvMainChatsGlobalDefines.TypeStatusMessage> newMapStatusesMessages = new HashMap<>();

            for (String key : mapStatusesMessages.keySet()) {
                JvClientServerSerializeProtocolMessage_pb.TextMessagesChangingStatusFromUser.StatusMessage statusTmp =
                        mapStatusesMessages.get(key);
                JvMainChatsGlobalDefines.TypeStatusMessage statusMsg =
                        JvMainChatsGlobalDefines.TypeStatusMessage.getTypeStatusMessage(statusTmp.getNumber());
                newMapStatusesMessages.put(UUID.fromString(key), statusMsg);
            }

            result.put(JvDefinesMessages.TypeData.MapStatusMessages, newMapStatusesMessages);
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvDefinesMessages.TypeData, Object> takeTextMessagesChangingStatusFromUserVerificationMessage(byte[] data) {
        HashMap<JvDefinesMessages.TypeData, Object> result = new HashMap<>();
        try {
            result.put(JvDefinesMessages.TypeData.BoolReply, JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data)
                    .getTextMessagesChangingStatusFromUserVerification().getReply());
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvDefinesMessages.TypeData, String> takeTextMessageRedirectServerToUserMessage(byte[] data) {
        HashMap<JvDefinesMessages.TypeData, String> result = new HashMap<>();
        try {
            JvClientServerSerializeProtocolMessage_pb.TextMessageSendUserToServer msgData =
                    JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data)
                            .getTextMessageSendUserToServer();
            JvClientServerSerializeProtocolMessage_pb.TextMessageInfo messageInfo = msgData.getTextMessageInfo();

            result.put(JvDefinesMessages.TypeData.UuidUserSender, messageInfo.getUuidUserSender());
            result.put(JvDefinesMessages.TypeData.UuidUserReceiver, messageInfo.getUuidUserReceiver());
            result.put(JvDefinesMessages.TypeData.UuidMessage, messageInfo.getUuidMessage());
            result.put(JvDefinesMessages.TypeData.TextMessage, messageInfo.getText());
            result.put(JvDefinesMessages.TypeData.Timestamp, messageInfo.getTimestamp());
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvDefinesMessages.TypeData, Object> takeTextMessageRedirectServerToUserVerificationMessage(byte[] data) {
        HashMap<JvDefinesMessages.TypeData, Object> result = new HashMap<>();
        try {
            result.put(JvDefinesMessages.TypeData.BoolReply, JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data)
                    .getTextMessageRedirectServerToUserVerification().getReply());
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvDefinesMessages.TypeData, Object> takeMessagesLoadRequestMessage(byte[] data) {
        HashMap<JvDefinesMessages.TypeData, Object> result = new HashMap<>();
        try {
            JvClientServerSerializeProtocolMessage_pb.MessagesLoadRequest msgData =
                    JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data)
                            .getMessagesLoadRequest();

            result.put(JvDefinesMessages.TypeData.UuidChat, UUID.fromString(msgData.getUuidChat()));
            result.put(JvDefinesMessages.TypeData.QuantityMessages, msgData.getQuantityMessages());
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvDefinesMessages.TypeData, List<Map<JvDefinesMessages.TypeData, Object>>> takeMessagesLoadReplyMessage(byte[] data) {
        HashMap<JvDefinesMessages.TypeData, List<Map<JvDefinesMessages.TypeData, Object>>> result = new HashMap<>();
        try {
            JvClientServerSerializeProtocolMessage_pb.MessagesLoadReply msgLoadReplyMsg =
                    JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data).getMessagesLoadReply();

            List<Map<JvDefinesMessages.TypeData, Object>> listMainData = new ArrayList<>();
            for (int i = 0; i < msgLoadReplyMsg.getMessagesInfoCount(); i++) {
                UUID uuidUserSender = UUID.fromString(msgLoadReplyMsg.getMessagesInfo(i).getUuidUserSender());
                UUID uuidUserReceiver = UUID.fromString(msgLoadReplyMsg.getMessagesInfo(i).getUuidUserReceiver());
                UUID uuidMessage = UUID.fromString(msgLoadReplyMsg.getMessagesInfo(i).getUuidMessage());
                int statusMessage = msgLoadReplyMsg.getMessagesInfo(i).getStatusMessage();
                String text = msgLoadReplyMsg.getMessagesInfo(i).getText();
                String timestamp = msgLoadReplyMsg.getMessagesInfo(i).getTimestamp();

                Map<JvDefinesMessages.TypeData, Object> newMap = new HashMap<>();

                newMap.put(JvDefinesMessages.TypeData.UuidUserSender, uuidUserSender);
                newMap.put(JvDefinesMessages.TypeData.UuidUserReceiver, uuidUserReceiver);
                newMap.put(JvDefinesMessages.TypeData.UuidMessage, uuidMessage);
                newMap.put(JvDefinesMessages.TypeData.StatusMessage, statusMessage);
                newMap.put(JvDefinesMessages.TypeData.TextMessage, text);
                newMap.put(JvDefinesMessages.TypeData.Timestamp, timestamp);

                listMainData.add(newMap);
            }

            result.put(JvDefinesMessages.TypeData.MessagesInfoList, listMainData);
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }
}