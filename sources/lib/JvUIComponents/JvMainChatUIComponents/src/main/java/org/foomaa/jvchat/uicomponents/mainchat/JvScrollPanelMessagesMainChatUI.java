package org.foomaa.jvchat.uicomponents.mainchat;

import org.foomaa.jvchat.ctrl.JvGetterControls;
import org.foomaa.jvchat.ctrl.JvMessagesDefinesCtrl;
import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.structobjects.JvMessageStructObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class JvScrollPanelMessagesMainChatUI extends JPanel {
    private final int intervalMilliSecondsSleepUpdating;
    private JScrollPane scrollPane;
    private JPanel panel;

    JvScrollPanelMessagesMainChatUI() {
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

    private void createPanelMessage(JvMessageStructObject messageObject, String constraints) {
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BorderLayout());

        // NOTE(VAD): надо для того, чтоб компоненты не растягивались
        JPanel tmpPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        tmpPanel.add(JvGetterMainChatUIComponents.getInstance().getBeanRectMessageMainChatUI(messageObject));

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

    public void addMessage(JvMessageStructObject messageObject) {
        String constraints = JvGetterControls.getInstance().getBeanMessagesDialogCtrl().isCurrentUserSender(messageObject) ?
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
        if (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getTextMessagesLoadReplyFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.TRUE) {
            changeAllMessages();
        }
        if (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getTextMessageRedirectServerToUserFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.TRUE) {
            addRedirectMessage();
        }
        if (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getTextMessagesChangingStatusFromServerFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.TRUE) {
            changeStatusMessage();
        }

        try {
            Thread.sleep(intervalMilliSecondsSleepUpdating);
        } catch (InterruptedException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Здесь не удалось выполнить sleep()");
        }
    }

    private void changeAllMessages() {
        panel.removeAll();

        List<JvMessageStructObject> allMessagesObjSorted = JvGetterControls.getInstance().getBeanMessagesDialogCtrl().getAllSortedMessages();
        for (JvMessageStructObject messageStructObject : allMessagesObjSorted) {
            addMessage(messageStructObject);
        }

        JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().setTextMessagesLoadReplyFlag(JvMessagesDefinesCtrl.TypeFlags.DEFAULT);
    }

    private void addRedirectMessage() {
        List<JvMessageStructObject> allMessagesObjSorted = JvGetterControls.getInstance().getBeanMessagesDialogCtrl().getAllSortedMessages();
        String currentPanelLogin = JvGetterControls.getInstance().getBeanMessagesDialogCtrl().getCurrentActiveLoginUI();

        for (JvMessageStructObject messageStructObject : allMessagesObjSorted) {
            if (findRectMessageByUuid(panel,
                    messageStructObject.getUuid()) == null &&
                    Objects.equals(currentPanelLogin, messageStructObject.getLoginSender())) {
                addMessage(messageStructObject);
            }
        }

        JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().setTextMessageRedirectServerToUserFlag(JvMessagesDefinesCtrl.TypeFlags.DEFAULT);
    }

    private JvRectMessageMainChatUI findRectMessageByUuid(JPanel panelMsg, UUID uuid) {
        Component[] components = panelMsg.getComponents();

        for (Component component : components) {
            if (component instanceof JvRectMessageMainChatUI rectMessage) {
                if (rectMessage.getUuid().equals(uuid)) {
                    return rectMessage;
                }
            } else if (component instanceof JPanel tmpPanel) {
                JvRectMessageMainChatUI rectMsg = findRectMessageByUuid(tmpPanel, uuid);
                if (rectMsg != null) {
                    return rectMsg;
                }
            }
        }

        return null;
    }

    private void changeStatusMessage() {
        List<JvMessageStructObject> allMessagesObjSorted = JvGetterControls.getInstance().getBeanMessagesDialogCtrl().getAllSortedMessages();
        String currentPanelLogin = JvGetterControls.getInstance().getBeanMessagesDialogCtrl().getCurrentActiveLoginUI();

        for (JvMessageStructObject messageStructObject : allMessagesObjSorted) {
            JvRectMessageMainChatUI rectMessage = findRectMessageByUuid(panel, messageStructObject.getUuid());
            if ( rectMessage != null && Objects.equals(currentPanelLogin, messageStructObject.getLoginSender())) {
                rectMessage.changeStatusMessage(messageStructObject.getStatusMessage());
            }
        }

        JvGetterControls.getInstance().getBeanMessagesDefinesCtrl()
                .setTextMessagesChangingStatusFromServerFlag(JvMessagesDefinesCtrl.TypeFlags.DEFAULT);
    }
}
