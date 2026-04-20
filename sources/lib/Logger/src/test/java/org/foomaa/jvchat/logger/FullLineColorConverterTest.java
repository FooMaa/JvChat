package org.foomaa.jvchat.logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.TimeZone;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;

@Deprecated
@ExtendWith(MockitoExtension.class)
class FullLineColorConverterTest {

    private final FullLineColorConverter converter = new FullLineColorConverter();

    @Mock
    private ILoggingEvent event;

    @BeforeEach
    void setupTimezone() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC")); // стабильность тестов
    }

    private void mockEvent(Level level, long timestamp, String message, StackTraceElement[] caller) {
        when(event.getLevel()).thenReturn(level);
        when(event.getTimeStamp()).thenReturn(timestamp);
        when(event.getFormattedMessage()).thenReturn(message);
        when(event.getCallerData()).thenReturn(caller);
    }

    @Test
    void shouldFormatFullLineCorrectly() {
        mockEvent(Level.INFO, 0L, "hello", new StackTraceElement[] {
            new StackTraceElement("MyClass", "method", "MyClass.java", 42)
        });

        String result = converter.convert(event);

        assertThat(result)
                .contains("01.01.1970")
                .contains("INFO ")
                .contains("MyClass:42")
                .contains("hello");
    }

    @Test
    void shouldUseUnknownLocationWhenCallerDataNull() {
        mockEvent(Level.INFO, 0L, "msg", null);

        String result = converter.convert(event);

        assertThat(result).contains("unknown:0");
    }

    @Test
    void shouldUseUnknownLocationWhenCallerEmpty() {
        mockEvent(Level.INFO, 0L, "msg", new StackTraceElement[0]);

        String result = converter.convert(event);

        assertThat(result).contains("unknown:0");
    }

    @Test
    void shouldApplyGreenColorForInfo() {
        mockEvent(Level.INFO, 0L, "msg", null);

        String result = converter.convert(event);

        assertThat(result).startsWith("\u001B[32m").endsWith("\u001B[0m");
    }

    @Test
    void shouldApplyRedColorForError() {
        mockEvent(Level.ERROR, 0L, "msg", null);

        String result = converter.convert(event);

        assertThat(result).startsWith("\u001B[31m").endsWith("\u001B[0m");
    }

    @Test
    void shouldReturnPlainLineForUnknownLevel() {
        mockEvent(Level.ALL, 0L, "msg", null);

        String result = converter.convert(event);

        assertThat(result).doesNotStartWith("\u001B").contains("ALL  ");
    }

    @Test
    void shouldPadLevelToFiveCharacters() {
        mockEvent(Level.INFO, 0L, "msg", null);

        String result = converter.convert(event);

        assertThat(result).contains("INFO ");
    }
}
