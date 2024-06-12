package org.foomaa.jvchat.uilinks;

import org.foomaa.jvchat.uicomponents.auth.JvGetterAuthUiComponents;


public class JvStartAuthentication {
    private static JvStartAuthentication instance;

    private JvStartAuthentication() {
        JvGetterAuthUiComponents.getInstance().getBeanAuthEntryFrame().openWindow();
    }

    public static JvStartAuthentication getInstance() {
        if (instance == null) {
            instance = new JvStartAuthentication();
        }
        return instance;
    }
}