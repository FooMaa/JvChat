package org.foomaa.jvchat.uicomponents.mainchat;

import org.foomaa.jvchat.settings.JvGetterSettings;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;

public class JvMainChatRectChat extends JPanel {
    private int id;
    private JLabel receiver;

    JvMainChatRectChat() {
        makeChatBox();
    }

    private void makeChatBox() {
        Box box = Box.createVerticalBox();

        JLabel contact = new JLabel("Name");
        contact.setFont(new Font("Times", Font.BOLD,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.017)));
        JLabel shortText = new JLabel("Text");
        shortText.setFont(new Font("Times", Font.PLAIN,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.017)));

        box.add(contact);
        box.add(shortText);
        setForeground(Color.BLUE);

        add(box);
        setBorder(new BevelBorder(BevelBorder.RAISED));
    }
}
