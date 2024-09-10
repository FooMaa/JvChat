package org.foomaa.jvchat.messages;

import com.google.protobuf.InvalidProtocolBufferException;
import org.foomaa.jvchat.globaldefines.JvDbGlobalDefines;
import org.foomaa.jvchat.globaldefines.JvMainChatsGlobalDefines;
import org.foomaa.jvchat.logger.JvLog;

import java.util.*;


public class JvDeserializatorDataMessages {
    private static JvDeserializatorDataMessages instance;

    private JvDeserializatorDataMessages() {}

    static JvDeserializatorDataMessages getInstance() {
        if (instance == null) {
            instance = new JvDeserializatorDataMessages();
        }
        return instance;
    }

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

    private HashMap<JvDefinesMessages.TypeData, Boolean> takeEntryReplyMessage(byte[] data) {
        HashMap<JvDefinesMessages.TypeData, Boolean> result = new HashMap<>();
        try {
            result.put(JvDefinesMessages.TypeData.BoolReply, JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data)
                    .getEntryReply().getReply());
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

    private HashMap<JvDefinesMessages.TypeData, String> takeChatsLoadRequestMessage(byte[] data) {
        HashMap<JvDefinesMessages.TypeData, String> result = new HashMap<>();
        try {
            result.put(JvDefinesMessages.TypeData.ChatsLoad, JvClientServerSerializeProtocolMessage_pb.General.parseFrom(data).
                    getChatsLoadRequest().getSender());
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
        return null;
    }

    private HashMap<JvDefinesMessages.TypeData, List<Map<String, JvMainChatsGlobalDefines.TypeStatusOnline>>> takeLoadUsersOnlineStatusReplyMessage(byte[] data) {
        return null;
    }
}