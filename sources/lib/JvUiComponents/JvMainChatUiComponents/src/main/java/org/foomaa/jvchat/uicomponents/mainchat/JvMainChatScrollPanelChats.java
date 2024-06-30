package org.foomaa.jvchat.uicomponents.mainchat;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

public class JvMainChatScrollPanelChats extends JPanel {
    private static JvMainChatScrollPanelChats instance;
    private ArrayList<Integer> idChats;

    private JvMainChatScrollPanelChats() {
        makePanel();
    }

    public static JvMainChatScrollPanelChats getInstance() {
        if (instance == null) {
            instance = new JvMainChatScrollPanelChats();
        }
        return instance;
    }

    private void makePanel() {
        Box box = Box.createVerticalBox();

        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat());
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat());
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat());
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat());
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat());
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat());
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat());
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat());
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat());
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat());
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat());
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat());
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat());
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat());
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat());
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat());
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat());
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat());

        JScrollPane scrollPane = new JScrollPane(box);
        scrollPane.setBorder(null);

        addListenerScrollPane(scrollPane);

        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());
        System.out.println(getSize());
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.PAGE_START;
        //gbc.fill = scrollPane.getVerticalScrollBar().isVisible() ? GridBagConstraints.BOTH : GridBagConstraints.HORIZONTAL;
        add(scrollPane, gbc);
    }

    private void changeScrollPane(JScrollPane scrollPane) {
        GridBagConstraints gbc = ((GridBagLayout) getLayout()).getConstraints(scrollPane);

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
}
