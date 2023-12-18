package org.foomaa.jvchat.uicomponents.auth;

import org.foomaa.jvchat.syssettings.JvDisplaySettings;

import javax.swing.*;
import java.awt.*;

public class JvAuthLabel extends JLabel {
    public JvAuthLabel(String text) {
        setText(text);
        setFont(new Font("Times", Font.PLAIN, JvDisplaySettings.getResizePixel(0.017)));
    }

    public void settingToError() {
        Dimension dim = new Dimension(JvDisplaySettings.
                getResizeFromDisplay(0.23,
                        JvDisplaySettings.TypeOfDisplayBorder.WIDTH),
                JvDisplaySettings.getResizeFromDisplay(0.03,
                        JvDisplaySettings.TypeOfDisplayBorder.HEIGHT));
        setFont(new Font("Times", Font.BOLD, JvDisplaySettings.getResizePixel(0.0099)));
        setForeground(Color.RED);
        setPreferredSize(dim);
        setHorizontalAlignment(SwingConstants.CENTER);
    }
}
