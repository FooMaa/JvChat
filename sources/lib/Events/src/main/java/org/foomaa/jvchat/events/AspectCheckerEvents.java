package org.foomaa.jvchat.events;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Field;


@Aspect
@Slf4j
public class AspectCheckerEvents {
    AspectCheckerEvents() {}

    @Around("@annotation(checkerEventsAnnotation)")
    @SuppressWarnings("unused")
    public Object checkFieldMatch(ProceedingJoinPoint joinPoint, CheckerEventsAnnotation checkerEventsAnnotation) throws Throwable {
        Object targetObject = joinPoint.getTarget();
        Object[] methodArgs = joinPoint.getArgs();
        String nameDestination = "destination";
        String nameUuid = "uuidKey";

        if (methodArgs.length == 0) {
            log.error("Method must have at least one parameter.");
            throw new IllegalArgumentException("Method must have at least one parameter.");
        }

        Object paramObject = methodArgs[0];

        String[] classFieldNames = checkerEventsAnnotation.connectionUuid();
        Object objectFieldValue = getFieldValue(paramObject, nameUuid);
        Object destination = getFieldValue(paramObject, nameDestination);

        boolean flagReturn = false;
        for (String classFieldName : classFieldNames) {
            Object classFieldValue = getFieldValue(targetObject, classFieldName);
            if (objectFieldValue != null && objectFieldValue.equals(classFieldValue) && destination == targetObject) {
                flagReturn = true;
            } else {
                log.info("Method execution skipped: uuid class argument != uuid event in this class");
            }
        }

        return flagReturn ? joinPoint.proceed() : null;
    }

    private Object getFieldValue(Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }
}
