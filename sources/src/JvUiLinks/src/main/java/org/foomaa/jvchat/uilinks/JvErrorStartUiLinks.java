package org.foomaa.jvchat.uilinks;

import org.foomaa.jvchat.uicomponents.auth.JvAuthOptionPane;
import org.foomaa.jvchat.uicomponents.auth.JvGetterAuthUiComponents;


public class JvErrorStartUiLinks {
    JvErrorStartUiLinks(String msg) {
        JvGetterAuthUiComponents.getInstance().getBeanAuthOptionPane(msg, JvAuthOptionPane.TypeDlg.ERROR);
        System.exit(1);
    }
}