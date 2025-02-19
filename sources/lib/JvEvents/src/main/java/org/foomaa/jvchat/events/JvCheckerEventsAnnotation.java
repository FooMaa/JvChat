package org.foomaa.jvchat.events;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface JvCheckerEventsAnnotation {
    String connectionUuid();
}