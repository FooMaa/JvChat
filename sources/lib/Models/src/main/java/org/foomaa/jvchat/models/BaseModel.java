package org.foomaa.jvchat.models;

import org.foomaa.jvchat.logger.Log;
import org.foomaa.jvchat.structobjects.BaseStructObject;
import org.foomaa.jvchat.structobjects.RootStructObject;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseModel {
    private RootStructObject rootObject;
    private final String nameModel;

    BaseModel() {
        nameModel = getClass().getSimpleName();
        rootObject = null;
    }

    public void addItem(BaseStructObject item, BaseStructObject parent) {
        parent.addChild(item);
    }

    public void removeItem(BaseStructObject item) {
        if (rootObject == null) {
            Log.write(Log.TypeLog.Error, "Here rootObject turned out to be null.");
            return;
        }

        if (!removeItemProcess(rootObject, item)) {
            Log.write(Log.TypeLog.Error, "There is an error when deleting an element.");
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

    protected void setRootObject(RootStructObject newRootObject) {
        if (rootObject != newRootObject) {
            rootObject = newRootObject;
            updateRootObjectsModel();
        }
    }

    protected BaseStructObject getRootObject() {
        return rootObject;
    }

    private void updateRootObjectsModel() {
        if (getClass() == RootObjectsModel.class) {
            return;
        }

        RootStructObject rootStructObjectRootModel =
                (RootStructObject) GetterModels.getInstance().getBeanRootObjectsModel().getRootObject();

        if (rootObject != null &&  rootObject != rootStructObjectRootModel) {
            GetterModels.getInstance().getBeanRootObjectsModel().addItem(rootObject, rootStructObjectRootModel);
        }
    }

    public String getNameModel() {
        return nameModel;
    }

    public void clearModel() {
        List<BaseStructObject> children = new ArrayList<>(rootObject.getChildren());
        if (children.isEmpty()) {
            Log.write(Log.TypeLog.Warn, "Empty children...");
            return;
        }

        for (BaseStructObject child : children) {
            rootObject.removeChild(child);
        }
    }
}
