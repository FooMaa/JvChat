package org.foomaa.jvchat.uicomponents.auth;

import org.foomaa.jvchat.ctrl.JvInitControls;
import org.foomaa.jvchat.ctrl.JvMessageCtrl;
import org.foomaa.jvchat.messages.JvSerializatorData;
import org.foomaa.jvchat.settings.JvDisplaySettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class JvNewPasswordFrame extends JFrame {
    private final JPanel panel;
    private final JvAuthLabel tInfo;
    private final JvAuthLabel tErrorHelpInfo;
    private final JvAuthPasswordField tPassword;
    private final JvAuthPasswordField tPasswordConfirm;
    private final JvAuthButton bRegister;
    private final String email;

    public JvNewPasswordFrame(String post) {
        super("NewPasswordWindow");

        email = post;
        panel = new JPanel();
        tInfo = new JvAuthLabel("Введите новый пароль:");
        tErrorHelpInfo = new JvAuthLabel("");
        tErrorHelpInfo.settingToError();
        tPassword = new JvAuthPasswordField("Пароль");
        tPasswordConfirm = new JvAuthPasswordField("Подтвердите пароль");
        bRegister = new JvAuthButton("ПРИНЯТЬ");

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
                JvInitControls.getMessageCtrl().sendMessage(JvSerializatorData.TypeMessage.ChangePasswordRequest,
                        email, tPassword.getInputText());
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
        tPassword.setNormalBorder();
        tPasswordConfirm.setNormalBorder();
        tErrorHelpInfo.setText("");

        Vector<String> fields = new Vector<>();

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
        setTitle("ВОССТАНОВЛЕНИЕ ПАРОЛЯ");
        setSize(JvDisplaySettings.getResizeFromDisplay(0.3,
                        JvDisplaySettings.TypeOfDisplayBorder.WIDTH),
                JvDisplaySettings.getResizeFromDisplay(0.275,
                        JvDisplaySettings.TypeOfDisplayBorder.HEIGHT));
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        toFront();
        setVisible(true);
        requestFocus();
    }

    private void closeWindow() {
        setVisible(false);
        dispose();
        new JvEntryFrame();
    }

    private void waitRepeatServer() {
        setEnabled(false);
        while (JvInitControls.getMessageCtrl().getChangePasswordRequest()
                == JvMessageCtrl.TypeFlags.DEFAULT) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException exception) {
                System.out.println("Не удалось ждать");
            }
        }
        if (JvInitControls.getMessageCtrl().getChangePasswordRequest()
                == JvMessageCtrl.TypeFlags.TRUE) {
            closeWindow();
        } else if (JvInitControls.getMessageCtrl().getChangePasswordRequest()
                == JvMessageCtrl.TypeFlags.FALSE) {
            setEnabled(true);
            new JvAuthOptionPane("Не удалось сменить пароль.", JvAuthOptionPane.TypeDlg.ERROR);
        }
    }
}