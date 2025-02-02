package org.foomaa.jvchat.events;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;


@Aspect
@Component
public class JvAspectCompareEventsUuids {
    JvAspectCompareEventsUuids() {}

    @Around("@annotation(compareEventsUuidsAnnotation)")
    public Object checkFieldMatch(ProceedingJoinPoint joinPoint, JvCompareEventsUuidsAnnotation compareEventsUuidsAnnotation) throws Throwable {
        System.out.println("ALOOO");
        Object targetObject = joinPoint.getTarget(); // Объект класса, где объявлен метод
        Object[] methodArgs = joinPoint.getArgs(); // Аргументы метода

        if (methodArgs.length == 0) {
            throw new IllegalArgumentException("Method must have at least one parameter.");
        }

        Object paramObject = methodArgs[0]; // Берем первый параметр метода
        String objectFieldName = compareEventsUuidsAnnotation.instanceField();
        String classFieldName = compareEventsUuidsAnnotation.argumentField();

        // Получаем значение поля у переданного объекта
        Object objectFieldValue = getFieldValue(paramObject, objectFieldName);
        // Получаем значение поля у класса, где находится метод
        Object classFieldValue = getFieldValue(targetObject, classFieldName);

        // Сравниваем значения полей
        if (objectFieldValue != null && objectFieldValue.equals(classFieldValue)) {
            return joinPoint.proceed(); // Выполняем метод
        } else {
            System.out.println("Method execution skipped: " + objectFieldName + " != " + classFieldName);
            return null; // Можно выбросить исключение или вернуть дефолтное значение
        }
    }

    // Вспомогательный метод для получения значения поля через Reflection
    private Object getFieldValue(Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }
}
