package org.foomaa.jvchat.uicomponents.mainchat;

import org.foomaa.jvchat.settings.JvGetterSettings;

import javax.swing.*;
import java.awt.*;


public class JvMainChatRectChat extends JPanel {
    private final String nickName;
    private final String shortLastMessage;
    private final String lastMessageSender;
    private final String status;
    private final String time;

    JvMainChatRectChat(String newNickName,
                       String newShortLastMessage,
                       String newLastMessageSender,
                       String newStatus,
                       String newTime) {
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

        setBackground(new Color(185, 248, 231));

        add(box, BorderLayout.CENTER);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }
}
