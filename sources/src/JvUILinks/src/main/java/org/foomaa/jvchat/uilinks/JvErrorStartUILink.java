package org.foomaa.jvchat.uilinks;

import org.foomaa.jvchat.uicomponents.auth.JvOptionPaneAuthUI;
import org.foomaa.jvchat.uicomponents.auth.JvGetterAuthUIComponents;


public class JvErrorStartUILink {
    JvErrorStartUILink(String msg) {
        JvGetterAuthUIComponents.getInstance().getBeanOptionPaneAuthUI(msg, JvOptionPaneAuthUI.TypeDlg.ERROR);
        System.exit(1);
    }
}