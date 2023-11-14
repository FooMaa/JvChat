package org.authuicomponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public class JvAuthActiveLabel extends JLabel {
    public JvAuthActiveLabel(String text) {
        setText(text);
        Font defaultfont = new Font("Times", Font.PLAIN, 11);
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
                Font font = new Font("Times", Font.BOLD, 11);
                Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
                attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                setFont(font.deriveFont(attributes));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Font font = new Font("Times", Font.PLAIN, 11);
                Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
                attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                setFont(defaultFont.deriveFont(attributes));
            }
        });
    }
}
