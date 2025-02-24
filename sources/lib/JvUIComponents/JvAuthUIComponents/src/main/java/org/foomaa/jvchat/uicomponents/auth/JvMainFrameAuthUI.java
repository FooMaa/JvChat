package org.foomaa.jvchat.uicomponents.auth;

import org.foomaa.jvchat.events.JvCheckerEventsAnnotation;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import org.foomaa.jvchat.events.JvBaseEvent;
import org.foomaa.jvchat.events.JvGetterEvents;
import org.foomaa.jvchat.settings.JvDisplaySettings;
import org.foomaa.jvchat.settings.JvGetterSettings;


public class JvMainFrameAuthUI extends JFrame {
    private final JvTitlePanelAuthUI titlePanel;
    private JvDefinesAuthUI.RegimeWorkMainFrame regimeWorkMainFrame;
    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private UUID uuidSignalCloseWindow;
    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private UUID uuidSignalChangeRegimeWorkEntry;
    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private UUID uuidSignalChangeRegimeWorkRegistration;

    JvMainFrameAuthUI() {
        super("EntryFrame");

        titlePanel = JvGetterAuthUIComponents.getInstance().getBeanTitlePanelAuthUI();
        regimeWorkMainFrame = JvDefinesAuthUI.RegimeWorkMainFrame.Auth;

        setIconImageFrame("/MainAppIcon.png");
        setPanelSettings();
        settingMovingTitlePanel();
        addListenerToElements();
        addGeneralSettingsToWidget();
        createConnections();
    }

    private void createConnections() {
        uuidSignalCloseWindow =
                JvGetterEvents.getInstance().getBeanMakerEvents().addConnect(
                        JvGetterAuthUIComponents.getInstance().getBeanEntryPanelAuthUI(),
                        this,
                        "closeWindow",
                        JvGetterAuthUIComponents.getInstance().getContext());
        uuidSignalChangeRegimeWorkEntry =
                JvGetterEvents.getInstance().getBeanMakerEvents().addConnect(
                        JvGetterAuthUIComponents.getInstance().getBeanEntryPanelAuthUI(),
                        this,
                        "changeRegimeWork",
                        JvGetterAuthUIComponents.getInstance().getContext());
        uuidSignalChangeRegimeWorkRegistration =
                JvGetterEvents.getInstance().getBeanMakerEvents().addConnect(
                        JvGetterAuthUIComponents.getInstance().getBeanRegistrationPanelAuthUI(),
                        this,
                        "changeRegimeWork",
                        JvGetterAuthUIComponents.getInstance().getContext());
    }

    private void setPanelSettings() {
        switch (regimeWorkMainFrame) {
            case Auth -> {
                titlePanel.setTitle("Entry");
                getContentPane().removeAll();
                getContentPane().add(titlePanel, BorderLayout.NORTH);
                getRootPane().setDefaultButton(JvGetterAuthUIComponents.getInstance().getBeanEntryPanelAuthUI().getDefaultButton());
                getContentPane().add(JvGetterAuthUIComponents.getInstance().getBeanEntryPanelAuthUI());
            }
            case Registration -> {
                titlePanel.setTitle("Registration");
                getContentPane().removeAll();
                getContentPane().add(titlePanel, BorderLayout.NORTH);
                getRootPane().setDefaultButton(JvGetterAuthUIComponents.getInstance().getBeanRegistrationPanelAuthUI().getDefaultButton());
                getContentPane().add(JvGetterAuthUIComponents.getInstance().getBeanRegistrationPanelAuthUI());
            }
            case VerifyCodeRegistration -> {
                titlePanel.setTitle("Verify code");
            }
            case VerifyCodeChangePassword -> {
                titlePanel.setTitle("Verify code");
            }
            case NewPassword -> {
                titlePanel.setTitle("New password");
            }
            case ResetPassword -> {
                titlePanel.setTitle("Reset password");
            }
        }
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
            closeWindow(null);
            System.exit(0);
        });

        titlePanel.getMinimizeButton().addActionListener(event -> minimizeWindow());
    }

    @JvCheckerEventsAnnotation(connectionUuid = "uuidSignalCloseWindow")
    @EventListener
    @Async
    @SuppressWarnings("unused")
    public void closeWindow(JvBaseEvent event) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(false);
    }

    @JvCheckerEventsAnnotation(connectionUuid = {"uuidSignalChangeRegimeWorkEntry", "uuidSignalChangeRegimeWorkRegistration"})
    @EventListener
    @Async
    @SuppressWarnings("unused")
    public void changeRegimeWork(JvBaseEvent event) {
        System.out.println("assss");
        regimeWorkMainFrame = (JvDefinesAuthUI.RegimeWorkMainFrame) event.getData();
        setPanelSettings();
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

        setSize(JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.3,
                        JvDisplaySettings.TypeOfDisplayBorder.WIDTH),
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.31,
                        JvDisplaySettings.TypeOfDisplayBorder.HEIGHT));

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
