package org.auth;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import org.syssettings.JvDisplaySettings;
import org.uicomponents.JvAuthButton;
import org.uicomponents.JvAuthTextField;
import org.uicomponents.JvAuthPasswordField;
import org.uicomponents.JvAuthLabel;

public class JvAuthFrame extends JFrame {
    private JPanel panel;
    private JvAuthLabel tInfo;
    private JvAuthTextField tLogin;
    private JvAuthPasswordField tPassword;
    private JvAuthButton bEnter;
    public JvAuthFrame() {
        super( "AuthenticationWindow" );

        panel = new JPanel();
        tInfo = new JvAuthLabel( "Введите данные для входа:" );
        tLogin = new JvAuthTextField( "логин" );
        tPassword = new JvAuthPasswordField( "пароль" ) ;
        bEnter = new JvAuthButton( "Войти" );

        makeFrameSetting();
        addListenerToElements();
        addGeneralSettingsToWidget();
    }

    private void makeFrameSetting() {
        panel.setLayout( new GridBagLayout() );
        GridBagConstraints gbc = new GridBagConstraints();

        int insX = JvDisplaySettings.getResizeFromDisplay( 0.025,
                JvDisplaySettings.TypeOfDisplayBorder.WIDTH );

        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.PAGE_START;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets( 10, 0, 5, 0 );
        gbc.gridy = 0;
        panel.add( tInfo, gbc );

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
    }

    private void addListenerToElements() {
        bEnter.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                System.out.println( "kokokokokok" );
            }
        } );
    }

    private void addGeneralSettingsToWidget() {
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        setTitle( "Вход" );
        setSize( JvDisplaySettings.getResizeFromDisplay( 0.3,
                        JvDisplaySettings.TypeOfDisplayBorder.WIDTH ) ,
                JvDisplaySettings.getResizeFromDisplay( 0.3,
                        JvDisplaySettings.TypeOfDisplayBorder.HEIGHT ) );
        setResizable( false );
        setLocationRelativeTo( null );
        setVisible( true );
    }
}
