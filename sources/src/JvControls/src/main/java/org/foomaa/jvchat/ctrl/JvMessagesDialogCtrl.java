package org.foomaa.jvchat.ctrl;


public class JvMessagesDialogCtrl {
    private static JvMessagesDialogCtrl instance;

    private JvMessagesDialogCtrl() {}

    static JvMessagesDialogCtrl getInstance() {
        if (instance == null) {
            instance = new JvMessagesDialogCtrl();
        }
        return instance;
    }


}
