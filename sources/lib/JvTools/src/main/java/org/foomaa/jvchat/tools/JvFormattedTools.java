package org.foomaa.jvchat.tools;

import org.foomaa.jvchat.logger.JvLog;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JvFormattedTools {
    private static JvFormattedTools instance;

    private JvFormattedTools() {}

    static JvFormattedTools getInstance() {
        if (instance == null) {
            instance = new JvFormattedTools();
        }
        return instance;
    }

    public String normalizeMillisecond(String timestamp, int normalizeCount) {
        String resultTimestamp;
        String[] parts = timestamp.split("\\.");

        if (parts.length == 2) {
            StringBuilder milliseconds = new StringBuilder(new StringBuilder(parts[1]));

            if (milliseconds.length() < normalizeCount) {
                while (milliseconds.length() < normalizeCount) {
                    milliseconds.append("0");
                }
            } else if (milliseconds.length() > normalizeCount) {
                milliseconds = new StringBuilder(milliseconds.substring(0, normalizeCount));
            }

            resultTimestamp = parts[0] + "." + milliseconds;
        } else {
            JvLog.write(JvLog.TypeLog.Error, "Не получилось нормально преобразовать дату и время к нужному формату");
            return null;
        }

        return resultTimestamp;
    }

    public String formattedTimestampToDB(LocalDateTime timestamp) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return timestamp.format(formatter);
    }

    public String formattedTimestampToStruct(LocalDateTime timestamp) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        return timestamp.format(formatter);
    }
}
