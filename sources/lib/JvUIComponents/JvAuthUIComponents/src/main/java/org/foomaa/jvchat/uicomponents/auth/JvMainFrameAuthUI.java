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
    private RegimeWork regimeWork;
    private UUID uuidTEST;

    public enum RegimeWork {
        Auth,
        Registration,
        ResetPassword,
        VerifyCode,
        NewPassword
    }

    JvMainFrameAuthUI() {
        super("EntryFrame");

        titlePanel = JvGetterAuthUIComponents.getInstance().getBeanTitlePanelAuthUI("Entry");
        regimeWork = RegimeWork.Auth;

        setIconImageFrame("/MainAppIcon.png");
        settingMovingTitlePanel();
        addListenerToElements();
        addGeneralSettingsToWidget();
        setPanel();

        uuidTEST = JvGetterEvents.getInstance().getBeanMakerEvents().addConnect(JvGetterAuthUIComponents.getInstance().getBeanEntryPanelAuthUI(), this, "Test", JvGetterAuthUIComponents.getInstance().getContext());
    }

    @JvCheckerEventsAnnotation(connectionUuid = "uuidTEST")
    @EventListener
    @Async
    public void handleSuccessful(JvBaseEvent event) {
        if (event.getUuidKey() != uuidTEST) return;
        System.out.println(event.getUuidKey());
        System.out.println(uuidTEST);
        System.out.println("######EVENT");
    }

    private void setPanel() {
        getContentPane().add(JvGetterAuthUIComponents.getInstance().getBeanEntryPanelAuthUI());
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

    private void closeWindow() {
        //dispose();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(false);
//        tLogin.setUnfocusFieldOnClose(true);
//        tPassword.setUnfocusFieldOnClose(true);
    }

    public void openWindow() {
        setVisible(true);
    }

    private void minimizeWindow() {
        setState(Frame.ICONIFIED);
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
        toFront();

        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));

//        getRootPane().setDefaultButton(bEnter);

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
