package org.foomaa.jvchat.settings;

import java.awt.*;

public class JvDisplaySettings {
    public enum TypeOfDisplayBorder {
        HEIGHT,
        WIDTH
    }
    private static final Dimension screenSize;
    public static int heightScreen;

    public static int widthScreen;

    static {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        heightScreen = screenSize.height;
        widthScreen = screenSize.width;
    }

    public static int getResizeFromDisplay(double scale, TypeOfDisplayBorder displayBorder) {
        switch (displayBorder) {
            case HEIGHT:
                return (int) Math.round(scale * heightScreen);
            case WIDTH:
                return (int) Math.round(scale * widthScreen);
        }
        return 0;
    }

    public static int getResizePixel(double scale) {
        return (int) Math.round(scale * heightScreen);
    }
    public static int getResizeFont(double scale) {
        return (int) Math.floor(scale * widthScreen);
    }
}
