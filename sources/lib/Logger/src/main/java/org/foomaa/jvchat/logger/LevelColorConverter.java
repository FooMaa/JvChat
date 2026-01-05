package org.foomaa.jvchat.logger;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class LevelColorConverter extends ClassicConverter {
    @Override
    public String convert(ILoggingEvent event) {
        String level = String.format("%-5s", event.getLevel()); // например "INFO "
        return switch (event.getLevel().toString()) {
            case "TRACE" -> "\u001B[36m" + level + "\u001B[0m"; // cyan
            case "DEBUG" -> "\u001B[34m" + level + "\u001B[0m"; // blue
            case "INFO" -> "\u001B[32m" + level + "\u001B[0m";  // green
            case "WARN" -> "\u001B[33m" + level + "\u001B[0m";  // yellow
            case "ERROR" -> "\u001B[31m" + level + "\u001B[0m"; // red
            default -> level;
        };
    }
}
