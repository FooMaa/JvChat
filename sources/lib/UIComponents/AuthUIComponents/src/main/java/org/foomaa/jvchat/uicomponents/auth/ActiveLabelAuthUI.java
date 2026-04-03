package org.foomaa.jvchat.uicomponents.auth;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.swing.*;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.globaldefines.FontsGlobalDefines;
import org.foomaa.jvchat.settings.DisplaySettings;

@Slf4j
public class ActiveLabelAuthUI extends JLabel {
    // DI ↓
    private final DisplaySettings displaySettings;
    private final FontsGlobalDefines fontsGlobalDefines;
    private final ToolTipAuthUIFactory toolTipAuthUIFactory;

    // DI(P) ↓
    private ToolTipAuthUI toolTip;

    @Builder
    ActiveLabelAuthUI(
            DisplaySettings displaySettings,
            FontsGlobalDefines fontsGlobalDefines,
            ToolTipAuthUIFactory toolTipAuthUIFactory) {
        this.displaySettings = Objects.requireNonNull(displaySettings, "displaySettings is mandatory");
        this.fontsGlobalDefines = Objects.requireNonNull(fontsGlobalDefines, "fontsGlobalDefines is mandatory");
        this.toolTipAuthUIFactory = Objects.requireNonNull(toolTipAuthUIFactory, "toolTipAuthUIFactory is mandatory");

        setFont(false);
        setForeground(Color.WHITE);
        addCustomListenerToElem();
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

    private void setFont(boolean isEnteredMouse) {
        try {
            int size = displaySettings.getResizePixel(0.011);
            Font steticaFont = fontsGlobalDefines.createMainSteticaFont(isEnteredMouse ? Font.BOLD : Font.PLAIN, size);
            Map<TextAttribute, Object> attributes = new HashMap<>(steticaFont.getAttributes());
            attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_DOTTED);
            setFont(steticaFont.deriveFont(attributes));
        } catch (IOException | FontFormatException exception) {
            log.error("steticaFont not created here.");
        }
    }

    private void addCustomListenerToElem() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setFont(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setFont(false);
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setFont(false);
            }
        });
    }
}
