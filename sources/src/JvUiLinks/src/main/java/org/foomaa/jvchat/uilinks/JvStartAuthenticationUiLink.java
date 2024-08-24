package org.foomaa.jvchat.uilinks;

import org.foomaa.jvchat.uicomponents.auth.JvGetterAuthUIComponents;


public class JvStartAuthenticationUiLink {
    private static JvStartAuthenticationUiLink instance;

    private JvStartAuthenticationUiLink() {
        JvGetterAuthUIComponents.getInstance().getBeanEntryFrameAuthUI().openWindow();
    }

    public static JvStartAuthenticationUiLink getInstance() {
        if (instance == null) {
            instance = new JvStartAuthenticationUiLink();
        }
        return instance;
    }
}