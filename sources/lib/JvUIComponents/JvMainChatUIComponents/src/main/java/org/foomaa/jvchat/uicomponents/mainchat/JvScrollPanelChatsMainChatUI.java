package org.foomaa.jvchat.uicomponents.mainchat;

import org.foomaa.jvchat.ctrl.JvChatsCtrl;
import org.foomaa.jvchat.ctrl.JvGetterControls;
import org.foomaa.jvchat.ctrl.JvMessagesDefinesCtrl;
import org.foomaa.jvchat.globaldefines.JvMainChatsGlobalDefines;
import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.messages.JvDefinesMessages;
import org.foomaa.jvchat.settings.JvGetterSettings;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class JvScrollPanelChatsMainChatUI extends JPanel {
    private static JvScrollPanelChatsMainChatUI instance;
    private final int intervalMilliSecondsSleepUpdating;
    private final int intervalSecondsWaitLoopUpdate;
    private Box boxComponents;

    private JvScrollPanelChatsMainChatUI() {
        intervalMilliSecondsSleepUpdating = 30000;
        intervalSecondsWaitLoopUpdate = 5;
        makePanel();
        runningThreadUpdateOnline();
    }

    public static JvScrollPanelChatsMainChatUI getInstance() {
        if (instance == null) {
            instance = new JvScrollPanelChatsMainChatUI();
        }
        return instance;
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

        JvChatsCtrl chatsCtrl = JvGetterControls.getInstance().getBeanChatsCtrl();
        List<String> loginsList = getLoginsList();

        for (String login : loginsList) {
            JvRectChatMainChatUI component = JvGetterMainChatUIComponents.getInstance()
                    .getBeanRectChatMainChatUI(
                            login,
                            chatsCtrl.getLastMessage(login),
                            chatsCtrl.getLastMessageSender(login),
                            chatsCtrl.getTimeHMLastMessage(login),
                            chatsCtrl.getStatusLastMessage(login));
            boxComponents.add(component);
            addListenerToElements(component);
        }
    }

    private void setRequestChatsToServer() {
        String login = JvGetterSettings.getInstance().getBeanUsersInfoSettings().getLogin();

        JvGetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(
                JvDefinesMessages.TypeMessage.ChatsLoadRequest, login);
    }

    private List<String> getLoginsList() {
        List<String> loginsList = new ArrayList<>();
        while (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getChatsLoadReplyFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.DEFAULT) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException exception) {
                JvLog.write(JvLog.TypeLog.Error, "Не удалось ждать");
            }

            if (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getChatsLoadReplyFlag() ==
                    JvMessagesDefinesCtrl.TypeFlags.TRUE) {
                JvChatsCtrl chatsCtrl = JvGetterControls.getInstance().getBeanChatsCtrl();
                loginsList = chatsCtrl.getLoginsChats();
            }
        }

        return loginsList;
    }

    public void addListenerToElements(Component component) {
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("###");
            }
        });;
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
        Map<String, JvMainChatsGlobalDefines.TypeStatusOnline> onlineStatusesUsers =
                JvGetterControls.getInstance().getBeanChatsCtrl().getOnlineStatusesUsers();
        Map<String, String> lastOnlineTimeUsers =
                JvGetterControls.getInstance().getBeanChatsCtrl().getLastOnlineTimeUsersText();

        for (Component component : boxComponents.getComponents()) {
            JvRectChatMainChatUI rectChatMainChatUI = (JvRectChatMainChatUI) component;
            String login = rectChatMainChatUI.getNickName();
            rectChatMainChatUI.setLastOnlineDateTime(lastOnlineTimeUsers.get(login));
            rectChatMainChatUI.setStatusOnline(onlineStatusesUsers.get(login));
        }
    }
}
