package org.foomaa.jvchat.globaldefines;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.*;

import org.junit.jupiter.api.Test;

class FontsGlobalDefinesTest {
    private final FontsGlobalDefines fonts = new FontsGlobalDefines();

    @Test
    void shouldCreateMainSteticaFont() throws Exception {
        Font font = fonts.createMainSteticaFont(Font.PLAIN, 14);

        assertThat(font).isNotNull();
        assertThat(font.getSize()).isEqualTo(14);
    }

    @Test
    void shouldCreateMainMMColumnFont() throws Exception {
        Font font = fonts.createMainMMColumnFont(Font.BOLD, 16);

        assertThat(font).isNotNull();
        assertThat(font.getSize()).isEqualTo(16);
    }

    @Test
    void shouldCreateMainMavobleFont() throws Exception {
        Font font = fonts.createMainMavobleFont(Font.ITALIC, 18);

        assertThat(font).isNotNull();
        assertThat(font.getSize()).isEqualTo(18);
    }

    @Test
    void shouldNormalizeInvalidStyleToZero() throws Exception {
        Font font = fonts.createMainSteticaFont(999, 12);

        assertThat(font).isNotNull();
        assertThat(font.getStyle()).isEqualTo(0); // ключевая проверка
    }

    @Test
    void shouldKeepValidPlainStyle() throws Exception {
        Font font = fonts.createMainSteticaFont(Font.PLAIN, 12);

        assertThat(font).isNotNull();
        assertThat(font.getStyle()).isEqualTo(Font.PLAIN);
    }

    @Test
    void shouldKeepBoldStyleWhenValid() throws Exception {
        Font font = fonts.createMainMMColumnFont(Font.BOLD, 12);

        assertThat(font).isNotNull();
        assertThat(font.getStyle()).isEqualTo(Font.BOLD);
    }

    @Test
    void shouldAcceptZeroSize() throws Exception {
        Font font = fonts.createMainSteticaFont(Font.PLAIN, 0);

        assertThat(font).isNotNull();
        assertThat(font.getSize()).isEqualTo(0);
    }
}
