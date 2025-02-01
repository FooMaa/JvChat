package org.foomaa.jvchat.structobjects;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class JvConnectionEventStructObject extends JvBaseStructObject {
    private Class<?> classSender;
    private Class<?> classReceiver;
    private Object objectSender;
    private Object objectReceiver;
    private AnnotationConfigApplicationContext context;

    JvConnectionEventStructObject() {
        classSender = null;
        classReceiver = null;
        objectSender = null;
        objectReceiver = null;
        context = null;

        commitProperties();
    }

    public void setClassSender(Class<?> newClassSender) {
        if (classSender != newClassSender) {
            classSender = newClassSender;
            commitProperties();
        }
    }

    public void setClassReceiver(Class<?> newClassReceiver) {
        if (classReceiver != newClassReceiver) {
            classReceiver = newClassReceiver;
        }
    }

    public void setObjectSender(Object newObjectSender) {
        if (objectSender != newObjectSender) {
            objectSender = newObjectSender;
            commitProperties();
        }
    }

    public void setObjectReceiver(Object newObjectReceiver) {
        if (objectReceiver != newObjectReceiver) {
            objectReceiver = newObjectReceiver;
            commitProperties();
        }
    }

    public void setContext(AnnotationConfigApplicationContext newContext) {
        if (context != newContext) {
            context = newContext;
            commitProperties();
        }
    }

    public AnnotationConfigApplicationContext getContext() {
        return context;
    }

    public Object getObjectReceiver() {
        return objectReceiver;
    }

    public Object getObjectSender() {
        return objectSender;
    }

    public Class<?> getClassReceiver() {
        return classReceiver;
    }

    public Class<?> getClassSender() {
        return classSender;
    }
}
