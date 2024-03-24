package org.foomaa.jvchat.uicomponents.auth;

import org.foomaa.jvchat.settings.JvDisplaySettings;

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
        setFont(new Font("Times", Font.BOLD, JvDisplaySettings.getResizeFont(0.006)));
        setForeground(Color.RED);
        setPreferredSize(dim);
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    @Override
    public void setText(String text){
       super.setText("<html><center>" + text + "</center></html>");
    }
}