package org.foomaa.jvchat.structobjects;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Objects;


public class JvConnectionEventStructObject extends JvBaseStructObject {
    private Object objectSender;
    private Object objectReceiver;
    private String customNameEvent;

    private AnnotationConfigApplicationContext context;

    JvConnectionEventStructObject() {
        objectSender = null;
        objectReceiver = null;
        context = null;
        customNameEvent = "";

        commitProperties();
    }
    public void setCustomNameEvent(String newCustomNameEvent) {
        if (!Objects.equals(customNameEvent, newCustomNameEvent)) {
            customNameEvent = newCustomNameEvent;
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

    public void setObjectSender(Object newObjectSender) {
        if (objectSender != newObjectSender) {
            objectSender = newObjectSender;
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

    public String getCustomNameEvent() {
        return customNameEvent;
    }
}
