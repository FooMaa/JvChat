package org.foomaa.jvchat.uicomponents.auth;

import org.foomaa.jvchat.syssettings.JvDisplaySettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

public class JvRegistrationFrame extends JFrame {
    private final JPanel panel;
    private final JvAuthLabel tInfo;
    private final JvAuthTextField tLogin;
    private final JvAuthPasswordField tPassword;
    private final JvAuthPasswordField tPasswordConfirm;
    private final JvAuthButton bRegister;

    public JvRegistrationFrame() {
        super("RegistrationWindow");

        panel = new JPanel();
        tInfo = new JvAuthLabel("Введите данные для регистрации:");
        tLogin = new JvAuthTextField("Логин");
        tPassword = new JvAuthPasswordField("Пароль");
        tPasswordConfirm = new JvAuthPasswordField("Подтвердите пароль");
        bRegister = new JvAuthButton("РЕГИСТРАЦИЯ");

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

        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.PAGE_START;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.gridy = 0;
        panel.add(tInfo, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, insX, -30, insX);
        gbc.gridy = 1;
        panel.add(tLogin, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, insX, -30, insX);
        gbc.gridy = 2;
        panel.add(tPassword, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, insX, 0, insX);
        gbc.gridy = 3;
        panel.add(tPasswordConfirm, gbc);

        gbc.fill = GridBagConstraints.PAGE_END;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 0, 20, 0);
        gbc.ipadx = JvDisplaySettings.getResizeFromDisplay(0.03,
                JvDisplaySettings.TypeOfDisplayBorder.WIDTH);
        gbc.ipady = JvDisplaySettings.getResizeFromDisplay(0.01,
                JvDisplaySettings.TypeOfDisplayBorder.HEIGHT);
        gbc.gridy = 4;
        panel.add(bRegister, gbc);

        getContentPane().add(panel);
    }

    private void addListenerToElements() {
        bRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("kokokokokok");
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JvAuthFrame authFrame = new JvAuthFrame();
            }
        });

    }

    private void addGeneralSettingsToWidget() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("РЕГИСТРАЦИЯ");
        setSize(JvDisplaySettings.getResizeFromDisplay(0.3,
                        JvDisplaySettings.TypeOfDisplayBorder.WIDTH),
                JvDisplaySettings.getResizeFromDisplay(0.3,
                        JvDisplaySettings.TypeOfDisplayBorder.HEIGHT));
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        toFront();
        setVisible(true);
    }
}
