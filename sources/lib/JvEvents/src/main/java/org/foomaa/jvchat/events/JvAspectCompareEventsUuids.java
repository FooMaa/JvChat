package org.foomaa.jvchat.events;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Field;


@Aspect
public class JvAspectCompareEventsUuids {
    JvAspectCompareEventsUuids() {}

    @Around("@annotation(compareEventsUuidsAnnotation)")
    public Object checkFieldMatch(ProceedingJoinPoint joinPoint, JvCompareEventsUuidsAnnotation compareEventsUuidsAnnotation) throws Throwable {
        Object targetObject = joinPoint.getTarget();
        Object[] methodArgs = joinPoint.getArgs();

        if (methodArgs.length == 0) {
            throw new IllegalArgumentException("Method must have at least one parameter.");
        }

        Object paramObject = methodArgs[0];
        String objectFieldName = compareEventsUuidsAnnotation.parameterObjUuid();
        String classFieldName = compareEventsUuidsAnnotation.thisObjUuid();

        Object objectFieldValue = getFieldValue(paramObject, objectFieldName);
        Object classFieldValue = getFieldValue(targetObject, classFieldName);

        if (objectFieldValue != null && objectFieldValue.equals(classFieldValue)) {
            return joinPoint.proceed();
        } else {
            System.out.println("Method execution skipped: " + objectFieldName + " != " + classFieldName);
            return null;
        }
    }

    private Object getFieldValue(Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }
}
