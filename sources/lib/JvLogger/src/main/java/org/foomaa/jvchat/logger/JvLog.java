package org.foomaa.jvchat.logger;

import java.io.File;

// точка доступа к логированию
public class JvLog {
    private static JvLog instance;
    private static JvMainLogger mainLogger;

    public enum TypeLog {
        Debug,
        Info,
        Warn,
        Error,
        Trace
    }

    private JvLog() {
        mainLogger = JvGetterLogger.getInstance().getBeanLogger();
    }

    public static JvLog getInstance() {
        if (instance == null) {
            instance = new JvLog();
        }
        return instance;
    }

    public static JvLog write(TypeLog type, String text) {
        if (instance == null) {
            instance = new JvLog();
        }

        // chatGPT
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String fullFileName = "";
        int fileLine = 0;

        if (stackTrace.length >= 3) { // stackTrace[0] - getStackTrace, stackTrace[1] - getCallerClassPath, stackTrace[2] - caller method
            String fileName = stackTrace[2].getFileName();
            if (fileName != null) {
                String fileLogging = new File(fileName).getAbsolutePath();
                fileLine = stackTrace[2].getLineNumber();
                fullFileName = String.format("%s:%d", fileLogging, fileLine);
            } else {
                fullFileName = "Unknown file";
            }
        } else {
            fullFileName = "Unknown file";
        }

        JvLoggerSpringConfig.setContextPropertyFileName(fullFileName);
        JvLoggerSpringConfig.setContextPropertyColor(type);
        writingText(type, text);
        return instance;
    }

    private static void writingText(TypeLog type, String text) {
        switch (type) {
            case Debug -> mainLogger.Debug(text);
            case Info -> mainLogger.Info(text);
            case Warn -> mainLogger.Warn(text);
            case Error -> mainLogger.Error(text);
            case Trace -> mainLogger.Trace(text);
        }
    }


}
