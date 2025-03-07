package org.foomaa.jvchat.uicomponents.auth;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import org.foomaa.jvchat.globaldefines.JvGetterGlobalDefines;
import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.settings.JvDisplaySettings;
import org.foomaa.jvchat.settings.JvGetterSettings;


public class JvErrorLabelAuthUI extends JLabel {
    JvErrorLabelAuthUI(String text) {
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
        setFont();
        setForeground(Color.RED);
        setPreferredSize(dim);
        setHorizontalAlignment(SwingConstants.CENTER);
        addErrorListener();
    }

    private void setFont() {
        try {
            int size = JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFont(0.0064);
            Font steticaFont = JvGetterGlobalDefines.getInstance().getBeanFontsGlobalDefines()
                    .createMainSteticaFont(Font.BOLD, size);
            setFont(steticaFont);
        } catch (IOException | FontFormatException exception) {
            JvLog.write(JvLog.TypeLog.Error, "SteticaFont not created here");
        }
    }

    private void addErrorListener() {
        addPropertyChangeListener("text",propertyChangeEvent -> {
            if (!Objects.equals(getText(), "")) {
                Timer timer = new Timer(5000, actionEvent -> setText(""));
                timer.start();
            }
        });
    }

    @Override
    public void setText(String text){
       super.setText("<html><center>" + text + "</center></html>");
    }
}