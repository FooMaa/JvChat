package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.globaldefines.JvDbGlobalDefines;
import org.foomaa.jvchat.globaldefines.JvMainChatsGlobalDefines;
import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.messages.JvDefinesMessages;
import org.foomaa.jvchat.settings.JvGetterSettings;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;


public class JvOnlineServersCtrl {
    private static JvOnlineServersCtrl instance;
    private final List<CheckerOnline> listCheckerOnline;
    private final int intervalSecondsAfterLastSending;
    private final int intervalSecondsAfterLastUpdate;

    private static class CheckerOnline {
        public String login;
        public JvServersSocketThreadCtrl thread;
        public boolean isSending;
        public LocalDateTime dateTimeSending;
        public LocalDateTime dateTimeUpdating;
    }

    private JvOnlineServersCtrl() {
        listCheckerOnline = new ArrayList<>();
        intervalSecondsAfterLastSending = 10;
        intervalSecondsAfterLastUpdate = 30;
    }

    static JvOnlineServersCtrl getInstance() {
        if (instance == null) {
            instance = new JvOnlineServersCtrl();
        }
        return instance;
    }

    public void loadDataOnlineUsers() {
        List<Map<JvDbGlobalDefines.LineKeys, String>> dataFromDb  = JvGetterControls.getInstance()
                .getBeanDbCtrl().getMultipleInfoFromDb(
                        JvDbCtrl.TypeExecutionGetMultiple.OnlineUsers);

        for (Map<JvDbGlobalDefines.LineKeys, String> map : dataFromDb) {
            for (String login : map.values()) {
                CheckerOnline onlineUser = new CheckerOnline();
                onlineUser.login = login;
                onlineUser.dateTimeUpdating = LocalDateTime.now();
                listCheckerOnline.add(onlineUser);
            }
        }

        runningSendListenOnline();
    }

    public void addUsersOnline(String userLogin) {
        for (CheckerOnline onlineUser : listCheckerOnline) {
            if (Objects.equals(onlineUser.login, userLogin)) {
                onlineUser.dateTimeUpdating = LocalDateTime.now();
                return;
            }
        }

        CheckerOnline onlineUser = new CheckerOnline();
        onlineUser.login = userLogin;
        onlineUser.dateTimeUpdating = LocalDateTime.now();
        listCheckerOnline.add(onlineUser);

        saveStatusOnline(userLogin, JvMainChatsGlobalDefines.TypeStatusOnline.Online);
    }

    public void removeUsersOnline(String userLogin) {
        boolean flagContains = false;

        for (CheckerOnline onlineUser : listCheckerOnline) {
            if (Objects.equals(onlineUser.login, userLogin)) {
                listCheckerOnline.remove(onlineUser);
                saveStatusOnline(userLogin, JvMainChatsGlobalDefines.TypeStatusOnline.Offline);
                break;
            }
        }
    }

    private void saveStatusOnline(String userLogin, JvMainChatsGlobalDefines.TypeStatusOnline statusOnline) {
        int onlineStatusInteger = statusOnline.getValue();
        String onlineStatusString = String.valueOf(onlineStatusInteger);
        JvGetterControls.getInstance()
                .getBeanDbCtrl().insertQueryToDB(
                        JvDbCtrl.TypeExecutionInsert.OnlineUsersInfo,
                        userLogin,
                        onlineStatusString);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void runningSendListenOnline() {
        Runnable listenOnline = () -> {
            while (true) {
//                sendListeningPackage();
            }
        };

        Thread thread = new Thread(listenOnline);
        thread.start();
    }
// TODO (VAD): доделать
//    private void sendListeningPackage() {
//        LinkedList<JvServersSocketThreadCtrl> connectionList =
//                JvGetterControls.getInstance().getBeanNetworkCtrl().getConnectionList();
//
//        for (JvServersSocketThreadCtrl socketThreadCtrl : connectionList) {
//            try {
//                preSendingTasks(socketThreadCtrl);
//            } catch (InterruptedException exception) {
//                JvLog.write(JvLog.TypeLog.Error, "Здесь не удалось выполнить wait()");
//            }
//
//            JvGetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(
//                    JvDefinesMessages.TypeMessage.CheckOnlineUserRequest,
//                    JvGetterSettings.getInstance().getBeanServersInfoSettings().getIp(),
//                    socketThreadCtrl);
//
//            if (!listCheckerOnline.containsKey(socketThreadCtrl)) {
//                CheckerOnline checkerOnline = new CheckerOnline(true, LocalDateTime.now());
//                listCheckerOnline.put(socketThreadCtrl, checkerOnline);
//            }
//        }
//    }
//
//    private void preSendingTasks(JvServersSocketThreadCtrl socketThreadCtrl) throws InterruptedException {
//        if (listCheckerOnline.containsKey(socketThreadCtrl)) {
//            boolean flagSending = listCheckerOnline.get(socketThreadCtrl).isSending;
//            LocalDateTime lastSendingDateTime = listCheckerOnline.get(socketThreadCtrl).dateTime;
//
//            Duration duration = Duration.between(lastSendingDateTime, LocalDateTime.now());
//            long secondsAfterLastSending = duration.getSeconds();
//
//            if (flagSending && secondsAfterLastSending < intervalSecondsAfterLastSending) {
//                Thread.sleep(intervalSecondsAfterLastSending - secondsAfterLastSending);
//            }
//        }
//    }
}
