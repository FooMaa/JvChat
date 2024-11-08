package org.foomaa.jvchat.uicomponents.auth;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

import org.foomaa.jvchat.settings.JvGetterSettings;


public class JvActiveLabelAuthUI extends JLabel {
    JvActiveLabelAuthUI(String text) {
        setText(text);
        Font defaultfont = new Font("Times", Font.PLAIN,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.009));
        Map<TextAttribute, Object> attributes = new HashMap<>(defaultfont.getAttributes());
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        setFont(defaultfont.deriveFont(attributes));
        setForeground(Color.BLUE);
        addCustomListenerToElem(defaultfont);
    }

    private void addCustomListenerToElem(Font defaultFont) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Font font = new Font("Times", Font.BOLD,
                        JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.009));
                Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
                attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                setFont(font.deriveFont(attributes));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Font font = new Font("Times", Font.PLAIN,
                        JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.009));
                Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
                attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                setFont(defaultFont.deriveFont(attributes));
            }
        });
    }
}