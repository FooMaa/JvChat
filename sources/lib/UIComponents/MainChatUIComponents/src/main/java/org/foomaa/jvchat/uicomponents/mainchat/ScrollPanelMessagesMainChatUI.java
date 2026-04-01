package org.foomaa.jvchat.uicomponents.mainchat;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.swing.*;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.ctrl.MessagesDefinesCtrl;
import org.foomaa.jvchat.ctrl.MessagesDialogCtrl;
import org.foomaa.jvchat.structobjects.MessageStructObject;

@Slf4j
public class ScrollPanelMessagesMainChatUI extends JPanel {
    private final int intervalMilliSecondsSleepUpdating;
    private JScrollPane scrollPane;
    private JPanel panel;

    private final MessagesDefinesCtrl messagesDefinesCtrl;
    private final MessagesDialogCtrl messagesDialogCtrl;
    private final RectMessageMainChatUIFactory rectMessageMainChatUIFactory;

    @Builder
    ScrollPanelMessagesMainChatUI(
            MessagesDefinesCtrl messagesDefinesCtrl,
            MessagesDialogCtrl messagesDialogCtrl,
            RectMessageMainChatUIFactory rectMessageMainChatUIFactory) {
        this.messagesDefinesCtrl = Objects.requireNonNull(messagesDefinesCtrl, "messagesDefinesCtrl is mandatory");
        this.messagesDialogCtrl = Objects.requireNonNull(messagesDialogCtrl, "messagesDialogCtrl is mandatory");
        this.rectMessageMainChatUIFactory =
                Objects.requireNonNull(rectMessageMainChatUIFactory, "rectMessageMainChatUIFactory is mandatory");

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
        gbc.fill =
                scrollPane.getVerticalScrollBar().isVisible() ? GridBagConstraints.BOTH : GridBagConstraints.HORIZONTAL;
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
        tmpPanel.add(rectMessageMainChatUIFactory.create(messageObject));

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
        String constraints =
                messagesDialogCtrl.isCurrentUserSender(messageObject) ? BorderLayout.EAST : BorderLayout.WEST;
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
        if (messagesDefinesCtrl.getTextMessagesLoadReplyFlag() == MessagesDefinesCtrl.TypeFlags.TRUE) {
            changeAllMessages();
        }
        if (messagesDefinesCtrl.getTextMessageRedirectServerToUserFlag() == MessagesDefinesCtrl.TypeFlags.TRUE) {
            addRedirectMessage();
        }
        if (messagesDefinesCtrl.getTextMessagesChangingStatusFromServerFlag() == MessagesDefinesCtrl.TypeFlags.TRUE) {
            changeStatusMessage();
        }

        try {
            Thread.sleep(intervalMilliSecondsSleepUpdating);
        } catch (InterruptedException exception) {
            log.error("Здесь не удалось выполнить sleep()");
        }
    }

    private void changeAllMessages() {
        panel.removeAll();

        List<MessageStructObject> allMessagesObjSorted = messagesDialogCtrl.getAllSortedMessages();
        for (MessageStructObject messageStructObject : allMessagesObjSorted) {
            addMessage(messageStructObject);
        }

        messagesDefinesCtrl.setTextMessagesLoadReplyFlag(MessagesDefinesCtrl.TypeFlags.DEFAULT);
    }

    private void addRedirectMessage() {
        List<MessageStructObject> allMessagesObjSorted = messagesDialogCtrl.getAllSortedMessages();
        UUID currentPanelUuid = messagesDialogCtrl.getCurrentActiveChatUuid();

        for (MessageStructObject messageStructObject : allMessagesObjSorted) {
            UUID uuidChat = messagesDialogCtrl.findUuidChatByUuidUser(messageStructObject.getUuidUserSender());
            if (findRectMessageByUuid(panel, messageStructObject.getUuid()) == null
                    && uuidChat != null
                    && uuidChat.equals(currentPanelUuid)) {
                addMessage(messageStructObject);
            }
        }

        messagesDefinesCtrl.setTextMessageRedirectServerToUserFlag(MessagesDefinesCtrl.TypeFlags.DEFAULT);
    }

    private RectMessageMainChatUI findRectMessageByUuid(JPanel panelMsg, UUID uuid) {
        java.awt.Component[] components = panelMsg.getComponents();

        for (java.awt.Component component : components) {
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
        List<MessageStructObject> allMessagesObjSorted = messagesDialogCtrl.getAllSortedMessages();

        for (MessageStructObject messageStructObject : allMessagesObjSorted) {
            RectMessageMainChatUI rectMessage = findRectMessageByUuid(panel, messageStructObject.getUuid());
            if (rectMessage != null) {
                rectMessage.changeStatusMessage(messageStructObject.getStatusMessage());
            }
        }

        messagesDefinesCtrl.setTextMessagesChangingStatusFromServerFlag(MessagesDefinesCtrl.TypeFlags.DEFAULT);
    }
}
