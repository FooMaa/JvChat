package org.auth;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UiAuthenticationWindow extends JFrame {
    public UiAuthenticationWindow () {
        setTitle("Вход");
        JButton button1 = new JButton();

//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        int heightScreen = screenSize.height;
//        int widthScreen = screenSize.width;
//        button1.setSize( Math.round( 0.25 * widthScreen ) ,
//                Math.round( 0.25 * heightScreen ) );
        button1.setText("");
        getContentPane().add(button1);
        button1.setVisible(true);
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                System.out.println("kokokokokok");
            }
        });

    }

}
