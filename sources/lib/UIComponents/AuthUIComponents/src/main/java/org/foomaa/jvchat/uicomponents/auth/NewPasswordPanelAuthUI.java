package org.foomaa.jvchat.uicomponents.auth;

import java.awt.*;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.ctrl.MessagesDefinesCtrl;
import org.foomaa.jvchat.ctrl.SendMessagesCtrl;
import org.foomaa.jvchat.messages.DefinesMessages;
import org.foomaa.jvchat.settings.DisplaySettings;
import org.foomaa.jvchat.signals.Signal;
import org.foomaa.jvchat.signals.SignalFactory;

@Slf4j
public class NewPasswordPanelAuthUI extends JPanel {
    private String email;

    // DI ↓
    private final DisplaySettings displaySettings;
    private final SendMessagesCtrl sendMessagesCtrl;
    private final MessagesDefinesCtrl messagesDefinesCtrl;
    private final OptionPaneAuthUIFactory optionPaneAuthUIFactory;

    // DI(P) ↓
    private final ErrorLabelAuthUI tErrorHelpInfo;
    private final PasswordFieldAuthUI tPassword;
    private final PasswordFieldAuthUI tPasswordConfirm;
    private final ButtonAuthUI bAccept;
    private final ButtonAuthUI bBack;

    // Signals ↓
    @Getter
    private final Signal<RecordsAuthUI.Regime> changeRegimeWork;

    @Builder
    NewPasswordPanelAuthUI(
            DisplaySettings displaySettings,
            SendMessagesCtrl sendMessagesCtrl,
            MessagesDefinesCtrl messagesDefinesCtrl,
            ButtonAuthUIFactory buttonAuthUIFactory,
            ErrorLabelAuthUIFactory errorLabelAuthUIFactory,
            PasswordFieldAuthUIFactory passwordFieldAuthUIFactory,
            OptionPaneAuthUIFactory optionPaneAuthUIFactory,
            SignalFactory signalFactory) {
        Objects.requireNonNull(buttonAuthUIFactory, "buttonAuthUIFactory is mandatory");
        Objects.requireNonNull(errorLabelAuthUIFactory, "errorLabelAuthUIFactory is mandatory");
        Objects.requireNonNull(passwordFieldAuthUIFactory, "passwordFieldAuthUIFactory is mandatory");
        Objects.requireNonNull(signalFactory, "signalFactory is mandatory");

        this.displaySettings = Objects.requireNonNull(displaySettings, "displaySettings is mandatory");
        this.sendMessagesCtrl = Objects.requireNonNull(sendMessagesCtrl, "sendMessagesCtrl is mandatory");
        this.messagesDefinesCtrl = Objects.requireNonNull(messagesDefinesCtrl, "messagesDefinesCtrl is mandatory");
        this.optionPaneAuthUIFactory =
                Objects.requireNonNull(optionPaneAuthUIFactory, "optionPaneAuthUIFactory is mandatory");

        this.changeRegimeWork = signalFactory.create();

        tErrorHelpInfo = errorLabelAuthUIFactory.create("");
        tErrorHelpInfo.settingToError();
        tPassword = passwordFieldAuthUIFactory.create("Password");
        tPasswordConfirm = passwordFieldAuthUIFactory.create("Confirm password");
        bAccept = buttonAuthUIFactory.create("Accept");
        bBack = buttonAuthUIFactory.create("Back");

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

        int insX = displaySettings.getResizeFromDisplay(0.025, DisplaySettings.TypeOfDisplayBorder.WIDTH);
        int gridyNum = 0;

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets =
                new Insets(displaySettings.getResizePixel(0.085), insX, displaySettings.getResizePixel(0.004), insX);
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
        gbc.insets = new Insets(0, insX, displaySettings.getResizePixel(0.0104), insX);
        gbc.gridy = gridyNum;
        add(tErrorHelpInfo, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.PAGE_END;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, displaySettings.getResizePixel(0.026), displaySettings.getResizePixel(0.017), 0);
        gbc.ipadx = displaySettings.getResizeFromDisplay(0.015, DisplaySettings.TypeOfDisplayBorder.WIDTH);
        gbc.ipady = displaySettings.getResizeFromDisplay(0.004, DisplaySettings.TypeOfDisplayBorder.HEIGHT);
        gbc.gridy = gridyNum;
        add(bBack, gbc);

        gbc.fill = GridBagConstraints.PAGE_END;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.insets = new Insets(0, 0, displaySettings.getResizePixel(0.017), displaySettings.getResizePixel(0.026));

        gbc.ipadx = displaySettings.getResizeFromDisplay(0.015, DisplaySettings.TypeOfDisplayBorder.WIDTH);
        gbc.ipady = displaySettings.getResizeFromDisplay(0.004, DisplaySettings.TypeOfDisplayBorder.HEIGHT);
        gbc.gridy = gridyNum;
        add(bAccept, gbc);
    }

    private void addListenerToElements() {
        bAccept.addActionListener(event -> {
            if (checkFields()) {
                sendMessagesCtrl.sendMessage(
                        DefinesMessages.TypeMessage.ChangePasswordRequest, email, tPassword.getInputText());
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

        if (!Objects.equals(tPassword.getInputText(), "")
                && !Objects.equals(tPasswordConfirm.getInputText(), "")
                && !Objects.equals(tPassword.getInputText(), tPasswordConfirm.getInputText())) {
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

    public ButtonAuthUI getDefaultButton() {
        return bAccept;
    }

    private void changeRegimeBack() {
        changeRegimeWork.emit(new RecordsAuthUI.Regime(DefinesAuthUI.RegimeWorkMainFrame.ResetPassword));
        settingUnfocusFieldsOnChangeRegime();
    }

    private void changeRegimeNext() {
        changeRegimeWork.emit(new RecordsAuthUI.Regime(DefinesAuthUI.RegimeWorkMainFrame.Auth));
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
        while (messagesDefinesCtrl.getChangePasswordRequest() == MessagesDefinesCtrl.TypeFlags.DEFAULT) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException exception) {
                log.error("Couldn't wait.");
            }
        }
        if (messagesDefinesCtrl.getChangePasswordRequest() == MessagesDefinesCtrl.TypeFlags.TRUE) {
            changeRegimeNext();
            setEnabled(true);
        } else if (messagesDefinesCtrl.getChangePasswordRequest() == MessagesDefinesCtrl.TypeFlags.FALSE) {
            setEnabled(true);
            optionPaneAuthUIFactory.create().show("Failed to change password.", OptionPaneAuthUI.TypeDlg.ERROR);
        }
    }
}
