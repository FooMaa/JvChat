package org.foomaa.jvchat.uicomponents.auth;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.ctrl.GetterControls;
import org.foomaa.jvchat.ctrl.MessagesDefinesCtrl;
import org.foomaa.jvchat.events.GetterEvents;
import org.foomaa.jvchat.messages.DefinesMessages;
import org.foomaa.jvchat.settings.DisplaySettings;
import org.foomaa.jvchat.tools.UsersTools;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile("users")
@Slf4j
public class RegistrationPanelAuthUI extends JPanel {
    private final TextFieldAuthUI tLogin;
    private final TextFieldAuthUI tEmail;
    private final ErrorLabelAuthUI tErrorHelpInfo;
    private final PasswordFieldAuthUI tPassword;
    private final PasswordFieldAuthUI tPasswordConfirm;
    private final ButtonAuthUI bRegister;
    private final ButtonAuthUI bBack;

    private final DisplaySettings displaySettings;
    private final UsersTools usersTools;
    private final ObjectProvider<OptionPaneAuthUI> optionPaneObjectProvider;

    RegistrationPanelAuthUI(DisplaySettings displaySettings,
                            UsersTools usersTools,
                            ObjectProvider<ButtonAuthUI> buttonObjectProvider,
                            ObjectProvider<ErrorLabelAuthUI> errorLabelObjectProvider,
                            ObjectProvider<PasswordFieldAuthUI> passwordFieldObjectProvider,
                            ObjectProvider<TextFieldAuthUI> textFieldObjectProvider,
                            ObjectProvider<OptionPaneAuthUI> optionPaneObjectProvider) {
        this.displaySettings = displaySettings;
        this.usersTools = usersTools;
        this.optionPaneObjectProvider = optionPaneObjectProvider;

        tLogin = textFieldObjectProvider.getObject("Login");
        tEmail = textFieldObjectProvider.getObject("Email");
        tErrorHelpInfo = errorLabelObjectProvider.getObject("");
        tPassword = passwordFieldObjectProvider.getObject("Password");
        tPasswordConfirm = passwordFieldObjectProvider.getObject("Confirm password");
        bRegister = buttonObjectProvider.getObject("Next");
        bBack = buttonObjectProvider.getObject("Back");

        settingComponents();
        makePanelSetting();
        addListenerToElements();
        makePanelTransparent();
    }

    private void settingComponents() {
        tErrorHelpInfo.settingToError();

        bRegister.setToolTip("To email confirmation");
        bBack.setToolTip("To go back");
        tPassword.setToolTip("To set password");
        tPasswordConfirm.setToolTip("To confirm password");
        tLogin.setToolTip("To set login");
        tEmail.setToolTip("To set email");
    }

    private void makePanelTransparent() {
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
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
        gbc.insets = new Insets(displaySettings.getResizePixel(0.03), insX,
                displaySettings.getResizePixel(0.0045), insX);
        gbc.gridy = gridyNum;
        add(tLogin, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, insX, displaySettings.getResizePixel(0.0045), insX);
        gbc.gridy = gridyNum;
        add(tEmail, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, insX, displaySettings.getResizePixel(0.0045), insX);
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
        gbc.insets = new Insets(0, insX, displaySettings.getResizePixel(0.0084), insX);
        gbc.gridy = gridyNum;
        add(tErrorHelpInfo, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.PAGE_END;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, displaySettings.getResizePixel(0.026),
                displaySettings.getResizePixel(0.017), 0);
        gbc.ipadx = displaySettings.getResizeFromDisplay(0.015, DisplaySettings.TypeOfDisplayBorder.WIDTH);
        gbc.ipady = displaySettings.getResizeFromDisplay(0.004, DisplaySettings.TypeOfDisplayBorder.HEIGHT);
        gbc.gridy = gridyNum;
        add(bBack, gbc);

        gbc.fill = GridBagConstraints.PAGE_END;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.insets = new Insets(0, 0, displaySettings.getResizePixel(0.017),
                displaySettings.getResizePixel(0.026));
        gbc.ipadx = displaySettings.getResizeFromDisplay(0.015, DisplaySettings.TypeOfDisplayBorder.WIDTH);
        gbc.ipady = displaySettings.getResizeFromDisplay(0.004, DisplaySettings.TypeOfDisplayBorder.HEIGHT);
        gbc.gridy = gridyNum;
        add(bRegister, gbc);
    }

    private void addListenerToElements() {
        bRegister.addActionListener(event -> {
            if (checkFields()) {
                GetterControls.getInstance()
                        .getBeanSendMessagesCtrl().sendMessage(DefinesMessages.TypeMessage.RegistrationRequest,
                        tLogin.getInputText(), tEmail.getInputText(), tPassword.getInputText());
                waitRepeatServer();
            }
        });

        bBack.addActionListener(event -> changeRegimeBack());
    }

    private boolean checkFields() {
        tLogin.setErrorBorder(false);
        tEmail.setErrorBorder(false);
        tPassword.setErrorBorder(false);
        tPasswordConfirm.setErrorBorder(false);
        tErrorHelpInfo.setText("");

        Vector<String> fields = new Vector<>();

        if (Objects.equals(tLogin.getInputText(), "")) {
            tLogin.setErrorBorder(true);
            fields.add("\"Login\"");
        }
        if (Objects.equals(tEmail.getInputText(), "") || !usersTools.validateInputEmail(tEmail.getInputText())) {
            tEmail.setErrorBorder(true);
            fields.add("\"Email\"");
        }
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
            tErrorHelpInfo.setText("The entered passwords must match!");
            return false;
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

    public ButtonAuthUI getDefaultButton() {
        return bRegister;
    }

    private void changeRegimeBack() {
        GetterEvents.getInstance().getBeanMakerEvents().event(
                this,
                "changeRegimeWork",
                DefinesAuthUI.RegimeWorkMainFrame.Auth);
        settingUnfocusFieldsOnChangeRegime();
    }

    private void changeRegimeNext() {
        GetterEvents.getInstance().getBeanMakerEvents().event(
                this,
                "changeRegimeWork",
                DefinesAuthUI.RegimeWorkMainFrame.VerifyCodeRegistration,
                tLogin.getInputText(),
                tEmail.getInputText(),
                tPassword.getInputText());
        settingUnfocusFieldsOnChangeRegime();
    }

    private void settingUnfocusFieldsOnChangeRegime() {
        tLogin.setUnfocusFieldOnClose(true);
        tEmail.setUnfocusFieldOnClose(true);
        tPassword.setUnfocusFieldOnClose(true);
        tPasswordConfirm.setUnfocusFieldOnClose(true);
    }

    private void waitRepeatServer() {
        setEnabled(false);
        while (GetterControls.getInstance().getBeanMessagesDefinesCtrl().getRegistrationRequestFlag() ==
                MessagesDefinesCtrl.TypeFlags.DEFAULT) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException exception) {
                log.error("Couldn't wait.");
            }
        }
        if (GetterControls.getInstance().getBeanMessagesDefinesCtrl().getRegistrationRequestFlag() ==
                MessagesDefinesCtrl.TypeFlags.TRUE) {
            changeRegimeNext();
            setEnabled(true);
        } else if (GetterControls.getInstance().getBeanMessagesDefinesCtrl().getRegistrationRequestFlag() ==
                MessagesDefinesCtrl.TypeFlags.FALSE) {
            setEnabled(true);
            openErrorPane();
        }
    }

    private void openErrorPane() {
        switch (GetterControls.getInstance().getBeanMessagesDefinesCtrl().getErrorRegistrationFlag()) {
            case NoError -> optionPaneObjectProvider.getObject("The error is not clear.", OptionPaneAuthUI.TypeDlg.ERROR);
            case EmailSending -> optionPaneObjectProvider.getObject("The email may be invalid.", OptionPaneAuthUI.TypeDlg.ERROR);
            case Login -> optionPaneObjectProvider.getObject("This login is already in use.", OptionPaneAuthUI.TypeDlg.ERROR);
            case Email -> optionPaneObjectProvider.getObject("This email is already in use.", OptionPaneAuthUI.TypeDlg.ERROR);
            case LoginAndEmail -> optionPaneObjectProvider.getObject("The email and login data are already in use.", OptionPaneAuthUI.TypeDlg.ERROR);
        }
    }
}