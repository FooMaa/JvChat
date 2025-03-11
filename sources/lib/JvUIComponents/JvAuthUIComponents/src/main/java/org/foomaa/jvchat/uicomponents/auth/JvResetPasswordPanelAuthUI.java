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
import org.foomaa.jvchat.tools.JvGetterTools;


public class JvResetPasswordPanelAuthUI extends JPanel {
    private final JvTextFieldAuthUI tEmail;
    private final JvErrorLabelAuthUI tErrorHelpInfo;
    private final JvButtonAuthUI bSet;
    private final JvButtonAuthUI bBack;
    private final String backgroundPath;

    JvResetPasswordPanelAuthUI() {
        tEmail = JvGetterAuthUIComponents.getInstance().getBeanTextFieldAuthUI("Почта");
        tErrorHelpInfo = JvGetterAuthUIComponents.getInstance().getBeanErrorLabelAuthUI("");
        tErrorHelpInfo.settingToError();
        bSet = JvGetterAuthUIComponents.getInstance().getBeanButtonAuthUI("Send");
        bBack = JvGetterAuthUIComponents.getInstance().getBeanButtonAuthUI("Back");
        backgroundPath = "/AuthMainBackground.png";

        settingComponents();
        makePanelSetting();
        addListenerToElements();
    }

    private void settingComponents() {
        tErrorHelpInfo.settingToError();

        tEmail.setToolTip("To set email");
        bSet.setToolTip("To email confirmation");
        bBack.setToolTip("To go back");
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
                getResizeFromDisplay(0.025,
                        JvDisplaySettings.TypeOfDisplayBorder.WIDTH);
        int gridyNum = 0;

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.115), insX,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.004), insX);
        gbc.gridy = gridyNum;
        add(tEmail, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, insX,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.0084), insX);
        gbc.gridy = gridyNum;
        add(tErrorHelpInfo, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.PAGE_END;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.046),
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.026),
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.017), 0);
        gbc.ipadx = JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.015,
                JvDisplaySettings.TypeOfDisplayBorder.WIDTH);
        gbc.ipady = JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.004,
                JvDisplaySettings.TypeOfDisplayBorder.HEIGHT);
        gbc.gridy = gridyNum;
        add(bBack, gbc);

        gbc.fill = GridBagConstraints.PAGE_END;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.insets = new Insets(JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.046), 0,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.017),
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.026));
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
                JvGetterControls.getInstance()
                        .getBeanSendMessagesCtrl().sendMessage(JvDefinesMessages.TypeMessage.ResetPasswordRequest,
                        tEmail.getInputText());
                waitRepeatServer();
            }
        });

        bBack.addActionListener(event -> {
            changeRegimeWindowBack();
        });
    }

    private boolean checkFields() {
        tEmail.setErrorBorder(false);
        tErrorHelpInfo.setText("");

        if (Objects.equals(tEmail.getInputText(), "") ||
                !JvGetterTools.getInstance().getBeanUsersTools().validateInputEmail(tEmail.getInputText())) {
            tEmail.setErrorBorder(true);
            tErrorHelpInfo.setText("The \"Email\" field must be completed or corrected");
            return false;
        }

        return true;
    }

    public JvButtonAuthUI getDefaultButton() {
        return bSet;
    }

    private void changeRegimeWindowBack() {
        JvGetterEvents.getInstance().getBeanMakerEvents().event(
                this,
                "changeRegimeWork",
                JvDefinesAuthUI.RegimeWorkMainFrame.Auth);
        settingUnfocusFieldsOnChangeRegime();
    }

    private void changeRegimeWindowNext() {
        JvGetterEvents.getInstance().getBeanMakerEvents().event(
                this,
                "changeRegimeWork",
                JvDefinesAuthUI.RegimeWorkMainFrame.VerifyCodeResetPassword,
                tEmail.getInputText());
        settingUnfocusFieldsOnChangeRegime();
    }

    private void settingUnfocusFieldsOnChangeRegime() {
        tEmail.setUnfocusFieldOnClose(true);
    }

    private void waitRepeatServer() {
        setEnabled(false);
        while (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getResetPasswordRequestFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.DEFAULT) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException exception) {
                JvLog.write(JvLog.TypeLog.Error, "Couldn't wait.");
            }
        }
        if (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getResetPasswordRequestFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.TRUE) {
            changeRegimeWindowNext();
            setEnabled(true);
        } else if (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getResetPasswordRequestFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.FALSE) {
            setEnabled(true);
            JvGetterAuthUIComponents.getInstance()
                    .getBeanOptionPaneAuthUI("This email is not registered.", JvOptionPaneAuthUI.TypeDlg.ERROR);
        }
    }
}