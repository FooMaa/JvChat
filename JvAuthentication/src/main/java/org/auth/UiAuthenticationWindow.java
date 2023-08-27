package org.auth;

import javax.swing.*;
import java.awt.*;

public class UiAuthenticationWindow extends JFrame {
    public UiAuthenticationWindow () {
        getContentPane().setBackground(Color.WHITE);
        setTitle("ALL");
        getContentPane().setLayout(null);
        JButton button1 = new JButton();
        button1.setSize(100,100);
        button1.setText("lolo");
        getContentPane().add(button1);
        button1.setVisible(true);

    }

}
