package org.foomaa.jvchat.uilinks;

import org.foomaa.jvchat.uicomponents.auth.JvGetterAuthUIComponents;


public class JvStartAuthenticationUILink {
    JvStartAuthenticationUILink() {
        JvGetterAuthUIComponents.getInstance().getBeanEntryFrameAuthUI().openWindow();
    }
}