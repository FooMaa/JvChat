package org.foomaa.jvchat.uicomponents.auth;

import javax.imageio.IIOException;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.io.InputStream;


public class JvButtonAuthUI extends JButton {
    JvButtonAuthUI(String text) {
        setText(text);
        setBackground(Color.WHITE);
        setFocusable(false);
        addListenerToElements();


        // Загрузка и регистрация шрифта
        Font customFont = null;
        try {
            InputStream inputStream = getClass().getResourceAsStream("/MainFont.otf");
            customFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            inputStream.close();
            customFont = customFont.deriveFont(Font.BOLD, 24f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (FontFormatException | IOException e) {
            System.out.println("aaa");
        }

        setFont(customFont);
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
}