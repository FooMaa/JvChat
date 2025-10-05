package org.foomaa.jvchat.models;

import org.foomaa.jvchat.structobjects.JvGetterStructObjects;


public class RootObjectsModel extends BaseModel {
    RootObjectsModel() {
        setRootObject(JvGetterStructObjects.getInstance()
                .getBeanRootStructObject(getNameModel()));
    }
}
