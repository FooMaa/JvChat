package org.auth;

import org.syssettings.JvDisplaySettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UiAuthenticationWindow extends JFrame {
    public UiAuthenticationWindow () {
        super( "UiAuthenticationWindow" );

        setDefaultCloseOperation( EXIT_ON_CLOSE );
        setTitle("Вход");

        JPanel panel = new JPanel();
        panel.setLayout( new GridBagLayout() );
        GridBagConstraints gbc = new GridBagConstraints();

        int insX = JvDisplaySettings.getResizeFromDisplay( 0.025,
                JvDisplaySettings.TypeOfDisplayBorder.WIDTH );

        gbc.weightx = 0.5;
        gbc.weighty = 0.5;

        JLabel tInfo = new JLabel("Введите данные для входа:");
        tInfo.setFont(new Font("Times", Font.PLAIN, 20));
        gbc.fill = GridBagConstraints.PAGE_START;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets( 10, 0, 5, 0 );
        gbc.gridy = 0;

        panel.add( tInfo, gbc );

        JTextField tLogin = new JTextField( "логин" );
        tLogin.setFocusable( false );
        tLogin.addMouseListener( new MouseAdapter() {

            public void mouseClicked( MouseEvent e ) {
                tLogin.setFocusable( true );
                tLogin.selectAll();
                tLogin.removeMouseListener( this );
            }
        } );
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets( 0, insX, 10, insX );
        gbc.ipadx = JvDisplaySettings.getResizeFromDisplay( 0.1,
                JvDisplaySettings.TypeOfDisplayBorder.WIDTH );
        gbc.ipady = JvDisplaySettings.getResizeFromDisplay( 0.01,
                JvDisplaySettings.TypeOfDisplayBorder.HEIGHT );
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        panel.add( tLogin, gbc );

        JPasswordField tPassword = new JPasswordField( "пароль" );
        tPassword.setFocusable( false );
        tPassword.addMouseListener( new MouseAdapter() {

            public void mouseClicked( MouseEvent e ) {
                tPassword.setFocusable( true );
                tPassword.selectAll();
            }
        } );
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets( 0, insX, 0, insX );
        gbc.ipadx = JvDisplaySettings.getResizeFromDisplay( 0.1,
                JvDisplaySettings.TypeOfDisplayBorder.WIDTH );
        gbc.ipady = JvDisplaySettings.getResizeFromDisplay( 0.01,
                JvDisplaySettings.TypeOfDisplayBorder.HEIGHT );
        gbc.gridy = 2;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        panel.add( tPassword, gbc );
        System.out.println(tPassword.getSize());
        System.out.println(tLogin.getSize());
        JButton bEnter = new JButton( "Войти" );
        gbc.fill = GridBagConstraints.PAGE_END;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets( 0, 0, 20, 0 );
        gbc.ipadx = JvDisplaySettings.getResizeFromDisplay( 0.05,
                JvDisplaySettings.TypeOfDisplayBorder.WIDTH );;
        gbc.ipady = JvDisplaySettings.getResizeFromDisplay( 0.01,
                JvDisplaySettings.TypeOfDisplayBorder.HEIGHT );;
        gbc.gridy = 3;
        panel.add( bEnter, gbc );

        getContentPane().add( panel );
        bEnter.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {

                System.out.println( "kokokokokok" );
            }
        } );

    }

}
