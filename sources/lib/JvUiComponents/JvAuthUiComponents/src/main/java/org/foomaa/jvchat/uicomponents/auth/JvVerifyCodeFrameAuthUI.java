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


public class JvVerifyCodeFrameAuthUI extends JFrame {
    private static JvVerifyCodeFrameAuthUI instance;
    private final JPanel panel;
    private final JvLabelAuthUI tInfo;
    private final JvTextFieldAuthUI tCode;
    private final JvLabelAuthUI tErrorHelpInfo;
    private final JvButtonAuthUI bSet;
    private String login;
    private String email;
    private String password;
    private RegimeWork regime;

    public enum RegimeWork {
        Registration,
        ResetPassword
    }

    private JvVerifyCodeFrameAuthUI(RegimeWork rw) {
        super("VerifyCodeWindow");

        regime = rw;
        panel = new JPanel();
        tInfo = JvGetterAuthUIComponents.getInstance().getBeanLabelAuthUI("Введите код из почты (действует 60 с.):");
        tCode = JvGetterAuthUIComponents.getInstance().getBeanTextFieldAuthUI("Код");
        tErrorHelpInfo = JvGetterAuthUIComponents.getInstance().getBeanLabelAuthUI("");
        tErrorHelpInfo.settingToError();
        bSet = JvGetterAuthUIComponents.getInstance().getBeanButtonAuthUI("ОТПРАВИТЬ");

        makeFrameSetting();
        addListenerToElements();
        addGeneralSettingsToWidget();
    }

    public static JvVerifyCodeFrameAuthUI getInstance(RegimeWork rw) {
        if (instance == null) {
            instance = new JvVerifyCodeFrameAuthUI(rw);
        } else {
            instance.setRegime(rw);
        }
        return instance;
    }

    public void setRegime(RegimeWork newRegime) {
        if (regime != newRegime) {
            regime = newRegime;
        }
    }

    public void setParametersRegistration(String pLogin, String pEmail, String pPassword) {
        login = pLogin;
        email = pEmail;
        password = pPassword;
    }

    public void setParametersResetPassword(String pEmail) {
        email = pEmail;
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
        panel.add(tCode, gbc);
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
        panel.add(bSet, gbc);

        getContentPane().add(panel);
    }

    private void addListenerToElements() {
        bSet.addActionListener(event -> {
            if (checkFields()) {
                if (regime == RegimeWork.ResetPassword) {
                    JvGetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(JvDefinesMessages.TypeMessage.VerifyFamousEmailRequest,
                            email, tCode.getInputText());
                    waitRepeatServerResetPassword();
                } else if (regime == RegimeWork.Registration) {
                    JvGetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(JvDefinesMessages.TypeMessage.VerifyRegistrationEmailRequest,
                            login, email, password, tCode.getInputText());
                    waitRepeatServerRegistration();
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JvGetterAuthUIComponents.getInstance().getBeanEntryFrameAuthUI().openWindow();
                tCode.setUnfocusFieldOnClose(false);
            }
        });
    }

    private boolean checkFields() {
        tCode.setNormalBorder();
        tErrorHelpInfo.setText("");

        Vector<String> fields = new Vector<>();

        if (Objects.equals(tCode.getInputText(), "") ||
                (tCode.getInputText().length() != 6 )) {
            tCode.setErrorBorder();
            fields.add("\"Код\"");
        }

        StringBuilder concatFields = new StringBuilder();
        if (!fields.isEmpty()) {
            for (int i = 0; i < fields.size(); i++) {
                concatFields.append(fields.elementAt(i)).append(", ");
            }
            concatFields = new StringBuilder(concatFields.substring(0, concatFields.length() - 2));
            if (fields.size() == 1) {
                tErrorHelpInfo.setText(String.format("Поле %s должно быть заполнено и содержать отправленный код", concatFields));
            } else {
                tErrorHelpInfo.setText(String.format("Поля %s должны быть заполнены и содержать отправленный код", concatFields));
            }
            return false;
        }
        return true;
    }

    private void addGeneralSettingsToWidget() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("ПОДТВЕРЖДЕНИЕ ПОЧТЫ");
        setSize(JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.3,
                        JvDisplaySettings.TypeOfDisplayBorder.WIDTH),
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.25,
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
        if (regime == RegimeWork.Registration) {
            JvGetterAuthUIComponents.getInstance().getBeanEntryFrameAuthUI().openWindow();
        } else if (regime == RegimeWork.ResetPassword) {
            JvGetterAuthUIComponents.getInstance().getBeanNewPasswordFrameAuthUI(email).openWindow();
        }
        tCode.setUnfocusFieldOnClose(false);
    }

    public void openWindow() {
        setVisible(true);
    }

    private void waitRepeatServerResetPassword() {
        setEnabled(false);
        while (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getVerifyFamousEmailRequestFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.DEFAULT) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException exception) {
                JvLog.write(JvLog.TypeLog.Error, "Не удалось ждать");
            }
        }
        if (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getVerifyFamousEmailRequestFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.TRUE) {
            closeWindow();
            setEnabled(true);
        } else if (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getVerifyFamousEmailRequestFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.FALSE) {
            setEnabled(true);
            JvGetterAuthUIComponents.getInstance()
                    .getBeanOptionPaneAuthUI("Код не верен. Введите код полученный по почте еще раз. " +
                            "Мог истечь срок действия кода, введите почту и получите новый.", JvOptionPaneAuthUI.TypeDlg.ERROR);
        }
    }

    private void waitRepeatServerRegistration() {
        setEnabled(false);
        while (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getVerifyRegistrationEmailRequestFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.DEFAULT) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException exception) {
                JvLog.write(JvLog.TypeLog.Error, "Не удалось ждать");
            }
        }
        if (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getVerifyRegistrationEmailRequestFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.TRUE) {
            closeWindow();
            setEnabled(true);
        } else if (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getVerifyRegistrationEmailRequestFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.FALSE) {
            setEnabled(true);
            openErrorPane();
        }
    }

    private void openErrorPane() {
        switch (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getErrorVerifyRegEmailFlag()) {
            case NoError -> JvGetterAuthUIComponents.getInstance()
                    .getBeanOptionPaneAuthUI("Ошибка не выяснена.", JvOptionPaneAuthUI.TypeDlg.ERROR);
            case EmailSending -> JvGetterAuthUIComponents.getInstance()
                    .getBeanOptionPaneAuthUI("Возможно почта недействительна.", JvOptionPaneAuthUI.TypeDlg.ERROR);
            case Login -> JvGetterAuthUIComponents.getInstance()
                    .getBeanOptionPaneAuthUI("Данный логин уже используется.", JvOptionPaneAuthUI.TypeDlg.ERROR);
            case Email -> JvGetterAuthUIComponents.getInstance()
                    .getBeanOptionPaneAuthUI("Данная почта уже используется.", JvOptionPaneAuthUI.TypeDlg.ERROR);
            case Code -> JvGetterAuthUIComponents.getInstance()
                    .getBeanOptionPaneAuthUI("Код не верен. Введите код полученный по почте еще раз. " +
                    "Мог истечь срок действия кода, введите почту и получите новый.", JvOptionPaneAuthUI.TypeDlg.ERROR);
            case LoginAndEmail ->
                    JvGetterAuthUIComponents.getInstance()
                            .getBeanOptionPaneAuthUI("Данные почта и логин уже используются.", JvOptionPaneAuthUI.TypeDlg.ERROR);
        }
    }
}