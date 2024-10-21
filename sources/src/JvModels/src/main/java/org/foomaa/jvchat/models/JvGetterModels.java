package org.foomaa.jvchat.models;

import org.foomaa.jvchat.structobjects.JvMessageStructObject;
import org.foomaa.jvchat.structobjects.JvStructObjectsSpringConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class JvGetterModels {
    private static JvGetterModels instance;
    private final AnnotationConfigApplicationContext context;

    private JvGetterModels() {
        context = new AnnotationConfigApplicationContext(JvModelsSpringConfig.class);
    }

    public static JvGetterModels getInstance() {
        if (instance == null) {
            instance = new JvGetterModels();
        }
        return instance;
    }

    public JvRootObjectsModel getBeanRootObjectsModel() {
        return context.getBean(JvModelsSpringConfig.NameBeans.BeanRootObjectsModel.getValue(),
                JvRootObjectsModel.class);
    }
}