package org.foomaa.jvchat.uicomponents.auth;

import org.foomaa.jvchat.syssettings.JvDisplaySettings;
import org.foomaa.jvchat.ctrl.JvDbCtrl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Vector;

public class JvRegistrationFrame extends JFrame {
    private final JPanel panel;
    private final JvAuthLabel tInfo;
    private final JvAuthTextField tLogin;
    private final JvAuthLabel tErrorHelpInfo;
    private final JvAuthPasswordField tPassword;
    private final JvAuthPasswordField tPasswordConfirm;
    private final JvAuthButton bRegister;
    public JvRegistrationFrame() {
        super("RegistrationWindow");

        panel = new JPanel();
        tInfo = new JvAuthLabel("Введите данные для регистрации:");
        tLogin = new JvAuthTextField("Логин");
        tErrorHelpInfo = new JvAuthLabel("");
        tErrorHelpInfo.settingToError();
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
        gbc.insets = new Insets(0, insX, JvDisplaySettings.getResizePixel(0.004), insX);
        gbc.gridy = 2;
        panel.add(tPassword, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, insX, 0, insX);
        gbc.gridy = 3;
        panel.add(tPasswordConfirm, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, insX, JvDisplaySettings.getResizePixel(0.0084), insX);
        gbc.gridy = 4;
        panel.add(tErrorHelpInfo, gbc);

        gbc.fill = GridBagConstraints.PAGE_END;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 0, JvDisplaySettings.getResizePixel(0.017), 0);
        gbc.ipadx = JvDisplaySettings.getResizeFromDisplay(0.03,
                JvDisplaySettings.TypeOfDisplayBorder.WIDTH);
        gbc.ipady = JvDisplaySettings.getResizeFromDisplay(0.01,
                JvDisplaySettings.TypeOfDisplayBorder.HEIGHT);
        gbc.gridy = 5;
        panel.add(bRegister, gbc);

        getContentPane().add(panel);
    }

    private void addListenerToElements() {
        bRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkFields();
                try {
                    writeUserInDb();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JvAuthFrame authFrame = new JvAuthFrame();
            }
        });

    }

    private void writeUserInDb() throws SQLException {
        JvDbCtrl db = JvDbCtrl.getInstance();
        db.queryToDataBase(JvDbCtrl.TypeExecution.InsertRegisterForm,
                tLogin.getInputText(), tPassword.getInputText());
    }

    private void checkFields() {
        tLogin.setNormalBorder();
        tPassword.setNormalBorder();
        tPasswordConfirm.setNormalBorder();
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
        if (Objects.equals(tPasswordConfirm.getInputText(), "")) {
            tPasswordConfirm.setErrorBorder();
            fields.add("\"Подтвердите пароль\"");
        }
        if (!Objects.equals(tPassword.getInputText(), "") &&
                !Objects.equals(tPasswordConfirm.getInputText(), "") &&
                !Objects.equals(tPassword.getInputText(), tPasswordConfirm.getInputText())) {
            tPassword.setErrorBorder();
            tPasswordConfirm.setErrorBorder();
            tErrorHelpInfo.setText("Введенные пароли должны совпадать");
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

    private void addGeneralSettingsToWidget() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("РЕГИСТРАЦИЯ");
        setSize(JvDisplaySettings.getResizeFromDisplay(0.3,
                        JvDisplaySettings.TypeOfDisplayBorder.WIDTH),
                JvDisplaySettings.getResizeFromDisplay(0.30,
                        JvDisplaySettings.TypeOfDisplayBorder.HEIGHT));
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        toFront();
        setVisible(true);
    }
}
