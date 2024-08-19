package org.foomaa.jvchat.messages;

import com.google.protobuf.InvalidProtocolBufferException;
import org.foomaa.jvchat.globaldefines.JvDbGlobalDefines;
import org.foomaa.jvchat.logger.JvLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JvMessagesDeserializatorData {
    private static JvMessagesDeserializatorData instance;

    private JvMessagesDeserializatorData() {}

    public static JvMessagesDeserializatorData getInstance() {
        if (instance == null) {
            instance = new JvMessagesDeserializatorData();
        }
        return instance;
    }

    public HashMap<JvMessagesDefines.TypeData, ?> deserializeData(JvMessagesDefines.TypeMessage type, byte[] data) {
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
        };
    }

    public JvMessagesDefines.TypeMessage getTypeMessage(byte[] data) {
        JvMessagesDefines.TypeMessage type = null;
        try {
            int numberType = ClientServerSerializeProtocol_pb.General.parseFrom(data).getType();
            type = JvMessagesDefines.TypeMessage.getTypeMsg(numberType);
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised type");
        }
        return type;
    }

    private HashMap<JvMessagesDefines.TypeData, String> takeEntryRequestMessage(byte[] data) {
        HashMap<JvMessagesDefines.TypeData, String> result = new HashMap<>();
        try {
            result.put(JvMessagesDefines.TypeData.Login, ClientServerSerializeProtocol_pb.General.parseFrom(data)
                    .getEntryRequest().getLogin());
            result.put(JvMessagesDefines.TypeData.Password, ClientServerSerializeProtocol_pb.General.parseFrom(data)
                    .getEntryRequest().getPassword());
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvMessagesDefines.TypeData, Boolean> takeEntryReplyMessage(byte[] data) {
        HashMap<JvMessagesDefines.TypeData, Boolean> result = new HashMap<>();
        try {
            result.put(JvMessagesDefines.TypeData.BoolReply, ClientServerSerializeProtocol_pb.General.parseFrom(data)
                    .getEntryReply().getReply());
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvMessagesDefines.TypeData, String> takeRegistrationRequestMessage(byte[] data) {
        HashMap<JvMessagesDefines.TypeData, String> result = new HashMap<>();
        try {
            result.put(JvMessagesDefines.TypeData.Login, ClientServerSerializeProtocol_pb.General.parseFrom(data).
                    getRegistrationRequest().getLogin());
            result.put(JvMessagesDefines.TypeData.Email, ClientServerSerializeProtocol_pb.General.parseFrom(data).
                    getRegistrationRequest().getEmail());
            result.put(JvMessagesDefines.TypeData.Password, ClientServerSerializeProtocol_pb.General.parseFrom(data).
                    getRegistrationRequest().getPassword());
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvMessagesDefines.TypeData, Object> takeRegistrationReplyMessage(byte[] data) {
        HashMap<JvMessagesDefines.TypeData, Object> result = new HashMap<>();
        try {
            result.put(JvMessagesDefines.TypeData.BoolReply, ClientServerSerializeProtocol_pb.General.parseFrom(data)
                    .getRegistrationReply().getReply());
            result.put(JvMessagesDefines.TypeData.ErrorReg, JvMessagesDefines.TypeErrorRegistration.getTypeError(
                    ClientServerSerializeProtocol_pb.General.parseFrom(data).
                            getRegistrationReply().getError().getNumber()));
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvMessagesDefines.TypeData, String> takeVerifyRegistrationEmailRequestMessage(byte[] data) {
        HashMap<JvMessagesDefines.TypeData, String> result = new HashMap<>();
        try {
            result.put(JvMessagesDefines.TypeData.Login, ClientServerSerializeProtocol_pb.General.parseFrom(data).
                    getVerifyRegistrationEmailRequest().getLogin());
            result.put(JvMessagesDefines.TypeData.Email, ClientServerSerializeProtocol_pb.General.parseFrom(data).
                    getVerifyRegistrationEmailRequest().getEmail());
            result.put(JvMessagesDefines.TypeData.Password, ClientServerSerializeProtocol_pb.General.parseFrom(data).
                    getVerifyRegistrationEmailRequest().getPassword());
            result.put(JvMessagesDefines.TypeData.VerifyCode, ClientServerSerializeProtocol_pb.General.parseFrom(data).
                    getVerifyRegistrationEmailRequest().getCode());
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvMessagesDefines.TypeData, Object> takeVerifyRegistrationEmailReplyMessage(byte[] data) {
        HashMap<JvMessagesDefines.TypeData, Object> result = new HashMap<>();
        try {
            result.put(JvMessagesDefines.TypeData.BoolReply, ClientServerSerializeProtocol_pb.General.parseFrom(data)
                    .getVerifyRegistrationEmailReply().getReply());
            result.put(JvMessagesDefines.TypeData.ErrorReg, JvMessagesDefines.TypeErrorRegistration.getTypeError(
                    ClientServerSerializeProtocol_pb.General.parseFrom(data).
                            getVerifyRegistrationEmailReply().getError().getNumber()));
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvMessagesDefines.TypeData, String> takeResetPasswordRequestMessage(byte[] data) {
        HashMap<JvMessagesDefines.TypeData, String> result = new HashMap<>();
        try {
            result.put(JvMessagesDefines.TypeData.Email, ClientServerSerializeProtocol_pb.General.parseFrom(data).
                    getResetPasswordRequest().getEmail());
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvMessagesDefines.TypeData, Boolean> takeResetPasswordReplyMessage(byte[] data) {
        HashMap<JvMessagesDefines.TypeData, Boolean> result = new HashMap<>();
        try {
            result.put(JvMessagesDefines.TypeData.BoolReply, ClientServerSerializeProtocol_pb.General.parseFrom(data)
                    .getResetPasswordReply().getReply());
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvMessagesDefines.TypeData, String> takeVerifyFamousEmailRequestMessage(byte[] data) {
        HashMap<JvMessagesDefines.TypeData, String> result = new HashMap<>();
        try {
            result.put(JvMessagesDefines.TypeData.Email, ClientServerSerializeProtocol_pb.General.parseFrom(data).
                    getVerifyFamousEmailRequest().getEmail());
            result.put(JvMessagesDefines.TypeData.VerifyCode, ClientServerSerializeProtocol_pb.General.parseFrom(data).
                    getVerifyFamousEmailRequest().getCode());
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvMessagesDefines.TypeData, Boolean> takeVerifyFamousEmailReplyMessage(byte[] data) {
        HashMap<JvMessagesDefines.TypeData, Boolean> result = new HashMap<>();
        try {
            result.put(JvMessagesDefines.TypeData.BoolReply, ClientServerSerializeProtocol_pb.General.parseFrom(data)
                    .getVerifyFamousEmailReply().getReply());
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvMessagesDefines.TypeData, String> takeChangePasswordRequestMessage(byte[] data) {
        HashMap<JvMessagesDefines.TypeData, String> result = new HashMap<>();
        try {
            result.put(JvMessagesDefines.TypeData.Email, ClientServerSerializeProtocol_pb.General.parseFrom(data).
                    getChangePasswordRequest().getEmail());
            result.put(JvMessagesDefines.TypeData.Password, ClientServerSerializeProtocol_pb.General.parseFrom(data).
                    getChangePasswordRequest().getPassword());
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvMessagesDefines.TypeData, Boolean> takeChangePasswordReplyMessage(byte[] data) {
        HashMap<JvMessagesDefines.TypeData, Boolean> result = new HashMap<>();
        try {
            result.put(JvMessagesDefines.TypeData.BoolReply, ClientServerSerializeProtocol_pb.General.parseFrom(data)
                    .getChangePasswordReply().getReply());
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvMessagesDefines.TypeData, String> takeChatsLoadRequestMessage(byte[] data) {
        HashMap<JvMessagesDefines.TypeData, String> result = new HashMap<>();
        try {
            result.put(JvMessagesDefines.TypeData.ChatsLoad, ClientServerSerializeProtocol_pb.General.parseFrom(data).
                    getChatsLoadRequest().getSender());
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }

    private HashMap<JvMessagesDefines.TypeData, List<Map<JvDbGlobalDefines.LineKeys, String>>> takeChatsLoadReplyMessage(byte[] data) {
        HashMap<JvMessagesDefines.TypeData, List<Map<JvDbGlobalDefines.LineKeys, String>>> result = new HashMap<>();
        try {
            ClientServerSerializeProtocol_pb.ChatsLoadReply chatsLoadReplyMsg =
                    ClientServerSerializeProtocol_pb.General.parseFrom(data).getChatsLoadReply();

            List<Map<JvDbGlobalDefines.LineKeys, String>> listToResult = new ArrayList<>();
            for (int i = 0; i < chatsLoadReplyMsg.getChatsInfoMapCount(); i++) {
                Map<String, String> map = chatsLoadReplyMsg.getChatsInfoMap(i).getMapInfoMap();
                Map<JvDbGlobalDefines.LineKeys, String> newMap = new HashMap<>();

                for (String key : map.keySet()) {
                    newMap.put(JvDbGlobalDefines.LineKeys.getTypeLineKey(key), map.get(key));
                }

                listToResult.add(newMap);
            }

            result.put(JvMessagesDefines.TypeData.ChatsInfoList, listToResult);
        } catch (InvalidProtocolBufferException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in protobuf deserialised data");
        }
        return result;
    }
}
