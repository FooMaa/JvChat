package org.foomaa.jvchat.uicomponents.auth;

import org.foomaa.jvchat.ctrl.JvGetterControls;
import org.foomaa.jvchat.ctrl.JvMessagesDefinesCtrl;
import org.foomaa.jvchat.messages.JvMessagesDefines;
import org.foomaa.jvchat.settings.JvDisplaySettings;
import org.foomaa.jvchat.settings.JvGetterSettings;
import org.foomaa.jvchat.tools.JvGetterTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class JvAuthRegistrationFrame extends JFrame {
    private static JvAuthRegistrationFrame instance;
    private final JPanel panel;
    private final JvAuthLabel tInfo;
    private final JvAuthTextField tLogin;
    private final JvAuthTextField tEmail;
    private final JvAuthLabel tErrorHelpInfo;
    private final JvAuthPasswordField tPassword;
    private final JvAuthPasswordField tPasswordConfirm;
    private final JvAuthButton bRegister;

    private JvAuthRegistrationFrame() {
        super("RegistrationWindow");

        panel = new JPanel();
        tInfo = JvGetterAuthUiComponents.getInstance().getBeanAuthLabel("Введите данные для регистрации:");
        tLogin = JvGetterAuthUiComponents.getInstance().getBeanAuthTextField("Логин");
        tEmail = JvGetterAuthUiComponents.getInstance().getBeanAuthTextField("Почта");
        tErrorHelpInfo = JvGetterAuthUiComponents.getInstance().getBeanAuthLabel("");
        tErrorHelpInfo.settingToError();
        tPassword = JvGetterAuthUiComponents.getInstance().getBeanAuthPasswordField("Пароль");
        tPasswordConfirm = JvGetterAuthUiComponents.getInstance().getBeanAuthPasswordField("Подтвердите пароль");
        bRegister = JvGetterAuthUiComponents.getInstance().getBeanAuthButton("РЕГИСТРАЦИЯ");

        makeFrameSetting();
        addListenerToElements();
        addGeneralSettingsToWidget();
    }

    public static JvAuthRegistrationFrame getInstance() {
        if (instance == null) {
            instance = new JvAuthRegistrationFrame();
        }
        return instance;
    }

    private void makeFrameSetting() {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        int insX = JvGetterSettings.getInstance().getBeanDisplaySettings().
                getResizeFromDisplay(0.025,
                        JvDisplaySettings.TypeOfDisplayBorder.WIDTH);
        int gridyNum = 0;

        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.PAGE_START;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.0125), 0,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.0084), 0);
        gbc.gridy = gridyNum;
        panel.add(tInfo, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, insX,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.004), insX);
        gbc.gridy = gridyNum;
        panel.add(tLogin, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, insX,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.004), insX);
        gbc.gridy = gridyNum;
        panel.add(tEmail, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, insX,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.004), insX);
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
        gbc.insets = new Insets(0, insX,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.0084), insX);
        gbc.gridy = gridyNum;
        panel.add(tErrorHelpInfo, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.PAGE_END;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 0,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.017), 0);
        gbc.ipadx = JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.03,
                JvDisplaySettings.TypeOfDisplayBorder.WIDTH);
        gbc.ipady = JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.01,
                JvDisplaySettings.TypeOfDisplayBorder.HEIGHT);
        gbc.gridy = gridyNum;
        panel.add(bRegister, gbc);

        getContentPane().add(panel);
    }

    private void addListenerToElements() {
        bRegister.addActionListener(event -> {
            if (checkFields()) {
                JvGetterControls.getInstance()
                        .getBeanSendMessagesCtrl().sendMessage(JvMessagesDefines.TypeMessage.RegistrationRequest,
                        tLogin.getInputText(), tEmail.getInputText(), tPassword.getInputText());
                waitRepeatServer();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JvGetterAuthUiComponents.getInstance().getBeanAuthEntryFrame().openWindow();
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
                !JvGetterTools.getInstance().getBeanMainTools().validateInputEmail(tEmail.getInputText())) {
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
        setSize(JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.3,
                        JvDisplaySettings.TypeOfDisplayBorder.WIDTH),
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.30,
                        JvDisplaySettings.TypeOfDisplayBorder.HEIGHT));
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        toFront();
        setVisible(true);
        requestFocus();
    }

    private void closeWindow() {
        JvAuthVerifyCodeFrame frm = JvGetterAuthUiComponents.getInstance()
                .getBeanAuthVerifyCodeFrame(JvAuthVerifyCodeFrame.RegimeWork.Registration);
        frm.setRegime(JvAuthVerifyCodeFrame.RegimeWork.Registration);
        frm.setParametersRegistration(tLogin.getInputText(), tEmail.getInputText(), tPassword.getInputText());
        frm.openWindow();
        setVisible(false);
        //dispose();
    }

    public void openWindow() {
        setVisible(true);
    }

    private void waitRepeatServer() {
        setEnabled(false);
        while (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getRegistrationRequestFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.DEFAULT) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException exception) {
                System.out.println("Не удалось ждать");
            }
        }
        if (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getRegistrationRequestFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.TRUE) {
            closeWindow();
            setEnabled(true);
        } else if (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getRegistrationRequestFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.FALSE) {
            setEnabled(true);
            openErrorPane();
        }
    }

    private void openErrorPane() {
        switch (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getErrorRegistrationFlag()) {
            case NoError -> JvGetterAuthUiComponents.getInstance()
                    .getBeanAuthOptionPane("Ошибка не выяснена.", JvAuthOptionPane.TypeDlg.ERROR);
            case EmailSending -> JvGetterAuthUiComponents.getInstance()
                    .getBeanAuthOptionPane("Возможно почта недействительна.", JvAuthOptionPane.TypeDlg.ERROR);
            case Login -> JvGetterAuthUiComponents.getInstance()
                    .getBeanAuthOptionPane("Данный логин уже используется.", JvAuthOptionPane.TypeDlg.ERROR);
            case Email -> JvGetterAuthUiComponents.getInstance()
                    .getBeanAuthOptionPane("Данная почта уже используется.", JvAuthOptionPane.TypeDlg.ERROR);
            case LoginAndEmail ->
                    JvGetterAuthUiComponents.getInstance()
                            .getBeanAuthOptionPane("Данные почта и логин уже используются.", JvAuthOptionPane.TypeDlg.ERROR);
        }
    }
}