package org.foomaa.jvchat.globaldefines;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class JvFontsGlobalDefines {
    JvFontsGlobalDefines() {}

    public Font createMainSteticaFont(int style, float size) throws IOException, FontFormatException {
        InputStream inputStream = getClass().getResourceAsStream("/MainSteticaFont.otf");

        if (inputStream == null) {
            return null;
        }

        Font customFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
        inputStream.close();
        style = (style & -4) == 0 ? style : 0;
        customFont = customFont.deriveFont(style, size);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(customFont);

        return customFont;
    }

    public Font createMainMMFont(int style, float size) throws IOException, FontFormatException {
        InputStream inputStream = getClass().getResourceAsStream("/MainMMFont.otf");

        if (inputStream == null) {
            return null;
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
            return null;
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
