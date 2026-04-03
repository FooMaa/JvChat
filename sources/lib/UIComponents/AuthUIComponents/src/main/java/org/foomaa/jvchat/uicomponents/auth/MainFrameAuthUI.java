package org.foomaa.jvchat.uicomponents.auth;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.*;

import lombok.Builder;

import org.foomaa.jvchat.settings.DisplaySettings;

public class MainFrameAuthUI extends JFrame {
    private DefinesAuthUI.RegimeWorkMainFrame regimeWorkMainFrame;
    private JPanel backgroundPanel;
    private final String backgroundPath;
    private final String loadGifPath;
    private JLabel loadGifLabel;

    // DI ↓
    private final DisplaySettings displaySettings;
    private final EntryPanelAuthUI entryPanelAuthUI;
    private final NewPasswordPanelAuthUI newPasswordPanelAuthUI;
    private final RegistrationPanelAuthUI registrationPanelAuthUI;
    private final ResetPasswordPanelAuthUI resetPasswordPanelAuthUI;
    private final VerifyCodePanelAuthUI verifyCodePanelAuthUI;
    private final TitlePanelAuthUI titlePanel;

    @Builder
    MainFrameAuthUI(
            DisplaySettings displaySettings,
            EntryPanelAuthUI entryPanelAuthUI,
            NewPasswordPanelAuthUI newPasswordPanelAuthUI,
            RegistrationPanelAuthUI registrationPanelAuthUI,
            ResetPasswordPanelAuthUI resetPasswordPanelAuthUI,
            VerifyCodePanelAuthUI verifyCodePanelAuthUI,
            TitlePanelAuthUI titlePanel) {
        super("EntryFrame");

        this.displaySettings = Objects.requireNonNull(displaySettings, "displaySettings is mandatory");
        this.entryPanelAuthUI = Objects.requireNonNull(entryPanelAuthUI, "entryPanelAuthUI is mandatory");
        this.newPasswordPanelAuthUI =
                Objects.requireNonNull(newPasswordPanelAuthUI, "newPasswordPanelAuthUI is mandatory");
        this.registrationPanelAuthUI =
                Objects.requireNonNull(registrationPanelAuthUI, "registrationPanelAuthUI is mandatory");
        this.resetPasswordPanelAuthUI =
                Objects.requireNonNull(resetPasswordPanelAuthUI, "resetPasswordPanelAuthUI is mandatory");
        this.verifyCodePanelAuthUI =
                Objects.requireNonNull(verifyCodePanelAuthUI, "verifyCodePanelAuthUI is mandatory");
        this.titlePanel = Objects.requireNonNull(titlePanel, "titlePanel is mandatory");

        regimeWorkMainFrame = DefinesAuthUI.RegimeWorkMainFrame.Auth;
        backgroundPath = "/AuthMainBackground.png";
        loadGifPath = "/Load.gif";

        setIconImageFrame("/MainAppIcon.png");
        settingBackgroundPanel();
        settingLoadLabel();
        setPanelSettings();
        settingMovingTitlePanel();
        addListenerToElements();
        addGeneralSettingsToWidget();
        createConnections();
    }

    private void createConnections() {
        entryPanelAuthUI.getCloseWindow().connect(this::closeWindow);
        entryPanelAuthUI.getChangeRegimeWork().connect(signal -> changeRegimeWork(signal.regime()));

        registrationPanelAuthUI
                .getChangeRegimeWorkNext()
                .connect(
                        signal -> changeRegimeWork(signal.regime(), signal.login(), signal.email(), signal.password()));
        registrationPanelAuthUI.getChangeRegimeWorkNext().connect(signal -> changeRegimeWork(signal.regime()));

        verifyCodePanelAuthUI.getChangeRegimeWork().connect(signal -> changeRegimeWork(signal.regime()));
        verifyCodePanelAuthUI
                .getChangeRegimeWorkWithEmail()
                .connect(signal -> changeRegimeWork(signal.regime(), signal.email()));

        resetPasswordPanelAuthUI.getChangeRegimeWork().connect(signal -> changeRegimeWork(signal.regime()));
        resetPasswordPanelAuthUI
                .getChangeRegimeWorkWithEmail()
                .connect(signal -> changeRegimeWork(signal.regime(), signal.email()));

        newPasswordPanelAuthUI.getChangeRegimeWork().connect(signal -> changeRegimeWork(signal.regime()));
    }

    private void settingBackgroundPanel() {
        backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Image img = null;
                try {
                    img = ImageIO.read(Objects.requireNonNull(getClass().getResource(backgroundPath)));
                } catch (IOException e) {
                    e.getStackTrace();
                }

                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        getContentPane().add(backgroundPanel);
    }

    private void setPanelSettings(Object... data) {
        switch (regimeWorkMainFrame) {
            case Auth -> {
                EntryPanelAuthUI entryPanel = entryPanelAuthUI;
                loadGifStart("Entry", entryPanel.getDefaultButton(), entryPanel);
            }
            case Registration -> {
                RegistrationPanelAuthUI registrationPanel = registrationPanelAuthUI;
                loadGifStart("Registration", registrationPanel.getDefaultButton(), registrationPanel);
            }
            case VerifyCodeRegistration -> {
                VerifyCodePanelAuthUI verifyCodePanel = verifyCodePanelAuthUI;
                loadGifStart("Verify code", verifyCodePanel.getDefaultButton(), verifyCodePanel);
                verifyCodePanel.setParametersRegistration((String) data[1], (String) data[2], (String) data[3]);
            }
            case VerifyCodeResetPassword -> {
                VerifyCodePanelAuthUI verifyCodePanel = verifyCodePanelAuthUI;
                loadGifStart("Verify code", verifyCodePanel.getDefaultButton(), verifyCodePanel);
                verifyCodePanel.setParametersResetPassword((String) data[1]);
            }
            case ResetPassword -> {
                ResetPasswordPanelAuthUI resetPasswordPanel = resetPasswordPanelAuthUI;
                loadGifStart("Reset password", resetPasswordPanel.getDefaultButton(), resetPasswordPanel);
            }
            case NewPassword -> {
                NewPasswordPanelAuthUI newPasswordPanel = newPasswordPanelAuthUI;
                loadGifStart("New password", newPasswordPanel.getDefaultButton(), newPasswordPanel);
                newPasswordPanel.setEmail((String) data[1]);
            }
        }
    }

    private void updateVisualPanel(String textTitle, ButtonAuthUI defaultButton, JPanel newPanel) {
        titlePanel.setTitle(textTitle);
        getContentPane().remove(titlePanel);
        backgroundPanel.removeAll();

        getContentPane().add(titlePanel, BorderLayout.NORTH);
        getRootPane().setDefaultButton(defaultButton);

        // backgroundPanel.add(newPanel);
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.add(newPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private void loadGifStart(String textTitle, ButtonAuthUI defaultButton, JPanel newPanel) {
        Timer timerLoadGif = new Timer(1000, actionEvent -> updateVisualPanel(textTitle, defaultButton, newPanel));
        timerLoadGif.setRepeats(false);

        loadingState();

        timerLoadGif.start();
    }

    private void settingLoadLabel() {
        loadGifLabel =
                new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource(loadGifPath))));
        loadGifLabel.setOpaque(false);
        loadGifLabel.setBackground(new Color(0, 0, 0, 0));
    }

    private void loadingState() {
        titlePanel.setTitle("Loading");
        getContentPane().remove(titlePanel);
        backgroundPanel.removeAll();
        getContentPane().add(titlePanel, BorderLayout.NORTH);

        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.add(loadGifLabel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private void setIconImageFrame(String path) {
        try {
            Image img = ImageIO.read(Objects.requireNonNull(getClass().getResource(path)));
            setIconImage(img);
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    private void addListenerToElements() {
        titlePanel.getCloseButton().addActionListener(event -> {
            closeWindow();
            System.exit(0);
        });

        titlePanel.getMinimizeButton().addActionListener(event -> minimizeWindow());
    }

    public void closeWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(false);
    }

    public void changeRegimeWork(DefinesAuthUI.RegimeWorkMainFrame regime) {
        regimeWorkMainFrame = regime;
        setPanelSettings();
    }

    public void changeRegimeWork(
            DefinesAuthUI.RegimeWorkMainFrame regime, String login, String email, String password) {
        regimeWorkMainFrame = regime;
        setPanelSettings(login, email, password);
    }

    public void changeRegimeWork(DefinesAuthUI.RegimeWorkMainFrame regime, String email) {
        regimeWorkMainFrame = regime;
        setPanelSettings(email);
    }

    public void openWindow() {
        setVisible(true);
    }

    private void minimizeWindow() {
        setState(Frame.ICONIFIED);
    }

    private void addGeneralSettingsToWidget() {
        setUndecorated(true);
        pack();

        setSize(
                displaySettings.getResizeFromDisplay(0.3, DisplaySettings.TypeOfDisplayBorder.WIDTH),
                displaySettings.getResizeFromDisplay(0.31, DisplaySettings.TypeOfDisplayBorder.HEIGHT));

        setResizable(false);
        setLocationRelativeTo(null);
        toFront();

        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));

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
}
