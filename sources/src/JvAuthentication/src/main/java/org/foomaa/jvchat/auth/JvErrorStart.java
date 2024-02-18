package org.foomaa.jvchat.auth;

import org.foomaa.jvchat.uicomponents.auth.JvAuthOptionPane;

public class JvErrorStart {
    public JvErrorStart(String msg) {
        new JvAuthOptionPane(msg, JvAuthOptionPane.TypeDlg.ERROR);
        System.exit(1);
    }
}
