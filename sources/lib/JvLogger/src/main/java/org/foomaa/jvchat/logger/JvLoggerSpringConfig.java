package org.foomaa.jvchat.logger;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.util.StatusPrinter;
import org.foomaa.jvchat.globaldefines.JvGetterGlobalDefines;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.Lazy;


@Configuration
public class JvLoggerSpringConfig {
    private static LoggerContext context;

    public enum NameBeans {
        BeanConfigureLogback("beanConfigureLogback"),
        BeanLog("beanLog"),
        BeanMainLogger("beanMainLogger");

        private final String value;

        NameBeans(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    // это генерировал ChatGPT
    @Bean(name = "beanConfigureLogback")
    @Scope("singleton")
    public LoggerContext beanConfigureLogback() {
        context = (LoggerContext) LoggerFactory.getILoggerFactory();

        // Создание Pattern Layout
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        encoder.setPattern("%property{colorCodeStart}%d{yyyy-MM-dd HH:mm:ss} %property{fullFileName} [ %-4level ] - %msg%property{colorCodeEnd}%n");
        encoder.start();

        // Создание Console Appender
        ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();
        consoleAppender.setContext(context);
        consoleAppender.setEncoder(encoder);
        consoleAppender.start();

        // Настройка Root Logger
        Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.INFO);
        rootLogger.addAppender(consoleAppender);

        // Настройка логгера для пакета
        Logger exampleLogger = (Logger) LoggerFactory.getLogger("org.foomaa.jvchat.logger");
        exampleLogger.setLevel(Level.DEBUG);
        exampleLogger.setAdditive(false);
        exampleLogger.addAppender(consoleAppender);

        // Печать статуса конфигурации
        StatusPrinter.printInCaseOfErrorsOrWarnings(context);
        return context;
    }

    // нужно, чтоб динамически изменять имя файла
    public static void setContextPropertyFileName(String fullFileName) {
        String namePropertyFileName = "fullFileName";
        context.putProperty(namePropertyFileName, fullFileName);
    }

    public static void setContextPropertyColor(JvLog.TypeLog type) {
        String namePropertyColorStart = "colorCodeStart";
        String namePropertyColorEnd = "colorCodeEnd";

        switch (type) {
            case Debug -> context.putProperty(namePropertyColorStart,
                    JvGetterGlobalDefines.getInstance().getBeanColorsAnsiGlobalDefines().BLUE);
            case Info, Trace -> context.putProperty(namePropertyColorStart,
                    JvGetterGlobalDefines.getInstance().getBeanColorsAnsiGlobalDefines().GREEN);
            case Warn -> context.putProperty(namePropertyColorStart,
                    JvGetterGlobalDefines.getInstance().getBeanColorsAnsiGlobalDefines().YELLOW);
            case Error -> context.putProperty(namePropertyColorStart,
                    JvGetterGlobalDefines.getInstance().getBeanColorsAnsiGlobalDefines().RED);
        }

        context.putProperty(namePropertyColorEnd,
                JvGetterGlobalDefines.getInstance().getBeanColorsAnsiGlobalDefines().RESET);
    }

    @Bean(name = "beanMainLogger")
    @Lazy
    @Scope("singleton")
    public JvMainLogger beanMainLogger() {
        return JvMainLogger.getInstance();
    }

    @Bean(name = "beanLog")
    @Lazy
    @Scope("singleton")
    public JvLog beanLog() {
        return JvLog.getInstance();
    }
}