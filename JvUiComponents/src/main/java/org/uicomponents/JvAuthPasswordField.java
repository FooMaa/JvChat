package org.uicomponents;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class JvAuthPasswordField extends JPasswordField {
    private BufferedImage visibleImage;
    private static boolean flagEye = false;

    public JvAuthPasswordField(String text ) {
        setText( text );
        setFocusable( false );
        setFont( new Font( "Times", Font.BOLD, 14 ) );
        setEchoChar( ( char ) 0 );
        setForeground( Color.GRAY );
        try {
            visibleImage = ImageIO.read( new File("JvUiComponents/src/main/java/org/uicomponents/resources/eye.png") );
        } catch (IOException ex) {
            System.out.println( "Нет иконки глазка" );
            return;
        }
        addListenerToElem();
    }

    @Override
    protected void paintComponent( Graphics g ) {
        super.paintComponent( g );

        int y = ( getHeight() - visibleImage.getHeight() ) / 2;
        g.drawImage(visibleImage, this.getWidth() - 30, y, this);
        addMouseListener( new MouseAdapter() {
            public void mouseClicked( MouseEvent e ) {
                if ( flagEye == false ) {
                    setEchoChar( '•' );
                    flagEye = true;
                } else {
                    setEchoChar( ( char ) 0 );
                    flagEye = false;
                }
            }
        } );
    }

    private void addListenerToElem() {
        addMouseListener( new MouseAdapter() {
            public void mouseClicked( MouseEvent e ) {
                setFocusable( true );
                setForeground( Color.BLACK );
                setText( "" );
                setEchoChar( '•' );
                requestFocusInWindow();
                removeMouseListener( this );
            }
        } );
    }
}
