package org.foomaa.jvchat.uicomponents.auth;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.foomaa.jvchat.ctrl.JvGetterControls;
import org.foomaa.jvchat.ctrl.JvMessagesDefinesCtrl;
import org.foomaa.jvchat.events.JvGetterEvents;
import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.messages.JvDefinesMessages;
import org.foomaa.jvchat.settings.JvDisplaySettings;
import org.foomaa.jvchat.settings.JvGetterSettings;


public class JvVerifyCodePanelAuthUI extends JPanel {
    private final JvTextFieldAuthUI tCode;
    private final JvErrorLabelAuthUI tErrorHelpInfo;
    private final JvButtonAuthUI bSet;
    private String login;
    private String email;
    private String password;
    private RegimeWork regime;
    private final String backgroundPath;

    public enum RegimeWork {
        Registration,
        ResetPassword
    }

    JvVerifyCodePanelAuthUI() {
        tCode = JvGetterAuthUIComponents.getInstance().getBeanTextFieldAuthUI("Code (valid for 60 sec.)");
        tErrorHelpInfo = JvGetterAuthUIComponents.getInstance().getBeanErrorLabelAuthUI("");
        tErrorHelpInfo.settingToError();
        bSet = JvGetterAuthUIComponents.getInstance().getBeanButtonAuthUI("SEND");
        backgroundPath = "/AuthMainBackground.png";

        makeFrameSetting();
        addListenerToElements();
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

    private void makeFrameSetting() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        int insX = JvGetterSettings.getInstance().getBeanDisplaySettings().
                getResizeFromDisplay(0.025,
                        JvDisplaySettings.TypeOfDisplayBorder.WIDTH);
        int gridyNum = 0;

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.12), insX,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.004), insX);
        gbc.gridy = gridyNum;
        add(tCode, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, insX,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.0084), insX);
        gbc.gridy = gridyNum;
        add(tErrorHelpInfo, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets(JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.046), 0,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.017), 0);
        gbc.ipadx = JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.015,
                JvDisplaySettings.TypeOfDisplayBorder.WIDTH);
        gbc.ipady = JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.004,
                JvDisplaySettings.TypeOfDisplayBorder.HEIGHT);
        gbc.gridy = gridyNum;
        add(bSet, gbc);
    }

    private void addListenerToElements() {
        bSet.addActionListener(event -> {
            if (checkFields()) {
                if (regime == RegimeWork.ResetPassword) {
                    JvGetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(JvDefinesMessages.TypeMessage.VerifyFamousEmailRequest,
                            email, tCode.getInputText());
                    waitRepeatServerResetPassword();
                } else if (regime == RegimeWork.Registration) {
                    JvGetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(JvDefinesMessages.TypeMessage.VerifyRegistrationEmailRequest,
                            login, email, password, tCode.getInputText());
                    waitRepeatServerRegistration();
                }
            }
        });
    }

    private boolean checkFields() {
        tCode.setNormalBorder();
        tErrorHelpInfo.setText("");

        if (Objects.equals(tCode.getInputText(), "") ||
                (tCode.getInputText().length() != 6 )) {
            tCode.setErrorBorder();
            tErrorHelpInfo.setText("The \"Code\" field must be completed and contain the submitted code");
            return false;
        }

        return true;
    }

    public JvButtonAuthUI getDefaultButton() {
        return bSet;
    }

    private void changeRegime() {
        if (regime == RegimeWork.Registration) {
            JvGetterEvents.getInstance().getBeanMakerEvents().event(
                    this,
                    "changeRegimeWork",
                    JvDefinesAuthUI.RegimeWorkMainFrame.Auth);
        } else if (regime == RegimeWork.ResetPassword) {
            JvGetterAuthUIComponents.getInstance().getBeanNewPasswordFrameAuthUI(email).openWindow();
        }
        tCode.setUnfocusFieldOnClose(false);
    }

    private void waitRepeatServerResetPassword() {
        setEnabled(false);
        while (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getVerifyFamousEmailRequestFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.DEFAULT) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException exception) {
                JvLog.write(JvLog.TypeLog.Error, "Failed to wait.");
            }
        }
        if (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getVerifyFamousEmailRequestFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.TRUE) {
            changeRegime();
            setEnabled(true);
        } else if (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getVerifyFamousEmailRequestFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.FALSE) {
            setEnabled(true);
            JvGetterAuthUIComponents.getInstance()
                    .getBeanOptionPaneAuthUI("The code is not correct. Enter the code you received by mail again.\n" +
                            "The code may have expired, enter your email again and get a new one.", JvOptionPaneAuthUI.TypeDlg.ERROR);
        }
    }

    private void waitRepeatServerRegistration() {
        setEnabled(false);
        while (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getVerifyRegistrationEmailRequestFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.DEFAULT) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException exception) {
                JvLog.write(JvLog.TypeLog.Error, "Failed to wait.");
            }
        }
        if (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getVerifyRegistrationEmailRequestFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.TRUE) {
            changeRegime();
            setEnabled(true);
        } else if (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getVerifyRegistrationEmailRequestFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.FALSE) {
            setEnabled(true);
            openErrorPane();
        }
    }

    private void openErrorPane() {
        switch (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getErrorVerifyRegEmailFlag()) {
            case NoError -> JvGetterAuthUIComponents.getInstance()
                    .getBeanOptionPaneAuthUI("The error is not clear.", JvOptionPaneAuthUI.TypeDlg.ERROR);
            case EmailSending -> JvGetterAuthUIComponents.getInstance()
                    .getBeanOptionPaneAuthUI("The email may be invalid.", JvOptionPaneAuthUI.TypeDlg.ERROR);
            case Login -> JvGetterAuthUIComponents.getInstance()
                    .getBeanOptionPaneAuthUI("This login is already in use.", JvOptionPaneAuthUI.TypeDlg.ERROR);
            case Email -> JvGetterAuthUIComponents.getInstance()
                    .getBeanOptionPaneAuthUI("This email is already in use.", JvOptionPaneAuthUI.TypeDlg.ERROR);
            case Code -> JvGetterAuthUIComponents.getInstance()
                    .getBeanOptionPaneAuthUI("The code is not correct. Enter the code you received by mail again.\n" +
                    "The code may have expired, enter your email again and get a new one.", JvOptionPaneAuthUI.TypeDlg.ERROR);
            case LoginAndEmail ->
                    JvGetterAuthUIComponents.getInstance()
                            .getBeanOptionPaneAuthUI("The email and login data are already in use.", JvOptionPaneAuthUI.TypeDlg.ERROR);
        }
    }
}