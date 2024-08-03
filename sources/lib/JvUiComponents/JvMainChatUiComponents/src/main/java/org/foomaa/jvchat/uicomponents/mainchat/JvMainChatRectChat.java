package org.foomaa.jvchat.uicomponents.mainchat;

import org.foomaa.jvchat.settings.JvGetterSettings;

import javax.swing.*;
import java.awt.*;

public class JvMainChatRectChat extends JPanel {
    private final String nickName;
    private final String shortLastMessage;

    JvMainChatRectChat(String newNickName, String newShortLastMessage) {
        nickName = newNickName;
        shortLastMessage = newShortLastMessage;
        makeChatBox();
    }

    private void makeChatBox() {
        Box box = Box.createVerticalBox();

        JLabel contact = new JLabel(nickName);
        contact.setFont(new Font("Times", Font.BOLD,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.017)));
        JLabel shortText = new JLabel(shortLastMessage);
        shortText.setFont(new Font("Times", Font.PLAIN,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.017)));

        box.add(contact);
        box.add(shortText);
        setBackground(new Color(185, 248, 231));

        add(box, BorderLayout.CENTER);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }
}
