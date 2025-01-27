package org.foomaa.jvchat.uicomponents.auth;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import org.foomaa.jvchat.ctrl.JvGetterControls;
import org.foomaa.jvchat.ctrl.JvMessagesDefinesCtrl;
import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.messages.JvDefinesMessages;
import org.foomaa.jvchat.settings.JvDisplaySettings;
import org.foomaa.jvchat.settings.JvGetterSettings;
import org.foomaa.jvchat.uicomponents.mainchat.JvGetterMainChatUIComponents;


public class JvEntryFrameAuthUI extends JFrame {
    private JPanel panel;
    private final JvTextFieldAuthUI tLogin;
    private final JvErrorLabelAuthUI tErrorHelpInfo;
    private final JvPasswordFieldAuthUI tPassword;
    private final JvButtonAuthUI bEnter;
    private final JvActiveLabelAuthUI activeRegisterLabel;
    private final JvActiveLabelAuthUI activeMissLabel;
    private final JvTitlePanelAuthUI titlePanel;

    JvEntryFrameAuthUI() {
        super("EntryWindow");

        createPanel("/AuthMainBackground.png");

        tLogin = JvGetterAuthUIComponents.getInstance().getBeanTextFieldAuthUI("Login");
        tErrorHelpInfo = JvGetterAuthUIComponents.getInstance().getBeanErrorLabelAuthUI("");
        tErrorHelpInfo.settingToError();
        tPassword = JvGetterAuthUIComponents.getInstance().getBeanPasswordFieldAuthUI("Password");
        bEnter = JvGetterAuthUIComponents.getInstance().getBeanButtonAuthUI("NEXT");
        activeMissLabel = JvGetterAuthUIComponents.getInstance().getBeanActiveLabelAuthUI("Reset password");
        activeRegisterLabel = JvGetterAuthUIComponents.getInstance().getBeanActiveLabelAuthUI("Registration");
        titlePanel = JvGetterAuthUIComponents.getInstance().getBeanTitlePanelAuthUI("Entry");

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
        bEnter.setToolTip("To log in");
        tPassword.setToolTip("To set password");
        tLogin.setToolTip("To set login");
        activeMissLabel.setToolTip("To the password recovery form");
        activeRegisterLabel.setToolTip("To registration form");
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
                getResizeFromDisplay(0.025, JvDisplaySettings.TypeOfDisplayBorder.WIDTH);
        int gridyNum = 0;

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.075), insX,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.004), insX);
        gbc.gridy = gridyNum;
        panel.add(tLogin, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, insX, JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.01), insX);
        gbc.gridy = gridyNum;
        panel.add(tPassword, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridy = gridyNum;
        panel.add(activeMissLabel, gbc);
        gridyNum++;

        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 0,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.002), 0);
        gbc.gridy = gridyNum;
        panel.add(activeRegisterLabel, gbc);
        gridyNum++;

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, insX,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.0084), insX);
        gbc.gridy = gridyNum;
        panel.add(tErrorHelpInfo, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets(0, 0,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.017), 0);
        gbc.ipadx = JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.015,
                JvDisplaySettings.TypeOfDisplayBorder.WIDTH);
        gbc.ipady = JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.004,
                JvDisplaySettings.TypeOfDisplayBorder.HEIGHT);
        gbc.gridy = gridyNum;
        panel.add(bEnter, gbc);

        getContentPane().add(panel);
    }

    private void addListenerToElements() {
        bEnter.addActionListener(event -> {
            if (checkFields()) {
                JvGetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(JvDefinesMessages.TypeMessage.EntryRequest,
                        tLogin.getInputText(), tPassword.getInputText());
                waitRepeatServer();
            }
        });

        activeMissLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JvGetterAuthUIComponents.getInstance().getBeanResetPasswordFrameAuthUI().openWindow();
                closeWindow();
            }
        });

        activeRegisterLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JvGetterAuthUIComponents.getInstance().getBeanRegistrationFrameAuthUI().openWindow();
                closeWindow();
            }
        });

        titlePanel.getCloseButton().addActionListener(event -> {
            closeWindow();
            System.exit(0);
        });

        titlePanel.getMinimizeButton().addActionListener(event -> minimizeWindow());
    }

    private boolean checkFields() {
        tPassword.setNormalBorder();
        tLogin.setNormalBorder();
        tErrorHelpInfo.setText("");

        Vector<String> fields = new Vector<>();

        if (Objects.equals(tLogin.getInputText(), "")) {
            tLogin.setErrorBorder();
            fields.add("\"Login\"");
        }
        if (Objects.equals(tPassword.getInputText(), "")) {
            tPassword.setErrorBorder();
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

    private void closeWindow() {
        //dispose();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(false);
        tLogin.setUnfocusFieldOnClose(true);
        tPassword.setUnfocusFieldOnClose(true);
    }

    private void minimizeWindow() {
        setState(Frame.ICONIFIED);
    }

    public void openWindow() {
        setVisible(true);
    }

    private void addGeneralSettingsToWidget() {
        setUndecorated(true);
        getContentPane().add(titlePanel, BorderLayout.NORTH);
        pack();

        setSize(JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.3,
                        JvDisplaySettings.TypeOfDisplayBorder.WIDTH),
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.3,
                        JvDisplaySettings.TypeOfDisplayBorder.HEIGHT));

        setResizable(false);
        setLocationRelativeTo(null);
        toFront();

        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));

        getRootPane().setDefaultButton(bEnter);

        setVisible(true);
        requestFocus();
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

    private void waitRepeatServer() {
        setEnabled(false);
        while (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getEntryRequestFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.DEFAULT) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException exception) {
                JvLog.write(JvLog.TypeLog.Error, "Couldn't wait.");
            }
        }
        if (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getEntryRequestFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.TRUE) {
            openMainPage();
        } else if (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getEntryRequestFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.FALSE) {
            setEnabled(true);
            JvGetterAuthUIComponents.getInstance()
                    .getBeanOptionPaneAuthUI("Login failed, data is incorrect.", JvOptionPaneAuthUI.TypeDlg.ERROR);
        }
    }

    private void openMainPage() {
        JvGetterSettings.getInstance().getBeanUsersInfoSettings().setLogin(tLogin.getInputText());

        UUID uuidUser = JvGetterSettings.getInstance().getBeanUsersInfoSettings().getUuid();
        JvGetterControls.getInstance().getBeanSendMessagesCtrl()
                .sendMessage(JvDefinesMessages.TypeMessage.CheckOnlineUserReply, uuidUser);

        closeWindow();
        setEnabled(true);

        JvGetterMainChatUIComponents.getInstance().getBeanMainFrameMainChatUI().openWindow();

        JvLog.write(JvLog.TypeLog.Info, "Login done.");
    }
}