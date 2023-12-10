package org.foomaa.jvchat.uicomponents.auth;

import javax.swing.*;
import java.awt.*;

public class JvAuthLabel extends JLabel {
    public JvAuthLabel(String text) {
        setText(text);
        setFont(new Font("Times", Font.PLAIN, 20));
    }

    public void resetSize(int size){
        setFont(new Font("Times", Font.PLAIN, size));
    }
}
