package org.foomaa.jvchat.uilinks;

import org.foomaa.jvchat.uicomponents.auth.JvGetterAuthUIComponents;


public class JvStartAuthenticationUILink {
    private static JvStartAuthenticationUILink instance;

    private JvStartAuthenticationUILink() {
        JvGetterAuthUIComponents.getInstance().getBeanEntryFrameAuthUI().openWindow();
    }

    public static JvStartAuthenticationUILink getInstance() {
        if (instance == null) {
            instance = new JvStartAuthenticationUILink();
        }
        return instance;
    }
}