package org.foomaa.jvchat.uicomponents.auth;

import lombok.extern.slf4j.Slf4j;
import org.foomaa.jvchat.globaldefines.FontsGlobalDefines;
import org.foomaa.jvchat.settings.DisplaySettings;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;


@Component
@Scope("prototype")
@Profile("users")
@Slf4j
public class ButtonAuthUI extends JButton {
    private ToolTipAuthUI toolTip;
    private final DisplaySettings displaySettings;
    private final FontsGlobalDefines fontsGlobalDefines;
    private final ObjectProvider<ToolTipAuthUI> toolTipObjectProvider;

    ButtonAuthUI(DisplaySettings displaySettings,
                 FontsGlobalDefines fontsGlobalDefines,
                 ObjectProvider<ToolTipAuthUI> toolTipObjectProvider,
                 String text) {
        this.displaySettings = displaySettings;
        this.fontsGlobalDefines = fontsGlobalDefines;
        this.toolTipObjectProvider = toolTipObjectProvider;

        setText(text);
        setBackground(Color.WHITE);
        setFocusable(false);
        addListenerToElements();
        setFont();
    }

    public void setToolTip(String text) {
        toolTip = toolTipObjectProvider.getObject();
        createToolTip();
        setToolTipText(text);
    }

    @Override
    public JToolTip createToolTip() {
        return toolTip;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(getBackground().darker());
        } else if (getModel().isRollover()) {
            g.setColor(getBackground().brighter());
        } else {
            g.setColor(getBackground());
        }
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 15, 15));
        g2.setColor(getForeground());
        super.paintComponent(g);
    }

    @Override
    public void setContentAreaFilled(boolean b) {
    }

    @Override
    public boolean isContentAreaFilled() {
        return false;
    }

    @Override
    public boolean isBorderPainted() {
        return false;
    }

    @Override
    public boolean isFocusPainted() {
        return false;
    }

    private void addListenerToElements() {
        addActionListener(event -> {
            setFocusable(true);
            requestFocusInWindow();
        });
    }

    private void setFont() {
        try {
            int size = displaySettings.getResizeFont(0.008);
            Font steticaFont = fontsGlobalDefines.createMainSteticaFont(Font.PLAIN, size);
            setFont(steticaFont);
        } catch (IOException | FontFormatException exception) {
            log.error("SteticaFont not created here.");
        }
    }
}