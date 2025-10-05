package org.foomaa.jvchat.uicomponents.mainchat;

import org.foomaa.jvchat.globaldefines.GetterGlobalDefines;
import org.foomaa.jvchat.logger.Log;
import org.foomaa.jvchat.settings.JvGetterSettings;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;

public class JvToolTipMainChatUI extends JToolTip {
    JvToolTipMainChatUI() {
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
            int size = JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFont(0.008);
            Font steticaFont = GetterGlobalDefines.getInstance().getBeanFontsGlobalDefines()
                    .createMainSteticaFont(Font.PLAIN, size);
            setFont(steticaFont);
        } catch (IOException | FontFormatException exception) {
            Log.write(Log.TypeLog.Error, "steticaFont not created here.");
        }
    }
}
