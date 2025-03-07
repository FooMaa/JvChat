package org.foomaa.jvchat.uicomponents.auth;

import javax.swing.*;
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
    private JvToolTipAuthUI toolTip;
    private final String defaultText;
    private boolean isErrorBorderActive;
    private final int borderSize;

    JvTextFieldAuthUI(String text) {
        defaultText = text;
        borderSize = 2;
        isErrorBorderActive = false;

        settingTextPanel();
        addListenerToElem();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int cornerRadius = 20;
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);

        if (isErrorBorderActive) {
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(borderSize));
            g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
        }

        g2d.dispose();
    }

    public void setToolTip(String text) {
        toolTip = JvGetterAuthUIComponents.getInstance().getBeanToolTipAuthUI();
        createToolTip();
        setToolTipText(text);

        textField.createToolTip();
        textField.setToolTipText(text);
    }

    @Override
    public JToolTip createToolTip() {
        return toolTip;
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
        setErrorBorder(false);
        setOpaque(false);
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
        textField = new JTextField() {
            @Override
            public JToolTip createToolTip() {
                return toolTip;
            }
        };
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
            JvLog.write(JvLog.TypeLog.Error, "steticaFont was not created here.");
        }
    }

    public String getInputText() {
        if (!Objects.equals(textField.getText(), defaultText)) {
            return textField.getText();
        }
        return "";
    }

    public void setErrorBorder(boolean flagActiveBorder) {
        isErrorBorderActive = flagActiveBorder;
        revalidate();
        repaint();
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