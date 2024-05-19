package org.foomaa.jvchat.settings;

import java.awt.*;

public class JvDisplaySettings {
    JvDisplaySettings() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        heightScreen = screenSize.height;
        widthScreen = screenSize.width;
    }

    public enum TypeOfDisplayBorder {
        HEIGHT,
        WIDTH
    }

    private final Dimension screenSize;
    public int heightScreen;
    public int widthScreen;

    public int getResizeFromDisplay(double scale, TypeOfDisplayBorder displayBorder) {
        return switch (displayBorder) {
            case HEIGHT -> (int) Math.round(scale * heightScreen);
            case WIDTH -> (int) Math.round(scale * widthScreen);
        };
    }

    public int getResizePixel(double scale) {
        return (int) Math.round(scale * heightScreen);
    }
    public int getResizeFont(double scale) {
        return (int) Math.floor(scale * widthScreen);
    }
}