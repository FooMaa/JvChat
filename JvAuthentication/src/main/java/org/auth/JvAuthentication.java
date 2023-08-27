package org.auth;

public class JvAuthentication
{
    public JvAuthentication() {
        System.out.println( "Hello World! lox" );
        UiAuthenticationWindow authWin = new UiAuthenticationWindow();
        authWin.setSize(500,200);
        authWin.setVisible(true);

    }
}
