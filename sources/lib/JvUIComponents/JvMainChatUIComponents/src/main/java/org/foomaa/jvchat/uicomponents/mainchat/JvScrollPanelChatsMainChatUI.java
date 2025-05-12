package org.foomaa.jvchat.uicomponents.mainchat;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.foomaa.jvchat.ctrl.JvGetterControls;
import org.foomaa.jvchat.ctrl.JvMessagesDefinesCtrl;
import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.messages.JvDefinesMessages;
import org.foomaa.jvchat.settings.JvGetterSettings;
import org.foomaa.jvchat.structobjects.JvChatStructObject;
import org.foomaa.jvchat.structobjects.JvUserStructObject;


public class JvScrollPanelChatsMainChatUI extends JPanel {
    private final int intervalMilliSecondsSleepUpdating;
    private final int intervalSecondsWaitLoopUpdate;
    private Box boxComponents;
    private final String backgroundPath;
    private final String loadGifPath;
    private JLabel loadGifLabel;
    private JvRectChatMainChatUI selectedElement;

    JvScrollPanelChatsMainChatUI() {
        intervalMilliSecondsSleepUpdating = 30000;
        intervalSecondsWaitLoopUpdate = 5;
        backgroundPath = "/MainChatMainBackground.png";
        loadGifPath = "/Load.gif";

        selectedElement = null;

        settingLoadLabel();
        loadGifStart();
    }

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

    private void settingLoadLabel() {
        loadGifLabel = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource(loadGifPath))));
        loadGifLabel.setOpaque(false);
        loadGifLabel.setBackground(new Color(0, 0, 0, 0));
    }

    private void loadGifStart() {
        Timer timerLoadGif = new Timer(1000, actionEvent -> updateVisualPanel());
        timerLoadGif.setRepeats(false);

        loadingState();

        timerLoadGif.start();
    }

    private void updateVisualPanel() {
        makePanel();
        runningThreadUpdateOnline();
    }

    private void loadingState() {
        removeAll();

        setLayout(new BorderLayout());
        add(loadGifLabel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private void makePanel() {
        removeAll();

        setBorder(BorderFactory.createMatteBorder(0,0,0,7, Color.GRAY));

        boxComponents = Box.createVerticalBox();
        loadChatsInBox();

        JScrollPane scrollPane = new JScrollPane(boxComponents);
        scrollPane.setBorder(null);

        addListenerScrollPane(scrollPane);

        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());

        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.PAGE_START;
        add(scrollPane, gbc);

        revalidate();
        repaint();
    }

    private void changeScrollPane(JScrollPane scrollPane) {
        GridBagConstraints gbc = ((GridBagLayout) getLayout()).getConstraints(scrollPane);

        remove(scrollPane);

        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.fill = scrollPane.getVerticalScrollBar().isVisible() ? GridBagConstraints.BOTH : GridBagConstraints.HORIZONTAL;
        add(scrollPane, gbc);

        revalidate();
        repaint();
    }

    private void addListenerScrollPane(JScrollPane scrollPane) {
        scrollPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                changeScrollPane(scrollPane);
            }
        });
    }

    private void loadChatsInBox() {
        setRequestChatsToServer();

        List<JvChatStructObject> chatsObjects = getChatsObjects();

        for (JvChatStructObject chat : chatsObjects) {
            JvRectChatMainChatUI component = JvGetterMainChatUIComponents.getInstance().getBeanRectChatMainChatUI(chat);
            boxComponents.add(component);
            connectSelectingElement(component);
        }
    }

    private void connectSelectingElement(JvRectChatMainChatUI component) {
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                changeSelectElement(component);
                requestMessagesFromServer();
            }
        });
    }

    private void changeSelectElement(JvRectChatMainChatUI component) {
        if (selectedElement != null) {
            selectedElement.setFlagSelect(false);
        }
        component.setFlagSelect(true);
        selectedElement = component;
    }

    private void requestMessagesFromServer() {
        UUID uuidChat = selectedElement.getUuidChat();
        int quantityMessages = JvGetterSettings.getInstance().getBeanUISettings().getQuantityMessagesLoad();

        JvGetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(
                JvDefinesMessages.TypeMessage.MessagesLoadRequest, uuidChat, quantityMessages);
    }

    private void setRequestChatsToServer() {
        UUID uuidUser = JvGetterSettings.getInstance().getBeanUsersInfoSettings().getUuid();
        JvGetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(
                JvDefinesMessages.TypeMessage.ChatsLoadRequest, uuidUser);
    }

    private List<JvChatStructObject> getChatsObjects() {
        List<JvChatStructObject> chatsStructObjectsList = new ArrayList<>();
        while (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getChatsLoadReplyFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.DEFAULT) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException exception) {
                JvLog.write(JvLog.TypeLog.Error, "Не удалось ждать");
            }

            if (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getChatsLoadReplyFlag() ==
                    JvMessagesDefinesCtrl.TypeFlags.TRUE) {
                chatsStructObjectsList = JvGetterControls.getInstance().getBeanChatsCtrl().getChatsObjects();
            }
        }

        return chatsStructObjectsList;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void runningThreadUpdateOnline() {
        Runnable listenOnline = () -> {
            while (true) {
                processUpdatingOnline();
            }
        };

        Thread thread = new Thread(listenOnline);
        thread.start();
    }

    private void processUpdatingOnline() {
        sendingUpdateOnlinePackage();

        while (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getLoadUsersOnlineReplyFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.DEFAULT) {
            try {
                TimeUnit.SECONDS.sleep(intervalSecondsWaitLoopUpdate);
            } catch (InterruptedException exception) {
                JvLog.write(JvLog.TypeLog.Error, "Здесь не удалось выполнить sleep()");
            }
        }

        installingUpdatingDataInRectChats();

        try {
            Thread.sleep(intervalMilliSecondsSleepUpdating);
        } catch (InterruptedException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Здесь не удалось выполнить sleep()");
        }
    }

    private void sendingUpdateOnlinePackage() {
        List<UUID> uuidsUsersChats = JvGetterControls.getInstance().getBeanChatsCtrl().getUuidsUsersChats();
        JvGetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(
                JvDefinesMessages.TypeMessage.LoadUsersOnlineStatusRequest,
                uuidsUsersChats);
    }

    private void installingUpdatingDataInRectChats() {
        for (Component component : boxComponents.getComponents()) {
            JvRectChatMainChatUI rectChatMainChatUI = (JvRectChatMainChatUI) component;

            UUID uuidUser = rectChatMainChatUI.getUuidUser();

            JvUserStructObject user = JvGetterControls.getInstance().getBeanChatsCtrl().getUserObjectsByUuidUser(uuidUser);
            String lastOnlineString = JvGetterControls.getInstance().getBeanChatsCtrl().getTimeFormattedLastOnline(user.getTimestampLastOnline());

            rectChatMainChatUI.setLastOnlineDateTime(lastOnlineString);
            rectChatMainChatUI.setStatusOnline(user.getStatusOnline());
        }
    }

    public Box getBoxComponents() {
        return boxComponents;
    }
}
