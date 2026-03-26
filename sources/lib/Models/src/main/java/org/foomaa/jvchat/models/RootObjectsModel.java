package org.foomaa.jvchat.models;

import org.springframework.stereotype.Component;

import org.foomaa.jvchat.structobjects.RootStructObjectFactory;

@Component
public class RootObjectsModel extends BaseModel {
    RootObjectsModel(RootStructObjectFactory rootStructObjectFactory) {
        super(null, rootStructObjectFactory);
    }
}
