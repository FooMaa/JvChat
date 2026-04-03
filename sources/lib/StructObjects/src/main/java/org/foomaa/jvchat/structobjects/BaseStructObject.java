package org.foomaa.jvchat.structobjects;

import java.lang.reflect.Field;
import java.util.*;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public abstract class BaseStructObject {
    private BaseStructObject parent;
    private List<BaseStructObject> children;
    private UUID uuid;
    private final HashMap<String, Object> properties;

    BaseStructObject() {
        parent = null;
        children = new ArrayList<>();
        uuid = UUID.randomUUID();
        properties = new HashMap<>();
    }

    public Object getProperty(String name) {
        return properties.get(name);
    }

    public void setParent(BaseStructObject newParent) {
        if (parent != newParent) {
            parent = newParent;
        }
    }

    public void addChild(BaseStructObject child) {
        child.setParent(this);
        children.add(child);
    }

    public void removeChild(BaseStructObject child) {
        children.remove(child);
    }

    @Deprecated
    @SuppressWarnings("unused")
    public void setChildren(List<BaseStructObject> newChildren) {
        if (children != newChildren) {
            children = newChildren;
        }
    }

    protected void commitProperties() {
        Class<?> currentClass = getClass();

        while (currentClass != null) {
            for (Field field : currentClass.getDeclaredFields()) {
                field.setAccessible(true);

                try {
                    properties.put(field.getName(), field.get(this));
                } catch (IllegalAccessException exception) {
                    log.error("There's a permissions problem here.");
                }
            }

            currentClass = currentClass.getEnclosingClass();
        }
    }

    public void setUuid(UUID newUuid) {
        if (!Objects.equals(uuid, newUuid)) {
            uuid = newUuid;
            commitProperties();
        }
    }
}
