package org.foomaa.jvchat.uicomponents.auth;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.util.Objects;

import javax.swing.*;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.globaldefines.FontsGlobalDefines;
import org.foomaa.jvchat.settings.DisplaySettings;

@Slf4j
public class ButtonAuthUI extends JButton {
    private ToolTipAuthUI toolTip;
    private final DisplaySettings displaySettings;
    private final FontsGlobalDefines fontsGlobalDefines;
    private final ToolTipAuthUIFactory toolTipAuthUIFactory;

    @Builder
    ButtonAuthUI(DisplaySettings displaySettings, FontsGlobalDefines fontsGlobalDefines,
            ToolTipAuthUIFactory toolTipAuthUIFactory) {
        this.displaySettings = Objects.requireNonNull(displaySettings, "displaySettings is mandatory");
        this.fontsGlobalDefines = Objects.requireNonNull(fontsGlobalDefines, "fontsGlobalDefines is mandatory");
        this.toolTipAuthUIFactory = Objects.requireNonNull(toolTipAuthUIFactory, "toolTipAuthUIFactory is mandatory");

        setBackground(Color.WHITE);
        setFocusable(false);
        addListenerToElements();
        setFont();
    }

    public void setToolTip(String text) {
        toolTip = toolTipAuthUIFactory.create();
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
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
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
