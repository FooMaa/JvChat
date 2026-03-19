package org.foomaa.jvchat.structobjects;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
public class RootStructObject extends BaseStructObject {
    private final String nameModel;

    RootStructObject(String newNameModel) {
        nameModel = newNameModel;
        commitProperties();
    }

    public String getNameModel() {
        return nameModel;
    }
}
