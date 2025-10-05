package org.foomaa.jvchat.uilinks;

import org.foomaa.jvchat.uicomponents.auth.OptionPaneAuthUI;
import org.foomaa.jvchat.uicomponents.auth.GetterAuthUIComponents;


public class ErrorStartUILink {
    ErrorStartUILink(String msg) {
        GetterAuthUIComponents.getInstance().getBeanOptionPaneAuthUI(msg, OptionPaneAuthUI.TypeDlg.ERROR);
        System.exit(1);
    }
}