package org.foomaa.jvchat.structobjects;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.*;


@Slf4j
public abstract class BaseStructObject {
    private BaseStructObject parent;
    private List<BaseStructObject> children;
    private final HashMap<String, Object> properties;
    private UUID uuid;

    BaseStructObject() {
        properties = new HashMap<>();
        children = new ArrayList<>();
        parent = null;
        uuid = UUID.randomUUID();
    }

    public Object getProperty(String name) {
        return properties.get(name);
    }

    public BaseStructObject getParent() {
        return parent;
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

    public List<BaseStructObject> getChildren() {
        return children;
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

    public UUID getUuid() {
        return uuid;
    }
}
