package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.globaldefines.JvDbGlobalDefines;
import org.foomaa.jvchat.globaldefines.JvMainChatsGlobalDefines;
import org.foomaa.jvchat.globaldefines.JvMainGlobalDefines;
import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.messages.JvDefinesMessages;
import org.foomaa.jvchat.settings.JvGetterSettings;
import org.foomaa.jvchat.tools.JvGetterTools;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;


public class JvOnlineServersCtrl {
    private static JvOnlineServersCtrl instance;
    private final List<CheckerOnline> listCheckerOnline;
    private final int intervalSecondsAfterLastSending;
    private final int intervalSecondsAfterLastUpdate;
    private final int intervalSecondsSleepAfterRoundUpdating;

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
        intervalSecondsSleepAfterRoundUpdating = 5;
    }

    static JvOnlineServersCtrl getInstance() {
        if (instance == null) {
            instance = new JvOnlineServersCtrl();
        }
        return instance;
    }

    private boolean isThreadInListCheckerOnline(JvServersSocketThreadCtrl socketThreadCtrl) {
        for (CheckerOnline checkerOnline : listCheckerOnline) {
            if (checkerOnline.thread == socketThreadCtrl) {
                return true;
            }
        }
        return false;
    }

    private CheckerOnline getCheckerOnlineByThread(JvServersSocketThreadCtrl socketThreadCtrl) {
        for (CheckerOnline checkerOnline : listCheckerOnline) {
            if (checkerOnline.thread == socketThreadCtrl) {
                return checkerOnline;
            }
        }
        return null;
    }

    private boolean isLoginInListCheckerOnline(String userLogin) {
        for (CheckerOnline checkerOnline : listCheckerOnline) {
            if (Objects.equals(checkerOnline.login, userLogin)) {
                return true;
            }
        }
        return false;
    }

    private CheckerOnline getCheckerOnlineByUserLogin(String userLogin) {
        for (CheckerOnline checkerOnline : listCheckerOnline) {
            if (Objects.equals(checkerOnline.login, userLogin)) {
                return checkerOnline;
            }
        }
        return null;
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

        runningThreadSendingListenOnline();
        runningThreadUpdateListenOnline();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void runningThreadSendingListenOnline() {
        Runnable sendingListenOnline = () -> {
            while (true) {
                sendListeningPackage();
            }
        };

        Thread threadSending = new Thread(sendingListenOnline);
        threadSending.start();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void runningThreadUpdateListenOnline() {
        Runnable updateListenOnline = () -> {
            while (true) {
                updateListeningStructure();
            }
        };

        Thread updateThread = new Thread(updateListenOnline);
        updateThread.start();
    }

    public void addUsersOnline(String userLogin, Thread threadFrom) {
        if (!isLoginInListCheckerOnline(userLogin) &&
                !isThreadInListCheckerOnline((JvServersSocketThreadCtrl) threadFrom)) {
            CheckerOnline onlineUser = new CheckerOnline();
            onlineUser.login = userLogin;
            onlineUser.isSending = false;
            onlineUser.thread = (JvServersSocketThreadCtrl) threadFrom;
            onlineUser.dateTimeUpdating = LocalDateTime.now();
            listCheckerOnline.add(onlineUser);
            saveStatusOnline(userLogin, JvMainChatsGlobalDefines.TypeStatusOnline.Online);
        } else if (isThreadInListCheckerOnline((JvServersSocketThreadCtrl) threadFrom)) {
            CheckerOnline onlineUser = getCheckerOnlineByThread((JvServersSocketThreadCtrl) threadFrom);
            if (onlineUser == null) {
                JvLog.write(JvLog.TypeLog.Error, "Здесь online user оказался null");
                return;
            }
            onlineUser.login = userLogin;
            onlineUser.isSending = false;
            onlineUser.dateTimeUpdating = LocalDateTime.now();
        } else if (isLoginInListCheckerOnline(userLogin)) {
            CheckerOnline onlineUser = getCheckerOnlineByUserLogin(userLogin);
            if (onlineUser == null) {
                JvLog.write(JvLog.TypeLog.Error, "Здесь online user оказался null");
                return;
            }
            onlineUser.thread = (JvServersSocketThreadCtrl) threadFrom;
            onlineUser.isSending = false;
            onlineUser.dateTimeUpdating = LocalDateTime.now();
        }
    }

    private void removeUsersOnline(CheckerOnline onlineUser) {
        if (listCheckerOnline.contains(onlineUser)) {
            String userLogin = onlineUser.login;
            listCheckerOnline.remove(onlineUser);
            saveStatusOnline(userLogin, JvMainChatsGlobalDefines.TypeStatusOnline.Offline);
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

    private void sendListeningPackage() {
        LinkedList<JvServersSocketThreadCtrl> connectionList =
                JvGetterControls.getInstance().getBeanNetworkCtrl().getConnectionList();

        for (JvServersSocketThreadCtrl socketThreadCtrl : connectionList) {
            try {
                preSendingTasks(socketThreadCtrl);
            } catch (InterruptedException exception) {
                JvLog.write(JvLog.TypeLog.Error, "Здесь не удалось выполнить sleep()");
            }

            JvGetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(
                    JvDefinesMessages.TypeMessage.CheckOnlineUserRequest,
                    JvGetterSettings.getInstance().getBeanServersInfoSettings().getIp(),
                    socketThreadCtrl);


            if (!isThreadInListCheckerOnline(socketThreadCtrl)) {
                CheckerOnline onlineUser = new CheckerOnline();
                onlineUser.thread = socketThreadCtrl;
                onlineUser.isSending = true;
                onlineUser.dateTimeSending = LocalDateTime.now();
                listCheckerOnline.add(onlineUser);
            }
        }
    }

    private void preSendingTasks(JvServersSocketThreadCtrl socketThreadCtrl) throws InterruptedException {
        if (isThreadInListCheckerOnline(socketThreadCtrl)) {
            CheckerOnline onlineUser = getCheckerOnlineByThread(socketThreadCtrl);
            if (onlineUser == null) {
                JvLog.write(JvLog.TypeLog.Error, "Здесь online user оказался null");
                return;
            }

            boolean flagSending = onlineUser.isSending;
            LocalDateTime lastSendingDateTime = onlineUser.dateTimeSending;

            Duration duration = Duration.between(lastSendingDateTime, LocalDateTime.now());
            long secondsAfterLastSending = duration.getSeconds();

            if (flagSending && secondsAfterLastSending < intervalSecondsAfterLastSending) {
                Thread.sleep(intervalSecondsAfterLastSending - secondsAfterLastSending);
            }
        }
    }

    private void updateListeningStructure() {
        for (CheckerOnline onlineUser : listCheckerOnline) {
            LocalDateTime lastUpdatingDateTime = onlineUser.dateTimeUpdating;
            Duration duration = Duration.between(lastUpdatingDateTime, LocalDateTime.now());
            long secondsAfterLastUpdating = duration.getSeconds();
            if (secondsAfterLastUpdating > intervalSecondsAfterLastUpdate) {
                removeUsersOnline(onlineUser);
            }
        }

        try {
            Thread.sleep(intervalSecondsSleepAfterRoundUpdating);
        } catch (InterruptedException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Здесь не удалось выполнить sleep()");
        }
    }

    public Map<String, JvMainChatsGlobalDefines.TypeStatusOnline> getStatusesUsers(List<String> logins) {
        Map<String, JvMainChatsGlobalDefines.TypeStatusOnline> resultMap = new HashMap<>();
        for (String login : logins) {
            boolean isLoginOnline = isLoginInListCheckerOnline(login);
            if (isLoginOnline) {
                resultMap.put(login, JvMainChatsGlobalDefines.TypeStatusOnline.Online);
            } else {
                resultMap.put(login, JvMainChatsGlobalDefines.TypeStatusOnline.Offline);
            }
        }
        return resultMap;
    }

    public Map<String, String> getLastOnlineTimeUsers(List<String> logins) {
        Map<String, String> resultMap = new HashMap<>();
        for (String login : logins) {
            boolean isLoginOnline = isLoginInListCheckerOnline(login);
            if (!isLoginOnline) {
//                List<Map<JvDbGlobalDefines.LineKeys, String>> dataFromDb = JvGetterControls.getInstance().getBeanDbCtrl()
//                        .getMultipleInfoFromDb(JvDbCtrl.TypeExecutionGetMultiple.StatusOnlineTimeUser, login);
//                dataFromDb.get()
            }
        }
        return resultMap;
    }
}
