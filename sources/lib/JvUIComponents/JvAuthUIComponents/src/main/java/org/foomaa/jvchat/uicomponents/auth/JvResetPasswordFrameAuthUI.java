package org.foomaa.jvchat.uicomponents.auth;

import org.foomaa.jvchat.ctrl.JvGetterControls;
import org.foomaa.jvchat.ctrl.JvMessagesDefinesCtrl;
import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.messages.JvDefinesMessages;
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


public class JvResetPasswordFrameAuthUI extends JFrame {
    private static JvResetPasswordFrameAuthUI instance;
    private final JPanel panel;
    private final JvLabelAuthUI tInfo;
    private final JvTextFieldAuthUI tEmail;
    private final JvLabelAuthUI tErrorHelpInfo;
    private final JvButtonAuthUI bSet;

    private JvResetPasswordFrameAuthUI() {
        super("ResetPasswordWindow");

        panel = new JPanel();
        tInfo = JvGetterAuthUIComponents.getInstance().getBeanLabelAuthUI("Введите адрес почты:");
        tEmail = JvGetterAuthUIComponents.getInstance().getBeanTextFieldAuthUI("Почта");
        tErrorHelpInfo = JvGetterAuthUIComponents.getInstance().getBeanLabelAuthUI("");
        tErrorHelpInfo.settingToError();
        bSet = JvGetterAuthUIComponents.getInstance().getBeanButtonAuthUI("ОТПРАВИТЬ");

        makeFrameSetting();
        addListenerToElements();
        addGeneralSettingsToWidget();
    }

    static JvResetPasswordFrameAuthUI getInstance() {
        if (instance == null) {
            instance = new JvResetPasswordFrameAuthUI();
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
        panel.add(tEmail, gbc);
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
                JvGetterControls.getInstance()
                        .getBeanSendMessagesCtrl().sendMessage(JvDefinesMessages.TypeMessage.ResetPasswordRequest,
                        tEmail.getInputText());
                waitRepeatServer();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JvGetterAuthUIComponents.getInstance().getBeanEntryFrameAuthUI().openWindow();
                tEmail.setUnfocusFieldOnClose(false);
            }
        });
    }

    private boolean checkFields() {
        tEmail.setNormalBorder();
        tErrorHelpInfo.setText("");

        Vector<String> fields = new Vector<>();

        if (Objects.equals(tEmail.getInputText(), "") ||
                !JvGetterTools.getInstance().getBeanUsersTools().validateInputEmail(tEmail.getInputText())) {
            tEmail.setErrorBorder();
            fields.add("\"Почта\"");
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
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.25,
                        JvDisplaySettings.TypeOfDisplayBorder.HEIGHT));

        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        toFront();

        getRootPane().setDefaultButton(bSet);

        setVisible(true);
        requestFocus();
    }

    private void closeWindow() {
        JvVerifyCodeFrameAuthUI frm = JvGetterAuthUIComponents.getInstance()
                .getBeanVerifyCodeFrameAuthUI(JvVerifyCodeFrameAuthUI.RegimeWork.ResetPassword);
        frm.setRegime(JvVerifyCodeFrameAuthUI.RegimeWork.ResetPassword);
        frm.setParametersResetPassword(tEmail.getInputText());
        frm.openWindow();
        setVisible(false);
        tEmail.setUnfocusFieldOnClose(false);
        //dispose();
    }

    public void openWindow() {
        setVisible(true);
    }

    private void waitRepeatServer() {
        setEnabled(false);
        while (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getResetPasswordRequestFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.DEFAULT) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException exception) {
                JvLog.write(JvLog.TypeLog.Error, "Не удалось ждать");
            }
        }
        if (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getResetPasswordRequestFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.TRUE) {
            closeWindow();
            setEnabled(true);
        } else if (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getResetPasswordRequestFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.FALSE) {
            setEnabled(true);
            JvGetterAuthUIComponents.getInstance()
                    .getBeanOptionPaneAuthUI("Данная почта не зарегистрирована.", JvOptionPaneAuthUI.TypeDlg.ERROR);
        }
    }
}