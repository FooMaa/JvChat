package org.foomaa.jvchat.uicomponents.auth;

import org.foomaa.jvchat.settings.JvDisplaySettings;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class JvAuthPasswordField extends JPanel {
    private BufferedImage visibleImage;
    private BufferedImage invisibleImage;
    private boolean flagEye = false;
    private boolean unLockPass = false;
    private JPasswordField passwordField;
    private JButton button;
    private String defaultText;
    private final int gap = 5;
    private final int borderSize = 1;

    public JvAuthPasswordField(String text) {
        visibleImage = setIcon("/eye.png");
        invisibleImage = setIcon("/eye-close.png");

        setLayout(new FlowLayout(FlowLayout.LEADING, gap, 0));
        defaultText = text;

        settingPassAndButtonPanel();
        addListenerToElem();
    }

    private BufferedImage setIcon(String path) {
        try {
            return ImageIO.read(getClass().getResource(path));
        } catch (IOException ex) {
            System.out.println("Нет иконки глазка");
        }
        return null;
    }

    private void settingButtonImage() {
        button = new JButton(new ImageIcon(invisibleImage));
        button.setContentAreaFilled(false);
        button.setBorder(null);
        button.setEnabled(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(invisibleImage.getWidth(),
                invisibleImage.getHeight()));
    }

    private void addListenerToElem() {
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
                    passwordField.setText(defaultText);
                    button.setEnabled(false);
                    unLockPass = false;
                }
            }
        });

    }

    private void settingPassAndButtonPanel() {
        Dimension dim = new Dimension(JvDisplaySettings.getResizeFromDisplay(0.23,
                JvDisplaySettings.TypeOfDisplayBorder.WIDTH),
                JvDisplaySettings.getResizeFromDisplay(0.03,
                        JvDisplaySettings.TypeOfDisplayBorder.HEIGHT));
        settingButtonImage();
        settingPassField(dim);
        add(passwordField);
        add(button);
        setBackground(passwordField.getBackground());
        setBorder(null);
        setPreferredSize(dim);
    }

    private void settingPassField(Dimension dim) {
        passwordField = new JPasswordField();
        Dimension calcNewDim = new Dimension((int) dim.getWidth() -
                button.getPreferredSize().width,
                (int) dim.getHeight() - borderSize * 2);
        passwordField.setPreferredSize(calcNewDim);
        passwordField.setBorder(null);
        passwordField.setText(defaultText);
        passwordField.setFont(new Font("Times", Font.BOLD, JvDisplaySettings.getResizePixel(0.012)));
        passwordField.setForeground(Color.lightGray);
        passwordField.setFocusable(false);
        passwordField.setEchoChar((char) 0);
    }

    public String getInputText() {
        if (!Objects.equals(passwordField.getText(), defaultText)) {
            return passwordField.getText();
        }
        return "";
    }

    public void setErrorBorder() {
        setBorder(new LineBorder(Color.red, borderSize));
    }

    public void setNormalBorder() {
        setBorder(null);
    }
}
