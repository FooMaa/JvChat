package org.foomaa.jvchat.models;

import java.util.Objects;

import lombok.Builder;

import org.foomaa.jvchat.structobjects.RootStructObjectFactory;

public class RootObjectsModel extends BaseModel {
    @Builder
    RootObjectsModel(RootStructObjectFactory rootStructObjectFactory) {
        super(null, Objects.requireNonNull(rootStructObjectFactory, "rootStructObjectFactory is mandatory"));
    }
}
