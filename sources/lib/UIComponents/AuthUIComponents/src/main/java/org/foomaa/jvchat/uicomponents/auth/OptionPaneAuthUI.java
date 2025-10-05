package org.foomaa.jvchat.uicomponents.auth;

import javax.swing.*;


public class OptionPaneAuthUI extends JOptionPane {
    public enum TypeDlg {
        ERROR,
        WARNING
    }

    OptionPaneAuthUI(String msg, TypeDlg type) {
        switch (type) {
            case ERROR:
                JFrame frame = new JFrame();
                showMessageDialog(frame, msg,
                        "Error", JOptionPane.ERROR_MESSAGE );
                break;
            case WARNING:
                break;
        }
    }
}