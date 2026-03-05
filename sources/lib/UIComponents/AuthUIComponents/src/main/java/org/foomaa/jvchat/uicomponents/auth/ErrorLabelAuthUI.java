package org.foomaa.jvchat.uicomponents.auth;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.globaldefines.FontsGlobalDefines;
import org.foomaa.jvchat.settings.DisplaySettings;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
@Profile("users")
@Slf4j
public class ErrorLabelAuthUI extends JLabel {
    private final Timer timerVisible;
    private final DisplaySettings displaySettings;
    private final FontsGlobalDefines fontsGlobalDefines;

    ErrorLabelAuthUI(DisplaySettings displaySettings,
                     FontsGlobalDefines fontsGlobalDefines,
                     String text) {
        this.displaySettings = displaySettings;
        this.fontsGlobalDefines = fontsGlobalDefines;

        timerVisible = new Timer(5000, actionEvent -> setText(""));
        timerVisible.setRepeats(false);

        setText(text);
        setFont(new Font("Times", Font.PLAIN, displaySettings.getResizePixel(0.017)));
    }

    public void settingToError() {
        Dimension dim = new Dimension(displaySettings.getResizeFromDisplay(0.23,
                DisplaySettings.TypeOfDisplayBorder.WIDTH), displaySettings.getResizeFromDisplay(0.03,
                DisplaySettings.TypeOfDisplayBorder.HEIGHT));
        setFont();
        setForeground(Color.RED);
        setPreferredSize(dim);
        setHorizontalAlignment(SwingConstants.CENTER);
        addErrorListener();
    }

    private void setFont() {
        try {
            int size = displaySettings.getResizeFont(0.0064);
            Font steticaFont = fontsGlobalDefines.createMainSteticaFont(Font.BOLD, size);
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