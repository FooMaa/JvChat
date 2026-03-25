package org.foomaa.jvchat.uicomponents.auth;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.ctrl.MessagesDefinesCtrl;
import org.foomaa.jvchat.ctrl.SendMessagesCtrl;
import org.foomaa.jvchat.events.GetterEvents;
import org.foomaa.jvchat.messages.DefinesMessages;
import org.foomaa.jvchat.settings.DisplaySettings;
import org.foomaa.jvchat.settings.UsersInfoSettings;
import org.foomaa.jvchat.uicomponents.mainchat.MainFrameMainChatUI;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile("users")
@Slf4j
public class EntryPanelAuthUI extends JPanel {
    private final TextFieldAuthUI tLogin;
    private final ErrorLabelAuthUI tErrorHelpInfo;
    private final PasswordFieldAuthUI tPassword;
    private final ButtonAuthUI bEnter;
    private final ActiveLabelAuthUI activeRegisterLabel;
    private final ActiveLabelAuthUI activeMissLabel;
    private final UsersInfoSettings usersInfoSettings;
    private final DisplaySettings displaySettings;
    private final MainFrameMainChatUI mainFrameMainChatUI;
    private final SendMessagesCtrl sendMessagesCtrl;
    private final MessagesDefinesCtrl messagesDefinesCtrl;
    private final OptionPaneAuthUIFactory optionPaneAuthUIFactory;

    EntryPanelAuthUI(UsersInfoSettings usersInfoSettings,
                     DisplaySettings displaySettings,
                     @Lazy MainFrameMainChatUI mainFrameMainChatUI,
                     SendMessagesCtrl sendMessagesCtrl,
                     MessagesDefinesCtrl messagesDefinesCtrl,
                     ActiveLabelAuthUIFactory activeLabelAuthUIFactory,
                     ButtonAuthUIFactory buttonAuthUIFactory,
                     ErrorLabelAuthUIFactory errorLabelAuthUIFactory,
                     PasswordFieldAuthUIFactory passwordFieldAuthUIFactory,
                     TextFieldAuthUIFactory textFieldAuthUIFactory,
                     OptionPaneAuthUIFactory optionPaneAuthUIFactory) {
        this.usersInfoSettings = usersInfoSettings;
        this.displaySettings = displaySettings;
        this.mainFrameMainChatUI = mainFrameMainChatUI;
        this.sendMessagesCtrl = sendMessagesCtrl;
        this.messagesDefinesCtrl = messagesDefinesCtrl;
        this.optionPaneAuthUIFactory = optionPaneAuthUIFactory;

        tLogin = textFieldAuthUIFactory.create("Login");
        tErrorHelpInfo = errorLabelAuthUIFactory.create("");
        tPassword = passwordFieldAuthUIFactory.create("Password");
        bEnter = buttonAuthUIFactory.create("Next");
        activeMissLabel = activeLabelAuthUIFactory.create("Reset password");
        activeRegisterLabel = activeLabelAuthUIFactory.create("Registration");

        settingComponents();
        makePanelSetting();
        addListenerToElements();
        makePanelTransparent();
    }

    private void settingComponents() {
        tErrorHelpInfo.settingToError();

        bEnter.setToolTip("To log in");
        tPassword.setToolTip("To set password");
        tLogin.setToolTip("To set login");
        activeMissLabel.setToolTip("To the password recovery form");
        activeRegisterLabel.setToolTip("To registration form");
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
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(displaySettings.getResizePixel(0.075), insX,
                displaySettings.getResizePixel(0.004), insX);
        gbc.gridy = gridyNum;
        add(tLogin, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, insX, displaySettings.getResizePixel(0.01), insX);
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
        gbc.insets = new Insets(0, 0, displaySettings.getResizePixel(0.002), 0);
        gbc.gridy = gridyNum;
        add(activeRegisterLabel, gbc);
        gridyNum++;

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, insX, displaySettings.getResizePixel(0.0084), insX);
        gbc.gridy = gridyNum;
        add(tErrorHelpInfo, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets(0, 0, displaySettings.getResizePixel(0.017), 0);
        gbc.ipadx = displaySettings.getResizeFromDisplay(0.015, DisplaySettings.TypeOfDisplayBorder.WIDTH);
        gbc.ipady = displaySettings.getResizeFromDisplay(0.004, DisplaySettings.TypeOfDisplayBorder.HEIGHT);
        gbc.gridy = gridyNum;
        add(bEnter, gbc);
    }

    private void addListenerToElements() {
        bEnter.addActionListener(event -> {
            if (checkFields()) {
                sendMessagesCtrl.sendMessage(DefinesMessages.TypeMessage.EntryRequest,
                        tLogin.getInputText(), tPassword.getInputText());
                waitRepeatServer();
            }
        });

        activeMissLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                changeRegime(DefinesAuthUI.RegimeWorkMainFrame.ResetPassword);
            }
        });

        activeRegisterLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                changeRegime(DefinesAuthUI.RegimeWorkMainFrame.Registration);
            }
        });
    }

    private void closeFrameWindow() {
        GetterEvents.getInstance().getBeanMakerEvents().event(this, "closeWindow");
        tLogin.setUnfocusFieldOnClose(true);
        tPassword.setUnfocusFieldOnClose(true);
    }

    private void changeRegime(DefinesAuthUI.RegimeWorkMainFrame regime) {
        GetterEvents.getInstance().getBeanMakerEvents().event(this, "changeRegimeWork", regime);
        tLogin.setUnfocusFieldOnClose(true);
        tPassword.setUnfocusFieldOnClose(true);
    }

    private boolean checkFields() {
        tPassword.setErrorBorder(false);
        tLogin.setErrorBorder(false);
        tErrorHelpInfo.setText("");

        Vector<String> fields = new Vector<>();

        if (Objects.equals(tLogin.getInputText(), "")) {
            tLogin.setErrorBorder(true);
            fields.add("\"Login\"");
        }
        if (Objects.equals(tPassword.getInputText(), "")) {
            tPassword.setErrorBorder(true);
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
        while (messagesDefinesCtrl.getEntryRequestFlag() ==
                MessagesDefinesCtrl.TypeFlags.DEFAULT) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException exception) {
                log.error("Couldn't wait.");
            }
        }
        if (messagesDefinesCtrl.getEntryRequestFlag() ==
                MessagesDefinesCtrl.TypeFlags.TRUE) {
            openMainPage();
        } else if (messagesDefinesCtrl.getEntryRequestFlag() ==
                MessagesDefinesCtrl.TypeFlags.FALSE) {
            setEnabled(true);
            optionPaneAuthUIFactory.create().show("Login failed, data is incorrect.", OptionPaneAuthUI.TypeDlg.ERROR);
        }
    }

    private void openMainPage() {
        usersInfoSettings.setLogin(tLogin.getInputText());

        UUID uuidUser = usersInfoSettings.getUuid();
        sendMessagesCtrl.sendMessage(DefinesMessages.TypeMessage.CheckOnlineUserReply, uuidUser);

        closeFrameWindow();
        setEnabled(true);

        mainFrameMainChatUI.openWindow();

        log.info("Login done.");
    }

    public ButtonAuthUI getDefaultButton() {
        return bEnter;
    }
}