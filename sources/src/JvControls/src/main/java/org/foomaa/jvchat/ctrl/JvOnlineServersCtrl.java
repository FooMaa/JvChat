package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.globaldefines.JvMainChatsGlobalDefines;

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
            saveStatusOnline(userLogin, JvMainChatsGlobalDefines.TypeStatusOnline.Online);
        }
    }

    public void removeUsersOnline(String userLogin) {
        if (usersOnline.contains(userLogin)) {
            usersOnline.remove(userLogin);
            saveStatusOnline(userLogin, JvMainChatsGlobalDefines.TypeStatusOnline.Offline);
        }
    }

    public void saveStatusOnline(String userLogin, JvMainChatsGlobalDefines.TypeStatusOnline statusOnline) {
        int onlineStatusInteger = statusOnline.getValue();
        String onlineStatusString = String.valueOf(onlineStatusInteger);
        JvGetterControls.getInstance()
                .getBeanDbCtrl().insertQueryToDB(
                        JvDbCtrl.TypeExecutionInsert.OnlineUsersInfo,
                        userLogin,
                        onlineStatusString);
    }
}
