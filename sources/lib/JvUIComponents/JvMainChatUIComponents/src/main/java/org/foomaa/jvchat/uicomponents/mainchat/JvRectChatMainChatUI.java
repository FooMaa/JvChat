package org.foomaa.jvchat.uicomponents.mainchat;

import org.foomaa.jvchat.ctrl.JvChatsCtrl;
import org.foomaa.jvchat.settings.JvGetterSettings;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;


public class JvRectChatMainChatUI extends JPanel {
    private final String nickName;
    private final String shortLastMessage;
    private final String lastMessageSender;
    private final String timeLastMessage;
    private final JvChatsCtrl.TypeStatusMessage statusMessage;
    private final JvChatsCtrl.TypeStatusOnline statusOnline;
    private final String timeLastOnline;

    JvRectChatMainChatUI(String newNickName,
                         String newShortLastMessage,
                         String newLastMessageSender,
                         String newTimeLastMessage,
                         String newTimeLastOnline,
                         JvChatsCtrl.TypeStatusMessage newStatusMessage,
                         JvChatsCtrl.TypeStatusOnline newStatusOnline) {
        nickName = newNickName;
        shortLastMessage = newShortLastMessage;
        lastMessageSender = newLastMessageSender;
        timeLastMessage = newTimeLastMessage;
        timeLastOnline = newTimeLastOnline;
        statusMessage = newStatusMessage;
        statusOnline = newStatusOnline;

        makeChatBox();
    }

    private void makeChatBox() {
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());

        JLabel loginLabel = new JLabel(nickName + " " + createStringStatusOnline());
        loginLabel.setFont(new Font("Times", Font.BOLD,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.017)));

        int gridyNum = 0;

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridy = gridyNum;
        gbc.insets = new Insets(1, 5, 1, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        add(loginLabel, gbc);

        JLabel timeLabel = new JLabel(timeLastMessage);
        timeLabel.setFont(new Font("Times", Font.PLAIN,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.014)));

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.gridy = gridyNum;
        gbc.insets = new Insets(1, 5, 1, 5);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        add(timeLabel, gbc);
        gridyNum++;

        JLabel lastMessageLabel = new JLabel(createLastMessageString());
        lastMessageLabel.setFont(new Font("Times", Font.PLAIN,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.014)));

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridy = gridyNum;
        gbc.insets = new Insets(1, 5, 1, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.WEST;
        add(lastMessageLabel, gbc);

        setStatusFront();
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    private void setStatusFront() {
        switch (statusMessage) {
            case Sent -> setBackground(new Color(254,252,190));
            case Delivered -> setBackground(new Color(181,252,250));
            case Read -> setBackground(new Color(191,254,188));
            case Error -> setBackground(new Color(254,196,200));
        }
    }

    private String createLastMessageString() {
        String currentLogin = JvGetterSettings.getInstance().getBeanUserInfoSettings().getLogin();

        if (Objects.equals(lastMessageSender, currentLogin)) {
            return "Вы:" + shortLastMessage;
        }

        return shortLastMessage;
    }

    private String createStringStatusOnline() {
        switch (statusOnline) {
            case Error -> {
                return "Ошибка состояния";
            }
            case Offline -> {
                return String.format(
                        "Не в сети. Последний в %s",
                        timeLastOnline);
            }
            case Online -> {
                return "В сети";
            }
        }
        return "";
    }
}
