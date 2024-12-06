package org.foomaa.jvchat.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.foomaa.jvchat.structobjects.*;


public class JvCheckersOnlineModel extends JvBaseModel {
    JvCheckersOnlineModel() {
        setRootObject(JvGetterStructObjects.getInstance()
                .getBeanRootStructObject(getNameModel()));
    }

    public void createNewCheckersOnline(String login, LocalDateTime dateTimeUpdating) {
        JvCheckerOnlineStructObject checkerOnlineStructObject =
                JvGetterStructObjects.getInstance().getBeanCheckerOnlineStructObject();

        JvUserStructObject userStructObject =
                JvGetterModels.getInstance().getBeanUsersModel().findCreateUserStructObjectByLogin(login);

        checkerOnlineStructObject.setUser(userStructObject);
        checkerOnlineStructObject.setDateTimeUpdating(dateTimeUpdating);

        addItem(checkerOnlineStructObject, getRootObject());
    }

    public void createNewCheckersOnline(Runnable runnable, boolean isSending, LocalDateTime dateTimeSending, LocalDateTime dateTimeUpdating) {
        JvCheckerOnlineStructObject checkerOnlineStructObject =
                JvGetterStructObjects.getInstance().getBeanCheckerOnlineStructObject();

        JvSocketRunnableCtrlStructObject socketRunnableCtrlStructObject =
                JvGetterModels.getInstance().getBeanSocketRunnableCtrlModel().findCreateSocketRunnableCtrlStructObjectByRunnable(runnable);

        checkerOnlineStructObject.setSocketRunnableCtrlStructObject(socketRunnableCtrlStructObject);
        checkerOnlineStructObject.setIsSending(isSending);
        checkerOnlineStructObject.setDateTimeSending(dateTimeSending);
        checkerOnlineStructObject.setDateTimeUpdating(dateTimeUpdating);

        addItem(checkerOnlineStructObject, getRootObject());
    }

    public void createNewCheckersOnline(String login, Runnable runnable, boolean isSending, LocalDateTime dateTimeSending, LocalDateTime dateTimeUpdating) {
        JvCheckerOnlineStructObject checkerOnlineStructObject =
                JvGetterStructObjects.getInstance().getBeanCheckerOnlineStructObject();

        JvUserStructObject userStructObject =
                JvGetterModels.getInstance().getBeanUsersModel().findCreateUserStructObjectByLogin(login);
        JvSocketRunnableCtrlStructObject socketRunnableCtrlStructObject =
                JvGetterModels.getInstance().getBeanSocketRunnableCtrlModel().findCreateSocketRunnableCtrlStructObjectByRunnable(runnable);
        
        checkerOnlineStructObject.setUser(userStructObject);
        checkerOnlineStructObject.setSocketRunnableCtrlStructObject(socketRunnableCtrlStructObject);
        checkerOnlineStructObject.setIsSending(isSending);
        checkerOnlineStructObject.setDateTimeSending(dateTimeSending);
        checkerOnlineStructObject.setDateTimeUpdating(dateTimeUpdating);

        addItem(checkerOnlineStructObject, getRootObject());
    }

    public List<JvCheckerOnlineStructObject> getAllCheckersOnline() {
        List<JvCheckerOnlineStructObject> resultList = new ArrayList<>();

        for (JvBaseStructObject baseStructObject : getRootObject().getChildren()) {
            JvCheckerOnlineStructObject checkerOnlineStructObject =
                    (JvCheckerOnlineStructObject) baseStructObject;
            resultList.add(checkerOnlineStructObject);
        }

        return resultList;
    }
}
