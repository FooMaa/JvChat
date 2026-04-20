package org.foomaa.jvchat.uicomponents.mainchat;

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

import javax.imageio.ImageIO;
import javax.swing.*;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.ctrl.ChatsCtrl;
import org.foomaa.jvchat.ctrl.MessagesDefinesCtrl;
import org.foomaa.jvchat.ctrl.SendMessagesCtrl;
import org.foomaa.jvchat.messages.DefinesMessages;
import org.foomaa.jvchat.settings.UISettings;
import org.foomaa.jvchat.settings.UsersInfoSettings;
import org.foomaa.jvchat.structobjects.ChatStructObject;
import org.foomaa.jvchat.structobjects.UserStructObject;

@Slf4j
public class ScrollPanelChatsMainChatUI extends JPanel {
    private final int intervalMilliSecondsSleepUpdating;
    private final int intervalSecondsWaitLoopUpdate;

    @Getter
    private Box boxComponents;

    private final String backgroundPath;
    private final String loadGifPath;
    private JLabel loadGifLabel;
    private RectChatMainChatUI selectedElement;

    // DI ↓
    private final UsersInfoSettings usersInfoSettings;
    private final UISettings uiSettings;
    private final SendMessagesCtrl sendMessagesCtrl;
    private final MessagesDefinesCtrl messagesDefinesCtrl;
    private final ChatsCtrl chatsCtrl;
    private final RectChatMainChatUIFactory rectChatFactory;

    @Builder
    ScrollPanelChatsMainChatUI(
            UsersInfoSettings usersInfoSettings,
            UISettings uiSettings,
            SendMessagesCtrl sendMessagesCtrl,
            MessagesDefinesCtrl messagesDefinesCtrl,
            ChatsCtrl chatsCtrl,
            RectChatMainChatUIFactory rectChatFactory) {
        this.usersInfoSettings = Objects.requireNonNull(usersInfoSettings, "usersInfoSettings is mandatory");
        this.uiSettings = Objects.requireNonNull(uiSettings, "uiSettings is mandatory");
        this.sendMessagesCtrl = Objects.requireNonNull(sendMessagesCtrl, "sendMessagesCtrl is mandatory");
        this.messagesDefinesCtrl = Objects.requireNonNull(messagesDefinesCtrl, "messagesDefinesCtrl is mandatory");
        this.chatsCtrl = Objects.requireNonNull(chatsCtrl, "chatsCtrl is mandatory");
        this.rectChatFactory = Objects.requireNonNull(rectChatFactory, "rectChatFactory is mandatory");

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
        super.paintComponent(g);

        Image img = null;
        try {
            img = ImageIO.read(Objects.requireNonNull(getClass().getResource(backgroundPath)));
        } catch (IOException e) {
            e.getStackTrace();
        }

        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }

    private void settingLoadLabel() {
        loadGifLabel =
                new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource(loadGifPath))));
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

        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 7, Color.GRAY));

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
        gbc.fill =
                scrollPane.getVerticalScrollBar().isVisible() ? GridBagConstraints.BOTH : GridBagConstraints.HORIZONTAL;
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

        List<ChatStructObject> chatsObjects = getChatsObjects();

        for (ChatStructObject chat : chatsObjects) {
            RectChatMainChatUI component = rectChatFactory.create(chat);
            component.paintRect();
            boxComponents.add(component);
            connectSelectingElement(component);
        }
    }

    private void connectSelectingElement(RectChatMainChatUI component) {
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                changeSelectElement(component);
                requestMessagesFromServer();
            }
        });
    }

    private void changeSelectElement(RectChatMainChatUI component) {
        if (selectedElement != null) {
            selectedElement.setFlagSelect(false);
        }
        component.setFlagSelect(true);
        selectedElement = component;
    }

    private void requestMessagesFromServer() {
        UUID uuidChat = selectedElement.getUuidChat();
        int quantityMessages = uiSettings.getQuantityMessagesLoad();

        sendMessagesCtrl.sendMessage(DefinesMessages.TypeMessage.MessagesLoadRequest, uuidChat, quantityMessages);
    }

    private void setRequestChatsToServer() {
        UUID uuidUser = usersInfoSettings.getUuid();
        sendMessagesCtrl.sendMessage(DefinesMessages.TypeMessage.ChatsLoadRequest, uuidUser);
    }

    private List<ChatStructObject> getChatsObjects() {
        List<ChatStructObject> chatsStructObjectsList = new ArrayList<>();
        while (messagesDefinesCtrl.getChatsLoadReplyFlag() == MessagesDefinesCtrl.TypeFlags.DEFAULT) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException exception) {
                log.error("Не удалось ждать");
            }

            if (messagesDefinesCtrl.getChatsLoadReplyFlag() == MessagesDefinesCtrl.TypeFlags.TRUE) {
                chatsStructObjectsList = chatsCtrl.getChatsObjects();
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

        while (messagesDefinesCtrl.getLoadUsersOnlineReplyFlag() == MessagesDefinesCtrl.TypeFlags.DEFAULT) {
            try {
                TimeUnit.SECONDS.sleep(intervalSecondsWaitLoopUpdate);
            } catch (InterruptedException exception) {
                log.error("Здесь не удалось выполнить sleep()");
            }
        }

        installingUpdatingDataInRectChats();

        try {
            Thread.sleep(intervalMilliSecondsSleepUpdating);
        } catch (InterruptedException exception) {
            log.error("Здесь не удалось выполнить sleep()");
        }
    }

    private void sendingUpdateOnlinePackage() {
        List<UUID> uuidsUsersChats = chatsCtrl.getUuidsUsersChats();
        sendMessagesCtrl.sendMessage(DefinesMessages.TypeMessage.LoadUsersOnlineStatusRequest, uuidsUsersChats);
    }

    private void installingUpdatingDataInRectChats() {
        for (java.awt.Component component : boxComponents.getComponents()) {
            RectChatMainChatUI rectChatMainChatUI = (RectChatMainChatUI) component;

            UUID uuidUser = rectChatMainChatUI.getUuidUser();

            UserStructObject user = chatsCtrl.getUserObjectsByUuidUser(uuidUser);
            String lastOnlineString = chatsCtrl.getTimeFormattedLastOnline(user.getTimestampLastOnline());

            rectChatMainChatUI.setLastOnlineDateTime(lastOnlineString);
            rectChatMainChatUI.setStatusOnline(user.getStatusOnline());
        }
    }
}
