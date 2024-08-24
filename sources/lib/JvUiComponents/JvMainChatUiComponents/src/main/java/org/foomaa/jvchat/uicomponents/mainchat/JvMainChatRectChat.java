package org.foomaa.jvchat.uicomponents.mainchat;

import org.foomaa.jvchat.ctrl.JvChatsCtrl;
import org.foomaa.jvchat.settings.JvGetterSettings;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;


public class JvMainChatRectChat extends JPanel {
    private final String nickName;
    private final String shortLastMessage;
    private final String lastMessageSender;
    private final String time;
    private final JvChatsCtrl.TypeStatusMessage status;

    JvMainChatRectChat(String newNickName,
                       String newShortLastMessage,
                       String newLastMessageSender,
                       String newTime,
                       JvChatsCtrl.TypeStatusMessage newStatus) {
        nickName = newNickName;
        shortLastMessage = newShortLastMessage;
        lastMessageSender = newLastMessageSender;
        status = newStatus;
        time = newTime;

        makeChatBox();
    }

    private void makeChatBox() {
        Box box = Box.createVerticalBox();

        JLabel upperLabel = new JLabel(nickName);
        upperLabel.setFont(new Font("Times", Font.BOLD,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.017)));

        JLabel lowerLabel = new JLabel();
        String currentLogin = JvGetterSettings.getInstance().getBeanUserInfoSettings().getLogin();

        if (Objects.equals(lastMessageSender, currentLogin)) {
            lowerLabel.setText("Вы: " + shortLastMessage);
        } else {
            lowerLabel.setText(shortLastMessage);
        }

        lowerLabel.setFont(new Font("Times", Font.PLAIN,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.017)));

        box.add(upperLabel);
        box.add(lowerLabel);

        setStatusFront();

        add(box, BorderLayout.CENTER);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    private void setStatusFront() {
        switch (status) {
            case Sent -> setBackground(new Color(150, 150, 30));
            case Delivered -> setBackground(new Color(50, 150, 180));
            case Read -> setBackground(new Color(50, 180, 50));
            case Error -> setBackground(new Color(180, 50, 50));
        }
    }
}
