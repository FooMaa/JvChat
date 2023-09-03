package org.auth;

import org.syssettings.JvSystemSettings;
import java.lang.Math;

public class JvAuthentication
{
    public JvAuthentication() {
        System.out.println( "Hello World! lox" );
        UiAuthenticationWindow authWin = new UiAuthenticationWindow();
        authWin.setSize( ( int ) Math.round ( 0.25 * JvSystemSettings.widthScreen ) ,
                ( int ) Math.round( 0.25 * JvSystemSettings.heightScreen ) );
        authWin.setLocationRelativeTo(null);
        authWin.setVisible(true);
    }
}
