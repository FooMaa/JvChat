package org.uicomponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JvAuthTextField extends JTextField {
    public JvAuthTextField ( String text ) {
        setText( text );
        setFocusable( false );
        setFont( new Font( "Times", Font.BOLD, 14 ) );
        setForeground( Color.GRAY );
        addListenerToElem();
    }

    private void addListenerToElem() {
        addMouseListener( new MouseAdapter() {
            public void mouseClicked( MouseEvent e ) {
                setFocusable( true );
                setForeground( Color.BLACK );
                setText( "" );
                requestFocusInWindow();
                removeMouseListener( this );
            }
        } );
    }
}
