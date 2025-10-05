package org.foomaa.jvchat.structobjects;


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
