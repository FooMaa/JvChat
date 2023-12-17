package org.foomaa.jvchat.uicomponents.auth;

import org.foomaa.jvchat.syssettings.JvDisplaySettings;

import javax.swing.*;
import java.awt.*;

public class JvAuthLabel extends JLabel {
    public JvAuthLabel(String text) {
        setText(text);
        int size = JvDisplaySettings.getResizeFromDisplay(0.017,
                        JvDisplaySettings.TypeOfDisplayBorder.HEIGHT);
        setFont(new Font("Times", Font.PLAIN, size));
    }

    public void settingToError() {
        Dimension dim = new Dimension(JvDisplaySettings.
                getResizeFromDisplay(0.23,
                        JvDisplaySettings.TypeOfDisplayBorder.WIDTH),
                JvDisplaySettings.getResizeFromDisplay(0.03,
                        JvDisplaySettings.TypeOfDisplayBorder.HEIGHT));
        int size = JvDisplaySettings.getResizeFromDisplay(0.01,
                JvDisplaySettings.TypeOfDisplayBorder.HEIGHT);

        setFont(new Font("Times", Font.BOLD, size));
        setForeground(Color.RED);
        setPreferredSize(dim);
        setHorizontalAlignment(SwingConstants.CENTER);
    }
}
