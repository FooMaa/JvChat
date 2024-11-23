package org.foomaa.jvchat.uicomponents.mainchat;

import org.foomaa.jvchat.ctrl.JvGetterControls;
import org.foomaa.jvchat.structobjects.JvMessageStructObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


public class JvScrollPanelMessagesMainChatUI extends JPanel {
    private static JvScrollPanelMessagesMainChatUI instance;

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
        JScrollPane scrollPane = new JScrollPane(new JPanel());
        scrollPane.setBorder(null);
        addListenerScrollPane(scrollPane);

        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());

        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.PAGE_END;
        add(scrollPane, gbc);
    }

    private void changeScrollPane(JScrollPane scrollPane) {
        JPanel scrollPanel = new JPanel();

        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));

        for (int i = 0; i < 50; i ++) {
            JPanel rowPanel = new JPanel();
            rowPanel.setLayout(new BorderLayout());

            if (i % 2 == 0) {
                rowPanel.add(JvGetterMainChatUIComponents.getInstance().getBeanRectMessageMainChatUI(null), BorderLayout.WEST);
            } else {
                rowPanel.add(JvGetterMainChatUIComponents.getInstance().getBeanRectMessageMainChatUI(null), BorderLayout.EAST);
            }
            scrollPanel.add(rowPanel);
        }

        scrollPane.setViewportView(scrollPanel);

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        scrollDown(verticalScrollBar);

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

    private void scrollDown(JScrollBar verticalScrollBar) {
        if (verticalScrollBar != null) {
            verticalScrollBar.setValue(verticalScrollBar.getMaximum());
        }
    }

    public void addMessage(JvMessageStructObject messageObject) {
        String constraints = JvGetterControls.getInstance().getBeanMessagesDialogCtrl().isCurrentUserSender(messageObject) ?
                BorderLayout.EAST : BorderLayout.WEST;

        //rowPanel.add(JvGetterMainChatUIComponents.getInstance().getBeanRectMessageMainChatUI(messageObject), constraints);
    }
}
