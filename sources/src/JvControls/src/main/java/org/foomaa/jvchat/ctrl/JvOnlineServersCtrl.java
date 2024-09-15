package org.foomaa.jvchat.ctrl;

import java.util.ArrayList;
import java.util.List;


public class JvOnlineServersCtrl {
    private static JvOnlineServersCtrl instance;
    private List<String> usersOnline;

    private JvOnlineServersCtrl() {
        usersOnline = new ArrayList<>();
    }

    static JvOnlineServersCtrl getInstance() {
        if (instance == null) {
            instance = new JvOnlineServersCtrl();
        }
        return instance;
    }

    public void addUsersOnline(String userLogin) {
        if (!usersOnline.contains(userLogin)) {
            usersOnline.add(userLogin);
        }
    }

    @Deprecated
    public void removeUsersOnline(String userLogin) {
        if (usersOnline.contains(userLogin)) {
            usersOnline.remove(userLogin);
        }
    }
}
