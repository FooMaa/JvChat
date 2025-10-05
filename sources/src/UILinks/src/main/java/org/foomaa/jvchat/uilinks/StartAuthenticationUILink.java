package org.foomaa.jvchat.uilinks;

import org.foomaa.jvchat.uicomponents.auth.GetterAuthUIComponents;


public class StartAuthenticationUILink {
    StartAuthenticationUILink() {
        GetterAuthUIComponents.getInstance().getBeanMainFrameAuthUI().openWindow();
    }
}