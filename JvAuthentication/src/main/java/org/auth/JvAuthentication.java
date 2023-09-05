package org.auth;

import org.syssettings.JvDisplaySettings;

public class JvAuthentication
{
    public JvAuthentication() {
        UiAuthenticationWindow authWin = new UiAuthenticationWindow();
        authWin.setSize( JvDisplaySettings.getResizeFromDisplay( 0.3,
                        JvDisplaySettings.TypeOfDisplayBorder.WIDTH ) ,
                JvDisplaySettings.getResizeFromDisplay( 0.3,
                        JvDisplaySettings.TypeOfDisplayBorder.HEIGHT ) );
        authWin.setResizable( false );
        authWin.requestFocusInWindow();
        authWin.setLocationRelativeTo( null );
        authWin.setVisible( true );
    }
}
