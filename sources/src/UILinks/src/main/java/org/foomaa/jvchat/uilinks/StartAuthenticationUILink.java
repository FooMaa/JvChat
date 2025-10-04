package org.foomaa.jvchat.uilinks;

import org.foomaa.jvchat.uicomponents.auth.JvGetterAuthUIComponents;


public class StartAuthenticationUILink {
    StartAuthenticationUILink() {
        JvGetterAuthUIComponents.getInstance().getBeanMainFrameAuthUI().openWindow();
    }
}