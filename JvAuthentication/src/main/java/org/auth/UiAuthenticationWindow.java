package org.auth;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UiAuthenticationWindow extends JFrame {
    public UiAuthenticationWindow () {
        setTitle("Вход");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JButton button1 = new JButton("Top");
        panel.add(button1);

        JButton button2 = new JButton("Center");
        panel.add(button2);

        JButton button3 = new JButton("Bottom");
        panel.add(button3);
        getContentPane().add(panel);


//        getContentPane().setLayout( new BoxLayout( getContentPane(), BoxLayout.PAGE_AXIS ) );
//        JButton bEnter = new JButton("Войти");
//        bEnter.setSize( ( int ) Math.round( 0.25 * getContentPane().getWidth() ),
//                ( int ) Math.round( 0.25 * getContentPane().getHeight() ) );
//        getContentPane().add(bEnter);
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                System.out.println("kokokokokok");
            }
        });

    }

}
