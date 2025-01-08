package org.foomaa.jvchat.messages;

import java.util.*;

import com.google.protobuf.InvalidProtocolBufferException;
import org.foomaa.jvchat.globaldefines.JvDbGlobalDefines;
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
            result.put(JvDefinesMessages.TypeData.Uuid, JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data)
                    .getEntryReply().getUuidUser());
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

            result.put(JvDefinesMessages.TypeData.Uuid, uuidUser);
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvDefinesMessages.TypeData, List<Map<JvDbGlobalDefines.LineKeys, String>>> takeChatsLoadReplyMessage(byte[] data) {
        HashMap<JvDefinesMessages.TypeData, List<Map<JvDbGlobalDefines.LineKeys, String>>> result = new HashMap<>();
        try {
            JvClientServerSerializeProtocolMessage_pb.ChatsLoadReply chatsLoadReplyMsg =
                    JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data).getChatsLoadReply();

            List<Map<JvDbGlobalDefines.LineKeys, String>> listMainData = new ArrayList<>();
            for (int i = 0; i < chatsLoadReplyMsg.getChatsInfoMapCount(); i++) {
                Map<String, String> map = chatsLoadReplyMsg.getChatsInfoMap(i).getMapInfoMap();
                Map<JvDbGlobalDefines.LineKeys, String> newMap = new HashMap<>();

                for (String key : map.keySet()) {
                    newMap.put(JvDbGlobalDefines.LineKeys.getTypeLineKey(key), map.get(key));
                }

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

    private HashMap<JvDefinesMessages.TypeData, String> takeCheckOnlineUserReplyMessage(byte[] data) {
        HashMap<JvDefinesMessages.TypeData, String> result = new HashMap<>();
        try {
            result.put(JvDefinesMessages.TypeData.Login, JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data).
                    getCheckOnlineReply().getLogin());
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvDefinesMessages.TypeData, List<String>> takeLoadUsersOnlineStatusRequestMessage(byte[] data) {
        HashMap<JvDefinesMessages.TypeData, List<String>> result = new HashMap<>();
        try {
            JvClientServerSerializeProtocolMessage_pb.LoadUsersOnlineStatusRequest loadUsersOnlineStatusRequestMsg =
                    JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data).getLoadUsersOnlineStatusRequest();

            List<String> listMainData = new ArrayList<>();
            for (int i = 0; i < loadUsersOnlineStatusRequestMsg.getLoginsCount(); i++) {
                String login = loadUsersOnlineStatusRequestMsg.getLogins(i);
                listMainData.add(login);
            }

            result.put(JvDefinesMessages.TypeData.LoginsList, listMainData);
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvDefinesMessages.TypeData, Map<String, ?>> takeLoadUsersOnlineStatusReplyMessage(byte[] data) {
        HashMap<JvDefinesMessages.TypeData, Map<String, ?>> result = new HashMap<>();
        try {
            JvClientServerSerializeProtocolMessage_pb.LoadUsersOnlineStatusReply loadUsersOnlineStatusReply =
                    JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data).getLoadUsersOnlineStatusReply();

            Map<String, JvClientServerSerializeProtocolMessage_pb.LoadUsersOnlineStatusReply.StatusOnline> mapStatusesUsers =
                    loadUsersOnlineStatusReply.getMapStatusOnlineMap();
            Map<String, String> mapLastOnlineTimeUsers = loadUsersOnlineStatusReply.getMapLastOnlineTimeMap();

            Map<String, JvMainChatsGlobalDefines.TypeStatusOnline> newMapStatusesUsers = new HashMap<>();
            for (String key : mapStatusesUsers.keySet()) {
                int integerStatus = mapStatusesUsers.get(key).getNumber();
                JvMainChatsGlobalDefines.TypeStatusOnline statusMsg =
                        JvMainChatsGlobalDefines.TypeStatusOnline.getTypeStatusOnline(integerStatus);

                newMapStatusesUsers.put(key, statusMsg);
            }

            result.put(JvDefinesMessages.TypeData.UsersOnlineInfoList, newMapStatusesUsers);
            result.put(JvDefinesMessages.TypeData.TimeStampLastOnlineString, mapLastOnlineTimeUsers);
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvDefinesMessages.TypeData, String> takeTextMessageSendUserToServerMessage(byte[] data) {
        HashMap<JvDefinesMessages.TypeData, String> result = new HashMap<>();
        try {
            JvClientServerSerializeProtocolMessage_pb.TextMessageSendUserToServer msgData =
                    JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data)
                            .getTextMessageSendUserToServer();
            JvClientServerSerializeProtocolMessage_pb.MessageInfo messageInfo = msgData.getMessageInfo();

            result.put(JvDefinesMessages.TypeData.LoginSender, messageInfo.getLoginSender());
            result.put(JvDefinesMessages.TypeData.LoginReceiver, messageInfo.getLoginReceiver());
            result.put(JvDefinesMessages.TypeData.Uuid, messageInfo.getUuid());
            result.put(JvDefinesMessages.TypeData.Text, messageInfo.getText());
            result.put(JvDefinesMessages.TypeData.TimeStampMessageSend, messageInfo.getTimestamp());
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
            JvClientServerSerializeProtocolMessage_pb.MessageInfo messageInfo = msgData.getMessageInfo();

            result.put(JvDefinesMessages.TypeData.LoginSender, messageInfo.getLoginSender());
            result.put(JvDefinesMessages.TypeData.LoginReceiver, messageInfo.getLoginReceiver());
            result.put(JvDefinesMessages.TypeData.Uuid, messageInfo.getUuid());
            result.put(JvDefinesMessages.TypeData.Text, messageInfo.getText());
            result.put(JvDefinesMessages.TypeData.TimeStampMessageSend, messageInfo.getTimestamp());
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

            result.put(JvDefinesMessages.TypeData.LoginRequesting, msgData.getLoginRequesting());
            result.put(JvDefinesMessages.TypeData.LoginDialog, msgData.getLoginDialog());
            result.put(JvDefinesMessages.TypeData.QuantityMessages, msgData.getQuantityMessages());
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvDefinesMessages.TypeData, List<Map<JvDbGlobalDefines.LineKeys, String>>> takeMessagesLoadReplyMessage(byte[] data) {
        HashMap<JvDefinesMessages.TypeData, List<Map<JvDbGlobalDefines.LineKeys, String>>> result = new HashMap<>();
        try {
            JvClientServerSerializeProtocolMessage_pb.MessagesLoadReply msgLoadReplyMsg =
                    JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data).getMessagesLoadReply();

            List<Map<JvDbGlobalDefines.LineKeys, String>> listMainData = new ArrayList<>();
            for (int i = 0; i < msgLoadReplyMsg.getMessagesInfoMapCount(); i++) {
                Map<String, String> map = msgLoadReplyMsg.getMessagesInfoMap(i).getMapInfoMap();
                Map<JvDbGlobalDefines.LineKeys, String> newMap = new HashMap<>();

                for (String key : map.keySet()) {
                    newMap.put(JvDbGlobalDefines.LineKeys.getTypeLineKey(key), map.get(key));
                }

                listMainData.add(newMap);
            }

            result.put(JvDefinesMessages.TypeData.MessagesInfoList, listMainData);
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }
}