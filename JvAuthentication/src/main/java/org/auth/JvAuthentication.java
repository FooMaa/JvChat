package org.auth;

import org.syssettings.JvSystemSettings;
import java.lang.Math;

public class JvAuthentication
{
    public JvAuthentication() {
        UiAuthenticationWindow authWin = new UiAuthenticationWindow();
        authWin.setSize( JvSystemSettings.getResizeFromDisplay( 0.3,
                        JvSystemSettings.TypeOfDisplayBorder.WIDTH ) ,
                JvSystemSettings.getResizeFromDisplay( 0.3,
                        JvSystemSettings.TypeOfDisplayBorder.HEIGHT ) );
        authWin.setResizable( false );
        authWin.requestFocusInWindow();
        authWin.setLocationRelativeTo( null );
        authWin.setVisible( true );
    }
}
