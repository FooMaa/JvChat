package org.foomaa.jvchat.ctrl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import org.foomaa.jvchat.globaldefines.JvDbGlobalDefines;
import org.foomaa.jvchat.globaldefines.JvMainChatsGlobalDefines;
import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.messages.JvDefinesMessages;
import org.foomaa.jvchat.models.JvCheckersOnlineModel;
import org.foomaa.jvchat.models.JvGetterModels;
import org.foomaa.jvchat.models.JvSocketRunnableCtrlModel;
import org.foomaa.jvchat.settings.JvGetterSettings;
import org.foomaa.jvchat.structobjects.JvCheckerOnlineStructObject;
import org.foomaa.jvchat.structobjects.JvSocketRunnableCtrlStructObject;
import org.foomaa.jvchat.structobjects.JvUserStructObject;


public class JvOnlineServersCtrl {
    private static JvOnlineServersCtrl instance;
    private final int intervalMilliSecondsAfterLastSending;
    private final int intervalMilliSecondsAfterLastUpdate;
    private final JvCheckersOnlineModel checkersOnlineModel;

    private JvOnlineServersCtrl() {
        checkersOnlineModel = JvGetterModels.getInstance().getBeanCheckersOnlineModel();
        intervalMilliSecondsAfterLastSending = 10000;
        intervalMilliSecondsAfterLastUpdate = 30000;
    }

    static JvOnlineServersCtrl getInstance() {
        if (instance == null) {
            instance = new JvOnlineServersCtrl();
        }
        return instance;
    }

    private boolean isRunnableInListCheckerOnline(JvSocketRunnableCtrl socketRunnableCtrl) {
        List<JvCheckerOnlineStructObject> listCheckersOnline = checkersOnlineModel.getAllCheckersOnline();

        for (JvCheckerOnlineStructObject checkerOnline : listCheckersOnline) {
            Runnable runnableSocketFromList = checkerOnline.getSocketRunnableCtrlStructObject().getSocketRunnableCtrl();
            if (runnableSocketFromList == null) {
                JvLog.write(JvLog.TypeLog.Error, "Здесь мы не смогли получить runnableSocketFromList, так как он null");
                continue;
            }

            if ( runnableSocketFromList == socketRunnableCtrl) {
                return true;
            }
        }

        return false;
    }

    private JvCheckerOnlineStructObject getCheckerOnlineByRunnable(JvSocketRunnableCtrl socketRunnableCtrl) {
        List<JvCheckerOnlineStructObject> listCheckersOnline = checkersOnlineModel.getAllCheckersOnline();

        for (JvCheckerOnlineStructObject checkerOnline : listCheckersOnline) {
            Runnable runnableSocketFromList = checkerOnline.getSocketRunnableCtrlStructObject().getSocketRunnableCtrl();
            if (runnableSocketFromList == null) {
                JvLog.write(JvLog.TypeLog.Error, "Здесь мы не смогли получить runnableSocketFromList, так как он null");
                continue;
            }

            if (runnableSocketFromList == socketRunnableCtrl) {
                return checkerOnline;
            }
        }

        return null;
    }

    private boolean isLoginInListCheckerOnline(String userLogin) {
        List<JvCheckerOnlineStructObject> listCheckersOnline = checkersOnlineModel.getAllCheckersOnline();

        for (JvCheckerOnlineStructObject checkerOnline : listCheckersOnline) {
            String loginFromList = checkerOnline.getUser().getLogin();
            if (Objects.equals(loginFromList, userLogin)) {
                return true;
            }
        }

        return false;
    }

    private JvCheckerOnlineStructObject getCheckerOnlineByUserLogin(String userLogin) {
        List<JvCheckerOnlineStructObject> listCheckersOnline = checkersOnlineModel.getAllCheckersOnline();

        for (JvCheckerOnlineStructObject checkerOnline : listCheckersOnline) {
            String loginFromList = checkerOnline.getUser().getLogin();
            if (Objects.equals(loginFromList, userLogin)) {
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
            runningRunnableListenOnline();
            return;
        }

        for (Map<JvDbGlobalDefines.LineKeys, String> map : dataFromDb) {
            for (String login : map.values()) {
                checkersOnlineModel.createNewCheckersOnline(login, LocalDateTime.now());
            }
        }

        runningRunnableListenOnline();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void runningRunnableListenOnline() {
        Runnable listenOnline = () -> {
            while (true) {
                listeningPackage();
            }
        };

        Thread thread = new Thread(listenOnline);
        thread.start();
    }

    public void addUsersOnline(String userLogin, Runnable runnableFrom) {
        JvCheckerOnlineStructObject onlineUser;

        if (isRunnableInListCheckerOnline((JvSocketRunnableCtrl) runnableFrom)) {
            onlineUser = getCheckerOnlineByRunnable((JvSocketRunnableCtrl) runnableFrom);
        } else if (isLoginInListCheckerOnline(userLogin)) {
            onlineUser = getCheckerOnlineByUserLogin(userLogin);
        } else {
            checkersOnlineModel.createNewCheckersOnline(
                    userLogin,
                    runnableFrom,
                    false,
                    LocalDateTime.now(),
                    LocalDateTime.now());
            return;
        }

        if (onlineUser == null) {
            JvLog.write(JvLog.TypeLog.Error, "Здесь onlineUser оказался null");
            return;
        }

        JvUserStructObject userStructObject =
                JvGetterModels.getInstance().getBeanUsersModel().findCreateUserStructObjectByLogin(userLogin);
        JvSocketRunnableCtrlStructObject socketRunnableCtrlStructObject =
                JvGetterModels.getInstance().getBeanSocketRunnableCtrlModel().findCreateSocketRunnableCtrlStructObjectByRunnable(runnableFrom);

        onlineUser.setUser(userStructObject);
        onlineUser.setSocketRunnableCtrlStructObject(socketRunnableCtrlStructObject);
        onlineUser.setIsSending(false);
        onlineUser.setDateTimeUpdating(LocalDateTime.now());
        onlineUser.setDateTimeSending(LocalDateTime.now());

        saveStatusOnline(userLogin, JvMainChatsGlobalDefines.TypeStatusOnline.Online);
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
        JvSocketRunnableCtrlModel socketRunnableCtrlModel = JvGetterModels.getInstance().getBeanSocketRunnableCtrlModel();
        if (socketRunnableCtrlModel.isEmpty()) {
            try {
                Thread.sleep(intervalMilliSecondsAfterLastSending);
                return;
            } catch (InterruptedException exception) {
                JvLog.write(JvLog.TypeLog.Error, "Здесь не удалось выполнить sleep()");
            }
        }

        List<JvSocketRunnableCtrlStructObject> connectionList = socketRunnableCtrlModel.getAllSocketRunnableCtrlStructObject();

        for (JvSocketRunnableCtrlStructObject socketRunnableCtrlStructObject : connectionList) {
            JvSocketRunnableCtrl socketRunnableCtrl = (JvSocketRunnableCtrl) socketRunnableCtrlStructObject.getSocketRunnableCtrl();
            if (socketRunnableCtrl == null) {
                JvLog.write(JvLog.TypeLog.Error, "socketRunnableCtrl оказался null");
                continue;
            }

            preSendingTasks(socketRunnableCtrl);
            JvGetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(
                    JvDefinesMessages.TypeMessage.CheckOnlineUserRequest,
                    JvGetterSettings.getInstance().getBeanServersInfoSettings().getIp(),
                    socketRunnableCtrl);

            if (!isRunnableInListCheckerOnline(socketRunnableCtrl)) {
                checkersOnlineModel.createNewCheckersOnline(socketRunnableCtrl, true, LocalDateTime.now(), LocalDateTime.now());
                continue;
            }

            JvCheckerOnlineStructObject onlineUser = getCheckerOnlineByRunnable(socketRunnableCtrl);
            if (onlineUser == null) {
                JvLog.write(JvLog.TypeLog.Error, "Здесь onlineUser оказался null");
                continue;
            }
            onlineUser.setIsSending(true);
            onlineUser.setDateTimeSending(LocalDateTime.now());
        }

        updateListeningStructure();
    }

    private void preSendingTasks(JvSocketRunnableCtrl socketRunnableCtrl) {
        if (isRunnableInListCheckerOnline(socketRunnableCtrl)) {
            JvCheckerOnlineStructObject onlineUser = getCheckerOnlineByRunnable(socketRunnableCtrl);
            if (onlineUser == null) {
                JvLog.write(JvLog.TypeLog.Error, "Здесь online user оказался null");
                return;
            }

            boolean flagSending = onlineUser.getIsSending();
            LocalDateTime lastSendingDateTime = onlineUser.getDateTimeSending();

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
        List<JvCheckerOnlineStructObject> listCheckersOnline = checkersOnlineModel.getAllCheckersOnline();

        for (JvCheckerOnlineStructObject onlineUser : listCheckersOnline) {
            LocalDateTime lastUpdatingDateTime = onlineUser.getDateTimeUpdating();
            Duration duration = Duration.between(lastUpdatingDateTime, LocalDateTime.now());
            long milliSecondsAfterLastUpdating = duration.toMillis();

            if (milliSecondsAfterLastUpdating > intervalMilliSecondsAfterLastUpdate) {
                System.out.println(onlineUser.getUser().getLogin());
                checkersOnlineModel.removeItem(onlineUser);
                System.out.println(onlineUser.getUser().getLogin());
                saveStatusOnline(onlineUser.getUser().getLogin(), JvMainChatsGlobalDefines.TypeStatusOnline.Offline);
            }
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
