package org.foomaa.jvchat.uicomponents.auth;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.ctrl.GetterControls;
import org.foomaa.jvchat.ctrl.MessagesDefinesCtrl;
import org.foomaa.jvchat.ctrl.SendMessagesCtrl;
import org.foomaa.jvchat.events.GetterEvents;
import org.foomaa.jvchat.messages.DefinesMessages;
import org.foomaa.jvchat.settings.DisplaySettings;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile("users")
@Slf4j
public class VerifyCodePanelAuthUI extends JPanel {
    private final TextFieldAuthUI tCode;
    private final ErrorLabelAuthUI tErrorHelpInfo;
    private final ButtonAuthUI bSet;
    private final ButtonAuthUI bBack;
    private String login;
    private String email;
    private String password;
    private RegimeWork regime;

    private final DisplaySettings displaySettings;
    private final SendMessagesCtrl sendMessagesCtrl;
    private final ObjectProvider<OptionPaneAuthUI> optionPaneObjectProvider;

    public enum RegimeWork {
        Registration,
        ResetPassword
    }

    VerifyCodePanelAuthUI(DisplaySettings displaySettings,
                          SendMessagesCtrl sendMessagesCtrl,
                          ObjectProvider<ButtonAuthUI> buttonObjectProvider,
                          ObjectProvider<ErrorLabelAuthUI> errorLabelObjectProvider,
                          ObjectProvider<TextFieldAuthUI> textFieldObjectProvider,
                          ObjectProvider<OptionPaneAuthUI> optionPaneObjectProvider) {
        this.displaySettings = displaySettings;
        this.sendMessagesCtrl = sendMessagesCtrl;
        this.optionPaneObjectProvider = optionPaneObjectProvider;

        tCode = textFieldObjectProvider.getObject("Code (valid for 60 sec.)");
        tErrorHelpInfo = errorLabelObjectProvider.getObject("");
        tErrorHelpInfo.settingToError();
        bSet = buttonObjectProvider.getObject("Send");
        bBack = buttonObjectProvider.getObject("Back");

        settingComponents();
        makePanelSetting();
        addListenerToElements();
        makePanelTransparent();
    }

    private void settingComponents() {
        tErrorHelpInfo.settingToError();

        tCode.setToolTip("To set login");
        bSet.setToolTip("To confirm email");
        bBack.setToolTip("To go back");
    }

    private void makePanelTransparent() {
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
    }

    public void setParametersRegistration(String pLogin, String pEmail, String pPassword) {
        regime = RegimeWork.Registration;
        login = pLogin;
        email = pEmail;
        password = pPassword;
    }

    public void setParametersResetPassword(String pEmail) {
        regime = RegimeWork.ResetPassword;
        email = pEmail;
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
        gbc.insets = new Insets(displaySettings.getResizePixel(0.115), insX, displaySettings.getResizePixel(0.004), insX);
        gbc.gridy = gridyNum;
        add(tCode, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, insX, displaySettings.getResizePixel(0.0084), insX);
        gbc.gridy = gridyNum;
        add(tErrorHelpInfo, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(displaySettings.getResizePixel(0.046), displaySettings.getResizePixel(0.026),
                displaySettings.getResizePixel(0.017), 0);
        gbc.ipadx = displaySettings.getResizeFromDisplay(0.015, DisplaySettings.TypeOfDisplayBorder.WIDTH);
        gbc.ipady = displaySettings.getResizeFromDisplay(0.004, DisplaySettings.TypeOfDisplayBorder.HEIGHT);
        gbc.gridy = gridyNum;
        add(bBack, gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.insets = new Insets(displaySettings.getResizePixel(0.046), 0, displaySettings.getResizePixel(0.017),
                displaySettings.getResizePixel(0.026));
        gbc.ipadx = displaySettings.getResizeFromDisplay(0.015, DisplaySettings.TypeOfDisplayBorder.WIDTH);
        gbc.ipady = displaySettings.getResizeFromDisplay(0.004, DisplaySettings.TypeOfDisplayBorder.HEIGHT);
        gbc.gridy = gridyNum;
        add(bSet, gbc);
    }

    private void addListenerToElements() {
        bSet.addActionListener(event -> {
            if (checkFields()) {
                if (regime == RegimeWork.ResetPassword) {
                    sendMessagesCtrl.sendMessage(DefinesMessages.TypeMessage.VerifyFamousEmailRequest,
                            email, tCode.getInputText());
                    waitRepeatServerResetPassword();
                } else if (regime == RegimeWork.Registration) {
                    sendMessagesCtrl.sendMessage(DefinesMessages.TypeMessage.VerifyRegistrationEmailRequest,
                            login, email, password, tCode.getInputText());
                    waitRepeatServerRegistration();
                }
            }
        });

        bBack.addActionListener(event -> changeRegimeBack());
    }

    private boolean checkFields() {
        tCode.setErrorBorder(false);
        tErrorHelpInfo.setText("");

        if (Objects.equals(tCode.getInputText(), "") ||
                (tCode.getInputText().length() != 6 )) {
            tCode.setErrorBorder(true);
            tErrorHelpInfo.setText("The \"Code\" field must be completed and contain the submitted code");
            return false;
        }

        return true;
    }

    public ButtonAuthUI getDefaultButton() {
        return bSet;
    }

    private void changeRegimeBack() {
        if (regime == RegimeWork.Registration) {
            GetterEvents.getInstance().getBeanMakerEvents().event(
                    this,
                    "changeRegimeWork",
                    DefinesAuthUI.RegimeWorkMainFrame.Registration);
        } else if (regime == RegimeWork.ResetPassword) {
            GetterEvents.getInstance().getBeanMakerEvents().event(
                    this,
                    "changeRegimeWork",
                    DefinesAuthUI.RegimeWorkMainFrame.ResetPassword);
        }
        settingUnfocusFieldsOnChangeRegime();
    }

    private void changeRegimeNext() {
        if (regime == RegimeWork.Registration) {
            GetterEvents.getInstance().getBeanMakerEvents().event(
                    this,
                    "changeRegimeWork",
                    DefinesAuthUI.RegimeWorkMainFrame.Auth);
        } else if (regime == RegimeWork.ResetPassword) {
            GetterEvents.getInstance().getBeanMakerEvents().event(
                    this,
                    "changeRegimeWork",
                    DefinesAuthUI.RegimeWorkMainFrame.NewPassword,
                    email);
        }
        settingUnfocusFieldsOnChangeRegime();
    }

    private void settingUnfocusFieldsOnChangeRegime() {
        tCode.setUnfocusFieldOnClose(true);
    }

    private void waitRepeatServerResetPassword() {
        setEnabled(false);
        while (GetterControls.getInstance().getBeanMessagesDefinesCtrl().getVerifyFamousEmailRequestFlag() ==
                MessagesDefinesCtrl.TypeFlags.DEFAULT) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException exception) {
                log.error("Failed to wait.");
            }
        }
        if (GetterControls.getInstance().getBeanMessagesDefinesCtrl().getVerifyFamousEmailRequestFlag() ==
                MessagesDefinesCtrl.TypeFlags.TRUE) {
            changeRegimeNext();
            setEnabled(true);
        } else if (GetterControls.getInstance().getBeanMessagesDefinesCtrl().getVerifyFamousEmailRequestFlag() ==
                MessagesDefinesCtrl.TypeFlags.FALSE) {
            setEnabled(true);
            optionPaneObjectProvider.getObject("The code is not correct. Enter the code you received by mail again.\n" +
                            "The code may have expired, enter your email again and get a new one.", OptionPaneAuthUI.TypeDlg.ERROR);
        }
    }

    private void waitRepeatServerRegistration() {
        setEnabled(false);
        while (GetterControls.getInstance().getBeanMessagesDefinesCtrl().getVerifyRegistrationEmailRequestFlag() ==
                MessagesDefinesCtrl.TypeFlags.DEFAULT) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException exception) {
                log.error("Failed to wait.");
            }
        }
        if (GetterControls.getInstance().getBeanMessagesDefinesCtrl().getVerifyRegistrationEmailRequestFlag() ==
                MessagesDefinesCtrl.TypeFlags.TRUE) {
            changeRegimeNext();
            setEnabled(true);
        } else if (GetterControls.getInstance().getBeanMessagesDefinesCtrl().getVerifyRegistrationEmailRequestFlag() ==
                MessagesDefinesCtrl.TypeFlags.FALSE) {
            setEnabled(true);
            openErrorPane();
        }
    }

    private void openErrorPane() {
        switch (GetterControls.getInstance().getBeanMessagesDefinesCtrl().getErrorVerifyRegEmailFlag()) {
            case NoError -> optionPaneObjectProvider.getObject("The error is not clear.", OptionPaneAuthUI.TypeDlg.ERROR);
            case EmailSending -> optionPaneObjectProvider.getObject("The email may be invalid.", OptionPaneAuthUI.TypeDlg.ERROR);
            case Login -> optionPaneObjectProvider.getObject("This login is already in use.", OptionPaneAuthUI.TypeDlg.ERROR);
            case Email -> optionPaneObjectProvider.getObject("This email is already in use.", OptionPaneAuthUI.TypeDlg.ERROR);
            case Code -> optionPaneObjectProvider.getObject("The code is not correct. Enter the code you received by mail again.\n" +
                    "The code may have expired, enter your email again and get a new one.", OptionPaneAuthUI.TypeDlg.ERROR);
            case LoginAndEmail -> optionPaneObjectProvider.getObject("The email and login data are already in use.", OptionPaneAuthUI.TypeDlg.ERROR);
        }
    }
}