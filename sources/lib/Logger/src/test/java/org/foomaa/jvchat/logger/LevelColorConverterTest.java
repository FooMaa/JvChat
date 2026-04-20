package org.foomaa.jvchat.logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;

@ExtendWith(MockitoExtension.class)
class LevelColorConverterTest {
    private final LevelColorConverter converter = new LevelColorConverter();

    @Mock
    private ILoggingEvent event;

    private void mockLevel(Level level) {
        when(event.getLevel()).thenReturn(level);
    }

    @Test
    void shouldColorTraceLevelCyan() {
        mockLevel(Level.TRACE);

        String result = converter.convert(event);

        assertThat(result).contains("\u001B[36m").contains("TRACE").contains("\u001B[0m");
    }

    @Test
    void shouldColorDebugLevelBlue() {
        mockLevel(Level.DEBUG);

        String result = converter.convert(event);

        assertThat(result).contains("\u001B[34m").contains("DEBUG").contains("\u001B[0m");
    }

    @Test
    void shouldColorInfoLevelGreen() {
        mockLevel(Level.INFO);

        String result = converter.convert(event);

        assertThat(result).contains("\u001B[32m").contains("INFO ").contains("\u001B[0m");
    }

    @Test
    void shouldColorWarnLevelYellow() {
        mockLevel(Level.WARN);

        String result = converter.convert(event);

        assertThat(result).contains("\u001B[33m").contains("WARN ").contains("\u001B[0m");
    }

    @Test
    void shouldColorErrorLevelRed() {
        mockLevel(Level.ERROR);

        String result = converter.convert(event);

        assertThat(result).contains("\u001B[31m").contains("ERROR").contains("\u001B[0m");
    }

    @Test
    void shouldReturnPlainLevelForUnknown() {
        mockLevel(Level.ALL);

        String result = converter.convert(event);

        assertThat(result).isEqualTo("ALL  ");
    }

    @Test
    void shouldPadLevelToFiveCharacters() {
        mockLevel(Level.INFO);

        String result = converter.convert(event);

        assertThat(result).contains("INFO "); // важно: с пробелом
    }
}
