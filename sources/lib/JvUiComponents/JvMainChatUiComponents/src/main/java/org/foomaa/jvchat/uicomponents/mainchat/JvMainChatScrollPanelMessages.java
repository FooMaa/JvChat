package org.foomaa.jvchat.uicomponents.mainchat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class JvMainChatScrollPanelMessages extends JPanel {
    private static JvMainChatScrollPanelMessages instance;

    JvMainChatScrollPanelMessages() {
        makePanel();
    }

    public static JvMainChatScrollPanelMessages getInstance() {
        if (instance == null) {
            instance = new JvMainChatScrollPanelMessages();
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
                rowPanel.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectMessage(), BorderLayout.WEST);
            } else {
                rowPanel.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectMessage(), BorderLayout.EAST);
            }
            scrollPanel.add(rowPanel);
        }
        
        scrollPane.setViewportView(scrollPanel);

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
}
