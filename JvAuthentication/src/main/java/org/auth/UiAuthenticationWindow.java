package org.auth;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UiAuthenticationWindow extends JFrame {
    public UiAuthenticationWindow () {
        setTitle("Вход");
        JButton button1 = new JButton();
        button1.setSize(100,100);
        button1.setText("lolo");
        getContentPane().add(button1);
        button1.setVisible(true);
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("kokokokokok");
            }
        });

    }

}
