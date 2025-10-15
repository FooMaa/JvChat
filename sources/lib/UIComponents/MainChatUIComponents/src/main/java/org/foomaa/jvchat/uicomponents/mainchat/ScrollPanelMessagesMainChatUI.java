package org.foomaa.jvchat.uicomponents.mainchat;

import org.foomaa.jvchat.ctrl.GetterControls;
import org.foomaa.jvchat.ctrl.MessagesDefinesCtrl;
import org.foomaa.jvchat.logger.Log;
import org.foomaa.jvchat.structobjects.MessageStructObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import java.util.UUID;


public class ScrollPanelMessagesMainChatUI extends JPanel {
    private final int intervalMilliSecondsSleepUpdating;
    private JScrollPane scrollPane;
    private JPanel panel;

    ScrollPanelMessagesMainChatUI() {
        intervalMilliSecondsSleepUpdating = 500;

        makePanel();
        runningThreadUpdateMessagesPanel();
    }

    private void makePanel() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        int unitIncrementScrollBar = 20;

        scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(null);
        scrollPane.setViewportView(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(unitIncrementScrollBar);

        addListenerScrollPane();

        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());

        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.SOUTH;
        add(scrollPane, gbc);
    }

    private void addListenerScrollPane() {
        scrollPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updatePanelMessages();
            }
        });

        scrollPane.getVerticalScrollBar().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                changeScrollPane();
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                changeScrollPane();
            }
       });
    }

    private void changeScrollPane() {
        GridBagConstraints gbc = ((GridBagLayout) getLayout()).getConstraints(scrollPane);

        remove(scrollPane);

        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        gbc.fill = scrollPane.getVerticalScrollBar().isVisible() ? GridBagConstraints.BOTH : GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.SOUTH;
        add(scrollPane, gbc);

        revalidate();
        repaint();
    }

    private void createPanelMessage(MessageStructObject messageObject, String constraints) {
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BorderLayout());

        // NOTE(VAD): надо для того, чтоб компоненты не растягивались
        JPanel tmpPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        tmpPanel.add(GetterMainChatUIComponents.getInstance().getBeanRectMessageMainChatUI(messageObject));

        rowPanel.add(tmpPanel, constraints);

        panel.add(rowPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
    }

    private void updatePanelMessages() {
        revalidate();
        repaint();
        scrollDownPanel();
    }

    private void scrollDownPanel() {
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        if (verticalScrollBar != null) {
            SwingUtilities.invokeLater(() -> verticalScrollBar.setValue(verticalScrollBar.getMaximum()));
        }
    }

    public void addMessage(MessageStructObject messageObject) {
        String constraints = GetterControls.getInstance().getBeanMessagesDialogCtrl().isCurrentUserSender(messageObject) ?
                BorderLayout.EAST : BorderLayout.WEST;
        createPanelMessage(messageObject, constraints);
        updatePanelMessages();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void runningThreadUpdateMessagesPanel() {
        Runnable listenUpdate = () -> {
            while (true) {
                processUpdatingMessages();
            }
        };

        Thread thread = new Thread(listenUpdate);
        thread.start();
    }

    private void processUpdatingMessages() {
        if (GetterControls.getInstance().getBeanMessagesDefinesCtrl().getTextMessagesLoadReplyFlag() ==
                MessagesDefinesCtrl.TypeFlags.TRUE) {
            changeAllMessages();
        }
        if (GetterControls.getInstance().getBeanMessagesDefinesCtrl().getTextMessageRedirectServerToUserFlag() ==
                MessagesDefinesCtrl.TypeFlags.TRUE) {
            addRedirectMessage();
        }
        if (GetterControls.getInstance().getBeanMessagesDefinesCtrl().getTextMessagesChangingStatusFromServerFlag() ==
                MessagesDefinesCtrl.TypeFlags.TRUE) {
            changeStatusMessage();
        }

        try {
            Thread.sleep(intervalMilliSecondsSleepUpdating);
        } catch (InterruptedException exception) {
            Log.write(Log.TypeLog.Error, "Здесь не удалось выполнить sleep()");
        }
    }

    private void changeAllMessages() {
        panel.removeAll();

        List<MessageStructObject> allMessagesObjSorted = GetterControls.getInstance().getBeanMessagesDialogCtrl().getAllSortedMessages();
        for (MessageStructObject messageStructObject : allMessagesObjSorted) {
            addMessage(messageStructObject);
        }

        GetterControls.getInstance().getBeanMessagesDefinesCtrl().setTextMessagesLoadReplyFlag(MessagesDefinesCtrl.TypeFlags.DEFAULT);
    }

    private void addRedirectMessage() {
        List<MessageStructObject> allMessagesObjSorted = GetterControls.getInstance().getBeanMessagesDialogCtrl().getAllSortedMessages();
        UUID currentPanelUuid = GetterControls.getInstance().getBeanMessagesDialogCtrl().getCurrentActiveChatUuid();

        for (MessageStructObject messageStructObject : allMessagesObjSorted) {
            UUID uuidChat = GetterControls.getInstance().getBeanMessagesDialogCtrl()
                    .findUuidChatByUuidUser(messageStructObject.getUuidUserSender());
            if (findRectMessageByUuid(panel, messageStructObject.getUuid()) == null &&
                    uuidChat != null &&
                    uuidChat.equals(currentPanelUuid)) {
                addMessage(messageStructObject);
            }
        }

        GetterControls.getInstance().getBeanMessagesDefinesCtrl().setTextMessageRedirectServerToUserFlag(MessagesDefinesCtrl.TypeFlags.DEFAULT);
    }

    private RectMessageMainChatUI findRectMessageByUuid(JPanel panelMsg, UUID uuid) {
        Component[] components = panelMsg.getComponents();

        for (Component component : components) {
            if (component instanceof RectMessageMainChatUI rectMessage) {
                if (rectMessage.getUuid().equals(uuid)) {
                    return rectMessage;
                }
            } else if (component instanceof JPanel tmpPanel) {
                RectMessageMainChatUI rectMsg = findRectMessageByUuid(tmpPanel, uuid);
                if (rectMsg != null) {
                    return rectMsg;
                }
            }
        }

        return null;
    }

    private void changeStatusMessage() {
        List<MessageStructObject> allMessagesObjSorted = GetterControls.getInstance().getBeanMessagesDialogCtrl().getAllSortedMessages();

        for (MessageStructObject messageStructObject : allMessagesObjSorted) {
            RectMessageMainChatUI rectMessage = findRectMessageByUuid(panel, messageStructObject.getUuid());
            if (rectMessage != null) {
                rectMessage.changeStatusMessage(messageStructObject.getStatusMessage());
            }
        }

        GetterControls.getInstance().getBeanMessagesDefinesCtrl()
                .setTextMessagesChangingStatusFromServerFlag(MessagesDefinesCtrl.TypeFlags.DEFAULT);
    }
}
