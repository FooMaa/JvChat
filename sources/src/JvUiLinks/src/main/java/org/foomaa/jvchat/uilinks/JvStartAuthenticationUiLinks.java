package org.foomaa.jvchat.uilinks;

import org.foomaa.jvchat.uicomponents.auth.JvGetterAuthUiComponents;


public class JvStartAuthenticationUiLinks {
    private static JvStartAuthenticationUiLinks instance;

    private JvStartAuthenticationUiLinks() {
        JvGetterAuthUiComponents.getInstance().getBeanAuthEntryFrame().openWindow();
    }

    public static JvStartAuthenticationUiLinks getInstance() {
        if (instance == null) {
            instance = new JvStartAuthenticationUiLinks();
        }
        return instance;
    }
}