package org.foomaa.jvchat.settings;

import java.awt.*;

import org.foomaa.jvchat.logger.JvLog;


public class JvDisplaySettings {
    private static JvDisplaySettings instance;
    public int heightScreen;
    public int widthScreen;

    public enum TypeOfDisplayBorder {
        HEIGHT,
        WIDTH
    }

    private JvDisplaySettings() {
        try {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            heightScreen = screenSize.height;
            widthScreen = screenSize.width;
        } catch (Throwable exception) {
            JvLog.write(JvLog.TypeLog.Error, "No X :0:0 find");
        }
    }

    public static JvDisplaySettings getInstance() {
        if (instance == null) {
            instance = new JvDisplaySettings();
        }
        return instance;
    }

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