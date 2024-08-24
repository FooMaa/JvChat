package org.foomaa.jvchat.uicomponents.mainchat;

import org.foomaa.jvchat.ctrl.JvChatsCtrl;
import org.foomaa.jvchat.ctrl.JvGetterControls;
import org.foomaa.jvchat.ctrl.JvMessagesDefinesCtrl;
import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.messages.JvDefinesMessages;
import org.foomaa.jvchat.settings.JvGetterSettings;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class JvScrollPanelChatsMainChatUI extends JPanel {
    private static JvScrollPanelChatsMainChatUI instance;
    private ArrayList<Integer> idChats;

    private JvScrollPanelChatsMainChatUI() {
        makePanel();
    }

    public static JvScrollPanelChatsMainChatUI getInstance() {
        if (instance == null) {
            instance = new JvScrollPanelChatsMainChatUI();
        }
        return instance;
    }

    private void makePanel() {
        setBorder(BorderFactory.createMatteBorder(0,0,0,7, Color.GRAY));

        Box box = Box.createVerticalBox();
        loadChatsInBox(box);

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
        setRequestChatsToServer();

        JvChatsCtrl chatsCtrl = JvGetterControls.getInstance().getBeanChatsCtrl();
        List<String> loginsList = getLoginsList();

        for (String login : loginsList) {
          box.add(JvGetterMainChatUIComponents.getInstance()
                  .getBeanRectChatMainChatUI(
                          login,
                          chatsCtrl.getLastMessage(login),
                          chatsCtrl.getLastMessageSender(login),
                          chatsCtrl.getTimeHMLastMessage(login),
                          chatsCtrl.getStatusLastMessage(login)));
        }
    }

    private void setRequestChatsToServer() {
        String login = JvGetterSettings.getInstance().getBeanUserInfoSettings().getLogin();

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
}
