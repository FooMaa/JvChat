package org.foomaa.jvchat.uicomponents.auth;

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

import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.settings.JvDisplaySettings;
import org.foomaa.jvchat.settings.JvGetterSettings;


public class JvPasswordFieldAuthUI extends JPanel {
    private final BufferedImage visibleImage;
    private final BufferedImage invisibleImage;
    private boolean flagEye = false;
    private boolean unLockPass = false;
    private JPasswordField passwordField;
    private JButton button;
    private final String defaultText;
    private final int borderSize = 1;

    JvPasswordFieldAuthUI(String text) {
        visibleImage = setIcon("/Eye.png");
        invisibleImage = setIcon("/Eye-close.png");
        defaultText = text;

        settingPassAndButtonPanel();
        addListenerToElem();
    }

    private BufferedImage setIcon(String path) {
        try {
            return ImageIO.read(Objects.requireNonNull(getClass().getResource(path)));
        } catch (IOException ex) {
            JvLog.write(JvLog.TypeLog.Error, "Нет иконки глазка");
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
                        assert invisibleImage != null;
                        button.setIcon(new ImageIcon(invisibleImage));
                        passwordField.setEchoChar('•');
                        flagEye = false;
                    } else {
                        assert visibleImage != null;
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
                focusTrue();
            }

            @Override
            public void focusLost(FocusEvent e) {
                focusFalse();
            }
        });

    }

    private void focusTrue() {
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

    private void focusFalse() {
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

    private void settingPassAndButtonPanel() {
        Dimension dim = new Dimension(JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.23,
                JvDisplaySettings.TypeOfDisplayBorder.WIDTH),
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.03,
                        JvDisplaySettings.TypeOfDisplayBorder.HEIGHT));
        settingButtonImage();
        settingPassField(dim);
        addElements();
        setBackground(passwordField.getBackground());
        setNormalBorder();
        setPreferredSize(dim);
    }

    private void addElements() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        int gridxNum = 0;

        gbc.weightx = 1.0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 5, 0, 0);
        gbc.gridx = gridxNum;
        add(passwordField, gbc);
        gridxNum++;

        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 5);
        gbc.gridx = gridxNum;
        add(button, gbc);
    }

    private void settingPassField(Dimension dim) {
        passwordField = new JPasswordField();
        Dimension calcNewDim = new Dimension((int) dim.getWidth() -
                button.getPreferredSize().width,
                (int) dim.getHeight() - borderSize * 2);
        passwordField.setPreferredSize(calcNewDim);
        passwordField.setBorder(null);
        passwordField.setText(defaultText);
        passwordField.setFont(new Font("Times", Font.BOLD,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.012)));
        passwordField.setForeground(Color.lightGray);
        passwordField.setFocusable(false);
        passwordField.setEchoChar((char) 0);
    }

    public String getInputText() {
        String password = new String(passwordField.getPassword());
        if (!Objects.equals(password, defaultText)) {
            return password;
        }
        return "";
    }

    public void setErrorBorder() {
        setBorder(new LineBorder(Color.red, borderSize));
    }

    public void setNormalBorder() {
        setBorder(null);
    }

    public void setUnfocusFieldOnClose(boolean needSaveText) {
        passwordField.setFocusable(false);
        if (!needSaveText) {
            passwordField.setText("");
        }
        focusFalse();

        passwordField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                passwordField.setFocusable(true);
                passwordField.requestFocusInWindow();
                passwordField.removeMouseListener(this);
            }
        });
    }
}