package org.foomaa.jvchat.globaldefines;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

import lombok.Builder;

public class FontsGlobalDefines {
    @Builder
    FontsGlobalDefines() {}

    public Font createMainSteticaFont(int style, float size) throws IOException, FontFormatException {
        InputStream inputStream = getClass().getResourceAsStream("/MainSteticaFont.otf");

        if (inputStream == null) {
            throw new IllegalStateException("Font resource not found");
        }

        Font customFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
        inputStream.close();
        style = (style & -4) == 0 ? style : 0;
        customFont = customFont.deriveFont(style, size);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(customFont);

        return customFont;
    }

    public Font createMainMMColumnFont(int style, float size) throws IOException, FontFormatException {
        InputStream inputStream = getClass().getResourceAsStream("/MainMMColumnFont.otf");

        if (inputStream == null) {
            throw new IllegalStateException("Font resource not found");
        }

        Font customFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
        inputStream.close();
        style = (style & -4) == 0 ? style : 0;
        customFont = customFont.deriveFont(style, size);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(customFont);

        return customFont;
    }

    public Font createMainMavobleFont(int style, float size) throws IOException, FontFormatException {
        InputStream inputStream = getClass().getResourceAsStream("/MainMavobleFont.otf");

        if (inputStream == null) {
            throw new IllegalStateException("Font resource not found");
        }

        Font customFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
        inputStream.close();
        style = (style & -4) == 0 ? style : 0;
        customFont = customFont.deriveFont(style, size);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(customFont);

        return customFont;
    }
}
