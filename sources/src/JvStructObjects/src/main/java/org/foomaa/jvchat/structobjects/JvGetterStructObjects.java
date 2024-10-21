package org.foomaa.jvchat.structobjects;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class JvGetterStructObjects {
    private static JvGetterStructObjects instance;
    private final AnnotationConfigApplicationContext context;

    private JvGetterStructObjects() {
        context = new AnnotationConfigApplicationContext(
                JvStructObjectsSpringConfig.class);
    }

    public static JvGetterStructObjects getInstance() {
        if (instance == null) {
            instance = new JvGetterStructObjects();
        }
        return instance;
    }

    public JvMessageStructObject getBeanMessageStructObject() {
        return context.getBean(JvStructObjectsSpringConfig.NameBeans.BeanMessageStructObject.getValue(),
                JvMessageStructObject.class);
    }

}