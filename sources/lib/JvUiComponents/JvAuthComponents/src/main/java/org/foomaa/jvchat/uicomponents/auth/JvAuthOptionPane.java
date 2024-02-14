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
                showMessageDialog(frame, msg, "Ошибка", JOptionPane.ERROR_MESSAGE );
                break;
        }
    }
}
