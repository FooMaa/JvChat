package org.foomaa.jvchat.uicomponents.auth;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import org.foomaa.jvchat.ctrl.JvGetterControls;
import org.foomaa.jvchat.ctrl.JvMessagesDefinesCtrl;
import org.foomaa.jvchat.events.JvGetterEvents;
import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.messages.JvDefinesMessages;
import org.foomaa.jvchat.settings.JvDisplaySettings;
import org.foomaa.jvchat.settings.JvGetterSettings;
import org.foomaa.jvchat.tools.JvGetterTools;


public class JvRegistrationPanelAuthUI extends JPanel {
    private final JvTextFieldAuthUI tLogin;
    private final JvTextFieldAuthUI tEmail;
    private final JvErrorLabelAuthUI tErrorHelpInfo;
    private final JvPasswordFieldAuthUI tPassword;
    private final JvPasswordFieldAuthUI tPasswordConfirm;
    private final JvButtonAuthUI bRegister;
    private final String backgroundPath;

    JvRegistrationPanelAuthUI() {
        tLogin = JvGetterAuthUIComponents.getInstance().getBeanTextFieldAuthUI("Login");
        tEmail = JvGetterAuthUIComponents.getInstance().getBeanTextFieldAuthUI("Email");
        tErrorHelpInfo = JvGetterAuthUIComponents.getInstance().getBeanErrorLabelAuthUI("");
        tPassword = JvGetterAuthUIComponents.getInstance().getBeanPasswordFieldAuthUI("Password");
        tPasswordConfirm = JvGetterAuthUIComponents.getInstance().getBeanPasswordFieldAuthUI("Confirm password");
        bRegister = JvGetterAuthUIComponents.getInstance().getBeanButtonAuthUI("NEXT");
        backgroundPath = "/AuthMainBackground.png";

        settingComponents();
        makeFrameSetting();
        addListenerToElements();
    }

    private void settingComponents() {
        tErrorHelpInfo.settingToError();

        bRegister.setToolTip("To email confirmation");
        tPassword.setToolTip("To set password");
        tPasswordConfirm.setToolTip("To confirm password");
        tLogin.setToolTip("To set login");
        tEmail.setToolTip("To set email");
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

    private void makeFrameSetting() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        int insX = JvGetterSettings.getInstance().getBeanDisplaySettings().
                getResizeFromDisplay(0.025,
                        JvDisplaySettings.TypeOfDisplayBorder.WIDTH);
        int gridyNum = 0;

        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.03), insX,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.0045), insX);
        gbc.gridy = gridyNum;
        add(tLogin, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, insX,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.0045), insX);
        gbc.gridy = gridyNum;
        add(tEmail, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, insX,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.0045), insX);
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
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.0084), insX);
        gbc.gridy = gridyNum;
        add(tErrorHelpInfo, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.PAGE_END;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 0,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.017), 0);
        gbc.ipadx = JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.015,
                JvDisplaySettings.TypeOfDisplayBorder.WIDTH);
        gbc.ipady = JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.004,
                JvDisplaySettings.TypeOfDisplayBorder.HEIGHT);
        gbc.gridy = gridyNum;
        add(bRegister, gbc);
    }

    private void addListenerToElements() {
        bRegister.addActionListener(event -> {
            if (checkFields()) {
                JvGetterControls.getInstance()
                        .getBeanSendMessagesCtrl().sendMessage(JvDefinesMessages.TypeMessage.RegistrationRequest,
                        tLogin.getInputText(), tEmail.getInputText(), tPassword.getInputText());
                waitRepeatServer();
            }
        });
    }

    private boolean checkFields() {
        tLogin.setNormalBorder();
        tEmail.setNormalBorder();
        tPassword.setNormalBorder();
        tPasswordConfirm.setNormalBorder();
        tErrorHelpInfo.setText("");

        Vector<String> fields = new Vector<>();

        if (Objects.equals(tLogin.getInputText(), "")) {
            tLogin.setErrorBorder();
            fields.add("\"Login\"");
        }
        if (Objects.equals(tEmail.getInputText(), "") ||
                !JvGetterTools.getInstance().getBeanUsersTools().validateInputEmail(tEmail.getInputText())) {
            tEmail.setErrorBorder();
            fields.add("\"Email\"");
        }
        if (Objects.equals(tPassword.getInputText(), "")) {
            tPassword.setErrorBorder();
            fields.add("\"Password\"");
        }
        if (Objects.equals(tPasswordConfirm.getInputText(), "")) {
            tPasswordConfirm.setErrorBorder();
            fields.add("\"Confirm password\"");
        }

        if (!Objects.equals(tPassword.getInputText(), "") &&
                !Objects.equals(tPasswordConfirm.getInputText(), "") &&
                !Objects.equals(tPassword.getInputText(), tPasswordConfirm.getInputText())) {
            tPassword.setErrorBorder();
            tPasswordConfirm.setErrorBorder();
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

    public JvButtonAuthUI getDefaultButton() {
        return bRegister;
    }

    private void changeRegimeWindow() {
        JvGetterEvents.getInstance().getBeanMakerEvents().event(
                this,
                "changeRegimeWork",
                JvDefinesAuthUI.RegimeWorkMainFrame.VerifyCodeRegistration,
                tLogin.getInputText(),
                tEmail.getInputText(),
                tPassword.getInputText());
        tLogin.setUnfocusFieldOnClose(true);
        tEmail.setUnfocusFieldOnClose(true);
        tPassword.setUnfocusFieldOnClose(true);
        tPasswordConfirm.setUnfocusFieldOnClose(true);
    }

    private void waitRepeatServer() {
        setEnabled(false);
        while (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getRegistrationRequestFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.DEFAULT) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException exception) {
                JvLog.write(JvLog.TypeLog.Error, "Couldn't wait.");
            }
        }
        if (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getRegistrationRequestFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.TRUE) {
            changeRegimeWindow();
            setEnabled(true);
        } else if (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getRegistrationRequestFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.FALSE) {
            setEnabled(true);
            openErrorPane();
        }
    }

    private void openErrorPane() {
        switch (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getErrorRegistrationFlag()) {
            case NoError -> JvGetterAuthUIComponents.getInstance()
                    .getBeanOptionPaneAuthUI("The error is not clear.", JvOptionPaneAuthUI.TypeDlg.ERROR);
            case EmailSending -> JvGetterAuthUIComponents.getInstance()
                    .getBeanOptionPaneAuthUI("The email may be invalid.", JvOptionPaneAuthUI.TypeDlg.ERROR);
            case Login -> JvGetterAuthUIComponents.getInstance()
                    .getBeanOptionPaneAuthUI("This login is already in use.", JvOptionPaneAuthUI.TypeDlg.ERROR);
            case Email -> JvGetterAuthUIComponents.getInstance()
                    .getBeanOptionPaneAuthUI("This email is already in use.", JvOptionPaneAuthUI.TypeDlg.ERROR);
            case LoginAndEmail ->
                    JvGetterAuthUIComponents.getInstance()
                            .getBeanOptionPaneAuthUI("The email and login data are already in use.", JvOptionPaneAuthUI.TypeDlg.ERROR);
        }
    }
}