package org.foomaa.jvchat.uicomponents.mainchat;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.util.Objects;

import org.foomaa.jvchat.settings.JvDisplaySettings;
import org.foomaa.jvchat.settings.JvGetterSettings;


public class JvMainFrameMainChatUI extends JFrame {
    private final String backgroundPath;
    private final String loadGifPath;
    private JPanel backgroundPanel;
    private JLabel loadGifLabel;
    private final JvTitlePanelMainChatUI titlePanel;
    private final JvMainPanelMainChatUI mainPanel;

    JvMainFrameMainChatUI() {
        super("MainChatWindow");

        mainPanel = JvGetterMainChatUIComponents.getInstance().getBeanMainPanelMainChatUI();
        titlePanel = JvGetterMainChatUIComponents.getInstance().getBeanTitlePanelMainChatUI();
        backgroundPath = "/MainChatMainBackground.png";
        loadGifPath = "/Load.gif";

        setIconImageFrame("/MainAppIcon.png");
        settingBackgroundPanel();
        settingLoadLabel();
        loadGifStart();
        settingMovingTitlePanel();
        addGeneralSettingsToWidget();
        addListenerToElements();
    }

    private void settingBackgroundPanel() {
        backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g) ;

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

    private void settingLoadLabel() {
        loadGifLabel = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource(loadGifPath))));
        loadGifLabel.setOpaque(false);
        loadGifLabel.setBackground(new Color(0, 0, 0, 0));
    }

    private void updateVisualPanel() {
        titlePanel.setTitle("JvChat");
        getContentPane().remove(titlePanel);
        backgroundPanel.removeAll();

        getContentPane().add(titlePanel, BorderLayout.NORTH);

        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.add(mainPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private void loadGifStart() {
        Timer timerLoadGif = new Timer(1000, actionEvent -> updateVisualPanel());
        timerLoadGif.setRepeats(false);

        loadingState();

        timerLoadGif.start();
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

    private void addGeneralSettingsToWidget() {
        setUndecorated(true);
        pack();

        setSize(JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.585,
                        JvDisplaySettings.TypeOfDisplayBorder.WIDTH),
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.5625,
                        JvDisplaySettings.TypeOfDisplayBorder.HEIGHT));

        Dimension minSiseDimension = new Dimension(JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.43,
                JvDisplaySettings.TypeOfDisplayBorder.WIDTH), JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.28,
                JvDisplaySettings.TypeOfDisplayBorder.HEIGHT));
        setMinimumSize(minSiseDimension);

        setResizable(true);
        setLocationRelativeTo(null);
        toFront();

        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));

        setVisible(true);
        requestFocus();
    }

    private void addListenerToElements() {
        titlePanel.getCloseButton().addActionListener(event -> {
            closeWindow();
            System.exit(0);
        });

        titlePanel.getMinimizeButton().addActionListener(event -> minimizeWindow());
    }

    public void openWindow() {
        setVisible(true);
    }

    public void closeWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(false);
    }

    private void minimizeWindow() {
        setState(Frame.ICONIFIED);
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
