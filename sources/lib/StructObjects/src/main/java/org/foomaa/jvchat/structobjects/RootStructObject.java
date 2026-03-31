package org.foomaa.jvchat.structobjects;

import java.util.Objects;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RootStructObject extends BaseStructObject {
    private String nameModel;

    @Builder
    RootStructObject() {
        nameModel = "";
        commitProperties();
    }

    public void setNameModel(String nameModel) {
        if (!Objects.equals(this.nameModel, nameModel)) {
            this.nameModel = nameModel;
            commitProperties();
        }
    }
}
