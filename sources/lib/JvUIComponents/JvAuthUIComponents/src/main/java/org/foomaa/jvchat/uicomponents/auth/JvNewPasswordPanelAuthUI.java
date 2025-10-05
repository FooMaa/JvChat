package org.foomaa.jvchat.uicomponents.auth;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import org.foomaa.jvchat.ctrl.GetterControls;
import org.foomaa.jvchat.ctrl.MessagesDefinesCtrl;
import org.foomaa.jvchat.events.GetterEvents;
import org.foomaa.jvchat.logger.Log;
import org.foomaa.jvchat.messages.DefinesMessages;
import org.foomaa.jvchat.settings.DisplaySettings;
import org.foomaa.jvchat.settings.GetterSettings;


public class JvNewPasswordPanelAuthUI extends JPanel {
    private final JvErrorLabelAuthUI tErrorHelpInfo;
    private final JvPasswordFieldAuthUI tPassword;
    private final JvPasswordFieldAuthUI tPasswordConfirm;
    private final JvButtonAuthUI bAccept;
    private final JvButtonAuthUI bBack;
    private String email;

    JvNewPasswordPanelAuthUI() {
        tErrorHelpInfo = JvGetterAuthUIComponents.getInstance().getBeanErrorLabelAuthUI("");
        tErrorHelpInfo.settingToError();
        tPassword = JvGetterAuthUIComponents.getInstance().getBeanPasswordFieldAuthUI("Password");
        tPasswordConfirm = JvGetterAuthUIComponents.getInstance().getBeanPasswordFieldAuthUI("Confirm password");
        bAccept = JvGetterAuthUIComponents.getInstance().getBeanButtonAuthUI("Accept");
        bBack = JvGetterAuthUIComponents.getInstance().getBeanButtonAuthUI("Back");

        settingComponents();
        makePanelSetting();
        addListenerToElements();
        makePanelTransparent();
    }

    private void settingComponents() {
        tErrorHelpInfo.settingToError();

        tPassword.setToolTip("To set password");
        tPasswordConfirm.setToolTip("To confirm password");
        bAccept.setToolTip("To accept new password");
        bBack.setToolTip("To go back");
    }

    private void makePanelTransparent() {
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
    }

    public void setEmail(String newEmail) {
        if (!Objects.equals(email, newEmail)) {
            email = newEmail;
        }
    }

    private void makePanelSetting() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        int insX = GetterSettings.getInstance().getBeanDisplaySettings().
                getResizeFromDisplay(0.025,
                        DisplaySettings.TypeOfDisplayBorder.WIDTH);
        int gridyNum = 0;

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(GetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.085), insX,
                GetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.004), insX);
        gbc.gridy = gridyNum;
        add(tPassword, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, insX, 0, insX);
        gbc.gridy = gridyNum;
        add(tPasswordConfirm, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, insX,
                GetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.0104), insX);
        gbc.gridy = gridyNum;
        add(tErrorHelpInfo, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.PAGE_END;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, GetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.026),
                GetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.017), 0);
        gbc.ipadx = GetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.015,
                DisplaySettings.TypeOfDisplayBorder.WIDTH);
        gbc.ipady = GetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.004,
                DisplaySettings.TypeOfDisplayBorder.HEIGHT);
        gbc.gridy = gridyNum;
        add(bBack, gbc);

        gbc.fill = GridBagConstraints.PAGE_END;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.insets = new Insets(0, 0,
                GetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.017), GetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.026));

        gbc.ipadx = GetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.015,
                DisplaySettings.TypeOfDisplayBorder.WIDTH);
        gbc.ipady = GetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.004,
                DisplaySettings.TypeOfDisplayBorder.HEIGHT);
        gbc.gridy = gridyNum;
        add(bAccept, gbc);
    }

    private void addListenerToElements() {
        bAccept.addActionListener(event -> {
            if (checkFields()) {
                GetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(DefinesMessages.TypeMessage.ChangePasswordRequest,
                        email, tPassword.getInputText());
                waitRepeatServer();
            }
        });

        bBack.addActionListener(event -> changeRegimeBack());
    }

    private boolean checkFields() {
        tPassword.setErrorBorder(false);
        tPasswordConfirm.setErrorBorder(false);
        tErrorHelpInfo.setText("");

        Vector<String> fields = new Vector<>();

        if (Objects.equals(tPassword.getInputText(), "")) {
            tPassword.setErrorBorder(true);
            fields.add("\"Password\"");
        }
        if (Objects.equals(tPasswordConfirm.getInputText(), "")) {
            tPasswordConfirm.setErrorBorder(true);
            fields.add("\"Confirm password\"");
        }

        if (!Objects.equals(tPassword.getInputText(), "") &&
                !Objects.equals(tPasswordConfirm.getInputText(), "") &&
                !Objects.equals(tPassword.getInputText(), tPasswordConfirm.getInputText())) {
            tPassword.setErrorBorder(true);
            tPasswordConfirm.setErrorBorder(true);
            tErrorHelpInfo.setText("The entered passwords must match.");
            return false;
        }

        StringBuilder concatFields = new StringBuilder();
        if (!fields.isEmpty()) {
            for (int i = 0; i < fields.size(); i++) {
                concatFields.append(fields.elementAt(i)).append(", ");
            }
            concatFields = new StringBuilder(concatFields.substring(0, concatFields.length() - 2));
            if (fields.size() == 1) {
                tErrorHelpInfo.setText(String.format("The %s field must be completed or corrected", concatFields));
            } else {
                tErrorHelpInfo.setText(String.format("The %s fields must be completed or corrected", concatFields));
            }
            return false;
        }
        return true;
    }

    public JvButtonAuthUI getDefaultButton() {
        return bAccept;
    }

    private void changeRegimeBack() {
        GetterEvents.getInstance().getBeanMakerEvents().event(
                this,
                "changeRegimeWork",
                JvDefinesAuthUI.RegimeWorkMainFrame.ResetPassword);
        settingUnfocusFieldsOnChangeRegime();
    }

    private void changeRegimeNext() {
        GetterEvents.getInstance().getBeanMakerEvents().event(
                this,
                "changeRegimeWork",
                JvDefinesAuthUI.RegimeWorkMainFrame.Auth);
        settingUnfocusFieldsOnChangeRegime();
    }

    private void settingUnfocusFieldsOnChangeRegime() {
        tPassword.setUnfocusFieldOnClose(false);
        tPasswordConfirm.setUnfocusFieldOnClose(false);
    }

    public void openWindow() {
        setVisible(true);
    }

    private void waitRepeatServer() {
        setEnabled(false);
        while (GetterControls.getInstance().getBeanMessagesDefinesCtrl().getChangePasswordRequest() ==
                MessagesDefinesCtrl.TypeFlags.DEFAULT) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException exception) {
                Log.write(Log.TypeLog.Error, "Couldn't wait.");
            }
        }
        if (GetterControls.getInstance().getBeanMessagesDefinesCtrl().getChangePasswordRequest() ==
                MessagesDefinesCtrl.TypeFlags.TRUE) {
            changeRegimeNext();
            setEnabled(true);
        } else if (GetterControls.getInstance().getBeanMessagesDefinesCtrl().getChangePasswordRequest() ==
                MessagesDefinesCtrl.TypeFlags.FALSE) {
            setEnabled(true);
            JvGetterAuthUIComponents.getInstance().getBeanOptionPaneAuthUI("Failed to change password.",
                    JvOptionPaneAuthUI.TypeDlg.ERROR);
        }
    }
}