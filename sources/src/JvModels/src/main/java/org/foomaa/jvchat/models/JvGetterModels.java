package org.foomaa.jvchat.models;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class JvGetterModels {
    private static JvGetterModels instance;
    private final AnnotationConfigApplicationContext context;

    private JvGetterModels() {
        context = new AnnotationConfigApplicationContext(
                JvModelsSpringConfig.class);
    }

    public static JvGetterModels getInstance() {
        if (instance == null) {
            instance = new JvGetterModels();
        }
        return instance;
    }

}