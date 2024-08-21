package org.foomaa.jvchat.uicomponents.mainchat;

import org.foomaa.jvchat.ctrl.JvGetterControls;
import org.foomaa.jvchat.messages.JvMessagesDefines;
import org.foomaa.jvchat.settings.JvGetterSettings;

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
        setBorder(BorderFactory.createMatteBorder(0,0,0,7, Color.GRAY));

        Box box = Box.createVerticalBox();
        loadChatsInBox(box);

        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat("ник", "text"));
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat("ник", "text"));
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat("ник", "text"));
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat("ник", "text"));
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat("ник", "text"));
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat("ник", "text"));
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat("ник", "text"));
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat("ник", "text"));
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat("ник", "text"));
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat("ник", "text"));
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat("ник", "text"));
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat("ник", "text"));
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat("ник", "text"));
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat("ник", "text"));
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat("ник", "text"));
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat("ник", "text"));
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat("ник", "text"));
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat("ник", "text"));
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat("ник", "text"));
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat("ник", "text"));
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat("ник", "text"));
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat("ник", "text"));
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat("ник", "text"));
        box.add(JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat("ник", "text"));

        JScrollPane scrollPane = new JScrollPane(box);
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

    private void loadChatsInBox(Box box) {
        getChatsInfoFromDb();
        //JvGetterMainChatUiComponents.getInstance().getBeanMainChatRectChat("ник", "text");

    }

    private void getChatsInfoFromDb() {
        String login = JvGetterSettings.getInstance().getBeanUserInfoSettings().getLogin();

        JvGetterControls.getInstance().getBeanSendMessagesCtrl().sendMessage(
                JvMessagesDefines.TypeMessage.ChatsLoadRequest, login);
    }
}
