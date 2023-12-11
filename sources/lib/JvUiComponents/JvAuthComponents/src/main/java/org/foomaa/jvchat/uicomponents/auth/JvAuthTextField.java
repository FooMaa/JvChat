package org.foomaa.jvchat.uicomponents.auth;

import org.foomaa.jvchat.syssettings.JvDisplaySettings;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class JvAuthTextField extends JPanel {
    private BufferedImage visibleImage;
    private JButton button;
    private JTextField textField;

    private String defaultText;
    private final int gap = 5;
    private final int borderSize = 1;

    public JvAuthTextField(String text) {
        setLayout(new FlowLayout(FlowLayout.LEADING, gap, 0));
        defaultText = text;

        settingTextAndButtonPanel();
        addListenerToElem();
    }

    private BufferedImage setIcon(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException ex) {
            System.out.println("Нет иконки глазка");
        }
        return null;
    }

    private void addButtonToPanel() {
        button = new JButton(new ImageIcon(visibleImage));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setMargin(new Insets(0, 0, 0, gap));
        button.setEnabled(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(visibleImage.getWidth() + gap,
                visibleImage.getHeight()));
        add(button);
    }

    private void addListenerToElem() {
        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                textField.setFocusable(true);
                textField.setForeground(Color.BLACK);
                textField.setText("");
                textField.requestFocusInWindow();
                textField.removeMouseListener(this);
            }
        });

        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getForeground() == Color.lightGray) {
                    textField.setForeground(Color.BLACK);
                    textField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (Objects.equals(textField.getText(), "")) {
                    textField.setForeground(Color.lightGray);
                    textField.setText(defaultText);
                }
            }
        });
    }

    private void settingTextAndButtonPanel() {
        Dimension dim = new Dimension(JvDisplaySettings.
                getResizeFromDisplay(0.23,
                        JvDisplaySettings.TypeOfDisplayBorder.WIDTH),
                JvDisplaySettings.getResizeFromDisplay(0.03,
                        JvDisplaySettings.TypeOfDisplayBorder.HEIGHT));
        setPreferredSize(dim);
        makeTextField(dim);

        add(textField);
        setBackground(textField.getBackground());
        setBorder(null);

        settingTextField();
    }

    private void makeTextField(Dimension dim) {
        textField = new JTextField();
        Dimension calcNewDim = new Dimension((int) dim.getWidth(),
                (int) dim.getHeight() - borderSize * 2);
        textField.setPreferredSize(calcNewDim);
    }

    private void settingTextField() {
        textField.setBorder(null);
        textField.setText(defaultText);
        textField.setFont(new Font("Times", Font.BOLD, 14));
        textField.setForeground(Color.lightGray);
        textField.setFocusable(false);
    }


    public String getInputText() {
        if (!Objects.equals(textField.getText(), defaultText)) {
            return textField.getText();
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