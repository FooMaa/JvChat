package org.foomaa.jvchat.settings;

import java.awt.*;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Profile("users")
@Slf4j
public class DisplaySettings {
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
