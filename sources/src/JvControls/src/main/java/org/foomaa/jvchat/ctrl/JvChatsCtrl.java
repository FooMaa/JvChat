package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.globaldefines.JvDbGlobalDefines;
import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.settings.JvGetterSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class JvChatsCtrl {
    private static JvChatsCtrl instance;
    private List<Map<JvDbGlobalDefines.LineKeys, String>> chatsInfo;

    public enum TypeStatusMessage {
        Error(-1),
        Sent(0),
        Delivered(1),
        Read(2);

        private final int value;

        TypeStatusMessage(int newValue) {
            value = newValue;
        }

        public int getValue() {
            return value;
        }

        public static TypeStatusMessage getTypeStatusMessage(int value) {
            TypeStatusMessage[] statusKeys = TypeStatusMessage.values();
            for (TypeStatusMessage statusKey : statusKeys) {
                if (statusKey.getValue() == value) {
                    return statusKey;
                }
            }
            return null;
        }
    }

    private JvChatsCtrl() {}

    static JvChatsCtrl getInstance() {
        if (instance == null) {
            instance = new JvChatsCtrl();
        }
        return instance;
    }

    public void setChatsInfo(List<Map<JvDbGlobalDefines.LineKeys, String>> newChatsInfo) {
        if (chatsInfo != newChatsInfo) {
            chatsInfo = newChatsInfo;
        }
        System.out.println("######################################");
        System.out.println(chatsInfo);
    }

    public List<Map<JvDbGlobalDefines.LineKeys, String>> getChatsInfo() {
        return chatsInfo;
    }

    public List<String> getLoginsChats() {
        if (chatsInfo.isEmpty()) {
            JvLog.write(JvLog.TypeLog.Warn, "chatsInfo пуст здесь");
            return null;
        }

        String currentUserLogin = JvGetterSettings.getInstance().getBeanUserInfoSettings().getLogin();
        List<String> listLogins = new ArrayList<>();

        for (Map<JvDbGlobalDefines.LineKeys, String> map : chatsInfo) {
            String sender = map.get(JvDbGlobalDefines.LineKeys.Sender);
            String receiver = map.get(JvDbGlobalDefines.LineKeys.Receiver);

            if (Objects.equals(sender, currentUserLogin)) {
                listLogins.add(receiver);
            } else if (Objects.equals(receiver, currentUserLogin)) {
                listLogins.add(sender);
            }
        }
        return listLogins;
    }

    public String getLastMessageByLogin(String login) {
        if (chatsInfo.isEmpty()) {
            JvLog.write(JvLog.TypeLog.Error, "chatsInfo пуст здесь");
            return null;
        }

        String lastMessage = "";

        for (Map<JvDbGlobalDefines.LineKeys, String> map : chatsInfo) {
            String sender = map.get(JvDbGlobalDefines.LineKeys.Sender);
            String receiver = map.get(JvDbGlobalDefines.LineKeys.Receiver);

            if (!Objects.equals(sender, login) && !Objects.equals(receiver, login)) {
                continue;
            }

            lastMessage = map.get(JvDbGlobalDefines.LineKeys.Message);
        }
        return lastMessage;
    }

    public TypeStatusMessage getStatusLastMessage(String login) {
        if (chatsInfo.isEmpty()) {
            JvLog.write(JvLog.TypeLog.Error, "chatsInfo пуст здесь");
            return null;
        }

        String statusMessageString = "";
        int statusMessageInteger = -1;

        for (Map<JvDbGlobalDefines.LineKeys, String> map : chatsInfo) {
            String sender = map.get(JvDbGlobalDefines.LineKeys.Sender);
            String receiver = map.get(JvDbGlobalDefines.LineKeys.Receiver);

            if (!Objects.equals(sender, login) && !Objects.equals(receiver, login)) {
                continue;
            }

            statusMessageString = map.get(JvDbGlobalDefines.LineKeys.Status);
        }

        try {
            statusMessageInteger = Integer.parseInt(statusMessageString);
        } catch (NumberFormatException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Статус сообщения невозможно определить, из-за невозможности приведения его к типу int");
        }

        return TypeStatusMessage.getTypeStatusMessage(statusMessageInteger);
    }

    
}