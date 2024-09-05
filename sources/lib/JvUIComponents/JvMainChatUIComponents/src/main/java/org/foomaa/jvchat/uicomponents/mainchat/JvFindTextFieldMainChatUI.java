package org.foomaa.jvchat.uicomponents.mainchat;

import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.settings.JvDisplaySettings;
import org.foomaa.jvchat.settings.JvGetterSettings;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;


public class JvFindTextFieldMainChatUI extends JPanel {
    private static JvFindTextFieldMainChatUI instance;
    private final BufferedImage image;
    private JTextField textField;
    private JButton button;
    private final String defaultText;
    private final int borderSize = 1;

    JvFindTextFieldMainChatUI(String text) {
        image = setIcon("/magnifier.png");
        defaultText = text;

        settingTextAndButtonPanel();
        addListenerToElem();
    }

    public static JvFindTextFieldMainChatUI getInstance(String text) {
        if (instance == null) {
            instance = new JvFindTextFieldMainChatUI(text);
        }
        return instance;
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
        button = new JButton(new ImageIcon(image));
        button.setContentAreaFilled(false);
        button.setBorder(null);
        button.setEnabled(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(image.getWidth(),
                image.getHeight()));
    }

    private void addListenerToElem() {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Find");
            }
        });

        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                textField.setFocusable(true);
                textField.setForeground(Color.BLACK);
                textField.setText("");
                textField.requestFocusInWindow();
                textField.removeMouseListener(this);
                button.setEnabled(true);
            }
        });

        textField.addFocusListener(new FocusListener() {
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
        if (textField.getForeground() == Color.lightGray) {
            textField.setForeground(Color.BLACK);
            textField.setText("");
        }
    }

    private void focusFalse() {
        if (Objects.equals(textField.getText(), "")) {
            textField.setForeground(Color.lightGray);
            textField.setText(defaultText);
        }
    }

    private void settingTextAndButtonPanel() {
        Dimension dim = new Dimension(JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.23,
                JvDisplaySettings.TypeOfDisplayBorder.WIDTH),
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.03,
                        JvDisplaySettings.TypeOfDisplayBorder.HEIGHT));
        settingButtonImage();
        settingTextField(dim);
        addElements();
        setNormalBorder();
        setBackground(textField.getBackground());
        setPreferredSize(dim);
    }

    private void addElements() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        int gridxNum = 0;

        gbc.weightx = 1.0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = gridxNum;
        add(textField, gbc);
        gridxNum++;

        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = gridxNum;
        add(button, gbc);
    }

    private void settingTextField(Dimension dim) {
        textField = new JTextField();
        Dimension calcNewDim = new Dimension((int) dim.getWidth() -
                button.getPreferredSize().width,
                (int) dim.getHeight() - borderSize * 2);
        textField.setPreferredSize(calcNewDim);
        textField.setBorder(null);
        textField.setText(defaultText);
        textField.setFont(new Font("Times", Font.BOLD,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.012)));
        textField.setForeground(Color.lightGray);
        textField.setFocusable(false);
    }

    public String getInputText() {
        if (!Objects.equals(textField.getText(), defaultText)) {
            return textField.getText();
        }
        return "";
    }

    public void setNormalBorder() {
        setBorder(BorderFactory.createMatteBorder(borderSize,borderSize,borderSize,7, Color.GRAY));
    }

    public void setUnfocusFieldOnClose(boolean needSaveText) {
        textField.setFocusable(false);
        if (!needSaveText) {
            textField.setText("");
        }
        focusFalse();

        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                textField.setFocusable(true);
                textField.requestFocusInWindow();
                textField.removeMouseListener(this);
            }
        });
    }
}