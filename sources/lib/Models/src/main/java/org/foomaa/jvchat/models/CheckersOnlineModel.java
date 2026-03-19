package org.foomaa.jvchat.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.foomaa.jvchat.structobjects.*;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class CheckersOnlineModel extends BaseModel {
    private final UsersModel usersModel;
    private final SocketRunnableCtrlModel socketRunnableCtrlModel;
    private final ObjectProvider<CheckerOnlineStructObject> checkerOnlineStructObjectObjectProvider;

    CheckersOnlineModel(UsersModel usersModel,
                        SocketRunnableCtrlModel socketRunnableCtrlModel,
                        ObjectProvider<CheckerOnlineStructObject> checkerOnlineStructObjectObjectProvider,
                        ObjectProvider<RootStructObject> rootStructObjectObjectProvider,
                        RootObjectsModel rootObjectsModel) {
        super(rootObjectsModel, rootStructObjectObjectProvider);

        this.usersModel = usersModel;
        this.socketRunnableCtrlModel = socketRunnableCtrlModel;
        this.checkerOnlineStructObjectObjectProvider = checkerOnlineStructObjectObjectProvider;
    }

    public void createNewCheckersOnline(UUID uuidUser, LocalDateTime dateTimeUpdating) {
        CheckerOnlineStructObject checkerOnlineStructObject = checkerOnlineStructObjectObjectProvider.getObject();

        UserStructObject userStructObject = usersModel.findCreateUserStructObjectByUuidUser(uuidUser);

        checkerOnlineStructObject.setUser(userStructObject);
        checkerOnlineStructObject.setDateTimeUpdating(dateTimeUpdating);

        addItem(checkerOnlineStructObject, getRootObject());
    }

    public void createNewCheckersOnline(Runnable runnable, boolean isSending, LocalDateTime dateTimeSending, LocalDateTime dateTimeUpdating) {
        CheckerOnlineStructObject checkerOnlineStructObject = checkerOnlineStructObjectObjectProvider.getObject();

        SocketRunnableCtrlStructObject socketRunnableCtrlStructObject =
                socketRunnableCtrlModel.findCreateSocketRunnableCtrlStructObjectByRunnable(runnable);

        checkerOnlineStructObject.setSocketRunnableCtrlStructObject(socketRunnableCtrlStructObject);
        checkerOnlineStructObject.setIsSending(isSending);
        checkerOnlineStructObject.setDateTimeSending(dateTimeSending);
        checkerOnlineStructObject.setDateTimeUpdating(dateTimeUpdating);

        addItem(checkerOnlineStructObject, getRootObject());
    }

    public void createNewCheckersOnline(UUID uuidUser, Runnable runnable, boolean isSending, LocalDateTime dateTimeSending, LocalDateTime dateTimeUpdating) {
        CheckerOnlineStructObject checkerOnlineStructObject = checkerOnlineStructObjectObjectProvider.getObject();

        UserStructObject userStructObject = usersModel.findCreateUserStructObjectByUuidUser(uuidUser);
        SocketRunnableCtrlStructObject socketRunnableCtrlStructObject =
                socketRunnableCtrlModel.findCreateSocketRunnableCtrlStructObjectByRunnable(runnable);
        
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
