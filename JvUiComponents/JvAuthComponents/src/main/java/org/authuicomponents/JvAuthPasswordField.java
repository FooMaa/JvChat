package org.authuicomponents;
import org.syssettings.JvDisplaySettings;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JvAuthPasswordField extends JPanel {
    private BufferedImage visibleImage;
    private static boolean flagEye = false;
    private JPasswordField passwordField;
    private JButton button;
    private JPanel passwordFieldWithButtonsPanel;
    private Dimension dim = new Dimension (JvDisplaySettings.getResizeFromDisplay( 0.23,
            JvDisplaySettings.TypeOfDisplayBorder.WIDTH ),
            JvDisplaySettings.getResizeFromDisplay( 0.03,
                    JvDisplaySettings.TypeOfDisplayBorder.HEIGHT ));

    public JvAuthPasswordField( String text ) {

        setIcon( "JvUiComponents/JvAuthComponents/src/main/java/org/authuicomponents/resources/eye.png" );

        passwordFieldWithButtonsPanel = new JPanel( new FlowLayout(
                SwingConstants.LEADING, 0, 0) );
        passwordFieldWithButtonsPanel.setPreferredSize( dim );
        passwordField = new JPasswordField();
        Dimension calcNewDim = new Dimension( (int) dim.getWidth() - visibleImage.getWidth(),
                (int) dim.getHeight() );
        passwordField.setPreferredSize( calcNewDim );
        passwordFieldWithButtonsPanel.add( passwordField );

        addButtonToPanel( passwordFieldWithButtonsPanel );

        passwordFieldWithButtonsPanel.setBackground( passwordField.getBackground() );
        passwordFieldWithButtonsPanel.setBorder( null );

        passwordField.setBorder( null );
        passwordField.setText( text );
        passwordField.setFont( new Font( "Times", Font.BOLD, 14 ) );
        passwordField.setForeground( Color.GRAY );
        passwordField.setFocusable( false );
        passwordField.setEchoChar( ( char ) 0 );

        addListenerToElem();
        add( passwordFieldWithButtonsPanel );
    }

    private void setIcon( String path ) {
        try {
            visibleImage = ImageIO.read( new File( path ) );
        } catch (IOException ex) {
            System.out.println( "Нет иконки глазка" );
        }
    }

    private final void addButtonToPanel( JPanel panel ) {
        button = new JButton( new ImageIcon( visibleImage ) );
        button.setContentAreaFilled( false );
        button.setBorderPainted( false );
        button.setMargin( new Insets ( 0,0,0,0 ) );
        button.setEnabled( false );
        button.setPreferredSize( new Dimension ( visibleImage.getWidth(),
               visibleImage.getHeight() ) );
        panel.add( button );
    }

    private void addListenerToElem() {
        button.addMouseListener( new MouseAdapter() {
            public void mouseClicked( MouseEvent e ) {
                if ( flagEye == true ) {
                    passwordField.setEchoChar( '•' );
                    flagEye = false;
                } else {
                    passwordField.setEchoChar( ( char ) 0 );
                    flagEye = true;
                }
            }
        } );

        passwordField.addMouseListener( new MouseAdapter() {
            public void mouseClicked( MouseEvent e ) {
                passwordField.setFocusable( true );
                passwordField.setForeground( Color.BLACK );
                passwordField.setText( "" );
                passwordField.setEchoChar( '•' );
                passwordField.requestFocusInWindow();
                passwordField.removeMouseListener( this );
                button.setEnabled( true );
            }
        } );
    }
}
