package org.foomaa.jvchat.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Log {
    public enum TypeLog {
        Debug,
        Info,
        Warn,
        Error,
        Trace,
    }

    private static final StackWalker WALKER =
            StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    private static Logger getCallerLogger() {
        Class<?> caller = WALKER.walk(frames ->
                frames
                        .filter(f -> !f.getClassName().equals(Log.class.getName()))
                        .findFirst()
                        .map(StackWalker.StackFrame::getDeclaringClass)
                        .orElse(Log.class)
        );
        return LoggerFactory.getLogger(caller);
    }

    public static void debug(String text) {
        getCallerLogger().debug(text);
    }

    public static void info(String text) {
        getCallerLogger().info(text);
    }

    public static void warn(String text) {
        getCallerLogger().warn(text);
    }

    public static void error(String text) {
        getCallerLogger().error(text);
    }

    public static void write(Log.TypeLog type, String text) {
    }
}
