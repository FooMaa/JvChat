package org.foomaa.jvchat.uicomponents.mainchat;

import org.foomaa.jvchat.settings.JvGetterSettings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;


public class JvRectMessageMainChatUI extends JTextArea {
    private final int borderSize;

    JvRectMessageMainChatUI() {
        borderSize = 10;

        settingLabel();
        addListenerToElements();
        setTextMessage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Рисуем фон облачка
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 16, 16);

        g2.setColor(new Color(16, 126, 16));

        int radius = 2;
        int diameter = radius * 2;
        int x1 = getSize().width - diameter - 5;
        int y1 = getSize().height - diameter - 5;
        int x2 = x1 - diameter - 1;

        // Риуем кружки доставки
        g2.fillOval(x1, y1, diameter, diameter);
        g2.fillOval(x2, y1, diameter, diameter);

        super.paintComponent(g);
    }

    private void settingLabel() {
        setOpaque(false);
        setEditable(false);
        setBackground(new Color(173, 216, 230));
        setForeground(Color.BLACK);
        setBorder(new EmptyBorder(borderSize, borderSize, borderSize, borderSize));
        setFont(new Font("Times", Font.PLAIN,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.014)));

        setLineWrap(true);
        setWrapStyleWord(true);

        setSize(new Dimension(200,  getPreferredSize().height));
    }

    private void addListenerToElements() {
        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                adjustHeight();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                adjustHeight();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                adjustHeight();
            }
        });
    }

    private void adjustHeight() {
        Dimension dimension = getSize();
        setSize(new Dimension(dimension.width, getPreferredSize().height));
        revalidate();
        repaint();
    }

    private void setTextMessage() {
        setText("AAAAAAAAAAAaaaaaaAAAAAAAAAAAAAAAAAAAABBBBBBBBBBBBBAAAAAAAAAAAaaaaaaAAAAAAAAAAAAAAAAAAAABBBBBBBBBBBBBBBbbbbBB" +
                "BBBBBBBBBBCCCCCCCCCCCCccccccBBBBBBBBBBCCCCCCCCCCCCcccccccccCCCCCCCCBBbbbbBBBBBBBBBBBBCCCCCCCCCCCCcccccccccCCCCCCCCcccCCCCCCCCBBbbbbBBBBBBBBBBBBCCCCCCCCCCCCcccccccccCCCCCCCC");
    }
}