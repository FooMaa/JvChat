package org.foomaa.jvchat.uicomponents.mainchat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
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
    private JvRectChatMainChatUI selectedElement;

    JvScrollPanelChatsMainChatUI() {
        intervalMilliSecondsSleepUpdating = 30000;
        intervalSecondsWaitLoopUpdate = 5;

        selectedElement = null;

        makePanel();
        runningThreadUpdateOnline();
    }

    private void makePanel() {
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
        String loginRequesting = JvGetterSettings.getInstance().getBeanUsersInfoSettings().getLogin();
        String loginDialog = selectedElement.getNickName();
        int quantityMessages = JvGetterSettings.getInstance().getBeanUISettings().getQuantityMessagesLoad();

        JvGetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(
                JvDefinesMessages.TypeMessage.MessagesLoadRequest, loginRequesting, loginDialog, quantityMessages);
    }

    private void setRequestChatsToServer() {
        UUID uuidUser = JvGetterSettings.getInstance().getBeanUsersInfoSettings().getUuid();

        JvGetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(
                JvDefinesMessages.TypeMessage.ChatsLoadRequest, uuidUser);
    }

    private List<JvChatStructObject> getChatsObjects() {
        List<JvChatStructObject> loginsList = new ArrayList<>();
        while (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getChatsLoadReplyFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.DEFAULT) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException exception) {
                JvLog.write(JvLog.TypeLog.Error, "Не удалось ждать");
            }

            if (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getChatsLoadReplyFlag() ==
                    JvMessagesDefinesCtrl.TypeFlags.TRUE) {
                loginsList = JvGetterControls.getInstance().getBeanChatsCtrl().getChatsObjects();
            }
        }

        return loginsList;
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
        List<String> loginsChats = JvGetterControls.getInstance().getBeanChatsCtrl().getLoginsChats();
        JvGetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(
                JvDefinesMessages.TypeMessage.LoadUsersOnlineStatusRequest,
                loginsChats);
    }

    private void installingUpdatingDataInRectChats() {
        for (Component component : boxComponents.getComponents()) {
            JvRectChatMainChatUI rectChatMainChatUI = (JvRectChatMainChatUI) component;
            String login = rectChatMainChatUI.getNickName();

            JvUserStructObject user = JvGetterControls.getInstance().getBeanChatsCtrl().getUserObjectsByLogin(login);
            String lastOnlineString = JvGetterControls.getInstance().getBeanChatsCtrl().getTimeFormattedLastOnline(user.getTimestampLastOnline());

            rectChatMainChatUI.setLastOnlineDateTime(lastOnlineString);
            rectChatMainChatUI.setStatusOnline(user.getStatusOnline());
        }
    }

    public Box getBoxComponents() {
        return boxComponents;
    }
}
