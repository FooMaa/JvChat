package org.foomaa.jvchat.structobjects;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

@Component
public class RootStructObjectFactory {
    private final ObjectProvider<RootStructObject> rootStructObjectObjectProvider;

    RootStructObjectFactory(ObjectProvider<RootStructObject> rootStructObjectObjectProvider) {
        this.rootStructObjectObjectProvider = rootStructObjectObjectProvider;
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
