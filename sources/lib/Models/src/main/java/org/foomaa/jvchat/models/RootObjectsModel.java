package org.foomaa.jvchat.models;

import org.foomaa.jvchat.structobjects.RootStructObject;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

@Component
public class RootObjectsModel extends BaseModel {
    RootObjectsModel(ObjectProvider<RootStructObject> rootStructObjectObjectProvider) {
        super(null, rootStructObjectObjectProvider);
    }
}
