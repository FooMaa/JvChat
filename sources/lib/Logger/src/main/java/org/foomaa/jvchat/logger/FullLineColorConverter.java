package org.foomaa.jvchat.logger;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


@Deprecated
public class FullLineColorConverter extends ClassicConverter {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss z");
    static {
        TIME_FORMAT.setTimeZone(TimeZone.getDefault());
    }

    @Override
    public String convert(ILoggingEvent event) {
        String date = DATE_FORMAT.format(new Date(event.getTimeStamp()));
        String time = TIME_FORMAT.format(new Date(event.getTimeStamp()));

        String level = String.format("%-5s", event.getLevel());
        String location = "unknown:0";

        if (event.getCallerData() != null && event.getCallerData().length > 0) {
            location = event.getCallerData()[0].getClassName() + ":" + event.getCallerData()[0].getLineNumber();
        }

        String msg = event.getFormattedMessage();
        String line = String.format("%s | %s | [%s] | %s | %s", date, time, level, location, msg);

        return switch (event.getLevel().toString()) {
            case "TRACE" -> "\u001B[36m" + line + "\u001B[0m"; // cyan
            case "DEBUG" -> "\u001B[34m" + line + "\u001B[0m"; // blue
            case "INFO" -> "\u001B[32m" + line + "\u001B[0m"; // green
            case "WARN" -> "\u001B[33m" + line + "\u001B[0m"; // yellow
            case "ERROR" -> "\u001B[31m" + line + "\u001B[0m"; // red
            default -> line;
        };
    }
}
