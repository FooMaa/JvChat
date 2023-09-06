package org.authuicomponents;
import org.syssettings.JvDisplaySettings;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class JvAuthTextField extends JPanel {
    private BufferedImage visibleImage;
    private static boolean flagEye = false;
    private JTextField textField;
    private JButton button;
    private JPanel textFieldWithButtonsPanel;
    private Dimension dim = new Dimension (JvDisplaySettings.getResizeFromDisplay( 0.23,
            JvDisplaySettings.TypeOfDisplayBorder.WIDTH ),
            JvDisplaySettings.getResizeFromDisplay( 0.03,
                    JvDisplaySettings.TypeOfDisplayBorder.HEIGHT ));
    public JvAuthTextField ( String text ) {
//        setIcon( "JvUiComponents/JvAuthComponents/src/main/java/org/authuicomponents/resources/eye.png" );

        textFieldWithButtonsPanel = new JPanel(  new FlowLayout(
                SwingConstants.LEADING, 0, 0) );
        textFieldWithButtonsPanel.setPreferredSize( dim );
        textField = new JTextField();
        textField.setPreferredSize( dim );

        textFieldWithButtonsPanel.add(textField);
        textFieldWithButtonsPanel.setBackground( textField.getBackground() );
        textFieldWithButtonsPanel.setBorder( null );

        textField.setBorder( null );
        textField.setText( text );
        textField.setFont( new Font( "Times", Font.BOLD, 14 ) );
        textField.setForeground( Color.GRAY );
        textField.setFocusable( false );

        addListenerToElem();
        add( textFieldWithButtonsPanel );
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
        panel.add( button );
    }

    private void addListenerToElem() {
        textField.addMouseListener( new MouseAdapter() {
            public void mouseClicked( MouseEvent e ) {
                textField.setFocusable( true );
                textField.setForeground( Color.BLACK );
                textField.setText( "" );
                textField.requestFocusInWindow();
                textField.removeMouseListener( this );
            }
        } );
    }
}