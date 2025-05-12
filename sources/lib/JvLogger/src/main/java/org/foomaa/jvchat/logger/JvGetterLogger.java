package org.foomaa.jvchat.logger;

import ch.qos.logback.classic.LoggerContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


class JvGetterLogger {
    private static JvGetterLogger instance;
    private final AnnotationConfigApplicationContext context;

    private JvGetterLogger() {
        context = new AnnotationConfigApplicationContext(
                JvLoggerSpringConfig.class);
        getBeanConfigureLogback();
    }

    static JvGetterLogger getInstance() {
        if (instance == null) {
            instance = new JvGetterLogger();
        }
        return instance;
    }

    private void getBeanConfigureLogback() {
        context.getBean(JvLoggerSpringConfig.NameBeans.BeanConfigureLogback.getValue(), LoggerContext.class);
    }

    JvMainLogger getBeanLogger() {
        return context.getBean(JvLoggerSpringConfig.NameBeans.BeanMainLogger.getValue(), JvMainLogger.class);
    }
}