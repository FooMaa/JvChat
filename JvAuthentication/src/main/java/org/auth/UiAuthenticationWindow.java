package org.auth;

import org.syssettings.JvSystemSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UiAuthenticationWindow extends JFrame {
    public UiAuthenticationWindow () {
        super( "UiAuthenticationWindow" );

        setDefaultCloseOperation( EXIT_ON_CLOSE );
        setTitle("Вход");

        JPanel panel = new JPanel();
        panel.setLayout( new GridBagLayout() );
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JLabel tInfo = new JLabel("Введите данные для входа:");
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets( 10, 0, 10, 0 );
        gbc.gridy = 0;
        panel.add( tInfo, gbc );

        JTextField tLogin = new JTextField( "Логин" );
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets( 0, 0, 10, 0 );
        gbc.ipadx = JvSystemSettings.getResizeFromDisplay( 0.1,
                JvSystemSettings.TypeOfDisplayBorder.WIDTH );
        gbc.ipady = JvSystemSettings.getResizeFromDisplay( 0.01,
                JvSystemSettings.TypeOfDisplayBorder.HEIGHT );
        tLogin.setSize( gbc.ipadx, gbc.ipady );
        gbc.gridy = 1;
        panel.add( tLogin, gbc );

        JTextField tPassword = new JTextField( "Пароль" );
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets( 0, 0, 0, 0 );
        gbc.ipadx = JvSystemSettings.getResizeFromDisplay( 0.1,
                JvSystemSettings.TypeOfDisplayBorder.WIDTH );
        gbc.ipady = JvSystemSettings.getResizeFromDisplay( 0.01,
                JvSystemSettings.TypeOfDisplayBorder.HEIGHT );
        gbc.gridy = 2;
        tPassword.setSize( gbc.ipadx, gbc.ipady );
        panel.add( tPassword, gbc );

        JButton bEnter = new JButton( "Войти" );
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets( 0, 0, 20, 0 );
        gbc.ipadx = JvSystemSettings.getResizeFromDisplay( 0.05,
                JvSystemSettings.TypeOfDisplayBorder.WIDTH );;
        gbc.ipady = JvSystemSettings.getResizeFromDisplay( 0.01,
                JvSystemSettings.TypeOfDisplayBorder.HEIGHT );;
        gbc.gridy = 4;
        panel.add( bEnter, gbc );

        getContentPane().add( panel );
        bEnter.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {

                System.out.println( "kokokokokok" );
            }
        } );

    }

}
