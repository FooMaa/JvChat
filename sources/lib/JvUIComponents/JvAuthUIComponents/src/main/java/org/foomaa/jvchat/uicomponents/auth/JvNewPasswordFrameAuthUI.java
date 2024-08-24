package org.foomaa.jvchat.uicomponents.auth;

import org.foomaa.jvchat.ctrl.JvGetterControls;
import org.foomaa.jvchat.ctrl.JvMessagesDefinesCtrl;
import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.messages.JvDefinesMessages;
import org.foomaa.jvchat.settings.JvDisplaySettings;
import org.foomaa.jvchat.settings.JvGetterSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.TimeUnit;


public class JvNewPasswordFrameAuthUI extends JFrame {
    private static JvNewPasswordFrameAuthUI instance;
    private final JPanel panel;
    private final JvLabelAuthUI tInfo;
    private final JvLabelAuthUI tErrorHelpInfo;
    private final JvPasswordFieldAuthUI tPassword;
    private final JvPasswordFieldAuthUI tPasswordConfirm;
    private final JvButtonAuthUI bRegister;
    private String email;


    private JvNewPasswordFrameAuthUI(String post) {
        super("NewPasswordWindow");

        email = post;
        panel = new JPanel();
        tInfo = JvGetterAuthUIComponents.getInstance().getBeanLabelAuthUI("Введите новый пароль:");
        tErrorHelpInfo = JvGetterAuthUIComponents.getInstance().getBeanLabelAuthUI("");
        tErrorHelpInfo.settingToError();
        tPassword = JvGetterAuthUIComponents.getInstance().getBeanPasswordFieldAuthUI("Пароль");
        tPasswordConfirm = JvGetterAuthUIComponents.getInstance().getBeanPasswordFieldAuthUI("Подтвердите пароль");
        bRegister = JvGetterAuthUIComponents.getInstance().getBeanButtonAuthUI("ПРИНЯТЬ");

        makeFrameSetting();
        addListenerToElements();
        addGeneralSettingsToWidget();
    }

    public static JvNewPasswordFrameAuthUI getInstance(String post) {
        if (instance == null) {
            instance = new JvNewPasswordFrameAuthUI(post);
        } else {
            instance.setEmail(post);
        }
        return instance;
    }

    private void setEmail(String newEmail) {
        if (!Objects.equals(email, newEmail)) {
            email = newEmail;
        }
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
                JvGetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(JvDefinesMessages.TypeMessage.ChangePasswordRequest,
                        email, tPassword.getInputText());
                waitRepeatServer();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JvGetterAuthUIComponents.getInstance().getBeanEntryFrameAuthUI().openWindow();
                tPassword.setUnfocusFieldOnClose(false);
                tPasswordConfirm.setUnfocusFieldOnClose(false);
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
        setSize(JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.3,
                        JvDisplaySettings.TypeOfDisplayBorder.WIDTH),
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.275,
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
        //dispose();
        JvGetterAuthUIComponents.getInstance().getBeanEntryFrameAuthUI().openWindow();
        tPassword.setUnfocusFieldOnClose(false);
        tPasswordConfirm.setUnfocusFieldOnClose(false);
    }

    public void openWindow() {
        setVisible(true);
    }

    private void waitRepeatServer() {
        setEnabled(false);
        while (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getChangePasswordRequest() ==
                JvMessagesDefinesCtrl.TypeFlags.DEFAULT) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException exception) {
                JvLog.write(JvLog.TypeLog.Error, "Не удалось ждать");
            }
        }
        if (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getChangePasswordRequest() ==
                JvMessagesDefinesCtrl.TypeFlags.TRUE) {
            closeWindow();
            setEnabled(true);
        } else if (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getChangePasswordRequest() ==
                JvMessagesDefinesCtrl.TypeFlags.FALSE) {
            setEnabled(true);
            JvGetterAuthUIComponents.getInstance().getBeanOptionPaneAuthUI("Не удалось сменить пароль.",
                    JvOptionPaneAuthUI.TypeDlg.ERROR);
        }
    }
}