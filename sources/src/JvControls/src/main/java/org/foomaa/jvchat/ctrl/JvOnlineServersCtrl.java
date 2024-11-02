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
    private final int intervalMilliSecondsAfterLastSending;
    private final int intervalMilliSecondsAfterLastUpdate;

    private static class CheckerOnline {
        public String login;
        public JvServersSocketRunnableCtrl thread;
        public boolean isSending;
        public LocalDateTime dateTimeSending;
        public LocalDateTime dateTimeUpdating;
    }

    private JvOnlineServersCtrl() {
        listCheckerOnline = new ArrayList<>();
        intervalMilliSecondsAfterLastSending = 10000;
        intervalMilliSecondsAfterLastUpdate = 30000;
    }

    static JvOnlineServersCtrl getInstance() {
        if (instance == null) {
            instance = new JvOnlineServersCtrl();
        }
        return instance;
    }

    private boolean isThreadInListCheckerOnline(JvServersSocketRunnableCtrl socketThreadCtrl) {
        for (CheckerOnline checkerOnline : listCheckerOnline) {
            if (checkerOnline.thread == socketThreadCtrl) {
                return true;
            }
        }
        return false;
    }

    private CheckerOnline getCheckerOnlineByThread(JvServersSocketRunnableCtrl socketThreadCtrl) {
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
        List<Map<JvDbGlobalDefines.LineKeys, String>> dataFromDb = JvGetterControls.getInstance()
                .getBeanDbCtrl().getMultipleInfoFromDb(
                        JvDbCtrl.TypeExecutionGetMultiple.OnlineUsers);

        if (dataFromDb == null) {
            runningThreadListenOnline();
            return;
        }

        for (Map<JvDbGlobalDefines.LineKeys, String> map : dataFromDb) {
            for (String login : map.values()) {
                CheckerOnline onlineUser = new CheckerOnline();
                onlineUser.login = login;
                onlineUser.dateTimeUpdating = LocalDateTime.now();
                listCheckerOnline.add(onlineUser);
            }
        }

        runningThreadListenOnline();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void runningThreadListenOnline() {
        Runnable listenOnline = () -> {
            while (true) {
                listeningPackage();
            }
        };

        Thread thread = new Thread(listenOnline);
        thread.start();
    }

    public void addUsersOnline(String userLogin, Thread threadFrom) {
        CheckerOnline onlineUser;

        if (isThreadInListCheckerOnline((JvServersSocketRunnableCtrl) threadFrom)) {
            onlineUser = getCheckerOnlineByThread((JvServersSocketRunnableCtrl) threadFrom);
        } else if (isLoginInListCheckerOnline(userLogin)) {
            onlineUser = getCheckerOnlineByUserLogin(userLogin);
        } else {
            onlineUser = new CheckerOnline();
            listCheckerOnline.add(onlineUser);
        }

        if (onlineUser == null) {
            JvLog.write(JvLog.TypeLog.Error, "Здесь onlineUser оказался null");
            return;
        }

        onlineUser.login = userLogin;
        onlineUser.thread = (JvServersSocketRunnableCtrl) threadFrom;
        onlineUser.isSending = false;
        onlineUser.dateTimeUpdating = LocalDateTime.now();
        onlineUser.dateTimeSending = LocalDateTime.now();
        saveStatusOnline(userLogin, JvMainChatsGlobalDefines.TypeStatusOnline.Online);
    }

    private void removeUsersOnline(CheckerOnline onlineUser) {
        if (listCheckerOnline.contains(onlineUser)) {
            String userLogin = onlineUser.login;
            listCheckerOnline.remove(onlineUser);
            if (!Objects.equals(userLogin, "") && userLogin != null) {
                saveStatusOnline(userLogin, JvMainChatsGlobalDefines.TypeStatusOnline.Offline);
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

    private void listeningPackage() {
        if (JvGetterControls.getInstance().getBeanNetworkCtrl().getActiveRunnableList().isEmpty()) {
            try {
                Thread.sleep(intervalMilliSecondsAfterLastSending);
                return;
            } catch (InterruptedException exception) {
                JvLog.write(JvLog.TypeLog.Error, "Здесь не удалось выполнить sleep()");
            }
        }

        LinkedList<JvServersSocketRunnableCtrl> connectionList = new LinkedList<>(
                JvGetterControls.getInstance().getBeanNetworkCtrl().getActiveRunnableList());

        for (JvServersSocketRunnableCtrl socketThreadCtrl : connectionList) {
            preSendingTasks(socketThreadCtrl);
            JvGetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(
                    JvDefinesMessages.TypeMessage.CheckOnlineUserRequest,
                    JvGetterSettings.getInstance().getBeanServersInfoSettings().getIp(),
                    socketThreadCtrl);

            if (!isThreadInListCheckerOnline(socketThreadCtrl)) {
                CheckerOnline onlineUser = new CheckerOnline();
                onlineUser.thread = socketThreadCtrl;
                onlineUser.isSending = true;
                onlineUser.dateTimeSending = LocalDateTime.now();
                onlineUser.dateTimeUpdating = LocalDateTime.now();
                listCheckerOnline.add(onlineUser);
                continue;
            }

            CheckerOnline onlineUser = getCheckerOnlineByThread(socketThreadCtrl);
            if (onlineUser == null) {
                JvLog.write(JvLog.TypeLog.Error, "Здесь onlineUser оказался null");
                continue;
            }
            onlineUser.isSending = true;
            onlineUser.dateTimeSending = LocalDateTime.now();
        }

        updateListeningStructure();
    }

    private void preSendingTasks(JvServersSocketRunnableCtrl socketThreadCtrl) {
        if (isThreadInListCheckerOnline(socketThreadCtrl)) {
            CheckerOnline onlineUser = getCheckerOnlineByThread(socketThreadCtrl);
            if (onlineUser == null) {
                JvLog.write(JvLog.TypeLog.Error, "Здесь online user оказался null");
                return;
            }

            boolean flagSending = onlineUser.isSending;
            LocalDateTime lastSendingDateTime = onlineUser.dateTimeSending;

            Duration duration = Duration.between(lastSendingDateTime, LocalDateTime.now());
            long milliSecondsAfterLastSending =  duration.toMillis();

            if (flagSending && milliSecondsAfterLastSending < intervalMilliSecondsAfterLastSending) {
                try {
                    Thread.sleep(intervalMilliSecondsAfterLastSending - milliSecondsAfterLastSending);
                } catch (InterruptedException exception) {
                    JvLog.write(JvLog.TypeLog.Error, "Здесь не удалось выполнить sleep()");
                }
            }
        }
    }

    private void updateListeningStructure() {
        List<CheckerOnline> listToRemove = new ArrayList<>();

        for (CheckerOnline onlineUser : listCheckerOnline) {
            LocalDateTime lastUpdatingDateTime = onlineUser.dateTimeUpdating;
            Duration duration = Duration.between(lastUpdatingDateTime, LocalDateTime.now());
            long milliSecondsAfterLastUpdating = duration.toMillis();

            if (milliSecondsAfterLastUpdating > intervalMilliSecondsAfterLastUpdate) {
                listToRemove.add(onlineUser);
            }
        }

        for (CheckerOnline onlineUser : listToRemove) {
            removeUsersOnline(onlineUser);
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
                String lastOnlineTime = JvGetterControls.getInstance().getBeanDbCtrl()
                        .getSingleDataFromDb(JvDbCtrl.TypeExecutionGetSingle.LastOnlineTimeUser, login);
                resultMap.put(login, lastOnlineTime);
            }
        }
        return resultMap;
    }
}
