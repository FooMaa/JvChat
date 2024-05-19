package org.foomaa.jvchat.uicomponents.auth;

import org.foomaa.jvchat.settings.JvDisplaySettings;
import org.foomaa.jvchat.settings.JvGetterSettings;

import javax.swing.*;
import java.awt.*;

public class JvAuthLabel extends JLabel {
    public JvAuthLabel(String text) {
        setText(text);
        setFont(new Font("Times", Font.PLAIN,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.017)));
    }

    public void settingToError() {
        Dimension dim = new Dimension(JvGetterSettings.getInstance().getBeanDisplaySettings().
                getResizeFromDisplay(0.23,
                        JvDisplaySettings.TypeOfDisplayBorder.WIDTH),
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.03,
                        JvDisplaySettings.TypeOfDisplayBorder.HEIGHT));
        setFont(new Font("Times", Font.BOLD,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFont(0.006)));
        setForeground(Color.RED);
        setPreferredSize(dim);
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    @Override
    public void setText(String text){
       super.setText("<html><center>" + text + "</center></html>");
    }
}