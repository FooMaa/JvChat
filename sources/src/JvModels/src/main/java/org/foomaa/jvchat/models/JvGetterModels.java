package org.foomaa.jvchat.models;

import org.foomaa.jvchat.logger.JvLog;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.Arrays;


public class JvGetterModels {
    private static JvGetterModels instance;
    private final AnnotationConfigApplicationContext context;

    private JvGetterModels() {
        context = new AnnotationConfigApplicationContext(
                JvModelsSpringConfig.class);
        JvLog.write(JvLog.TypeLog.Info, Arrays.toString(context.getEnvironment().getActiveProfiles()));
    }

    public static JvGetterModels getInstance() {
        if (instance == null) {
            instance = new JvGetterModels();
        }
        return instance;
    }

}