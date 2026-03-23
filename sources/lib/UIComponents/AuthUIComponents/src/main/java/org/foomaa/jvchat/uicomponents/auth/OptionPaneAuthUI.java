package org.foomaa.jvchat.uicomponents.auth;

import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;


@Component
@Scope("prototype")
@Profile("users")
public class OptionPaneAuthUI extends JOptionPane {
    public enum TypeDlg {
        ERROR,
        WARNING
    }

    OptionPaneAuthUI() {}

    public void show(String msg, TypeDlg type) {
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