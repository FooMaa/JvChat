package org.foomaa.jvchat.structobjects;

import java.util.Objects;
import java.util.UUID;


public class JvRootStructObject extends JvBaseStructObject {
    private UUID uuid;
    private String nameModel;

    JvRootStructObject(String newNameModel) {
        uuid = UUID.randomUUID();
        nameModel = newNameModel;

        commitProperties();
    }

    public void setUuid(UUID newUuid) {
        if (!uuid.equals(newUuid)) {
            uuid = newUuid;
            commitProperties();
        }
    }

    public void setNameModel(String newNameModel) {
        if (!Objects.equals(nameModel, newNameModel)) {
            nameModel = newNameModel;
            commitProperties();
        }
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getNameModel() {
        return nameModel;
    }
}
