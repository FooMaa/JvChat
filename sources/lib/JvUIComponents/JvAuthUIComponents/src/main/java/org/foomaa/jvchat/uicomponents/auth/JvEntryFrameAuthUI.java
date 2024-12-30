package org.foomaa.jvchat.uicomponents.auth;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import org.foomaa.jvchat.ctrl.JvGetterControls;
import org.foomaa.jvchat.ctrl.JvMessagesDefinesCtrl;
import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.messages.JvDefinesMessages;
import org.foomaa.jvchat.settings.JvDisplaySettings;
import org.foomaa.jvchat.settings.JvGetterSettings;
import org.foomaa.jvchat.uicomponents.mainchat.JvGetterMainChatUIComponents;


public class JvEntryFrameAuthUI extends JFrame {
    private final JPanel panel;
    private final JvLabelAuthUI tInfo;
    private final JvTextFieldAuthUI tLogin;
    private final JvLabelAuthUI tErrorHelpInfo;
    private final JvPasswordFieldAuthUI tPassword;
    private final JvButtonAuthUI bEnter;
    private final JvActiveLabelAuthUI activeRegisterLabel;
    private final JvActiveLabelAuthUI activeMissLabel;

    JvEntryFrameAuthUI() {
        super("EntryWindow");

        panel = new JPanel();
        tInfo = JvGetterAuthUIComponents.getInstance().getBeanLabelAuthUI("Введите данные для входа:");
        tLogin = JvGetterAuthUIComponents.getInstance().getBeanTextFieldAuthUI("Логин");
        tErrorHelpInfo = JvGetterAuthUIComponents.getInstance().getBeanLabelAuthUI("");
        tErrorHelpInfo.settingToError();
        tPassword = JvGetterAuthUIComponents.getInstance().getBeanPasswordFieldAuthUI("Пароль");
        bEnter = JvGetterAuthUIComponents.getInstance().getBeanButtonAuthUI("ВОЙТИ");
        activeMissLabel = JvGetterAuthUIComponents.getInstance().getBeanActiveLabelAuthUI("Не помню пароль");
        activeRegisterLabel = JvGetterAuthUIComponents.getInstance().getBeanActiveLabelAuthUI("Регистрация");

        setIconImageFrame("/MainAppIcon.png");
        makeFrameSetting();
        addListenerToElements();
        addGeneralSettingsToWidget();
    }

    private void setIconImageFrame(String path) {
        try {
            Image img = ImageIO.read(Objects.requireNonNull(getClass().getResource(path)));
            setIconImage(img);
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    private void makeFrameSetting() {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        int insX = JvGetterSettings.getInstance().getBeanDisplaySettings().
                getResizeFromDisplay(0.025,
                        JvDisplaySettings.TypeOfDisplayBorder.WIDTH);
        int gridyNum = 0;

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
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
        gbc.insets = new Insets(0, insX, 0, insX);
        gbc.gridy = gridyNum;
        panel.add(tPassword, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 0,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.004), 0);
        gbc.gridy = gridyNum;
        panel.add(activeMissLabel, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 0,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.0084), 0);
        gbc.gridy = gridyNum;
        panel.add(activeRegisterLabel, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, insX,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.0084), insX);
        gbc.gridy = gridyNum;
        panel.add(tErrorHelpInfo, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.PAGE_END;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets(0, 0,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.017), 0);
        gbc.ipadx = JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.03,
                JvDisplaySettings.TypeOfDisplayBorder.WIDTH);
        gbc.ipady = JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.01,
                JvDisplaySettings.TypeOfDisplayBorder.HEIGHT);
        gbc.gridy = gridyNum;
        panel.add(bEnter, gbc);

        getContentPane().add(panel);
    }

    private void addListenerToElements() {
        bEnter.addActionListener(event -> {
            if (checkFields()) {
                JvGetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(JvDefinesMessages.TypeMessage.EntryRequest,
                        tLogin.getInputText(), tPassword.getInputText());
                waitRepeatServer();
            }
        });

        activeMissLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JvGetterAuthUIComponents.getInstance().getBeanResetPasswordFrameAuthUI().openWindow();
                closeWindow();
            }
        });

        activeRegisterLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JvGetterAuthUIComponents.getInstance().getBeanRegistrationFrameAuthUI().openWindow();
                closeWindow();
            }
        });

    }

    private boolean checkFields() {
        tPassword.setNormalBorder();
        tLogin.setNormalBorder();
        tErrorHelpInfo.setText("");

        Vector<String> fields = new Vector<>();

        if (Objects.equals(tLogin.getInputText(), "")) {
            tLogin.setErrorBorder();
            fields.add("\"Логин\"");
        }
        if (Objects.equals(tPassword.getInputText(), "")) {
            tPassword.setErrorBorder();
            fields.add("\"Пароль\"");
        }

        StringBuilder concatFields = new StringBuilder();
        if (!fields.isEmpty()) {
            for (int i = 0; i < fields.size(); i++) {
                concatFields.append(fields.elementAt(i)).append(", ");
            }
            concatFields = new StringBuilder(concatFields.substring(0, concatFields.length() - 2));
            if (fields.size() == 1) {
                tErrorHelpInfo.setText(String.format("Поле %s должно быть заполнено", concatFields));
            } else {
                tErrorHelpInfo.setText(String.format("Поля %s должны быть заполнены", concatFields));
            }
            return false;
        }
        return true;
    }

    private void closeWindow() {
        //dispose();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(false);
        tLogin.setUnfocusFieldOnClose(true);
        tPassword.setUnfocusFieldOnClose(true);
    }

    public void openWindow() {
        setVisible(true);
    }

    private void addGeneralSettingsToWidget() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("ВХОД");

        setSize(JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.3,
                        JvDisplaySettings.TypeOfDisplayBorder.WIDTH),
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.3,
                        JvDisplaySettings.TypeOfDisplayBorder.HEIGHT));

        setResizable(false);
        setLocationRelativeTo(null);
        toFront();

        getRootPane().setDefaultButton(bEnter);

        setVisible(true);
        requestFocus();
    }

    private void waitRepeatServer() {
        setEnabled(false);
        while (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getEntryRequestFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.DEFAULT) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException exception) {
                JvLog.write(JvLog.TypeLog.Error, "Не удалось ждать");
            }
        }
        if (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getEntryRequestFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.TRUE) {
            openMainPage();
        } else if (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getEntryRequestFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.FALSE) {
            setEnabled(true);
            JvGetterAuthUIComponents.getInstance()
                    .getBeanOptionPaneAuthUI("Вход не выполнен, данные не верные.", JvOptionPaneAuthUI.TypeDlg.ERROR);
        }
    }

    private void openMainPage() {
        JvGetterSettings.getInstance().getBeanUsersInfoSettings().setLogin(tLogin.getInputText());

        JvGetterControls.getInstance().getBeanSendMessagesCtrl()
                .sendMessage(JvDefinesMessages.TypeMessage.CheckOnlineUserReply, tLogin.getInputText());

        closeWindow();
        setEnabled(true);

        JvGetterMainChatUIComponents.getInstance().getBeanMainFrameMainChatUI().openWindow();

        JvLog.write(JvLog.TypeLog.Info, "Вход выполнен");
    }
}