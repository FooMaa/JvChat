package org.foomaa.jvchat.uicomponents.auth;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.globaldefines.GetterGlobalDefines;
import org.foomaa.jvchat.settings.DisplaySettings;
import org.foomaa.jvchat.settings.GetterSettings;


@Slf4j
public class ErrorLabelAuthUI extends JLabel {
    private final Timer timerVisible;

    ErrorLabelAuthUI(String text) {
        timerVisible = new Timer(5000, actionEvent -> setText(""));
        timerVisible.setRepeats(false);

        setText(text);
        setFont(new Font("Times", Font.PLAIN,
                GetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.017)));
    }

    public void settingToError() {
        Dimension dim = new Dimension(GetterSettings.getInstance().getBeanDisplaySettings().
                getResizeFromDisplay(0.23,
                        DisplaySettings.TypeOfDisplayBorder.WIDTH),
                GetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.03,
                        DisplaySettings.TypeOfDisplayBorder.HEIGHT));
        setFont();
        setForeground(Color.RED);
        setPreferredSize(dim);
        setHorizontalAlignment(SwingConstants.CENTER);
        addErrorListener();
    }

    private void setFont() {
        try {
            int size = GetterSettings.getInstance().getBeanDisplaySettings().getResizeFont(0.0064);
            Font steticaFont = GetterGlobalDefines.getInstance().getBeanFontsGlobalDefines()
                    .createMainSteticaFont(Font.BOLD, size);
            setFont(steticaFont);
        } catch (IOException | FontFormatException exception) {
            log.error("SteticaFont not created here");
        }
    }

    private void addErrorListener() {
        addPropertyChangeListener("text",propertyChangeEvent -> {
            if (!Objects.equals(getText(), "")) {
                timerVisible.restart();
            }
        });
    }

    @Override
    public void setText(String text){
       super.setText("<html><center>" + text + "</center></html>");
    }
}