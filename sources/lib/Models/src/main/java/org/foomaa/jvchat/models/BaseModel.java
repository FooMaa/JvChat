package org.foomaa.jvchat.models;

import org.foomaa.jvchat.structobjects.BaseStructObject;
import org.foomaa.jvchat.structobjects.RootStructObject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;

import java.util.ArrayList;
import java.util.List;


@Slf4j
public abstract class BaseModel {
    private RootStructObject rootObject;
    private final String nameModel;
    private final ObjectProvider<RootStructObject> rootStructObjectObjectProvider;
    private final BaseModel rootModel;

    BaseModel(BaseModel rootModel, ObjectProvider<RootStructObject> rootStructObjectObjectProvider) {
        this.rootStructObjectObjectProvider = rootStructObjectObjectProvider;
        this.rootModel = rootModel;

        nameModel = getClass().getSimpleName();
        
        if (rootStructObjectObjectProvider != null) {
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
            rootObject = rootStructObjectObjectProvider.getObject(getNameModel());
            return;
        } else if (rootModel == null) {
            return;
        }

        RootStructObject rootStructObjectRootModel = (RootStructObject) rootModel.getRootObject();
        RootStructObject creatingRoot = rootStructObjectObjectProvider.getObject(getNameModel());

        if (rootStructObjectRootModel != null &&
                creatingRoot != rootStructObjectRootModel) {
            rootObject = rootStructObjectRootModel;
            rootModel.addItem(creatingRoot, rootStructObjectRootModel);
        }
    }

    public String getNameModel() {
        return nameModel;
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
