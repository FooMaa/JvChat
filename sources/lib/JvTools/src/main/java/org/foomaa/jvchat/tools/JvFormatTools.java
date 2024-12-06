package org.foomaa.jvchat.tools;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.foomaa.jvchat.globaldefines.JvMainChatsGlobalDefines;
import org.foomaa.jvchat.logger.JvLog;


public class JvFormatTools {
    JvFormatTools() {}

    private String normalizeMillisecond(String timestamp, int normalizeCount) {
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
            JvLog.write(JvLog.TypeLog.Warn, "Не получилось нормально преобразовать дату и время к нужному" +
                    " формату с миллисекундами, попробуем с regex");

            // Регулярное выражение для формата 'yyyy-MM-dd HH:mm:ss'
            String patternStr = "^(?<year>\\d{4})-(?<month>0[1-9]|1[012])-(?<day>0[1-9]|[12][0-9]|3[01])" +
                    "[T ](?<hour>[01][0-9]|2[0-3]):(?<minute>[0-5][0-9]):(?<second>[0-5][0-9])$";

            Pattern pattern= Pattern.compile(patternStr);
            Matcher m = pattern.matcher(timestamp);

            if (m.matches()) {
                String zeroMs = "0";
                String addingMs = zeroMs.repeat(normalizeCount);
                resultTimestamp = timestamp + "." + addingMs;
            } else {
                JvLog.write(JvLog.TypeLog.Error, "Не получилось нормально преобразовать дату и время к нужному формату");
                return null;
            }
        }

        return resultTimestamp;
    }

    public String localDateTimeToString(LocalDateTime timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return timestamp.format(formatter);
    }

    public LocalDateTime stringToLocalDateTime(String timestampStr, int normalizeCount) {
        if (timestampStr == null || Objects.equals(timestampStr, "")) {
            JvLog.write(JvLog.TypeLog.Error, "Ошибка при попытке парсинга времени последнего онлайна");
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        String timestampString = JvGetterTools.getInstance().getBeanFormatTools()
                .normalizeMillisecond(timestampStr, normalizeCount);

        if (timestampString == null) {
            JvLog.write(JvLog.TypeLog.Error, "Не получилось нормализовать дату и время к нужному формату");
            return null;
        }

        return LocalDateTime.parse(timestampString, formatter);
    }

    public JvMainChatsGlobalDefines.TypeStatusMessage statusMessageStringToInt(String statusMessageStr) {
        int statusMessageInt;
        try{
            statusMessageInt = Integer.parseInt(statusMessageStr);
        }
        catch (NumberFormatException ex){
            JvLog.write(JvLog.TypeLog.Error, "Тут статус не преобразовался в int");
            return JvMainChatsGlobalDefines.TypeStatusMessage.Error;
        }

        return JvMainChatsGlobalDefines.TypeStatusMessage.getTypeStatusMessage(statusMessageInt);
    }
}
