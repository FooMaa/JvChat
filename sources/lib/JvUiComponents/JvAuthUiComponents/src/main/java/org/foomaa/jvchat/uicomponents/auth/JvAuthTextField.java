package org.foomaa.jvchat.uicomponents.auth;

import org.foomaa.jvchat.settings.JvDisplaySettings;
import org.foomaa.jvchat.settings.JvGetterSettings;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class JvAuthTextField extends JPanel {
    private JTextField textField;

    private final String defaultText;
    private final int borderSize = 1;

    JvAuthTextField(String text) {
        int gap = 5;
        setLayout(new FlowLayout(FlowLayout.LEFT, gap, 0));
        defaultText = text;

        settingTextAndButtonPanel();
        addListenerToElem();
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
        Dimension dim = new Dimension(JvGetterSettings.getInstance().getBeanDisplaySettings().
                getResizeFromDisplay(0.23,
                        JvDisplaySettings.TypeOfDisplayBorder.WIDTH),
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.03,
                        JvDisplaySettings.TypeOfDisplayBorder.HEIGHT));
        settingTextField(dim);
        add(textField);
        setBackground(textField.getBackground());
        setBorder(null);
        setPreferredSize(dim);
    }

    private void settingTextField(Dimension dim) {
        textField = new JTextField();
        Dimension calcNewDim = new Dimension((int) dim.getWidth(),
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

    public void setErrorBorder() {
        setBorder(new LineBorder(Color.red, borderSize));
    }

    public void setNormalBorder() {
        setBorder(null);
    }

    public void setText(String text) {
        textField.setText(text);
    }
}