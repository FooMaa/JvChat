package org.foomaa.jvchat.uicomponents.auth;

import org.foomaa.jvchat.syssettings.JvDisplaySettings;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class JvAuthPasswordField extends JPanel {
    private BufferedImage visibleImage;
    private BufferedImage invisibleImage;
    private boolean flagEye = false;
    private static boolean unLockPass = false;
    private JPasswordField passwordField;
    private JButton button;
    private final JPanel passwordFieldWithButtonsPanel;
    private final int gap = 5;

    public JvAuthPasswordField(String text) {
        visibleImage = setIcon("/eye.png");
        invisibleImage = setIcon("/eye-close.png");

        passwordFieldWithButtonsPanel = new JPanel(new FlowLayout(
                FlowLayout.LEADING, gap, 0));

        settingPassAndButtonPanel(text);
        settingGeneralPanel(text);
    }

    private BufferedImage setIcon(String path) {
        try {
            return ImageIO.read(getClass().getResource(path));
        } catch (IOException ex) {
            System.out.println("Нет иконки глазка");
        }
        return null;
    }

    private void addButtonToPanel(JPanel panel) {
        button = new JButton(new ImageIcon(invisibleImage));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setMargin(new Insets(0, 0, 0, gap));
        button.setEnabled(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(invisibleImage.getWidth() + gap,
                invisibleImage.getHeight()));
        panel.add(button);
    }

    private void addListenerToElem(String text) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (unLockPass) {
                    if (flagEye) {
                        button.setIcon(new ImageIcon(invisibleImage));
                        passwordField.setEchoChar('•');
                        flagEye = false;
                    } else {
                        button.setIcon(new ImageIcon(visibleImage));
                        passwordField.setEchoChar((char) 0);
                        flagEye = true;
                    }
                    passwordField.requestFocusInWindow();
                }
            }
        });

        passwordField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                passwordField.setFocusable(true);
                passwordField.setForeground(Color.BLACK);
                passwordField.setText("");
                passwordField.setEchoChar('•');
                passwordField.requestFocusInWindow();
                passwordField.removeMouseListener(this);
                button.setEnabled(true);
                unLockPass = true;
            }
        });

        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (passwordField.getForeground() == Color.lightGray) {
                    if (!flagEye) {
                        passwordField.setEchoChar('•');
                    }
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setText("");
                    button.setEnabled(true);
                    unLockPass = true;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (Objects.equals(String.valueOf(passwordField.getPassword()), "")) {
                    if (!flagEye) {
                        passwordField.setEchoChar((char) 0);
                    }
                    passwordField.setForeground(Color.lightGray);
                    passwordField.setText(text);
                    button.setEnabled(false);
                    unLockPass = false;
                }
            }
        });

    }

    private void settingPassAndButtonPanel(String text) {
        Dimension dim = new Dimension(JvDisplaySettings.getResizeFromDisplay(0.23,
                JvDisplaySettings.TypeOfDisplayBorder.WIDTH),
                JvDisplaySettings.getResizeFromDisplay(0.03,
                        JvDisplaySettings.TypeOfDisplayBorder.HEIGHT));
        passwordFieldWithButtonsPanel.setPreferredSize(dim);

        makePassField(dim);

        passwordFieldWithButtonsPanel.add(passwordField);
        addButtonToPanel(passwordFieldWithButtonsPanel);
        passwordFieldWithButtonsPanel.setBackground(passwordField.getBackground());
        passwordFieldWithButtonsPanel.setBorder(null);

        settingPassField(text);
    }

    private void makePassField(Dimension dim) {
        passwordField = new JPasswordField();
        Dimension calcNewDim = new Dimension((int) dim.getWidth() -
                visibleImage.getWidth() - (gap * 3),
                (int) dim.getHeight());
        passwordField.setPreferredSize(calcNewDim);
    }

    private void settingPassField(String text) {
        passwordField.setBorder(null);
        passwordField.setText(text);
        passwordField.setFont(new Font("Times",
                Font.BOLD, 14));
        passwordField.setForeground(Color.lightGray);
        passwordField.setFocusable(false);
        passwordField.setEchoChar((char) 0);
    }

    private void settingGeneralPanel(String text) {
        addListenerToElem(text);
        add(passwordFieldWithButtonsPanel);
    }
}
