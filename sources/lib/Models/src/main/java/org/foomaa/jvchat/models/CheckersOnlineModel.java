package org.foomaa.jvchat.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import lombok.Builder;

import org.foomaa.jvchat.structobjects.*;

public class CheckersOnlineModel extends BaseModel {
    // DI ↓
    private final UsersModel usersModel;
    private final SocketRunnableCtrlModel socketRunnableCtrlModel;
    private final CheckerOnlineStructObjectFactory checkerOnlineStructObjectFactory;

    @Builder
    CheckersOnlineModel(
            UsersModel usersModel,
            SocketRunnableCtrlModel socketRunnableCtrlModel,
            CheckerOnlineStructObjectFactory checkerOnlineStructObjectFactory,
            RootStructObjectFactory rootStructObjectFactory,
            RootObjectsModel rootObjectsModel) {
        super(
                Objects.requireNonNull(rootObjectsModel, "rootObjectsModel is mandatory"),
                Objects.requireNonNull(rootStructObjectFactory, "rootStructObjectFactory is mandatory"));

        this.usersModel = Objects.requireNonNull(usersModel, "usersModel is mandatory");
        this.socketRunnableCtrlModel =
                Objects.requireNonNull(socketRunnableCtrlModel, "socketRunnableCtrlModel is mandatory");
        this.checkerOnlineStructObjectFactory = Objects.requireNonNull(
                checkerOnlineStructObjectFactory, "checkerOnlineStructObjectFactory is mandatory");
    }

    public void createNewCheckersOnline(UUID uuidUser, LocalDateTime dateTimeUpdating) {
        UserStructObject userStructObject = usersModel.findCreateUserStructObjectByUuidUser(uuidUser);
        CheckerOnlineStructObject checkerOnlineStructObject =
                checkerOnlineStructObjectFactory.create(userStructObject, dateTimeUpdating);

        addItem(checkerOnlineStructObject, getRootObject());
    }

    public void createNewCheckersOnline(
            Runnable runnable, boolean isSending, LocalDateTime dateTimeSending, LocalDateTime dateTimeUpdating) {
        SocketRunnableCtrlStructObject socketRunnableCtrlStructObject =
                socketRunnableCtrlModel.findCreateSocketRunnableCtrlStructObjectByRunnable(runnable);
        CheckerOnlineStructObject checkerOnlineStructObject = checkerOnlineStructObjectFactory.create(
                isSending, dateTimeSending, dateTimeUpdating, socketRunnableCtrlStructObject);

        addItem(checkerOnlineStructObject, getRootObject());
    }

    public void createNewCheckersOnline(
            UUID uuidUser,
            Runnable runnable,
            boolean isSending,
            LocalDateTime dateTimeSending,
            LocalDateTime dateTimeUpdating) {
        UserStructObject userStructObject = usersModel.findCreateUserStructObjectByUuidUser(uuidUser);
        SocketRunnableCtrlStructObject socketRunnableCtrlStructObject =
                socketRunnableCtrlModel.findCreateSocketRunnableCtrlStructObjectByRunnable(runnable);

        CheckerOnlineStructObject checkerOnlineStructObject = checkerOnlineStructObjectFactory.create(
                userStructObject, isSending, dateTimeSending, dateTimeUpdating, socketRunnableCtrlStructObject);

        addItem(checkerOnlineStructObject, getRootObject());
    }

    public List<CheckerOnlineStructObject> getAllCheckersOnline() {
        List<CheckerOnlineStructObject> resultList = new ArrayList<>();

        for (BaseStructObject baseStructObject : getRootObject().getChildren()) {
            CheckerOnlineStructObject checkerOnlineStructObject = (CheckerOnlineStructObject) baseStructObject;
            resultList.add(checkerOnlineStructObject);
        }

        return resultList;
    }
}
