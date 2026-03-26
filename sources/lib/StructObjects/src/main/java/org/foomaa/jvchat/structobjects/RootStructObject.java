package org.foomaa.jvchat.structobjects;

import java.util.Objects;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Scope("prototype")
@Getter
public class RootStructObject extends BaseStructObject {
    private String nameModel;

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
