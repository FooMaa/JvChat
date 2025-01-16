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
    private final int intervalMilliSecondsAfterLastSending;
    private final int intervalMilliSecondsAfterLastUpdate;
    private final JvCheckersOnlineModel checkersOnlineModel;

    JvOnlineServersCtrl() {
        checkersOnlineModel = JvGetterModels.getInstance().getBeanCheckersOnlineModel();
        intervalMilliSecondsAfterLastSending = 10000;
        intervalMilliSecondsAfterLastUpdate = 30000;
    }

    private boolean isRunnableInListCheckerOnline(JvSocketRunnableCtrl socketRunnableCtrl) {
        List<JvCheckerOnlineStructObject> listCheckersOnline = checkersOnlineModel.getAllCheckersOnline();

        for (JvCheckerOnlineStructObject checkerOnline : listCheckersOnline) {
            JvSocketRunnableCtrlStructObject socketRunnableCtrlStructObject = checkerOnline.getSocketRunnableCtrlStructObject();
            if (socketRunnableCtrlStructObject == null) {
                JvLog.write(JvLog.TypeLog.Error, "Здесь socketRunnableCtrlStructObject равен null");
                continue;
            }

            Runnable runnableSocketFromList = socketRunnableCtrlStructObject.getSocketRunnableCtrl();
            if (runnableSocketFromList == socketRunnableCtrl) {
                return true;
            }
        }

        return false;
    }

    private JvCheckerOnlineStructObject getCheckerOnlineByRunnable(JvSocketRunnableCtrl socketRunnableCtrl) {
        List<JvCheckerOnlineStructObject> listCheckersOnline = checkersOnlineModel.getAllCheckersOnline();

        for (JvCheckerOnlineStructObject checkerOnline : listCheckersOnline) {
            JvSocketRunnableCtrlStructObject socketRunnableCtrlStructObject = checkerOnline.getSocketRunnableCtrlStructObject();
            if (socketRunnableCtrlStructObject == null) {
                JvLog.write(JvLog.TypeLog.Error, "Здесь socketRunnableCtrlStructObject равен null");
                continue;
            }

            Runnable runnableSocketFromList = socketRunnableCtrlStructObject.getSocketRunnableCtrl();
            if (runnableSocketFromList == socketRunnableCtrl) {
                return checkerOnline;
            }
        }

        return null;
    }

    public boolean isUuidUserInListCheckerOnline(UUID uuidUser) {
        List<JvCheckerOnlineStructObject> listCheckersOnline = checkersOnlineModel.getAllCheckersOnline();

        for (JvCheckerOnlineStructObject checkerOnline : listCheckersOnline) {
            JvUserStructObject userStructObject = checkerOnline.getUser();
            if (userStructObject == null) {
                JvLog.write(JvLog.TypeLog.Error, "Здесь userStructObject равен null");
                continue;
            }

            UUID uuidUserFromList = userStructObject.getUuid();
            if (uuidUserFromList.equals(uuidUser)) {
                return true;
            }
        }

        return false;
    }

    private JvCheckerOnlineStructObject getCheckerOnlineByUuidUser(UUID uuidUser) {
        List<JvCheckerOnlineStructObject> listCheckersOnline = checkersOnlineModel.getAllCheckersOnline();

        for (JvCheckerOnlineStructObject checkerOnline : listCheckersOnline) {
            UUID uuidUserFromList = checkerOnline.getUser().getUuid();
            if (uuidUserFromList.equals(uuidUser)) {
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
            for (String uuidUser : map.values()) {
                checkersOnlineModel.createNewCheckersOnline(UUID.fromString(uuidUser), LocalDateTime.now());
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

    public void addUsersOnline(UUID uuidUser, Runnable runnableFrom) {
        JvCheckerOnlineStructObject onlineUser;

        if (isRunnableInListCheckerOnline((JvSocketRunnableCtrl) runnableFrom)) {
            onlineUser = getCheckerOnlineByRunnable((JvSocketRunnableCtrl) runnableFrom);
        } else if (isUuidUserInListCheckerOnline(uuidUser)) {
            onlineUser = getCheckerOnlineByUuidUser(uuidUser);
        } else {
            checkersOnlineModel.createNewCheckersOnline(
                    uuidUser,
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
                JvGetterModels.getInstance().getBeanUsersModel().findCreateUserStructObjectByUuidUser(uuidUser);
        JvSocketRunnableCtrlStructObject socketRunnableCtrlStructObject =
                JvGetterModels.getInstance().getBeanSocketRunnableCtrlModel().findCreateSocketRunnableCtrlStructObjectByRunnable(runnableFrom);

        onlineUser.setUser(userStructObject);
        onlineUser.setSocketRunnableCtrlStructObject(socketRunnableCtrlStructObject);
        onlineUser.setIsSending(false);
        onlineUser.setDateTimeUpdating(LocalDateTime.now());
        onlineUser.setDateTimeSending(LocalDateTime.now());

        saveStatusOnline(uuidUser, JvMainChatsGlobalDefines.TypeStatusOnline.Online);
    }

    private void saveStatusOnline(UUID uuidUser, JvMainChatsGlobalDefines.TypeStatusOnline statusOnline) {
        int onlineStatusInteger = statusOnline.getValue();
        String onlineStatusString = String.valueOf(onlineStatusInteger);
        JvGetterControls.getInstance()
                .getBeanDbCtrl().insertQueryToDB(
                        JvDbCtrl.TypeExecutionInsert.OnlineUsersInfo,
                        uuidUser.toString(),
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
                checkersOnlineModel.removeItem(onlineUser);

                JvUserStructObject userStructObject = onlineUser.getUser();
                if (userStructObject == null) {
                    JvLog.write(JvLog.TypeLog.Error, "Здесь userStructObject равен null");
                    continue;
                }

                saveStatusOnline(onlineUser.getUser().getUuid(), JvMainChatsGlobalDefines.TypeStatusOnline.Offline);
            }
        }
    }

    public Map<UUID, JvMainChatsGlobalDefines.TypeStatusOnline> getStatusesUsers(List<UUID> uuidsUsers) {
        Map<UUID, JvMainChatsGlobalDefines.TypeStatusOnline> resultMap = new HashMap<>();
        for (UUID uuidUser : uuidsUsers) {
            boolean isLoginOnline = isUuidUserInListCheckerOnline(uuidUser);
            if (isLoginOnline) {
                resultMap.put(uuidUser, JvMainChatsGlobalDefines.TypeStatusOnline.Online);
            } else {
                resultMap.put(uuidUser, JvMainChatsGlobalDefines.TypeStatusOnline.Offline);
            }
        }
        return resultMap;
    }

    public Map<UUID, String> getLastOnlineTimeUsers(List<UUID> uuidsUsers) {
        Map<UUID, String> resultMap = new HashMap<>();
        for (UUID uuidUser : uuidsUsers) {
            boolean isUserOnline = isUuidUserInListCheckerOnline(uuidUser);
            if (!isUserOnline) {
                String lastOnlineTime = JvGetterControls.getInstance().getBeanDbCtrl()
                        .getSingleDataFromDb(JvDbCtrl.TypeExecutionGetSingle.LastOnlineTimeUser, uuidUser.toString());
                resultMap.put(uuidUser, lastOnlineTime);
            }
        }
        return resultMap;
    }

    public Runnable getRunnableByUuidUser(UUID uuidUser) {
        List<JvCheckerOnlineStructObject> listCheckersOnline = checkersOnlineModel.getAllCheckersOnline();

        for (JvCheckerOnlineStructObject checkerOnline : listCheckersOnline) {
            JvSocketRunnableCtrlStructObject socketRunnableCtrlStructObject = checkerOnline.getSocketRunnableCtrlStructObject();
            if (socketRunnableCtrlStructObject == null) {
                JvLog.write(JvLog.TypeLog.Error, "Здесь socketRunnableCtrlStructObject равен null");
                continue;
            }

            if (checkerOnline.getUser().getUuid().equals(uuidUser)) {
                return socketRunnableCtrlStructObject.getSocketRunnableCtrl();
            }
        }

        return null;
    }
}
