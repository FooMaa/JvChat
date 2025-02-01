package org.foomaa.jvchat.uicomponents.auth;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import org.foomaa.jvchat.ctrl.JvGetterControls;
import org.foomaa.jvchat.ctrl.JvMessagesDefinesCtrl;
import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.messages.JvDefinesMessages;
import org.foomaa.jvchat.settings.JvDisplaySettings;
import org.foomaa.jvchat.settings.JvGetterSettings;
import org.foomaa.jvchat.tools.JvGetterTools;


public class JvRegistrationFrameAuthUI extends JFrame {
    private JPanel panel;
    private final JvTextFieldAuthUI tLogin;
    private final JvTextFieldAuthUI tEmail;
    private final JvErrorLabelAuthUI tErrorHelpInfo;
    private final JvPasswordFieldAuthUI tPassword;
    private final JvPasswordFieldAuthUI tPasswordConfirm;
    private final JvButtonAuthUI bRegister;
    private final JvTitlePanelAuthUI titlePanel;

    JvRegistrationFrameAuthUI() {
        super("RegistrationWindow");

        createPanel("/AuthMainBackground.png");

        tLogin = JvGetterAuthUIComponents.getInstance().getBeanTextFieldAuthUI("Login");
        tEmail = JvGetterAuthUIComponents.getInstance().getBeanTextFieldAuthUI("Email");
        tErrorHelpInfo = JvGetterAuthUIComponents.getInstance().getBeanErrorLabelAuthUI("");
        tPassword = JvGetterAuthUIComponents.getInstance().getBeanPasswordFieldAuthUI("Password");
        tPasswordConfirm = JvGetterAuthUIComponents.getInstance().getBeanPasswordFieldAuthUI("Confirm password");
        bRegister = JvGetterAuthUIComponents.getInstance().getBeanButtonAuthUI("NEXT");
        titlePanel = JvGetterAuthUIComponents.getInstance().getBeanTitlePanelAuthUI("Registration");

        settingComponents();
        settingMovingTitlePanel();
        setIconImageFrame("/MainAppIcon.png");
        makeFrameSetting();
        addListenerToElements();
        addGeneralSettingsToWidget();
    }

    private void setIconImageFrame(String path) {
        try {
            Image img = ImageIO.read(Objects.requireNonNull(getClass().getResource(path)));
            setIconImage(img);
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    private void settingComponents() {
        tErrorHelpInfo.settingToError();

        bRegister.setToolTip("To email confirmation");
        tPassword.setToolTip("To set password");
        tPasswordConfirm.setToolTip("To confirm password");
        tLogin.setToolTip("To set login");
        tEmail.setToolTip("To set email");
    }

    private void settingMovingTitlePanel() {
        final Point[] initialClick = new Point[1];

        titlePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initialClick[0] = e.getPoint();
                getComponentAt(initialClick[0]);
                titlePanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                titlePanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });

        titlePanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                titlePanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                int thisX = getLocation().x;
                int thisY = getLocation().y;

                int xMoved = (thisX + e.getX()) - (thisX + initialClick[0].x);
                int yMoved = (thisY + e.getY()) - (thisY + initialClick[0].y);

                setLocation(thisX + xMoved, thisY + yMoved);
            }
        });
    }

    private void createPanel(String path) {
        panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Image img = null;
                try {
                    img = ImageIO.read(Objects.requireNonNull(getClass().getResource(path)));
                } catch (IOException e) {
                    e.getStackTrace();
                }

                if (img == null) {
                    JvLog.write(JvLog.TypeLog.Error, "Here img turned out to be equal to null.");
                    return;
                }

                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
    }

    private void makeFrameSetting() {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        int insX = JvGetterSettings.getInstance().getBeanDisplaySettings().
                getResizeFromDisplay(0.025,
                        JvDisplaySettings.TypeOfDisplayBorder.WIDTH);
        int gridyNum = 0;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.03), insX,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.0045), insX);
        gbc.gridy = gridyNum;
        panel.add(tLogin, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, insX,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.0045), insX);
        gbc.gridy = gridyNum;
        panel.add(tEmail, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, insX,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.0045), insX);
        gbc.gridy = gridyNum;
        panel.add(tPassword, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, insX, 0, insX);
        gbc.gridy = gridyNum;
        panel.add(tPasswordConfirm, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, insX,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.0084), insX);
        gbc.gridy = gridyNum;
        panel.add(tErrorHelpInfo, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.PAGE_END;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 0,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.017), 0);
        gbc.ipadx = JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.03,
                JvDisplaySettings.TypeOfDisplayBorder.WIDTH);
        gbc.ipady = JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.01,
                JvDisplaySettings.TypeOfDisplayBorder.HEIGHT);
        gbc.gridy = gridyNum;
        panel.add(bRegister, gbc);

        getContentPane().add(panel);
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

        titlePanel.getCloseButton().addActionListener(event -> closeWindow());

        titlePanel.getMinimizeButton().addActionListener(event -> minimizeWindow());
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

    private void addGeneralSettingsToWidget() {
        setUndecorated(true);
        getContentPane().add(titlePanel, BorderLayout.NORTH);
        pack();

        setSize(JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.3,
                        JvDisplaySettings.TypeOfDisplayBorder.WIDTH),
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.31,
                        JvDisplaySettings.TypeOfDisplayBorder.HEIGHT));

        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        toFront();

        getRootPane().setDefaultButton(bRegister);

        setVisible(true);
        requestFocus();
    }

    private void nextCloseWindow() {
        JvVerifyCodeFrameAuthUI frm = JvGetterAuthUIComponents.getInstance()
                .getBeanVerifyCodeFrameAuthUI(JvVerifyCodeFrameAuthUI.RegimeWork.Registration);
        frm.setRegime(JvVerifyCodeFrameAuthUI.RegimeWork.Registration);
        frm.setParametersRegistration(tLogin.getInputText(), tEmail.getInputText(), tPassword.getInputText());
        frm.openWindow();
        setVisible(false);
        //dispose();
        tLogin.setUnfocusFieldOnClose(true);
        tEmail.setUnfocusFieldOnClose(true);
        tPassword.setUnfocusFieldOnClose(true);
        tPasswordConfirm.setUnfocusFieldOnClose(true);
    }

    private void closeWindow() {
        JvGetterAuthUIComponents.getInstance().getBeanMainFrameAuthUI().openWindow();
        tLogin.setUnfocusFieldOnClose(true);
        tEmail.setUnfocusFieldOnClose(true);
        tPassword.setUnfocusFieldOnClose(true);
        tPasswordConfirm.setUnfocusFieldOnClose(true);
        setVisible(false);
    }

    private void minimizeWindow() {
        setState(Frame.ICONIFIED);
    }

    public void openWindow() {
        setVisible(true);
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
            nextCloseWindow();
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
                    .getBeanOptionPaneAuthUI("Ошибка не выяснена.", JvOptionPaneAuthUI.TypeDlg.ERROR);
            case EmailSending -> JvGetterAuthUIComponents.getInstance()
                    .getBeanOptionPaneAuthUI("Возможно почта недействительна.", JvOptionPaneAuthUI.TypeDlg.ERROR);
            case Login -> JvGetterAuthUIComponents.getInstance()
                    .getBeanOptionPaneAuthUI("Данный логин уже используется.", JvOptionPaneAuthUI.TypeDlg.ERROR);
            case Email -> JvGetterAuthUIComponents.getInstance()
                    .getBeanOptionPaneAuthUI("Данная почта уже используется.", JvOptionPaneAuthUI.TypeDlg.ERROR);
            case LoginAndEmail ->
                    JvGetterAuthUIComponents.getInstance()
                            .getBeanOptionPaneAuthUI("Данные почта и логин уже используются.", JvOptionPaneAuthUI.TypeDlg.ERROR);
        }
    }
}