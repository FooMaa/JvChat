package org.foomaa.jvchat.uicomponents.auth;

import javax.swing.*;

public class JvAuthOptionPane extends JOptionPane {
    private JFrame frame;

    public enum TypeDlg {
        ERROR
    }

    public JvAuthOptionPane(String msg, TypeDlg type) {
        switch (type) {
            case ERROR:
//                JOptionPane pane = new JOptionPane(msg, JOptionPane.ERROR_MESSAGE);
//                JDialog dlg = pane.createDialog(frame, "Ошибка");
//                dlg.setSize(1000,200);
//                dlg.show();
                showMessageDialog(frame, msg, "Ошибка", JOptionPane.ERROR_MESSAGE );
                break;
        }
    }
}
