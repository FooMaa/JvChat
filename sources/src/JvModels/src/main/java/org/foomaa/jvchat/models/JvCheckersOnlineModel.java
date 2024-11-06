package org.foomaa.jvchat.models;

import org.foomaa.jvchat.structobjects.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JvCheckersOnlineModel extends JvBaseModel {
    private static JvCheckersOnlineModel instance;

    private JvCheckersOnlineModel() {
        setRootObject(JvGetterStructObjects.getInstance()
                .getBeanRootStructObject(getNameModel()));
    }

    static JvCheckersOnlineModel getInstance() {
        if (instance == null) {
            instance = new JvCheckersOnlineModel();
        }
        return instance;
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
