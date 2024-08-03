package org.foomaa.jvchat.ctrl;

public class JvChatsCtrl {
    private static JvChatsCtrl instance;

    private JvChatsCtrl() {}

    static JvChatsCtrl getInstance() {
        if (instance == null) {
            instance = new JvChatsCtrl();
        }
        return instance;
    }
}
