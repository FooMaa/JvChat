package org.foomaa.jvchat.settings;

import java.awt.*;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DisplaySettings {
    @Builder
    DisplaySettings() {
        try {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            heightScreen = screenSize.height;
            widthScreen = screenSize.width;
        } catch (Throwable exception) {
            log.error("Failed to get display size.");
        }
    }

    public int heightScreen;
    public int widthScreen;

    public enum TypeOfDisplayBorder {
        HEIGHT, WIDTH
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
