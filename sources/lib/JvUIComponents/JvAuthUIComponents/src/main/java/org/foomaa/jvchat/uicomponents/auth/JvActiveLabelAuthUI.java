package org.foomaa.jvchat.uicomponents.auth;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.foomaa.jvchat.globaldefines.JvGetterGlobalDefines;
import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.settings.JvGetterSettings;


public class JvActiveLabelAuthUI extends JLabel {
    private JvToolTipAuthUI toolTip;

    JvActiveLabelAuthUI(String text) {
        setText(text);
        setFont(false);
        setForeground(Color.MAGENTA);
        addCustomListenerToElem();
    }

    public void setToolTip(String text) {
        toolTip = JvGetterAuthUIComponents.getInstance().getBeanToolTipAuthUI();
        createToolTip();
        setToolTipText(text);
    }

    @Override
    public JToolTip createToolTip() {
        return toolTip;
    }

    private void setFont(boolean isEnteredMouse) {
        try {
            int size = JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.011);
            Font steticaFont = JvGetterGlobalDefines.getInstance().getBeanFontsGlobalDefines()
                    .createMainSteticaFont(isEnteredMouse ? Font.BOLD : Font.PLAIN, size);
            Map<TextAttribute, Object> attributes = new HashMap<>(steticaFont.getAttributes());
            attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_DOTTED);
            setFont(steticaFont.deriveFont(attributes));
        } catch (IOException | FontFormatException exception) {
            JvLog.write(JvLog.TypeLog.Error, "SteticaFont not created here.");
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
    }
}