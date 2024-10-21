package org.foomaa.jvchat.structobjects;

import org.foomaa.jvchat.logger.JvLog;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;


public abstract class JvBaseStructObject {
    JvBaseStructObject parent;
    List<JvBaseStructObject> children;
    HashMap<String, Object> properties;

    JvBaseStructObject() {
        properties = new HashMap<>();
    }

    public void setProperty(String name, Object value) {
        properties.put(name, value);
    }

    public Object getProperty(String name) {
        return properties.get(name);
    }

    public JvBaseStructObject getParent() {
        return parent;
    }

    public void setParent(JvBaseStructObject newParent) {
        if (parent != newParent) {
            parent = newParent;
        }
    }

    public void addChild(JvBaseStructObject child) {
        child.setParent(this);
        children.add(child);
    }

    public List<JvBaseStructObject> getChildren() {
        return children;
    }

    public void setChildren(List<JvBaseStructObject> newChildren) {
        if (children != newChildren) {
            children = newChildren;
        }
    }

    public void commitProperties() {
        Class<?> currentClass = getClass();

        while (currentClass != null) {
            for (Field field : currentClass.getDeclaredFields()) {
                field.setAccessible(true);

                try {
                    properties.put(field.getName(), field.get(this));
                } catch (IllegalAccessException exception) {
                    JvLog.write(JvLog.TypeLog.Error,"Тут возникла проблема с правами");
                }
            }

            currentClass = currentClass.getEnclosingClass();
        }
    }
}
