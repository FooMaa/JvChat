package org.foomaa.jvchat.models;

import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.structobjects.JvBaseStructObject;
import org.foomaa.jvchat.structobjects.JvRootStructObject;


public abstract class JvBaseModel {
    private JvRootStructObject rootObject;
    private String nameModel;

    JvBaseModel() {
        nameModel = getClass().getSimpleName();
        rootObject = null;
    }

    public void addItem(JvBaseStructObject item, JvBaseStructObject parent) {
        parent.addChild(item);
    }

    public void removeItem(JvBaseStructObject item) {
        if (rootObject == null) {
            JvLog.write(JvLog.TypeLog.Error, "Здесь rootObject оказался null");
            return;
        }

        if (!removeItemProcess(rootObject, item)) {
            JvLog.write(JvLog.TypeLog.Error, "Тут ошибка при удалении элемента");
        }
    }

    private boolean removeItemProcess(JvBaseStructObject parent, JvBaseStructObject item) {
        for (JvBaseStructObject child: rootObject.getChildren()) {
            if (child == item) {
                rootObject.removeChild(item);
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

    public void setRootObject(JvRootStructObject newRootObject) {
        if (rootObject != newRootObject) {
            rootObject = newRootObject;
            updateRootObjectsModel();
        }
    }

    public JvBaseStructObject getRootObject() {
        return rootObject;
    }

    private void updateRootObjectsModel() {
        JvRootStructObject rootStructObjectRootModel =
                (JvRootStructObject) JvGetterModels.getInstance().getBeanRootObjectsModel().getRootObject();

        if (rootObject != null &&  rootObject != rootStructObjectRootModel) {
            JvGetterModels.getInstance().getBeanRootObjectsModel().addItem(rootObject, rootStructObjectRootModel);
        }
    }

    public String getNameModel() {
        return nameModel;
    }
}
