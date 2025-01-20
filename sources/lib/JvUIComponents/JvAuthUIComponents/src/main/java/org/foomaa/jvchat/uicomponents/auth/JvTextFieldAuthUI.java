package org.foomaa.jvchat.uicomponents.auth;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Objects;

import org.foomaa.jvchat.globaldefines.JvGetterGlobalDefines;
import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.settings.JvDisplaySettings;
import org.foomaa.jvchat.settings.JvGetterSettings;


public class JvTextFieldAuthUI extends JPanel {
    private JTextField textField;

    private final String defaultText;
    private final int borderSize = 2;

    JvTextFieldAuthUI(String text) {
        defaultText = text;

        settingTextPanel();
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

    private void settingTextPanel() {
        Dimension dim = new Dimension(JvGetterSettings.getInstance().getBeanDisplaySettings().
                getResizeFromDisplay(0.23,
                        JvDisplaySettings.TypeOfDisplayBorder.WIDTH),
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.03,
                        JvDisplaySettings.TypeOfDisplayBorder.HEIGHT));
        settingTextField(dim);
        addElements();
        setBackground(textField.getBackground());
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
        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.gridx = gridxNum;
        add(textField, gbc);
    }

    private void settingTextField(Dimension dim) {
        textField = new JTextField();
        Dimension calcNewDim = new Dimension((int) dim.getWidth(),
                (int) dim.getHeight() - borderSize * 2);
        textField.setPreferredSize(calcNewDim);
        textField.setBorder(null);
        textField.setText(defaultText);
        textField.setForeground(Color.lightGray);
        textField.setFocusable(false);
        setFont();
    }

    private void setFont() {
        try {
            int size = JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.015);
            Font steticaFont = JvGetterGlobalDefines.getInstance().getBeanFontsGlobalDefines()
                    .createMainSteticaFont(Font.BOLD, size);
            textField.setFont(steticaFont);
        } catch (IOException | FontFormatException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Здесь не создался steticaFont");
        }
    }

    public String getInputText() {
        if (!Objects.equals(textField.getText(), defaultText)) {
            return textField.getText();
        }
        return "";
    }

    public void setErrorBorder() {
        setBorder(new LineBorder(Color.RED, borderSize));
    }

    public void setNormalBorder() {
        setBorder(null);
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