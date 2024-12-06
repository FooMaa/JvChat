package org.foomaa.jvchat.logger;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.foomaa.jvchat.globaldefines.JvGetterGlobalDefines;


public class JvLog {
    JvLog() {}

    private static JvMainLogger mainLogger;

    public enum TypeLog {
        Debug,
        Info,
        Warn,
        Error,
        Trace

    }

    public static void write(TypeLog type, String text) {
        mainLogger = JvGetterLogger.getInstance().getBeanLogger();

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String resultFile;

        if (stackTrace.length >= 3) {
            resultFile = buildStringFileLog(stackTrace);
        } else {
            resultFile = "Unknown file";
        }

        JvLoggerSpringConfig.setContextPropertyFileName(resultFile);
        JvLoggerSpringConfig.setContextPropertyColor(type);
        writingText(type, text);
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

    private static String buildStringFileLog(StackTraceElement[] stackTrace) {
        // stackTrace[0] - getStackTrace, stackTrace[1] - getCallerClassPath, stackTrace[2] - caller method
        String fileName = stackTrace[2].getFileName();
        if (fileName != null) {
            Path projectDirectory = getProjectDirectory();
            if (projectDirectory == null) {
                return  "Unknown file";
            }
            String fullFileName = findFileInDirByName(projectDirectory, fileName);
            if (!Objects.equals(fullFileName, "")) {
                return String.format("%s:%d", fullFileName, stackTrace[2].getLineNumber());
            } else {
                return  "Unknown file";
            }
        }
        return  "Unknown file";
    }

    private static String findFileInDirByName(Path directory, String fileName) {
        if (Files.isDirectory(directory)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
                for (Path entry : stream) {
                    if (Files.isDirectory(entry)) {
                        String result = findFileInDirByName(entry, fileName);
                        if (!Objects.equals(result, "")) {
                            return result;
                        }
                    } else if (entry.getFileName().toString().equals(fileName)) {
                        return entry.toString();
                    }
                }
            } catch (IOException exception) {
                return "";
            }
        }
        return "";
    }

    private static Path getProjectDirectory() {
        String nameProject = JvGetterGlobalDefines.getInstance().getBeanMainGlobalDefines().NAME_PROJECT;
        String pathString = System.getProperty("user.dir");

        Pattern patternPathForUnix = Pattern.compile("^(.*[\\\\/]" + nameProject + ")(?:[\\\\/].*)?$");
        Matcher matcherUnix = patternPathForUnix.matcher(pathString);

        if (matcherUnix.find()) {
            return Paths.get(matcherUnix.group(1));
        }

        return null;
    }
}