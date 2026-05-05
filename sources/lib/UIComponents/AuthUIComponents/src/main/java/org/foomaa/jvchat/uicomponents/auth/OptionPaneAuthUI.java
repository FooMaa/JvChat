package org.foomaa.jvchat.uicomponents.auth;

import javax.swing.*;

import lombok.Builder;

public class OptionPaneAuthUI extends JOptionPane {
    public enum TypeDlg {
        ERROR,
        WARNING
    }

    @Builder
    OptionPaneAuthUI() {}

    public void show(String msg, TypeDlg type) {
        switch (type) {
            case ERROR:
                JFrame frame = new JFrame();
                showMessageDialog(frame, msg, "Error", JOptionPane.ERROR_MESSAGE);
                break;
            case WARNING:
                break;
        }
    }
}
