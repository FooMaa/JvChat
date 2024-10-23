package org.foomaa.jvchat.structobjects;

import java.util.UUID;


public class JvRootStructObject extends JvBaseStructObject {
    private final UUID uuid;
    private final String nameModel;

    JvRootStructObject(String newNameModel) {
        uuid = UUID.randomUUID();
        nameModel = newNameModel;

        commitProperties();
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getNameModel() {
        return nameModel;
    }
}
