package org.foomaa.jvchat.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.foomaa.jvchat.structobjects.*;


public class CheckersOnlineModel extends BaseModel {
    CheckersOnlineModel() {
        setRootObject(GetterStructObjects.getInstance()
                .getBeanRootStructObject(getNameModel()));
    }

    public void createNewCheckersOnline(UUID uuidUser, LocalDateTime dateTimeUpdating) {
        CheckerOnlineStructObject checkerOnlineStructObject =
                GetterStructObjects.getInstance().getBeanCheckerOnlineStructObject();

        UserStructObject userStructObject =
                GetterModels.getInstance().getBeanUsersModel().findCreateUserStructObjectByUuidUser(uuidUser);

        checkerOnlineStructObject.setUser(userStructObject);
        checkerOnlineStructObject.setDateTimeUpdating(dateTimeUpdating);

        addItem(checkerOnlineStructObject, getRootObject());
    }

    public void createNewCheckersOnline(Runnable runnable, boolean isSending, LocalDateTime dateTimeSending, LocalDateTime dateTimeUpdating) {
        CheckerOnlineStructObject checkerOnlineStructObject =
                GetterStructObjects.getInstance().getBeanCheckerOnlineStructObject();

        SocketRunnableCtrlStructObject socketRunnableCtrlStructObject =
                GetterModels.getInstance().getBeanSocketRunnableCtrlModel().findCreateSocketRunnableCtrlStructObjectByRunnable(runnable);

        checkerOnlineStructObject.setSocketRunnableCtrlStructObject(socketRunnableCtrlStructObject);
        checkerOnlineStructObject.setIsSending(isSending);
        checkerOnlineStructObject.setDateTimeSending(dateTimeSending);
        checkerOnlineStructObject.setDateTimeUpdating(dateTimeUpdating);

        addItem(checkerOnlineStructObject, getRootObject());
    }

    public void createNewCheckersOnline(UUID uuidUser, Runnable runnable, boolean isSending, LocalDateTime dateTimeSending, LocalDateTime dateTimeUpdating) {
        CheckerOnlineStructObject checkerOnlineStructObject =
                GetterStructObjects.getInstance().getBeanCheckerOnlineStructObject();

        UserStructObject userStructObject =
                GetterModels.getInstance().getBeanUsersModel().findCreateUserStructObjectByUuidUser(uuidUser);
        SocketRunnableCtrlStructObject socketRunnableCtrlStructObject =
                GetterModels.getInstance().getBeanSocketRunnableCtrlModel().findCreateSocketRunnableCtrlStructObjectByRunnable(runnable);
        
        checkerOnlineStructObject.setUser(userStructObject);
        checkerOnlineStructObject.setSocketRunnableCtrlStructObject(socketRunnableCtrlStructObject);
        checkerOnlineStructObject.setIsSending(isSending);
        checkerOnlineStructObject.setDateTimeSending(dateTimeSending);
        checkerOnlineStructObject.setDateTimeUpdating(dateTimeUpdating);

        addItem(checkerOnlineStructObject, getRootObject());
    }

    public List<CheckerOnlineStructObject> getAllCheckersOnline() {
        List<CheckerOnlineStructObject> resultList = new ArrayList<>();

        for (BaseStructObject baseStructObject : getRootObject().getChildren()) {
            CheckerOnlineStructObject checkerOnlineStructObject =
                    (CheckerOnlineStructObject) baseStructObject;
            resultList.add(checkerOnlineStructObject);
        }

        return resultList;
    }
}
