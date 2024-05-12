package org.foomaa.jvchat.uilinks;

import org.foomaa.jvchat.uicomponents.auth.JvAuthOptionPane;


public class JvErrorStart {
    JvErrorStart(String msg) {
        new JvAuthOptionPane(msg, JvAuthOptionPane.TypeDlg.ERROR);
        System.exit(1);
    }
}