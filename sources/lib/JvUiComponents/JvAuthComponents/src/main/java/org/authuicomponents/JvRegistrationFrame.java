package org.authuicomponents;

import javax.swing.*;
import java.awt.*;

public class JvRegistrationFrame extends JFrame {
    public JvRegistrationFrame() {
        super( "RegistrationWindow" );

        setSize(new Dimension(100, 500));
        setResizable( false );
        setLocationRelativeTo( null );
        toFront();
        setVisible( true );
    }
}
