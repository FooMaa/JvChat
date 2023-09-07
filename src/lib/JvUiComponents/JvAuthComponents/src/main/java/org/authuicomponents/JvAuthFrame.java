package org.authuicomponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import org.syssettings.JvDisplaySettings;

public class JvAuthFrame extends JFrame {
    private final JPanel panel;
    private final JvAuthLabel tInfo;
    private final JvAuthTextField tLogin;
    private final JvAuthPasswordField tPassword;
    private final JvAuthButton bEnter;
    private final JvAuthActiveLabel activeRegisterLabel;
    private final JvAuthActiveLabel activeMissLabel;

    public JvAuthFrame() {
        super( "AuthenticationWindow" );

        panel = new JPanel();
        tInfo = new JvAuthLabel( "Введите данные для входа:" );
        tLogin = new JvAuthTextField( "Логин" );
        tPassword = new JvAuthPasswordField( "Пароль" ) ;
        bEnter = new JvAuthButton( "ВОЙТИ" );
        activeMissLabel = new JvAuthActiveLabel("Не помню пароль");
        activeRegisterLabel = new JvAuthActiveLabel("Регистрация");

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
        gbc.gridy = 1;
        panel.add( tLogin, gbc );

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets( 0, insX, 0, insX );
        gbc.gridy = 2;
        panel.add( tPassword, gbc );

        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets( 0, 0, 5, 0 );
        gbc.gridy = 3;
        panel.add( activeMissLabel, gbc );

        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets( 0, 0, 20, 0 );
        gbc.gridy = 4;
        panel.add( activeRegisterLabel, gbc );

        gbc.fill = GridBagConstraints.PAGE_END;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets( 0, 0, 20, 0 );
        gbc.ipadx = JvDisplaySettings.getResizeFromDisplay( 0.03,
                JvDisplaySettings.TypeOfDisplayBorder.WIDTH );
        gbc.ipady = JvDisplaySettings.getResizeFromDisplay( 0.01,
                JvDisplaySettings.TypeOfDisplayBorder.HEIGHT );
        gbc.gridy = 5;
        panel.add( bEnter, gbc );

        getContentPane().add( panel );
    }

    private void addListenerToElements() {
        bEnter.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                System.out.println( "kokokokokok" );
            }
        } );

        activeMissLabel.addMouseListener( new MouseAdapter() {
            @Override
            public void mouseClicked( MouseEvent e ) {
                System.out.println( "ayayayayay" );
            }
        } );

        activeRegisterLabel.addMouseListener( new MouseAdapter() {
            @Override
            public void mouseClicked( MouseEvent e ) {
                System.out.println( "oiioioioioioi" );
            }
        } );

    }

    private void addGeneralSettingsToWidget() {
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        setTitle( "ВХОД" );
        setSize( JvDisplaySettings.getResizeFromDisplay( 0.3,
                        JvDisplaySettings.TypeOfDisplayBorder.WIDTH ) ,
                JvDisplaySettings.getResizeFromDisplay( 0.3,
                        JvDisplaySettings.TypeOfDisplayBorder.HEIGHT ) );
        setResizable( false );
        setLocationRelativeTo( null );
        toFront();
        setVisible( true );
    }
}
