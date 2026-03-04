package org.foomaa.jvchat.uicomponents.auth;

import org.foomaa.jvchat.settings.DisplaySettings;
import org.foomaa.jvchat.globaldefines.GetterGlobalDefines;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;


@Component
@Profile("users")
@Slf4j
public class TitlePanelAuthUI extends JPanel {
    private final JButton closeButton;
    private final JButton minimizeButton;
    private final JLabel titleLabel;
    private ToolTipAuthUI toolTipClose;
    private ToolTipAuthUI toolTipMinimize;

    private final DisplaySettings displaySettings;

    TitlePanelAuthUI(DisplaySettings displaySettings) {
        this.displaySettings = displaySettings;

        closeButton = new JButton() {
            @Override
            public JToolTip createToolTip() {
                return toolTipClose;
            }
        };
        minimizeButton = new JButton() {
            @Override
            public JToolTip createToolTip() {
                return toolTipMinimize;
            }
        };
        titleLabel = new JLabel();

        setToolTips();
        settingButtonImage(closeButton, "/Close.png", "/CloseLight.png");
        settingButtonImage(minimizeButton, "/Minimize.png", "/MinimizeLight.png");

        settingTitleLabel();
        settingPanel();
    }

    public void setTitle(String text) {
        // It is necessary to give the component the opportunity to first recalculate its size itself
        titleLabel.setPreferredSize(null);
        titleLabel.setText(text);
        Dimension currentSize = titleLabel.getPreferredSize();
        // necessary because the font is custom, and the component may cut the text a little because of this
        titleLabel.setPreferredSize(new Dimension(currentSize.width + 2, currentSize.height));
    }

    private void setToolTips() {
        toolTipClose = GetterAuthUIComponents.getInstance().getBeanToolTipAuthUI();
        closeButton.createToolTip();
        closeButton.setToolTipText("Close");

        toolTipMinimize = GetterAuthUIComponents.getInstance().getBeanToolTipAuthUI();
        minimizeButton.createToolTip();
        minimizeButton.setToolTipText("Minimize");
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
            log.error("No icon.");
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
            int size = displaySettings.getResizeFont(0.0093);
            Font steticaFont = GetterGlobalDefines.getInstance().getBeanFontsGlobalDefines()
                    .createMainSteticaFont(Font.BOLD, size);
            titleLabel.setFont(steticaFont);
        } catch (IOException | FontFormatException exception) {
            log.error("steticaFont was not created here.");
        }
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