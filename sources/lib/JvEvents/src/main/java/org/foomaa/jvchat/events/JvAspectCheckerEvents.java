package org.foomaa.jvchat.events;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.foomaa.jvchat.logger.JvLog;

import java.lang.reflect.Field;


@Aspect
public class JvAspectCheckerEvents {
    JvAspectCheckerEvents() {}

    @Around("@annotation(checkerEventsAnnotation)")
    public Object checkFieldMatch(ProceedingJoinPoint joinPoint, JvCheckerEventsAnnotation checkerEventsAnnotation) throws Throwable {
        Object targetObject = joinPoint.getTarget();
        Object[] methodArgs = joinPoint.getArgs();
        String nameDestination = "destination";
        String nameUuid = "uuidKey";

        if (methodArgs.length == 0) {
            JvLog.write(JvLog.TypeLog.Error, "Method must have at least one parameter.");
            throw new IllegalArgumentException("Method must have at least one parameter.");
        }

        Object paramObject = methodArgs[0];

        String classFieldName = checkerEventsAnnotation.connectionUuid();
        Object objectFieldValue = getFieldValue(paramObject, nameUuid);
        Object classFieldValue = getFieldValue(targetObject, classFieldName);

        Object destination = getFieldValue(paramObject, nameDestination);

        if (objectFieldValue != null && objectFieldValue.equals(classFieldValue) && destination == targetObject) {
            return joinPoint.proceed();
        } else {
            JvLog.write(JvLog.TypeLog.Error, "Method execution skipped: uuid class argument != uuid event in this class");
            return null;
        }
    }

    private Object getFieldValue(Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }
}
