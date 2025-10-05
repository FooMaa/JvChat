package org.foomaa.jvchat.models;

import org.foomaa.jvchat.structobjects.GetterStructObjects;


public class RootObjectsModel extends BaseModel {
    RootObjectsModel() {
        setRootObject(GetterStructObjects.getInstance()
                .getBeanRootStructObject(getNameModel()));
    }
}
