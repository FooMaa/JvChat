package org.foomaa.jvchat.uicomponents.mainchat;

import org.foomaa.jvchat.ctrl.JvChatsCtrl;
import org.foomaa.jvchat.settings.JvGetterSettings;

import javax.swing.*;
import java.awt.*;


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

        JLabel contact = new JLabel(nickName);
        contact.setFont(new Font("Times", Font.BOLD,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.017)));
        JLabel shortMessage = new JLabel(shortLastMessage);
        shortMessage.setFont(new Font("Times", Font.PLAIN,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.017)));

        box.add(contact);
        box.add(shortMessage);

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
