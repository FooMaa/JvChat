package org.foomaa.jvchat.structobjects;


public class JvRootStructObject extends JvBaseStructObject {
    private final String nameModel;

    JvRootStructObject(String newNameModel) {
        nameModel = newNameModel;
        commitProperties();
    }

    public String getNameModel() {
        return nameModel;
    }
}
