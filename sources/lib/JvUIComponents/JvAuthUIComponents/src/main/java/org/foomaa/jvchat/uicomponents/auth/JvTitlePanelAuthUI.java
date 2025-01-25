package org.foomaa.jvchat.uicomponents.auth;

import org.foomaa.jvchat.globaldefines.JvGetterGlobalDefines;
import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.settings.JvGetterSettings;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class JvTitlePanelAuthUI extends JPanel {
    private final JButton closeButton;
    private final JButton minimizeButton;
    private final JLabel titleLabel;

    JvTitlePanelAuthUI(String text) {
        closeButton = new JButton();
        minimizeButton = new JButton();
        titleLabel = new JLabel(text);

        settingButtonImage(closeButton, "/Close.png", "/CloseLight.png");
        settingButtonImage(minimizeButton, "/Minimize.png", "/MinimizeLight.png");

        settingTitleLabel();

        settingPanel();
    }

    private void settingButtonImage(JButton button, String imagePathExited, String imagePathEntered) {
        try {
            BufferedImage imageExited = ImageIO.read(Objects.requireNonNull(getClass().getResource(imagePathExited)));
            ImageIcon iconExited = new ImageIcon(imageExited);

            BufferedImage imageEntered = ImageIO.read(Objects.requireNonNull(getClass().getResource(imagePathEntered)));
            ImageIcon iconEntered = new ImageIcon(imageEntered);

            button.setIcon(iconExited);
            button.setPreferredSize(new Dimension(iconExited.getIconWidth(), iconExited.getIconWidth()));

            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button.setIcon(iconEntered);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    button.setIcon(iconExited);
                }
            });
        } catch (IOException ex) {
            JvLog.write(JvLog.TypeLog.Error, "No icon.");
        }

        button.setContentAreaFilled(false);
        button.setBorder(null);
        button.setFocusPainted(false);
    }

    public JButton getCloseButton() {
        return closeButton;
    }

    public JButton getMinimizeButton() {
        return minimizeButton;
    }

    private void settingTitleLabel() {
        titleLabel.setForeground(Color.LIGHT_GRAY);
        try {
            int size = JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFont(0.0093);
            Font steticaFont = JvGetterGlobalDefines.getInstance().getBeanFontsGlobalDefines()
                    .createMainSteticaFont(Font.BOLD, size);
            titleLabel.setFont(steticaFont);
        } catch (IOException | FontFormatException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Здесь не создался steticaFont");
        }

        Dimension currentSize = titleLabel.getPreferredSize();
        // necessary because the font is custom, and the component may cut the text a little because of this
        titleLabel.setPreferredSize(new Dimension(currentSize.width + 2, currentSize.height));
    }

    private void settingPanel() {
        setLayout(new BorderLayout());
        Border bottomBorder =
                BorderFactory.createMatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY);
        setBorder(bottomBorder);

        JPanel titlePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 0, 0, 0);
        titlePanel.add(titleLabel, gbc);
        titlePanel.setBackground(Color.BLACK);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 4));
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(minimizeButton);
        buttonPanel.add(closeButton);

        add(titlePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
    }
}