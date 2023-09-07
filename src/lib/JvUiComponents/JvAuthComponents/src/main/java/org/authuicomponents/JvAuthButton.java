package org.authuicomponents;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JvAuthButton extends JButton {
    public JvAuthButton ( String text ) {
        setText( text );
        setFocusable( false );
        addListenerToElements();
    }

    private void addListenerToElements() {
        addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                setFocusable( true );
                requestFocusInWindow();
            }
        } );
    }
}
