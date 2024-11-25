package org.foomaa.jvchat.uicomponents.mainchat;

import org.foomaa.jvchat.ctrl.JvGetterControls;
import org.foomaa.jvchat.structobjects.JvMessageStructObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


public class JvScrollPanelMessagesMainChatUI extends JPanel {
    private static JvScrollPanelMessagesMainChatUI instance;
    private JScrollPane scrollPane;
    private JPanel panel;

    JvScrollPanelMessagesMainChatUI() {
        makePanel();
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
                makeDefaultMsg();
                updatePanelMessages();
            }
        });
    }

    // TODO(VAD): delete
    private void makeDefaultMsg() {
        for (int i = 0; i < 50; i ++) {
            if (i % 2 == 0) {
                createPanelMessage(null, BorderLayout.WEST);
            } else {
                createPanelMessage(null, BorderLayout.EAST);
            }
        }
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
            SwingUtilities.invokeLater(() -> {
                verticalScrollBar.setValue(verticalScrollBar.getMaximum());
            });
        }
    }

    public void addMessage(JvMessageStructObject messageObject) {
        String constraints = JvGetterControls.getInstance().getBeanMessagesDialogCtrl().isCurrentUserSender(messageObject) ?
                BorderLayout.EAST : BorderLayout.WEST;
        createPanelMessage(null, constraints);
        updatePanelMessages();
    }
}
