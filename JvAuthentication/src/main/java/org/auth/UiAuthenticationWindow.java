package org.auth;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UiAuthenticationWindow extends JFrame {
    public UiAuthenticationWindow () {
        setTitle("Вход");
        JPanel panel = new JPanel();
        panel.setLayout( new BoxLayout(panel, BoxLayout.PAGE_AXIS ) );

        JTextField button1 = new JTextField( "Top" );
        button1.setAlignmentX( Component.CENTER_ALIGNMENT );
        panel.add( button1 );

        JTextField button2 = new JTextField( "Center" );
        button2.setAlignmentX( Component.CENTER_ALIGNMENT );
        panel.add( button2 );

        JButton bEnter = new JButton( "Войти" );
        bEnter.setAlignmentX( Component.CENTER_ALIGNMENT );
        panel.add( bEnter );
        getContentPane().add( panel );


//        getContentPane().setLayout( new BoxLayout( getContentPane(), BoxLayout.PAGE_AXIS ) );
//        JButton bEnter = new JButton("Войти");
//        bEnter.setSize( ( int ) Math.round( 0.25 * getContentPane().getWidth() ),
//                ( int ) Math.round( 0.25 * getContentPane().getHeight() ) );
//        getContentPane().add(bEnter);
        bEnter.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {

                System.out.println( "kokokokokok" );
            }
        } );

    }

}
