package org.foomaa.jvchat.ctrl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.globaldefines.DbGlobalDefines;
import org.foomaa.jvchat.globaldefines.MainChatsGlobalDefines;
import org.foomaa.jvchat.messages.DefinesMessages;
import org.foomaa.jvchat.models.CheckersOnlineModel;
import org.foomaa.jvchat.models.SocketRunnableCtrlModel;
import org.foomaa.jvchat.models.UsersModel;
import org.foomaa.jvchat.settings.ServersInfoSettings;
import org.foomaa.jvchat.structobjects.CheckerOnlineStructObject;
import org.foomaa.jvchat.structobjects.SocketRunnableCtrlStructObject;
import org.foomaa.jvchat.structobjects.UserStructObject;

@Slf4j
public class OnlineServersCtrl {
    private final int intervalMilliSecondsAfterLastSending;
    private final int intervalMilliSecondsAfterLastUpdate;
    private final CheckersOnlineModel checkersOnlineModel;
    private final DbCtrl dbCtrl;
    private final ServersInfoSettings serversInfoSettings;
    private final UsersModel usersModel;
    private final SocketRunnableCtrlModel socketRunnableCtrlModel;
    private final SendMessagesCtrl sendMessagesCtrl;

    @Builder
    OnlineServersCtrl(DbCtrl dbCtrl, ServersInfoSettings serversInfoSettings, CheckersOnlineModel checkersOnlineModel,
            UsersModel usersModel, SocketRunnableCtrlModel socketRunnableCtrlModel, SendMessagesCtrl sendMessagesCtrl) {
        this.dbCtrl = Objects.requireNonNull(dbCtrl, "dbCtrl is mandatory");
        this.serversInfoSettings = Objects.requireNonNull(serversInfoSettings, "serversInfoSettings is mandatory");
        this.usersModel = Objects.requireNonNull(usersModel, "usersModel is mandatory");
        this.socketRunnableCtrlModel = Objects.requireNonNull(socketRunnableCtrlModel,
                "socketRunnableCtrlModel is mandatory");
        this.sendMessagesCtrl = Objects.requireNonNull(sendMessagesCtrl, "sendMessagesCtrl is mandatory");
        this.checkersOnlineModel = Objects.requireNonNull(checkersOnlineModel, "checkersOnlineModel is mandatory");

        intervalMilliSecondsAfterLastSending = 10000;
        intervalMilliSecondsAfterLastUpdate = 30000;
    }

    private boolean isRunnableInListCheckerOnline(SocketRunnableCtrl socketRunnableCtrl) {
        List<CheckerOnlineStructObject> listCheckersOnline = checkersOnlineModel.getAllCheckersOnline();

        for (CheckerOnlineStructObject checkerOnline : listCheckersOnline) {
            SocketRunnableCtrlStructObject socketRunnableCtrlStructObject = checkerOnline.getSocketRunnableCtrlStructObject();
            if (socketRunnableCtrlStructObject == null) {
                log.error("Here socketRunnableCtrlStructObject is null.");
                continue;
            }

            Runnable runnableSocketFromList = socketRunnableCtrlStructObject.getSocketRunnableCtrl();
            if (runnableSocketFromList == socketRunnableCtrl) {
                return true;
            }
        }

        return false;
    }

    private CheckerOnlineStructObject getCheckerOnlineByRunnable(SocketRunnableCtrl socketRunnableCtrl) {
        List<CheckerOnlineStructObject> listCheckersOnline = checkersOnlineModel.getAllCheckersOnline();

        for (CheckerOnlineStructObject checkerOnline : listCheckersOnline) {
            SocketRunnableCtrlStructObject socketRunnableCtrlStructObject = checkerOnline.getSocketRunnableCtrlStructObject();
            if (socketRunnableCtrlStructObject == null) {
                log.error("Here socketRunnableCtrlStructObject is null.");
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
        List<CheckerOnlineStructObject> listCheckersOnline = checkersOnlineModel.getAllCheckersOnline();

        for (CheckerOnlineStructObject checkerOnline : listCheckersOnline) {
            UserStructObject userStructObject = checkerOnline.getUser();
            if (userStructObject == null) {
                log.error("Here userStructObject is null.");
                continue;
            }

            UUID uuidUserFromList = userStructObject.getUuid();
            if (uuidUserFromList.equals(uuidUser)) {
                return true;
            }
        }

        return false;
    }

    private CheckerOnlineStructObject getCheckerOnlineByUuidUser(UUID uuidUser) {
        List<CheckerOnlineStructObject> listCheckersOnline = checkersOnlineModel.getAllCheckersOnline();

        for (CheckerOnlineStructObject checkerOnline : listCheckersOnline) {
            UUID uuidUserFromList = checkerOnline.getUser().getUuid();
            if (uuidUserFromList.equals(uuidUser)) {
                return checkerOnline;
            }
        }

        return null;
    }

    public void loadDataOnlineUsers() {
        List<Map<DbGlobalDefines.LineKeys, String>> dataFromDb = dbCtrl.getMultipleInfoFromDb(
                DbCtrl.TypeExecutionGetMultiple.OnlineUsers);

        if (dataFromDb == null) {
            runningRunnableListenOnline();
            return;
        }

        for (Map<DbGlobalDefines.LineKeys, String> map : dataFromDb) {
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
        CheckerOnlineStructObject onlineUser;

        if (isRunnableInListCheckerOnline((SocketRunnableCtrl) runnableFrom)) {
            onlineUser = getCheckerOnlineByRunnable((SocketRunnableCtrl) runnableFrom);
        } else if (isUuidUserInListCheckerOnline(uuidUser)) {
            onlineUser = getCheckerOnlineByUuidUser(uuidUser);
        } else {
            checkersOnlineModel.createNewCheckersOnline(uuidUser, runnableFrom, false, LocalDateTime.now(),
                    LocalDateTime.now());
            return;
        }

        if (onlineUser == null) {
            log.error("Here onlineUser turned out to be null.");
            return;
        }

        UserStructObject userStructObject = usersModel.findCreateUserStructObjectByUuidUser(uuidUser);
        SocketRunnableCtrlStructObject socketRunnableCtrlStructObject = socketRunnableCtrlModel.findCreateSocketRunnableCtrlStructObjectByRunnable(
                runnableFrom);

        onlineUser.setUser(userStructObject);
        onlineUser.setSocketRunnableCtrlStructObject(socketRunnableCtrlStructObject);
        onlineUser.setIsSending(false);
        onlineUser.setDateTimeUpdating(LocalDateTime.now());
        onlineUser.setDateTimeSending(LocalDateTime.now());

        saveStatusOnline(uuidUser, MainChatsGlobalDefines.TypeStatusOnline.Online);
    }

    private void saveStatusOnline(UUID uuidUser, MainChatsGlobalDefines.TypeStatusOnline statusOnline) {
        int onlineStatusInteger = statusOnline.getValue();
        String onlineStatusString = String.valueOf(onlineStatusInteger);
        dbCtrl.insertQueryToDB(DbCtrl.TypeExecutionInsert.OnlineUsersInfo, uuidUser.toString(), onlineStatusString);
    }

    private void listeningPackage() {
        if (socketRunnableCtrlModel.isEmpty()) {
            try {
                Thread.sleep(intervalMilliSecondsAfterLastSending);
                return;
            } catch (InterruptedException exception) {
                log.error("Thread.sleep() failed to running here.");
            }
        }

        List<SocketRunnableCtrlStructObject> connectionList = socketRunnableCtrlModel.getAllSocketRunnableCtrlStructObject();

        for (SocketRunnableCtrlStructObject socketRunnableCtrlStructObject : connectionList) {
            SocketRunnableCtrl socketRunnableCtrl = (SocketRunnableCtrl) socketRunnableCtrlStructObject.getSocketRunnableCtrl();
            if (socketRunnableCtrl == null) {
                log.error("socketRunnableCtrl turned out to be null.");
                continue;
            }

            preSendingTasks(socketRunnableCtrl);
            sendMessagesCtrl.sendMessage(DefinesMessages.TypeMessage.CheckOnlineUserRequest,
                    serversInfoSettings.getIp(), socketRunnableCtrl);

            if (!isRunnableInListCheckerOnline(socketRunnableCtrl)) {
                checkersOnlineModel.createNewCheckersOnline(socketRunnableCtrl, true, LocalDateTime.now(),
                        LocalDateTime.now());
                continue;
            }

            CheckerOnlineStructObject onlineUser = getCheckerOnlineByRunnable(socketRunnableCtrl);
            if (onlineUser == null) {
                log.error("Here onlineUser turned out to be null.");
                continue;
            }
            onlineUser.setIsSending(true);
            onlineUser.setDateTimeSending(LocalDateTime.now());
        }

        updateListeningStructure();
    }

    private void preSendingTasks(SocketRunnableCtrl socketRunnableCtrl) {
        if (isRunnableInListCheckerOnline(socketRunnableCtrl)) {
            CheckerOnlineStructObject onlineUser = getCheckerOnlineByRunnable(socketRunnableCtrl);
            if (onlineUser == null) {
                log.error("Here onlineUser turned out to be null");
                return;
            }

            boolean flagSending = onlineUser.isSending();
            LocalDateTime lastSendingDateTime = onlineUser.getDateTimeSending();

            Duration duration = Duration.between(lastSendingDateTime, LocalDateTime.now());
            long milliSecondsAfterLastSending = duration.toMillis();

            if (flagSending && milliSecondsAfterLastSending < intervalMilliSecondsAfterLastSending) {
                try {
                    Thread.sleep(intervalMilliSecondsAfterLastSending - milliSecondsAfterLastSending);
                } catch (InterruptedException exception) {
                    log.error("Thread.sleep() failed to running here.");
                }
            }
        }
    }

    private void updateListeningStructure() {
        List<CheckerOnlineStructObject> listCheckersOnline = checkersOnlineModel.getAllCheckersOnline();

        for (CheckerOnlineStructObject onlineUser : listCheckersOnline) {
            LocalDateTime lastUpdatingDateTime = onlineUser.getDateTimeUpdating();
            Duration duration = Duration.between(lastUpdatingDateTime, LocalDateTime.now());
            long milliSecondsAfterLastUpdating = duration.toMillis();

            if (milliSecondsAfterLastUpdating > intervalMilliSecondsAfterLastUpdate) {
                checkersOnlineModel.removeItem(onlineUser);

                UserStructObject userStructObject = onlineUser.getUser();
                if (userStructObject == null) {
                    log.error("Here userStructObject is null.");
                    continue;
                }

                saveStatusOnline(onlineUser.getUser().getUuid(), MainChatsGlobalDefines.TypeStatusOnline.Offline);
            }
        }
    }

    public Map<UUID, MainChatsGlobalDefines.TypeStatusOnline> getStatusesUsers(List<UUID> uuidsUsers) {
        Map<UUID, MainChatsGlobalDefines.TypeStatusOnline> resultMap = new HashMap<>();
        for (UUID uuidUser : uuidsUsers) {
            boolean isUserOnline = isUuidUserInListCheckerOnline(uuidUser);
            if (isUserOnline) {
                resultMap.put(uuidUser, MainChatsGlobalDefines.TypeStatusOnline.Online);
            } else {
                resultMap.put(uuidUser, MainChatsGlobalDefines.TypeStatusOnline.Offline);
            }
        }
        return resultMap;
    }

    public Map<UUID, String> getLastOnlineTimeUsers(List<UUID> uuidsUsers) {
        Map<UUID, String> resultMap = new HashMap<>();
        for (UUID uuidUser : uuidsUsers) {
            boolean isUserOnline = isUuidUserInListCheckerOnline(uuidUser);
            if (!isUserOnline) {
                String lastOnlineTime = dbCtrl.getSingleDataFromDb(DbCtrl.TypeExecutionGetSingle.LastOnlineTimeUser,
                        uuidUser.toString());
                resultMap.put(uuidUser, lastOnlineTime);
            }
        }
        return resultMap;
    }

    public Runnable getRunnableByUuidUser(UUID uuidUser) {
        List<CheckerOnlineStructObject> listCheckersOnline = checkersOnlineModel.getAllCheckersOnline();

        for (CheckerOnlineStructObject checkerOnline : listCheckersOnline) {
            SocketRunnableCtrlStructObject socketRunnableCtrlStructObject = checkerOnline.getSocketRunnableCtrlStructObject();
            if (socketRunnableCtrlStructObject == null) {
                log.error("Here socketRunnableCtrlStructObject is null.");
                continue;
            }

            if (checkerOnline.getUser().getUuid().equals(uuidUser)) {
                return socketRunnableCtrlStructObject.getSocketRunnableCtrl();
            }
        }

        return null;
    }
}
