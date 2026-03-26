package org.foomaa.jvchat.uicomponents.auth;

import java.awt.*;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.ctrl.MessagesDefinesCtrl;
import org.foomaa.jvchat.ctrl.SendMessagesCtrl;
import org.foomaa.jvchat.events.GetterEvents;
import org.foomaa.jvchat.messages.DefinesMessages;
import org.foomaa.jvchat.settings.DisplaySettings;
import org.foomaa.jvchat.tools.UsersTools;

@Component
@Profile("users")
@Slf4j
public class ResetPasswordPanelAuthUI extends JPanel {
    private final TextFieldAuthUI tEmail;
    private final ErrorLabelAuthUI tErrorHelpInfo;
    private final ButtonAuthUI bSet;
    private final ButtonAuthUI bBack;

    private final DisplaySettings displaySettings;
    private final UsersTools usersTools;
    private final SendMessagesCtrl sendMessagesCtrl;
    private final MessagesDefinesCtrl messagesDefinesCtrl;
    private final OptionPaneAuthUIFactory optionPaneAuthUIFactory;

    ResetPasswordPanelAuthUI(DisplaySettings displaySettings, UsersTools usersTools, SendMessagesCtrl sendMessagesCtrl,
            MessagesDefinesCtrl messagesDefinesCtrl, ButtonAuthUIFactory buttonAuthUIFactory,
            ErrorLabelAuthUIFactory errorLabelAuthUIFactory, TextFieldAuthUIFactory textFieldAuthUIFactory,
            OptionPaneAuthUIFactory optionPaneAuthUIFactory) {
        this.displaySettings = displaySettings;
        this.usersTools = usersTools;
        this.sendMessagesCtrl = sendMessagesCtrl;
        this.messagesDefinesCtrl = messagesDefinesCtrl;
        this.optionPaneAuthUIFactory = optionPaneAuthUIFactory;

        tEmail = textFieldAuthUIFactory.create("Email");
        tErrorHelpInfo = errorLabelAuthUIFactory.create("");
        tErrorHelpInfo.settingToError();
        bSet = buttonAuthUIFactory.create("Send");
        bBack = buttonAuthUIFactory.create("Back");

        settingComponents();
        makePanelSetting();
        addListenerToElements();
        makePanelTransparent();
    }

    private void settingComponents() {
        tErrorHelpInfo.settingToError();

        tEmail.setToolTip("To set email");
        bSet.setToolTip("To email confirmation");
        bBack.setToolTip("To go back");
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
        gbc.insets = new Insets(displaySettings.getResizePixel(0.115), insX, displaySettings.getResizePixel(0.004),
                insX);
        gbc.gridy = gridyNum;
        add(tEmail, gbc);
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
        gbc.insets = new Insets(displaySettings.getResizePixel(0.046), displaySettings.getResizePixel(0.026),
                displaySettings.getResizePixel(0.017), 0);
        gbc.ipadx = displaySettings.getResizeFromDisplay(0.015, DisplaySettings.TypeOfDisplayBorder.WIDTH);
        gbc.ipady = displaySettings.getResizeFromDisplay(0.004, DisplaySettings.TypeOfDisplayBorder.HEIGHT);
        gbc.gridy = gridyNum;
        add(bBack, gbc);

        gbc.fill = GridBagConstraints.PAGE_END;
        gbc.anchor = GridBagConstraints.NORTHEAST;
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
                sendMessagesCtrl.sendMessage(DefinesMessages.TypeMessage.ResetPasswordRequest, tEmail.getInputText());
                waitRepeatServer();
            }
        });

        bBack.addActionListener(event -> changeRegimeBack());
    }

    private boolean checkFields() {
        tEmail.setErrorBorder(false);
        tErrorHelpInfo.setText("");

        if (Objects.equals(tEmail.getInputText(), "") || !usersTools.validateInputEmail(tEmail.getInputText())) {
            tEmail.setErrorBorder(true);
            tErrorHelpInfo.setText("The \"Email\" field must be completed or corrected");
            return false;
        }

        return true;
    }

    public ButtonAuthUI getDefaultButton() {
        return bSet;
    }

    private void changeRegimeBack() {
        GetterEvents.getInstance().getBeanMakerEvents().event(this, "changeRegimeWork",
                DefinesAuthUI.RegimeWorkMainFrame.Auth);
        settingUnfocusFieldsOnChangeRegime();
    }

    private void changeRegimeNext() {
        GetterEvents.getInstance().getBeanMakerEvents().event(this, "changeRegimeWork",
                DefinesAuthUI.RegimeWorkMainFrame.VerifyCodeResetPassword, tEmail.getInputText());
        settingUnfocusFieldsOnChangeRegime();
    }

    private void settingUnfocusFieldsOnChangeRegime() {
        tEmail.setUnfocusFieldOnClose(true);
    }

    private void waitRepeatServer() {
        setEnabled(false);
        while (messagesDefinesCtrl.getResetPasswordRequestFlag() == MessagesDefinesCtrl.TypeFlags.DEFAULT) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException exception) {
                log.error("Couldn't wait.");
            }
        }
        if (messagesDefinesCtrl.getResetPasswordRequestFlag() == MessagesDefinesCtrl.TypeFlags.TRUE) {
            changeRegimeNext();
            setEnabled(true);
        } else if (messagesDefinesCtrl.getResetPasswordRequestFlag() == MessagesDefinesCtrl.TypeFlags.FALSE) {
            setEnabled(true);
            optionPaneAuthUIFactory.create().show("This email is not registered.", OptionPaneAuthUI.TypeDlg.ERROR);
        }
    }
}
