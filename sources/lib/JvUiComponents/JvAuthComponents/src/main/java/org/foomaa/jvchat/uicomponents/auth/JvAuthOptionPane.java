package org.foomaa.jvchat.uicomponents.auth;

import javax.swing.*;

public class JvAuthOptionPane extends JOptionPane {

    public enum TypeDlg {
        ERROR,
        WARNING
    }

    public JvAuthOptionPane(String msg, TypeDlg type) {
        switch (type) {
            case ERROR:
                JFrame frame = new JFrame();
                showMessageDialog(frame, msg,
                        "Ошибка", JOptionPane.ERROR_MESSAGE );
                break;
            case WARNING:
                break;
        }
    }
}
