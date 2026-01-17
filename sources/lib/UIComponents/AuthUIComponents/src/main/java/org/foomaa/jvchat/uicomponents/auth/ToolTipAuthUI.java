package org.foomaa.jvchat.uicomponents.auth;

import lombok.extern.slf4j.Slf4j;
import org.foomaa.jvchat.globaldefines.GetterGlobalDefines;
import org.foomaa.jvchat.settings.GetterSettings;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;


@Slf4j
public class ToolTipAuthUI extends JToolTip {
    ToolTipAuthUI() {
        setGeneralSettings();
    }

    private void setGeneralSettings() {
        Border bottomBorder =
                BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY);
        setBorder(bottomBorder);
        setBackground(Color.BLACK);
        setForeground(Color.LIGHT_GRAY);
        setFont();
    }

    private void setFont() {
        try {
            int size = GetterSettings.getInstance().getBeanDisplaySettings().getResizeFont(0.008);
            Font steticaFont = GetterGlobalDefines.getInstance().getBeanFontsGlobalDefines()
                    .createMainSteticaFont(Font.PLAIN, size);
            setFont(steticaFont);
        } catch (IOException | FontFormatException exception) {
            log.error("steticaFont not created here.");
        }
    }
}
