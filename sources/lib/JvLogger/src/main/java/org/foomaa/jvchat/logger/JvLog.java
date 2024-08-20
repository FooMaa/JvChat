package org.foomaa.jvchat.logger;

import org.foomaa.jvchat.globaldefines.JvGetterGlobalDefines;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

    public static void write(TypeLog type, String text) {
        getInstance();

        // chatGPT
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String resultFile;

        if (stackTrace.length >= 3) {
            resultFile = instance.buildStringFileLog(stackTrace);
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

    private String buildStringFileLog(StackTraceElement[] stackTrace) {
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

    private String findFileInDirByName(Path directory, String fileName) {
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

    private Path getProjectDirectory() {
        String nameProject = JvGetterGlobalDefines.getInstance().getBeanMainGlobalDefines().NAME_PROJECT;
        String path = System.getProperty("user.dir");

        Pattern pattern = Pattern.compile("^(.*/" + nameProject + ")(?:/.*)?$");
        Matcher matcher = pattern.matcher(path);

        if (matcher.find()) {
            return Paths.get(matcher.group(1));
        }

        return null;
    }
}