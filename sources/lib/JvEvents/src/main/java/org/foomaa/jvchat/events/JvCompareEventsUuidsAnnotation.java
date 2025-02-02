package org.foomaa.jvchat.events;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface JvCompareEventsUuidsAnnotation {
    String instanceField();
    String argumentField();
}