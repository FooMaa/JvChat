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

        settingButtonImage(closeButton, "/Close.png");
        settingButtonImage(minimizeButton, "/Minimize.png");

        settingCloseButton();
        settingMinimizeButton();
        settingTitleLabel();

        settingPanel();
    }

    private void settingButtonImage(JButton button, String imagePath) {
        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResource(imagePath)));
            button.setIcon(new ImageIcon(image));
            button.setPreferredSize(new Dimension(image.getWidth(),
                    image.getHeight()));
        } catch (IOException ex) {
            JvLog.write(JvLog.TypeLog.Error, "No icon.");
        }


        button.setContentAreaFilled(false);
        button.setBorder(null);
        button.setEnabled(false);
        button.setFocusPainted(false);
    }

    private void settingCloseButton() {
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
    }

    private void settingMinimizeButton() {
        minimizeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
    }

    private void settingTitleLabel() {
        titleLabel.setForeground(Color.LIGHT_GRAY);
        try {
            int size = JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFont(0.0095);
            Font steticaFont = JvGetterGlobalDefines.getInstance().getBeanFontsGlobalDefines()
                    .createMainSteticaFont(Font.BOLD, size);
            titleLabel.setFont(steticaFont);
        } catch (IOException | FontFormatException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Здесь не создался steticaFont");
        }

        Dimension currentSize = titleLabel.getPreferredSize();
        // нужно, т.к. шрифт кастомный, и компонент может немного съесть текста из-за этого
        titleLabel.setPreferredSize(new Dimension(currentSize.width + 2, currentSize.height));
    }

    private void settingPanel() {
        setLayout(new BorderLayout());
        Border bottomBorder =
                BorderFactory.createMatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY);
        setBorder(bottomBorder);

        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.add(titleLabel);
        titlePanel.setBackground(Color.BLACK);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(minimizeButton);
        buttonPanel.add(closeButton);

        add(titlePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
    }
}
