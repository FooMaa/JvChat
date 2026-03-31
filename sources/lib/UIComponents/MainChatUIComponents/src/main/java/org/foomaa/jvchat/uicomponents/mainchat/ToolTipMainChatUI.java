package org.foomaa.jvchat.uicomponents.mainchat;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import javax.swing.*;
import javax.swing.border.Border;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.globaldefines.FontsGlobalDefines;
import org.foomaa.jvchat.settings.DisplaySettings;

@Slf4j
public class ToolTipMainChatUI extends JToolTip {
    private final DisplaySettings displaySettings;
    private final FontsGlobalDefines fontsGlobalDefines;

    @Builder
    ToolTipMainChatUI(DisplaySettings displaySettings, FontsGlobalDefines fontsGlobalDefines) {
        this.displaySettings = Objects.requireNonNull(displaySettings, "displaySettings is mandatory");
        this.fontsGlobalDefines = Objects.requireNonNull(fontsGlobalDefines, "fontsGlobalDefines is mandatory");

        setGeneralSettings();
    }

    private void setGeneralSettings() {
        Border bottomBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY);
        setBorder(bottomBorder);
        setBackground(Color.BLACK);
        setForeground(Color.LIGHT_GRAY);
        setFont();
    }

    private void setFont() {
        try {
            int size = displaySettings.getResizeFont(0.008);
            Font steticaFont = fontsGlobalDefines.createMainSteticaFont(Font.PLAIN, size);
            setFont(steticaFont);
        } catch (IOException | FontFormatException exception) {
            log.error("steticaFont not created here.");
        }
    }
}
