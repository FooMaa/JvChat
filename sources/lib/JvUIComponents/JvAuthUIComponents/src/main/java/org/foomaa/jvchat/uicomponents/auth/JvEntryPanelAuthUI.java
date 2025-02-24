package org.foomaa.jvchat.uicomponents.auth;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import org.foomaa.jvchat.ctrl.JvGetterControls;
import org.foomaa.jvchat.ctrl.JvMessagesDefinesCtrl;
import org.foomaa.jvchat.events.JvGetterEvents;
import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.messages.JvDefinesMessages;
import org.foomaa.jvchat.settings.JvDisplaySettings;
import org.foomaa.jvchat.settings.JvGetterSettings;
import org.foomaa.jvchat.uicomponents.mainchat.JvGetterMainChatUIComponents;


public class JvEntryPanelAuthUI extends JPanel {
    private final JvTextFieldAuthUI tLogin;
    private final JvErrorLabelAuthUI tErrorHelpInfo;
    private final JvPasswordFieldAuthUI tPassword;
    private final JvButtonAuthUI bEnter;
    private final JvActiveLabelAuthUI activeRegisterLabel;
    private final JvActiveLabelAuthUI activeMissLabel;
    private final String backgroundPath;

    JvEntryPanelAuthUI() {
        tLogin = JvGetterAuthUIComponents.getInstance().getBeanTextFieldAuthUI("Login");
        tErrorHelpInfo = JvGetterAuthUIComponents.getInstance().getBeanErrorLabelAuthUI("");
        tPassword = JvGetterAuthUIComponents.getInstance().getBeanPasswordFieldAuthUI("Password");
        bEnter = JvGetterAuthUIComponents.getInstance().getBeanButtonAuthUI("NEXT");
        activeMissLabel = JvGetterAuthUIComponents.getInstance().getBeanActiveLabelAuthUI("Reset password");
        activeRegisterLabel = JvGetterAuthUIComponents.getInstance().getBeanActiveLabelAuthUI("Registration");
        backgroundPath = "/AuthMainBackground.png";

        settingComponents();
        makePanelSetting();
        addListenerToElements();
    }

    private void settingComponents() {
        tErrorHelpInfo.settingToError();

        bEnter.setToolTip("To log in");
        tPassword.setToolTip("To set password");
        tLogin.setToolTip("To set login");
        activeMissLabel.setToolTip("To the password recovery form");
        activeRegisterLabel.setToolTip("To registration form");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image img = null;
        try {
            img = ImageIO.read(Objects.requireNonNull(getClass().getResource(backgroundPath)));
        } catch (IOException e) {
            e.getStackTrace();
        }

        if (img == null) {
            JvLog.write(JvLog.TypeLog.Error, "Here img turned out to be equal to null.");
            return;
        }

        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }

    private void makePanelSetting() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        int insX = JvGetterSettings.getInstance().getBeanDisplaySettings().
                getResizeFromDisplay(0.025, JvDisplaySettings.TypeOfDisplayBorder.WIDTH);
        int gridyNum = 0;

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.075), insX,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.004), insX);
        gbc.gridy = gridyNum;
        add(tLogin, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, insX, JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.01), insX);
        gbc.gridy = gridyNum;
        add(tPassword, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridy = gridyNum;
        add(activeMissLabel, gbc);
        gridyNum++;

        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 0,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.002), 0);
        gbc.gridy = gridyNum;
        add(activeRegisterLabel, gbc);
        gridyNum++;

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, insX,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.0084), insX);
        gbc.gridy = gridyNum;
        add(tErrorHelpInfo, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets(0, 0,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.017), 0);
        gbc.ipadx = JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.015,
                JvDisplaySettings.TypeOfDisplayBorder.WIDTH);
        gbc.ipady = JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.004,
                JvDisplaySettings.TypeOfDisplayBorder.HEIGHT);
        gbc.gridy = gridyNum;
        add(bEnter, gbc);
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
                changeRegime(JvDefinesAuthUI.RegimeWorkMainFrame.ResetPassword);
            }
        });

        activeRegisterLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                changeRegime(JvDefinesAuthUI.RegimeWorkMainFrame.Registration);
            }
        });
    }

    private void closeFrameWindow() {
        JvGetterEvents.getInstance().getBeanMakerEvents().event(this, "closeWindow");
        tLogin.setUnfocusFieldOnClose(true);
        tPassword.setUnfocusFieldOnClose(true);
    }

    private void changeRegime(JvDefinesAuthUI.RegimeWorkMainFrame regime) {
        JvGetterEvents.getInstance().getBeanMakerEvents().event(this, "changeRegimeWork", regime);
        tLogin.setUnfocusFieldOnClose(true);
        tPassword.setUnfocusFieldOnClose(true);
    }

    private boolean checkFields() {
        tPassword.setNormalBorder();
        tLogin.setNormalBorder();
        tErrorHelpInfo.setText("");

        Vector<String> fields = new Vector<>();

        if (Objects.equals(tLogin.getInputText(), "")) {
            tLogin.setErrorBorder();
            fields.add("\"Login\"");
        }
        if (Objects.equals(tPassword.getInputText(), "")) {
            tPassword.setErrorBorder();
            fields.add("\"Password\"");
        }

        StringBuilder concatFields = new StringBuilder();
        if (!fields.isEmpty()) {
            for (int i = 0; i < fields.size(); i++) {
                concatFields.append(fields.elementAt(i)).append(", ");
            }
            concatFields = new StringBuilder(concatFields.substring(0, concatFields.length() - 2));
            if (fields.size() == 1) {
                tErrorHelpInfo.setText(String.format("Fill in the field %s!", concatFields));
            } else {
                tErrorHelpInfo.setText(String.format("Fill in the fields %s!", concatFields));
            }
            return false;
        }
        return true;
    }

    private void waitRepeatServer() {
        setEnabled(false);
        while (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getEntryRequestFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.DEFAULT) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException exception) {
                JvLog.write(JvLog.TypeLog.Error, "Couldn't wait.");
            }
        }
        if (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getEntryRequestFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.TRUE) {
            openMainPage();
        } else if (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getEntryRequestFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.FALSE) {
            setEnabled(true);
            JvGetterAuthUIComponents.getInstance()
                    .getBeanOptionPaneAuthUI("Login failed, data is incorrect.", JvOptionPaneAuthUI.TypeDlg.ERROR);
        }
    }

    private void openMainPage() {
        JvGetterSettings.getInstance().getBeanUsersInfoSettings().setLogin(tLogin.getInputText());

        UUID uuidUser = JvGetterSettings.getInstance().getBeanUsersInfoSettings().getUuid();
        JvGetterControls.getInstance().getBeanSendMessagesCtrl()
                .sendMessage(JvDefinesMessages.TypeMessage.CheckOnlineUserReply, uuidUser);

        closeFrameWindow();
        setEnabled(true);

        JvGetterMainChatUIComponents.getInstance().getBeanMainFrameMainChatUI().openWindow();

        JvLog.write(JvLog.TypeLog.Info, "Login done.");
    }

    public JvButtonAuthUI getDefaultButton() {
        return bEnter;
    }
}