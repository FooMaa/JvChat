package org.foomaa.jvchat.globaldefines;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class JvFontsGlobalDefines {
    JvFontsGlobalDefines() {}

    public Font createMainMMFont(float size) throws IOException, FontFormatException {
        InputStream inputStream = getClass().getResourceAsStream("/MainMMFont.otf");

        if (inputStream == null) {
            return null;
        }

        Font customFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
        inputStream.close();
        customFont = customFont.deriveFont(Font.BOLD, size);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(customFont);

        return customFont;
    }


}
