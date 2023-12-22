package org.foomaa.jvchat.uicomponents.auth;

import org.foomaa.jvchat.syssettings.JvDisplaySettings;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import java.util.Vector;

public class JvAuthFrame extends JFrame {
    private final JPanel panel;
    private final JvAuthLabel tInfo;
    private final JvAuthTextField tLogin;
    private final JvAuthLabel tErrorHelpInfo;
    private final JvAuthPasswordField tPassword;
    private final JvAuthButton bEnter;
    private final JvAuthActiveLabel activeRegisterLabel;
    private final JvAuthActiveLabel activeMissLabel;

    public JvAuthFrame() {
        super("AuthenticationWindow");

        panel = new JPanel();
        tInfo = new JvAuthLabel("Введите данные для входа:");
        tLogin = new JvAuthTextField("Логин");
        tErrorHelpInfo = new JvAuthLabel("");
        tErrorHelpInfo.settingToError();
        tPassword = new JvAuthPasswordField("Пароль");
        bEnter = new JvAuthButton("ВОЙТИ");
        activeMissLabel = new JvAuthActiveLabel("Не помню пароль");
        activeRegisterLabel = new JvAuthActiveLabel("Регистрация");

        makeFrameSetting();
        addListenerToElements();
        addGeneralSettingsToWidget();
    }

    private void makeFrameSetting() {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        int insX = JvDisplaySettings.
                getResizeFromDisplay(0.025,
                        JvDisplaySettings.TypeOfDisplayBorder.WIDTH);

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.PAGE_START;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(JvDisplaySettings.getResizePixel(0.0125), 0, JvDisplaySettings.getResizePixel(0.0084), 0);
        gbc.gridy = 0;
        panel.add(tInfo, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, insX, JvDisplaySettings.getResizePixel(0.004), insX);
        gbc.gridy = 1;
        panel.add(tLogin, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, insX, 0, insX);
        gbc.gridy = 2;
        panel.add(tPassword, gbc);

        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 0, JvDisplaySettings.getResizePixel(0.004), 0);
        gbc.gridy = 3;
        panel.add(activeMissLabel, gbc);

        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 0, JvDisplaySettings.getResizePixel(0.0084), 0);
        gbc.gridy = 4;
        panel.add(activeRegisterLabel, gbc);

        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, insX, JvDisplaySettings.getResizePixel(0.0084), insX);
        gbc.gridy = 5;
        panel.add(tErrorHelpInfo, gbc);

        gbc.fill = GridBagConstraints.PAGE_END;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets(0, 0, JvDisplaySettings.getResizePixel(0.017), 0);
        gbc.ipadx = JvDisplaySettings.getResizeFromDisplay(0.03,
                JvDisplaySettings.TypeOfDisplayBorder.WIDTH);
        gbc.ipady = JvDisplaySettings.getResizeFromDisplay(0.01,
                JvDisplaySettings.TypeOfDisplayBorder.HEIGHT);
        gbc.gridy = 6;
        panel.add(bEnter, gbc);

        getContentPane().add(panel);
    }

    private void addListenerToElements() {
        bEnter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkFields();
            }
        });

        activeMissLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("ayayayayay");
            }
        });

        activeRegisterLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JvRegistrationFrame registrationFrame = new JvRegistrationFrame();
                closeWindow();
            }
        });

    }

    private void checkFields() {
        tPassword.setNormalBorder();
        tLogin.setNormalBorder();
        tErrorHelpInfo.setText("");

        Vector<String> fields = new Vector<String>();

        if (Objects.equals(tLogin.getInputText(), "")) {
            tLogin.setErrorBorder();
            fields.add("\"Логин\"");
        }
        if (Objects.equals(tPassword.getInputText(), "")) {
            tPassword.setErrorBorder();
            fields.add("\"Пароль\"");
        }

        String concatFields = "";
        if (fields.size() != 0) {
            for (int i = 0; i < fields.size(); i++) {
                concatFields += fields.elementAt(i) + ", ";
            }
            concatFields = concatFields.substring(0, concatFields.length() - 2);
            if (fields.size() == 1) {
                tErrorHelpInfo.setText(String.format("Поле %s должно быть заполнено", concatFields));
            } else {
                tErrorHelpInfo.setText(String.format("Поля %s должны быть заполнены", concatFields));
            }
        }
    }

    private void closeWindow() {
        dispose();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(false);
    }

    private void addGeneralSettingsToWidget() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("ВХОД");
        setSize(JvDisplaySettings.getResizeFromDisplay(0.3,
                        JvDisplaySettings.TypeOfDisplayBorder.WIDTH),
                JvDisplaySettings.getResizeFromDisplay(0.3,
                        JvDisplaySettings.TypeOfDisplayBorder.HEIGHT));
        setResizable(false);
        setLocationRelativeTo(null);
        toFront();
        setVisible(true);
    }
}
