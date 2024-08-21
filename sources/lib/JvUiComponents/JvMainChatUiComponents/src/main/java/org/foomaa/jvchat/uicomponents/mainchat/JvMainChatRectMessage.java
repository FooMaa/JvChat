package org.foomaa.jvchat.uicomponents.mainchat;

import org.foomaa.jvchat.settings.JvGetterSettings;

import javax.swing.*;
import java.awt.*;


public class JvMainChatRectMessage extends JLabel {
    private int id;

    JvMainChatRectMessage() {
        settingLabel();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Рисуем фон облачка
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);

        super.paintComponent(g);
    }

    private void settingLabel() {
        setText("<html>AAAAAAAAAAAAAAAAAAAAAAA<br>AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA</html>");
        setOpaque(false);
        setBackground(new Color(173, 216, 230));
        setFont(new Font("Times", Font.PLAIN,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.014)));
        setForeground(Color.BLACK);
        setHorizontalAlignment(SwingConstants.LEFT);
        setVerticalAlignment(SwingConstants.TOP);
    }
}