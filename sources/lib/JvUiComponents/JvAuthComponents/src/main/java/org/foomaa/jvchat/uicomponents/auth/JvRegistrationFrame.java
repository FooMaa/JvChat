package org.foomaa.jvchat.uicomponents.auth;

import org.foomaa.jvchat.ctrl.JvMessageCtrl;
import org.foomaa.jvchat.messages.JvSerializatorData;
import org.foomaa.jvchat.settings.JvDisplaySettings;
import org.foomaa.jvchat.tools.JvTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class JvRegistrationFrame extends JFrame {
    private final JPanel panel;
    private final JvAuthLabel tInfo;
    private final JvAuthTextField tLogin;
    private final JvAuthTextField tEmail;
    private final JvAuthLabel tErrorHelpInfo;
    private final JvAuthPasswordField tPassword;
    private final JvAuthPasswordField tPasswordConfirm;
    private final JvAuthButton bRegister;

    public JvRegistrationFrame() {
        super("RegistrationWindow");

        panel = new JPanel();
        tInfo = new JvAuthLabel("Введите данные для регистрации:");
        tLogin = new JvAuthTextField("Логин");
        tEmail = new JvAuthTextField("Почта");
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
        int gridyNum = 0;

        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.PAGE_START;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(JvDisplaySettings.getResizePixel(0.0125), 0, JvDisplaySettings.getResizePixel(0.0084), 0);
        gbc.gridy = gridyNum;
        panel.add(tInfo, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, insX, JvDisplaySettings.getResizePixel(0.004), insX);
        gbc.gridy = gridyNum;
        panel.add(tLogin, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, insX, JvDisplaySettings.getResizePixel(0.004), insX);
        gbc.gridy = gridyNum;
        panel.add(tEmail, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, insX, JvDisplaySettings.getResizePixel(0.004), insX);
        gbc.gridy = gridyNum;
        panel.add(tPassword, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, insX, 0, insX);
        gbc.gridy = gridyNum;
        panel.add(tPasswordConfirm, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, insX, JvDisplaySettings.getResizePixel(0.0084), insX);
        gbc.gridy = gridyNum;
        panel.add(tErrorHelpInfo, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.PAGE_END;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 0, JvDisplaySettings.getResizePixel(0.017), 0);
        gbc.ipadx = JvDisplaySettings.getResizeFromDisplay(0.03,
                JvDisplaySettings.TypeOfDisplayBorder.WIDTH);
        gbc.ipady = JvDisplaySettings.getResizeFromDisplay(0.01,
                JvDisplaySettings.TypeOfDisplayBorder.HEIGHT);
        gbc.gridy = gridyNum;
        panel.add(bRegister, gbc);

        getContentPane().add(panel);
    }

    private void addListenerToElements() {
        bRegister.addActionListener(event -> {
            if (checkFields()) {
                JvMessageCtrl.getInstance().sendMessage(JvSerializatorData.TypeMessage.RegistrationRequest,
                        tLogin.getInputText(), tEmail.getInputText(), tPassword.getInputText());
                waitRepeatServer();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new JvEntryFrame();
            }
        });
    }

    private boolean checkFields() {
        tLogin.setNormalBorder();
        tEmail.setNormalBorder();
        tPassword.setNormalBorder();
        tPasswordConfirm.setNormalBorder();
        tErrorHelpInfo.setText("");

        Vector<String> fields = new Vector<>();

        if (Objects.equals(tLogin.getInputText(), "")) {
            tLogin.setErrorBorder();
            fields.add("\"Логин\"");
        }
        if (Objects.equals(tEmail.getInputText(), "") ||
                !JvTools.validateInputEmail(tEmail.getInputText())) {
            tEmail.setErrorBorder();
            fields.add("\"Почта\"");
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
            return false;
        }

        StringBuilder concatFields = new StringBuilder();
        if (!fields.isEmpty()) {
            for (int i = 0; i < fields.size(); i++) {
                concatFields.append(fields.elementAt(i)).append(", ");
            }
            concatFields = new StringBuilder(concatFields.substring(0, concatFields.length() - 2));
            if (fields.size() == 1) {
                tErrorHelpInfo.setText(String.format("Поле %s должно быть заполнено или исправлено", concatFields));
            } else {
                tErrorHelpInfo.setText(String.format("Поля %s должны быть заполнены или исправлены", concatFields));
            }
            return false;
        }
        return true;
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
        requestFocus();
    }

    private void closeWindow() {
        JvVerifyCodeFrame frm = new JvVerifyCodeFrame( JvVerifyCodeFrame.RegimeWork.Registration);
        frm.setParametersRegistration(tLogin.getInputText(), tEmail.getInputText(), tPassword.getInputText());
        setVisible(false);
        dispose();
    }

    private void waitRepeatServer() {
        setEnabled(false);
        while (JvMessageCtrl.getInstance().getRegistrationRequestFlag()
                == JvMessageCtrl.TypeFlags.DEFAULT) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException exception) {
                System.out.println("Не удалось ждать");
            }
        }
        if (JvMessageCtrl.getInstance().getRegistrationRequestFlag()
                == JvMessageCtrl.TypeFlags.TRUE) {
            closeWindow();
        } else if (JvMessageCtrl.getInstance().getRegistrationRequestFlag()
                == JvMessageCtrl.TypeFlags.FALSE) {
            setEnabled(true);
            openErrorPane();
        }
    }

    private void openErrorPane() {
        switch (JvMessageCtrl.getInstance().getErrorRegistrationFlag()) {
            case NoError -> new JvAuthOptionPane("Ошибка не выяснена.", JvAuthOptionPane.TypeDlg.ERROR);
            case Login -> new JvAuthOptionPane("Данный логин уже используется.", JvAuthOptionPane.TypeDlg.ERROR);
            case Email -> new JvAuthOptionPane("Данная почта уже используется.", JvAuthOptionPane.TypeDlg.ERROR);
            case LoginAndEmail ->
                    new JvAuthOptionPane("Данные почта и логин уже используются.", JvAuthOptionPane.TypeDlg.ERROR);
        }
    }
}