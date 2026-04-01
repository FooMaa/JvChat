package org.foomaa.jvchat.models;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.structobjects.BaseStructObject;
import org.foomaa.jvchat.structobjects.RootStructObject;
import org.foomaa.jvchat.structobjects.RootStructObjectFactory;

@Slf4j
public abstract class BaseModel {
    @Getter
    private final String nameModel;

    private RootStructObject rootObject;
    private final BaseModel rootModel;
    private final RootStructObjectFactory rootStructObjectFactory;

    BaseModel(BaseModel rootModel, RootStructObjectFactory rootStructObjectFactory) {
        this.rootStructObjectFactory = rootStructObjectFactory;
        this.rootModel = rootModel;

        nameModel = getClass().getSimpleName();

        if (rootStructObjectFactory != null) {
            installRoot();
        }
    }

    public void addItem(BaseStructObject item, BaseStructObject parent) {
        parent.addChild(item);
    }

    public void removeItem(BaseStructObject item) {
        if (rootObject == null) {
            log.error("Here rootObject turned out to be null.");
            return;
        }

        if (!removeItemProcess(rootObject, item)) {
            log.error("There is an error when deleting an element.");
        }
    }

    private boolean removeItemProcess(BaseStructObject parent, BaseStructObject item) {
        List<BaseStructObject> baseStructObjects = new ArrayList<>(parent.getChildren());

        for (BaseStructObject child : baseStructObjects) {
            if (child == item) {
                parent.removeChild(item);
                return true;
            } else {
                boolean removed = removeItemProcess(child, item);
                if (removed) {
                    return true;
                }
            }
        }

        return false;
    }

    protected BaseStructObject getRootObject() {
        return rootObject;
    }

    private void installRoot() {
        if (rootModel == null && getClass() == RootObjectsModel.class) {
            rootObject = rootStructObjectFactory.create(getNameModel());
            return;
        } else if (rootModel == null) {
            return;
        }

        RootStructObject rootStructObjectRootModel = (RootStructObject) rootModel.getRootObject();
        RootStructObject creatingRoot = rootStructObjectFactory.create(getNameModel());

        if (rootStructObjectRootModel != null && creatingRoot != rootStructObjectRootModel) {
            rootObject = creatingRoot;
            rootModel.addItem(creatingRoot, rootStructObjectRootModel);
        }
    }

    public void clearModel() {
        List<BaseStructObject> children = new ArrayList<>(rootObject.getChildren());
        if (children.isEmpty()) {
            log.warn("Empty children...");
            return;
        }

        for (BaseStructObject child : children) {
            rootObject.removeChild(child);
        }
    }
}
