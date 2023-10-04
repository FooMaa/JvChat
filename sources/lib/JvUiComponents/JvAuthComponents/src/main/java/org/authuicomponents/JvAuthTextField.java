package org.authuicomponents;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import org.syssettings.JvDisplaySettings;

public class JvAuthTextField extends JPanel {
    private BufferedImage visibleImage;
    private JButton button;
    private JTextField textField;
    private final JPanel textFieldWithButtonsPanel;
    private final int gap = 5;

    public JvAuthTextField ( String text ) {
        textFieldWithButtonsPanel = new JPanel( new FlowLayout(
                FlowLayout.LEADING, gap, 0 ) );
        settingTextAndButtonPanel( text );
        settingGeneralPanel( text );
    }

    private BufferedImage setIcon( String path ) {
        try {
            return ImageIO.read( new File( path ) );
        } catch ( IOException ex ) {
            System.out.println( "Нет иконки глазка" );
        }
        return null;
    }

    private void addButtonToPanel( JPanel panel ) {
        button = new JButton( new ImageIcon( visibleImage ) );
        button.setContentAreaFilled( false );
        button.setBorderPainted( false );
        button.setMargin( new Insets ( 0,0,0, gap ) );
        button.setEnabled( false );
        button.setFocusPainted( false );
        button.setPreferredSize( new Dimension ( visibleImage.getWidth() + gap,
                visibleImage.getHeight() ) );
        panel.add( button );
    }

    private void addListenerToElem( String text ) {
        textField.addMouseListener( new MouseAdapter() {
            @Override
            public void mouseClicked( MouseEvent e ) {
                textField.setFocusable( true );
                textField.setForeground( Color.BLACK );
                textField.setText( "" );
                textField.requestFocusInWindow();
                textField.removeMouseListener( this );
            }
        } );

        textField.addFocusListener( new FocusListener() {
            @Override
            public void focusGained( FocusEvent e )
            {
                if ( textField.getForeground() == Color.lightGray ) {
                    textField.setForeground( Color.BLACK );
                    textField.setText( "" );
                }
            }
            @Override
            public void focusLost( FocusEvent e ) {
                if ( Objects.equals( textField.getText(), "" ) ) {
                    textField.setForeground( Color.lightGray );
                    textField.setText( text );
                }
            }
        } );
    }

    private void settingTextAndButtonPanel( String text ) {
        Dimension dim = new Dimension( JvDisplaySettings.
                getResizeFromDisplay( 0.23,
                JvDisplaySettings.TypeOfDisplayBorder.WIDTH ),
                JvDisplaySettings.getResizeFromDisplay( 0.03,
                        JvDisplaySettings.TypeOfDisplayBorder.HEIGHT) );
        textFieldWithButtonsPanel.setPreferredSize( dim );
        makeTextField( dim );

        textFieldWithButtonsPanel.add( textField );
        textFieldWithButtonsPanel.setBackground( textField.getBackground() );
        textFieldWithButtonsPanel.setBorder( null );

        settingTextField( text );
    }

    private void makeTextField( Dimension dim ) {
        textField = new JTextField();
        textField.setPreferredSize( dim );
    }

    private void settingTextField( String text ) {
        textField.setBorder( null );
        textField.setText( text );
        textField.setFont( new Font( "Times", Font.BOLD, 14 ) );
        textField.setForeground( Color.lightGray );
        textField.setFocusable( false );
    }

    private void settingGeneralPanel( String text ) {
        addListenerToElem( text );
        add( textFieldWithButtonsPanel );
    }
}