package org.foomaa.jvchat.logger;

import ch.qos.logback.classic.LoggerContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


class GetterLogger {
    private static GetterLogger instance;
    private final AnnotationConfigApplicationContext context;

    private GetterLogger() {
        context = new AnnotationConfigApplicationContext(
                LoggerSpringConfig.class);
        getBeanConfigureLogback();
    }

    static GetterLogger getInstance() {
        if (instance == null) {
            instance = new GetterLogger();
        }
        return instance;
    }

    private void getBeanConfigureLogback() {
        context.getBean(LoggerSpringConfig.NameBeans.BeanConfigureLogback.getValue(), LoggerContext.class);
    }

    MainLogger getBeanLogger() {
        return context.getBean(LoggerSpringConfig.NameBeans.BeanMainLogger.getValue(), MainLogger.class);
    }
}