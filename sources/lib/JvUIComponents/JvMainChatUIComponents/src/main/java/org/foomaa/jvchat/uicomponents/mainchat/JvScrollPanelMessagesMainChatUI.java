package org.foomaa.jvchat.uicomponents.mainchat;

import org.foomaa.jvchat.ctrl.JvGetterControls;
import org.foomaa.jvchat.ctrl.JvMessagesDefinesCtrl;
import org.foomaa.jvchat.structobjects.JvMessageStructObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;


public class JvScrollPanelMessagesMainChatUI extends JPanel {
    private static JvScrollPanelMessagesMainChatUI instance;
    private JScrollPane scrollPane;
    private JPanel panel;

    JvScrollPanelMessagesMainChatUI() {
        makePanel();
        runningThreadUpdateMessagesPanel();
    }

    public static JvScrollPanelMessagesMainChatUI getInstance() {
        if (instance == null) {
            instance = new JvScrollPanelMessagesMainChatUI();
        }
        return instance;
    }

    private void makePanel() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(null);
        scrollPane.setViewportView(panel);

        addListenerScrollPane();

        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());

        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.PAGE_END;
        add(scrollPane, gbc);
    }

    private void addListenerScrollPane() {
        scrollPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updatePanelMessages();
            }
        });
    }

    private void createPanelMessage(JvMessageStructObject messageObject, String constraints) {
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BorderLayout());
        rowPanel.add(JvGetterMainChatUIComponents.getInstance().getBeanRectMessageMainChatUI(messageObject), constraints);
        panel.add(rowPanel);
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
        if (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getMessagesLoadReplyFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.TRUE) {
            changeAllMessages();
        }
        if (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getMessageRedirectServerToUserFlag() ==
                JvMessagesDefinesCtrl.TypeFlags.TRUE) {
            addRedirectMessage();
        }
    }

    private void changeAllMessages() {
        panel.removeAll();

        List<JvMessageStructObject> allMessagesObjSorted = JvGetterControls.getInstance().getBeanMessagesDialogCtrl().getAllSortedMessages();
        for (JvMessageStructObject messageStructObject : allMessagesObjSorted) {
            addMessage(messageStructObject);
        }

        JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().setMessagesLoadReplyFlag(JvMessagesDefinesCtrl.TypeFlags.DEFAULT);
    }

    private void addRedirectMessage() {
        JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().setMessageRedirectServerToUserFlag(JvMessagesDefinesCtrl.TypeFlags.DEFAULT);
    }
}
