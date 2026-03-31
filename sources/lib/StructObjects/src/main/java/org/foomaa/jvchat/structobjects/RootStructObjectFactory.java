package org.foomaa.jvchat.structobjects;

import java.util.Objects;

import org.springframework.beans.factory.ObjectProvider;

import lombok.Builder;

public class RootStructObjectFactory {
    private final ObjectProvider<RootStructObject> rootStructObjectObjectProvider;

    @Builder
    RootStructObjectFactory(ObjectProvider<RootStructObject> rootStructObjectObjectProvider) {
        this.rootStructObjectObjectProvider = Objects.requireNonNull(rootStructObjectObjectProvider,
                "rootStructObjectObjectProvider is mandatory");
    }

    public RootStructObject create() {
        return rootStructObjectObjectProvider.getObject();
    }

    public RootStructObject create(String nameModel) {
        RootStructObject rootStructObject = rootStructObjectObjectProvider.getObject();

        rootStructObject.setNameModel(nameModel);

        return rootStructObject;
    }
}
