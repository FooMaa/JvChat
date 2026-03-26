package org.foomaa.jvchat.structobjects;

import java.util.Objects;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Scope("prototype")
@Getter
public class ConnectionEventStructObject extends BaseStructObject {
    private Object objectSender;
    private Object objectReceiver;
    private String customNameEvent;

    private AnnotationConfigApplicationContext context;

    ConnectionEventStructObject() {
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
}
