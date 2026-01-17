package org.foomaa.jvchat.uicomponents.auth;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.globaldefines.GetterGlobalDefines;
import org.foomaa.jvchat.settings.GetterSettings;


@Slf4j
public class ActiveLabelAuthUI extends JLabel {
    private ToolTipAuthUI toolTip;

    ActiveLabelAuthUI(String text) {
        setText(text);
        setFont(false);
        setForeground(Color.WHITE);
        addCustomListenerToElem();
    }

    public void setToolTip(String text) {
        toolTip = GetterAuthUIComponents.getInstance().getBeanToolTipAuthUI();
        createToolTip();
        setToolTipText(text);
    }

    @Override
    public JToolTip createToolTip() {
        return toolTip;
    }

    private void setFont(boolean isEnteredMouse) {
        try {
            int size = GetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.011);
            Font steticaFont = GetterGlobalDefines.getInstance().getBeanFontsGlobalDefines()
                    .createMainSteticaFont(isEnteredMouse ? Font.BOLD : Font.PLAIN, size);
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